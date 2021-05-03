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
        if((flags & FLAG_MAX_DEPTH) == FLAG_MAX_DEPTH){
            array = src.array.clone();

        }else if((flags & FLAG_MINIMAL) == FLAG_MINIMAL){
            this.array = src.array;

        }else{
            throw new RuntimeException("Invalid flags : " + flags);
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