package vanguard;

public class VLSyncSeries<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncMap>>{

    public VLSyncSeries(int capacity, int resizer){
        super(new VLListType<>(capacity, resizer));
    }

    public VLSyncSeries(VLSyncMap<?, ?>[] array){
        super(new VLListType<>(array, 0));
    }

    public VLSyncSeries(VLSyncSeries<SOURCE> src, long flags){
        super(null);
        copy(src, flags);
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

        if((flags & FLAG_MINIMAL) == FLAG_MINIMAL){
            target = syncer.target;

        }else if((flags & FLAG_SHALLOW_ENTRIES) == FLAG_SHALLOW_ENTRIES){
            VLListType<VLSyncMap> entries = syncer.target;
            target = new VLListType<>((VLSyncMap[])entries.array().clone(), entries.resizerCount());

        }else if((flags & FLAG_MAX_DEPTH) == FLAG_MAX_DEPTH){
            VLListType<VLSyncMap> entries = syncer.target;
            target = new VLListType<>(entries.realSize(), entries.resizerCount());
            target.maximizeVirtualSize();

            int size = target.size();

            for(int i = 0; i < size; i++){
                target.set(i, (VLSyncMap)entries.get(i).duplicate(FLAG_MAX_DEPTH));
            }

        }else{
            throw new RuntimeException("Invalid flags : " + flags);
        }
    }

    @Override
    public VLSyncSeries<SOURCE> duplicate(long flags){
        return new VLSyncSeries<>(this, flags);
    }
}
