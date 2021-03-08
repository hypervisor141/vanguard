package vanguard;

public class VLSyncSeries<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncMap>>{

    public VLSyncSeries(int capacity, int resizer){
        super(new VLListType<>(capacity, resizer));
    }

    public VLSyncSeries(VLSyncMap<?, ?>[] array){
        super(new VLListType<>(array, 0));
    }

    @Override
    public void sync(SOURCE source){
        int size = target.size();
        target.get(0).sync(source);

        for(int i = 1; i < size; i++){
            target.get(i).sync(target.get(i - 1).target);
        }
    }
}
