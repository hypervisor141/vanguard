package vanguard;

import java.util.Arrays;

public class VLArrayLong extends VLArray<Long, long[]>{

    public VLArrayLong(long[] s) {
        super(s);
    }

    public VLArrayLong(int size) {
        super(new long[size]);
    }


    @Override
    public void set(int index, Long element) {
        array[index] = element;
    }

    @Override
    public Long get(int index) {
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
    public void stringify(StringBuilder src, Object hint){
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}