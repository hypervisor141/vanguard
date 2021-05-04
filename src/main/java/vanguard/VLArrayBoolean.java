package vanguard;

import java.util.Arrays;

public class VLArrayBoolean extends VLArray<Boolean, boolean[]>{

    public VLArrayBoolean(boolean[] s) {
        super(s);
    }

    public VLArrayBoolean(int size) {
        super(new boolean[size]);
    }

    public VLArrayBoolean(VLArrayBoolean src, long flags){
        copy(src, flags);
    }

    @Override
    public void set(int index, Boolean element) {
        array[index] = element;
    }

    @Override
    public Boolean get(int index) {
        return array[index];
    }

    @Override
    public void resize(int size) {
        array = VLArrayUtils.resize(array, size);
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public void copy(VLArray<Boolean, boolean[]> src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            array = src.array;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            array = src.array.clone();

        }else{
            Helper.throwMissingFlags(new String[]{ "FLAG_REFERENCE", "FLAG_DUPLICATE" });
        }
    }

    @Override
    public VLArrayBoolean duplicate(long flags){
        return new VLArrayBoolean(this, flags);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}