package vanguard;

public class VLSyncParallel<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncType<SOURCE>>>{

    public static final int DEPTH_SHALLOW_ENTRIES = 1;

    public VLSyncParallel(int capacity, int resizer){
        super(new VLListType<>(capacity, resizer));
    }

    public VLSyncParallel(VLSyncType<SOURCE>[] array){
        super(new VLListType<>(array, 0));
    }

    public VLSyncParallel(VLSyncParallel<SOURCE> src, int depth){
        super(null);
        copy(src, depth);
    }

    @Override
    public void sync(SOURCE source){
        int size = target.size();

        for(int i = 0; i < size; i++){
            target.get(i).sync(source);
        }
    }

    @Override
    public void copy(VLSyncType<SOURCE> src, int depth){
        VLSyncParallel<SOURCE> syncer = (VLSyncParallel<SOURCE>)src;

        if(depth == DEPTH_MIN){
            target = syncer.target;

        }else if(depth == DEPTH_SHALLOW_ENTRIES){
            VLListType<VLSyncType<SOURCE>> entries = syncer.target;
            target = new VLListType<>((VLSyncType<SOURCE>[])entries.array().clone(), entries.resizerCount());

        }else if(depth == DEPTH_MAX){
            VLListType<VLSyncType<SOURCE>> entries = syncer.target;
            target = new VLListType<>(entries.realSize(), entries.resizerCount());
            target.maximizeVirtualSize();

            int size = target.size();

            for(int i = 0; i < size; i++){
                target.set(i, entries.get(i));
            }

        }else{
            throw new RuntimeException("Invalid depth : " + depth);
        }
    }

    @Override
    public VLSyncParallel<SOURCE> duplicate(int depth){
        return new VLSyncParallel<>(this, depth);
    }
}
