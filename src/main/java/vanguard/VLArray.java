package vanguard;

public abstract class VLArray<TYPE, PROVIDER> implements VLStringify{

    protected PROVIDER array;

    public VLArray(PROVIDER array) {
        this.array = array;
    }

    public void transform(int index, VLVMatrix obj, boolean replace){

    }

    public abstract void set(int index, TYPE obj);

    public void provider(PROVIDER array) {
        this.array = array;
    }

    public abstract void resize(int size);

    public abstract int size();

    public abstract TYPE get(int index);

    public PROVIDER provider() {
        return array;
    }

    @Override
    public void stringify(StringBuilder src, Object hint) {
        src.append("size[");
        src.append(size());
        src.append("]");
    }
}
