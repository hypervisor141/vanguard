package vanguard;

import java.util.Arrays;

public class VLArrayBoolean extends VLArray<Boolean, boolean[]>{

    public VLArrayBoolean(boolean[] s) {
        super(s);
    }

    public VLArrayBoolean(int size) {
        super(new boolean[size]);
    }

    public VLArrayBoolean(VLArrayBoolean src, int depth){
        copy(src, depth);
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
    public void copy(VLArray<Boolean, boolean[]> src, int depth){
        if(depth == DEPTH_MIN){
            this.array = src.array;

        }else if(depth == DEPTH_MAX){
            array = src.array.clone();

        }else{
            throw new RuntimeException("Invalid depth : " + depth);
        }
    }

    @Override
    public VLArrayBoolean duplicate(int depth){
        return new VLArrayBoolean(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}