package hypervisor.vanguard.sync;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.list.arraybacked.VLListType;

public class VLSyncParallel<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncType<SOURCE>>>{

    public static final long FLAG_REFERENCE_ENTRIES = 0x1L;
    public static final long FLAG_DUPLICATE_ENTRIES = 0x2L;

    public VLSyncParallel(int capacity, int resizeoverhead){
        super(new VLListType<>(capacity, resizeoverhead));
    }

    public VLSyncParallel(VLSyncType<SOURCE>[] array){
        super(new VLListType<>(array, 0));
    }

    public VLSyncParallel(VLSyncParallel<SOURCE> src, long flags){
        copy(src, flags);
    }

    protected VLSyncParallel(){

    }

    @Override
    public void sync(SOURCE source){
        int size = target.size();

        for(int i = 0; i < size; i++){
            target.get(i).sync(source);
        }
    }

    @Override
    public void copy(VLSyncType<SOURCE> src, long flags){
        VLSyncParallel<SOURCE> syncer = (VLSyncParallel<SOURCE>)src;

        if((flags & VLCopyable.FLAG_REFERENCE) == VLCopyable.FLAG_REFERENCE){
            target = syncer.target;

        }else if((flags & VLCopyable.FLAG_DUPLICATE) == VLCopyable.FLAG_DUPLICATE){
            target = syncer.target.duplicate(VLListType.FLAG_DUPLICATE);

        }else if((flags & VLCopyable.FLAG_CUSTOM) == VLCopyable.FLAG_CUSTOM){
            if((flags & FLAG_REFERENCE_ENTRIES) == FLAG_REFERENCE_ENTRIES){
                target = syncer.target.duplicate(VLCopyable.FLAG_REFERENCE);

            }else if((flags & FLAG_DUPLICATE_ENTRIES) == FLAG_DUPLICATE_ENTRIES){
                target = syncer.target.duplicate(VLCopyable.FLAG_DUPLICATE);

            }else{
                VLCopyable.Helper.throwMissingSubFlags("FLAG_CUSTOM", "FLAG_REFERENCE_ENTRIES", "FLAG_DUPLICATE_ENTRIES");
            }

        }else{
            VLCopyable.Helper.throwMissingAllFlags();
        }
    }

    @Override
    public VLSyncParallel<SOURCE> duplicate(long flags){
        return new VLSyncParallel<>(this, flags);
    }
}
