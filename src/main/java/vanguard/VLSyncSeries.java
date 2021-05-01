package vanguard;

public class VLSyncSeries<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncMap>>{

    public static final int DEPTH_SHALLOW_ENTRIES = 1;

    public VLSyncSeries(int capacity, int resizer){
        super(new VLListType<>(capacity, resizer));
    }

    public VLSyncSeries(VLSyncMap<?, ?>[] array){
        super(new VLListType<>(array, 0));
    }

    public VLSyncSeries(VLSyncSeries<SOURCE> src, int depth){
        super(null);
        copy(src, depth);
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
    public void copy(VLSyncType<SOURCE> src, int depth){
        VLSyncSeries<SOURCE> syncer = (VLSyncSeries<SOURCE>)src;

        if(depth == DEPTH_MIN){
            target = syncer.target;

        }else if(depth == DEPTH_SHALLOW_ENTRIES){
            VLListType<VLSyncMap> entries = syncer.target;
            target = new VLListType<>((VLSyncMap[])entries.array().clone(), entries.resizerCount());

        }else if(depth == DEPTH_MAX){
            VLListType<VLSyncMap> entries = syncer.target;
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
    public VLSyncSeries<SOURCE> duplicate(int depth){
        return new VLSyncSeries<>(this, depth);
    }
}
