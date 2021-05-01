package vanguard;

import java.util.Arrays;

public class VLArrayInt extends VLArray<Integer, int[]> {

    public VLArrayInt(int[] s) {
        super(s);
    }

    public VLArrayInt(int size) {
        super(new int[size]);
    }

    public VLArrayInt(VLArrayInt src, int depth){
        copy(src, depth);
    }

    @Override
    public void set(int index, Integer element) {
        array[index] = element;
    }

    @Override
    public Integer get(int index) {
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
    public void copy(VLArray<Integer, int[]> src, int depth){
        if(depth == DEPTH_MIN){
            this.array = src.array;

        }else if(depth == DEPTH_MAX){
            array = src.array.clone();

        }else{
            throw new RuntimeException("Invalid depth : " + depth);
        }
    }

    @Override
    public VLArrayInt duplicate(int depth){
        return new VLArrayInt(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint) {
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}
