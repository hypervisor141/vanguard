package vanguard.sync;

public abstract class VLSyncMap<SOURCE, TARGET> implements VLSyncType<SOURCE>{

    protected TARGET target;

    public VLSyncMap(TARGET target){
        this.target = target;
    }

    protected VLSyncMap(){

    }

    public TARGET target(){
        return target;
    }

    @Override
    public void copy(VLSyncType<SOURCE> src, long flags){
        target = ((VLSyncMap<SOURCE, TARGET>)src).target;
    }

    @Override
    public abstract VLSyncType<SOURCE> duplicate(long flags);
}
