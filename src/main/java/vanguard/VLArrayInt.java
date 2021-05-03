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
        if((flags & FLAG_MAX_DEPTH) == FLAG_MAX_DEPTH){
            array = src.array.clone();

        }else if((flags & FLAG_MINIMAL) == FLAG_MINIMAL){
            this.array = src.array;

        }else{
            throw new RuntimeException("Invalid flags : " + flags);
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
