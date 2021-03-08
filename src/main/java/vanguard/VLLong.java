package vanguard;

public class VLLong implements VLStringify{

    private long field;

    public VLLong(int v) {
        field = v;
    }

    public VLLong() {

    }

    public void set(long field) {
        this.field = field;
    }

    public long get() {
        return field;
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("long[");
        src.append(field);
        src.append("]");
    }
}
