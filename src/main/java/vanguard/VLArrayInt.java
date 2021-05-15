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

    protected VLArrayInt(){

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
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            array = src.array;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            array = src.array.clone();

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public VLArrayInt duplicate(long flags){
        return new VLArrayInt(this, flags);
    }

    @Override
    public void log(VLLog log, Object data) {
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(array));
        log.append("]");
    }
}
