package vanguard;

public abstract class VLSyncMap<SOURCE, TARGET> implements VLSyncType<SOURCE>{

    protected TARGET target;

    public VLSyncMap(TARGET target){
        this.target = target;
    }

    public TARGET target(){
        return target;
    }
}
