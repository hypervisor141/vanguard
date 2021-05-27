package hypervisor.vanguard.array;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;

import java.util.Arrays;

public class VLArrayDouble extends VLArray<Double, double[]> {

    public VLArrayDouble(double[] s) {
        super(s);
    }

    public VLArrayDouble(int size) {
        super(new double[size]);
    }

    public VLArrayDouble(VLArrayDouble src, long flags){
        copy(src, flags);
    }

    protected VLArrayDouble(){

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
    public void copy(VLArray<Double, double[]> src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            array = src.array;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            array = src.array.clone();

        }else{
            VLCopyable.Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public VLArrayDouble duplicate(long flags){
        return new VLArrayDouble(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(array));
        log.append("]");
    }
}