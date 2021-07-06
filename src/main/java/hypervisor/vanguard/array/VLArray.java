package hypervisor.vanguard.array;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;
import hypervisor.vanguard.utils.VLLoggable;
import hypervisor.vanguard.variable.VLVMatrix;

public abstract class VLArray<TYPE, PROVIDER> implements VLArrayType, VLLoggable, VLCopyable<VLArray<TYPE, PROVIDER>> {
    
    public PROVIDER array;

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
    public void log(VLLog log, Object data){
        log.append("provider[");
        log.append(array.getClass().getSimpleName());
        log.append("] size[");
        log.append(size());
        log.append("]");
    }
}
