package vanguard.array;

import vanguard.utils.VLCopyable;
import vanguard.utils.VLLog;
import vanguard.utils.VLLoggable;
import vanguard.variable.VLVMatrix;

public abstract class VLArray<TYPE, PROVIDER> implements VLArrayType, VLLoggable, VLCopyable<VLArray<TYPE, PROVIDER>> {
    
    protected PROVIDER array;

    public VLArray(PROVIDER array) {
        this.array = array;
    }

    protected VLArray(){

    }

    public void transform(int index, VLVMatrix matrix, boolean replace){

    }

    public abstract void set(int index, TYPE obj);

    public void provider(PROVIDER array) {
        this.array = array;
    }

    public abstract TYPE get(int index);

    @Override
    public PROVIDER provider(){
        return array;
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("provider[");
        log.append(array.getClass().getSimpleName());
        log.append("] size[");
        log.append(size());
        log.append("]");
    }
}
