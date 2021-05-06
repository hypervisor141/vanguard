package vanguard;

public class VLSyncSeries<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncMap>>{

    public static final long FLAG_FORCE_REFERENCE_ENTRIES = 0x1L;
    public static final long FLAG_FORCE_DUPLICATE_ENTRIES = 0x2L;

    public VLSyncSeries(int capacity, int resizer){
        super(new VLListType<>(capacity, resizer));
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

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            target = syncer.target;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            target = syncer.target.duplicate(VLListType.FLAG_DUPLICATE);

        }else if((flags & FLAG_CUSTOM) == FLAG_CUSTOM){
            if((flags & FLAG_FORCE_REFERENCE_ENTRIES) == FLAG_FORCE_REFERENCE_ENTRIES){
                target = syncer.target.duplicate(FLAG_CUSTOM | VLListType.FLAG_FORCE_REFERENCE_ARRAY);

            }else if((flags & FLAG_FORCE_DUPLICATE_ENTRIES) == FLAG_FORCE_DUPLICATE_ENTRIES){
                target = syncer.target.duplicate(FLAG_CUSTOM | VLListType.FLAG_FORCE_DUPLICATE_ARRAY);

            }else{
                Helper.throwMissingSubFlags("FLAG_CUSTOM", "FLAG_FORCE_REFERENCE_ENTRIES", "FLAG_FORCE_DUPLICATE_ENTRIES");
            }

        }else{
            Helper.throwMissingAllFlags();
        }
    }

    @Override
    public VLSyncSeries<SOURCE> duplicate(long flags){
        return new VLSyncSeries<>(this, flags);
    }
}
