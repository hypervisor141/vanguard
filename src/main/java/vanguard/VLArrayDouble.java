package vanguard;

import java.util.Arrays;

public class VLArrayDouble extends VLArray<Double, double[]> {

    public VLArrayDouble(double[] s) {
        super(s);
    }

    public VLArrayDouble(int size) {
        super(new double[size]);
    }

    public VLArrayDouble(VLArrayDouble src, int depth){
        copy(src, depth);
    }

    @Override
    public void set(int index, Double element) {
        array[index] = element;
    }

    @Override
    public Double get(int index) {
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
    public void copy(VLArray<Double, double[]> src, int depth){
        if(depth == DEPTH_MIN){
            this.array = src.array;

        }else if(depth == DEPTH_MAX){
            array = src.array.clone();

        }else{
            throw new RuntimeException("Invalid depth : " + depth);
        }
    }

    @Override
    public VLArrayDouble duplicate(int depth){
        return new VLArrayDouble(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}