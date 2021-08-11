package hypervisor.vanguard.sync;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.list.arraybacked.VLListType;

public class VLSyncSeries<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncMap>>{

    public static final long FLAG_REFERENCE_ENTRIES = 0x1L;
    public static final long FLAG_DUPLICATE_ENTRIES = 0x2L;

    public VLSyncSeries(int capacity, int resizeoverhead){
        super(new VLListType<>(capacity, resizeoverhead));
    }

    public VLSyncSeries(VLSyncMap<?, ?>[] array){
        super(new VLListType<>(array, 0));
    }

    public VLSyncSeries(VLSyncSeries<SOURCE> src, long flags){
        copy(src, flags);
    }

    protected VLSyncSeries(){

    }

    @Override
    public void sync(SOURCE source){
        int size = target.size();
        target.get(0).sync(source);

        for(int i = 1; i < size; i++){
            target.get(i).sync(target.get(i - 1).target);
        }
    }

    @Override
    public void copy(VLSyncType<SOURCE> src, long flags){
        VLSyncSeries<SOURCE> syncer = (VLSyncSeries<SOURCE>)src;

        if((flags & VLCopyable.FLAG_REFERENCE) == VLCopyable.FLAG_REFERENCE){
            target = syncer.target;

        }else if((flags & VLCopyable.FLAG_DUPLICATE) == VLCopyable.FLAG_DUPLICATE){
            target = syncer.target.duplicate(VLListType.FLAG_DUPLICATE);

        }else if((flags & VLCopyable.FLAG_CUSTOM) == VLCopyable.FLAG_CUSTOM){
            if((flags & FLAG_REFERENCE_ENTRIES) == FLAG_REFERENCE_ENTRIES){
                target = syncer.target.duplicate(VLCopyable.FLAG_CUSTOM | VLListType.FLAG_DUPLICATE_ARRAY_BUT_REFERENCE_ELEMENTS);

            }else if((flags & FLAG_DUPLICATE_ENTRIES) == FLAG_DUPLICATE_ENTRIES){
                target = syncer.target.duplicate(VLCopyable.FLAG_CUSTOM | VLListType.FLAG_DUPLICATE_ARRAY_FULLY);

            }else{
                VLCopyable.Helper.throwMissingSubFlags("FLAG_CUSTOM", "FLAG_REFERENCE_ENTRIES", "FLAG_DUPLICATE_ENTRIES");
            }

        }else{
            VLCopyable.Helper.throwMissingAllFlags();
        }
    }

    @Override
    public VLSyncSeries<SOURCE> duplicate(long flags){
        return new VLSyncSeries<>(this, flags);
    }
}
