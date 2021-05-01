package vanguard;

import java.util.Arrays;

public class VLArrayFloat extends VLArray<Float, float[]> {

    public VLArrayFloat(float[] s) {
        super(s);
    }

    public VLArrayFloat(int size) {
        super(new float[size]);
    }

    public VLArrayFloat(VLArrayFloat src, int depth){
        copy(src, depth);
    }

    @Override
    public void set(int index, Float element) {
        array[index] = element;
    }

    @Override
    public Float get(int index) {
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
    public void copy(VLArray<Float, float[]> src, int depth){
        if(depth == DEPTH_MIN){
            this.array = src.array;

        }else if(depth == DEPTH_MAX){
            array = src.array.clone();

        }else{
            throw new RuntimeException("Invalid depth : " + depth);
        }
    }

    @Override
    public VLArrayFloat duplicate(int depth){
        return new VLArrayFloat(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint) {
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}
