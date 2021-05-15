package vanguard;

import java.util.Arrays;

public class VLArrayFloat extends VLArray<Float, float[]>{

    public VLArrayFloat(float[] s) {
        super(s);
    }

    public VLArrayFloat(int size) {
        super(new float[size]);
    }

    public VLArrayFloat(VLArrayFloat src, long flags){
        copy(src, flags);
    }

    protected VLArrayFloat(){

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
    public void copy(VLArray<Float, float[]> src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            array = src.array;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            array = src.array.clone();

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public VLArrayFloat duplicate(long flags){
        return new VLArrayFloat(this, flags);
    }

    @Override
    public void log(VLLog log, Object data) {
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(array));
        log.append("]");
    }
}
