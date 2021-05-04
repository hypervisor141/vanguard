package vanguard;

public class VLSyncParallel<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncType<SOURCE>>>{

    public static final long FLAG_FORCE_REFERENCE_ENTRIES = 0x1L;
    public static final long FLAG_FORCE_DUPLICATE_ENTRIES = 0x2L;

    public VLSyncParallel(int capacity, int resizer){
        super(new VLListType<>(capacity, resizer));
    }

    public VLSyncParallel(VLSyncType<SOURCE>[] array){
        super(new VLListType<>(array, 0));
    }

    public VLSyncParallel(VLSyncParallel<SOURCE> src, long flags){
        super(null);
        copy(src, flags);
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
                Helper.throwMissingFlag("FLAG_CUSTOM", "FLAG_FORCE_REFERENCE_ENTRIES", "FLAG_FORCE_DUPLICATE_ENTRIES");
            }

        }else{
            Helper.throwMissingBaseFlags();
        }
    }

    @Override
    public VLSyncParallel<SOURCE> duplicate(long flags){
        return new VLSyncParallel<>(this, flags);
    }
}
