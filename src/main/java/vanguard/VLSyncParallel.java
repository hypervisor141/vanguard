package vanguard;

public class VLSyncParallel<SOURCE> extends VLSyncMap<SOURCE, VLListType<VLSyncType<SOURCE>>>{

    public VLSyncParallel(int capacity, int resizer){
        super(new VLListType<>(capacity, resizer));
    }

    public VLSyncParallel(VLSyncType<SOURCE>[] array){
        super(new VLListType<>(array, 0));
    }

    @Override
    public void sync(SOURCE source){
        int size = target.size();

        for(int i = 0; i < size; i++){
            target.get(i).sync(source);
        }
    }
}
