package vanguard;

import java.util.Arrays;

public class VLArrayInt extends VLArray<Integer, int[]> {

    public VLArrayInt(int[] s) {
        super(s);
    }

    public VLArrayInt(int size) {
        super(new int[size]);
    }

    public VLArrayInt(VLArrayInt src, long flags){
        copy(src, flags);
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
    public void copy(VLArray<Integer, int[]> src, long flags){
        if((flags & FLAG_SHALLOW_COPY) == FLAG_SHALLOW_COPY){
            this.array = src.array;

        }else if((flags & FLAG_DEEP_COPY) == FLAG_DEEP_COPY){
            array = src.array.clone();

        }else{
            throw new RuntimeException("Invalid depth : " + flags);
        }
    }

    @Override
    public VLArrayInt duplicate(long flags){
        return new VLArrayInt(this, flags);
    }

    @Override
    public void stringify(StringBuilder src, Object hint) {
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}
