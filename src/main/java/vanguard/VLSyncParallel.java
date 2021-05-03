package vanguard;

public class VLSyncParallel<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncType<SOURCE>>>{

    public static final long FLAG_SHALLOW_ENTRIES = 0x2L;

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

        if((flags & FLAG_SHALLOW_COPY) == FLAG_SHALLOW_COPY){
            target = syncer.target;

        }else if((flags & FLAG_SHALLOW_ENTRIES) == FLAG_SHALLOW_ENTRIES){
            VLListType<VLSyncType<SOURCE>> entries = syncer.target;
            target = new VLListType<>((VLSyncType<SOURCE>[])entries.array().clone(), entries.resizerCount());

        }else if((flags & FLAG_DEEP_COPY) == FLAG_DEEP_COPY){
            VLListType<VLSyncType<SOURCE>> entries = syncer.target;
            target = new VLListType<>(entries.realSize(), entries.resizerCount());
            target.maximizeVirtualSize();

            int size = target.size();

            for(int i = 0; i < size; i++){
                target.set(i, entries.get(i));
            }

        }else{
            throw new RuntimeException("Invalid depth : " + flags);
        }
    }

    @Override
    public VLSyncParallel<SOURCE> duplicate(long flags){
        return new VLSyncParallel<>(this, flags);
    }
}
