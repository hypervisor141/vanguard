package vanguard;

public abstract class VLArray<TYPE, PROVIDER> implements VLArrayType, VLStringify, VLCopyable<VLArray<TYPE, PROVIDER>>{
    
    protected PROVIDER array;

    public VLArray(PROVIDER array) {
        this.array = array;
    }

    protected VLArray(){

    }

    public void transform(int index, VLVMatrix obj, boolean replace){

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
    public void stringify(StringBuilder src, Object hint){
        src.append("provider[");
        src.append(array.getClass().getSimpleName());
        src.append("] size[");
        src.append(size());
        src.append("]");
    }
}
