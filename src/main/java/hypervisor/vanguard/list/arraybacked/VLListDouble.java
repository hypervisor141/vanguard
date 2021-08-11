package hypervisor.vanguard.list.arraybacked;

import hypervisor.vanguard.utils.VLLog;
import hypervisor.vanguard.array.VLArrayUtils;

import java.util.Arrays;

public final class VLListDouble extends VLListArrayBacked<double[]> {

    public VLListDouble(int capacity, int resizeoverhead){
        super(0, resizeoverhead);
        backend = new double[capacity];
    }

    public VLListDouble(double[] data, int resizeoverhead){
        super(data.length, resizeoverhead);
        backend = data;
    }

    public VLListDouble(VLListDouble src, long flags){
        copy(src, flags);
    }

    protected VLListDouble(){

    }

    public void add(double item){
        expandIfNeeded(1);
        backend[vsize++] = item;
    }

    public void add(double[] items){
        int size = items.length;
        expandIfNeeded(size);

        for(int i = 0; i < size; i++){
            backend[vsize++] = items[i];
        }
    }

    public void add(VLListDouble items){
        int size = items.size();
        expandIfNeeded(size);

        size = items.size();

        for(int i = 0; i < size; i++){
            backend[vsize++] = items.get(i);
        }
    }

    public void add(int index, double item){
        expandIfNeeded(1);
        VLArrayUtils.addInPlace(index, vsize, backend, item);
        vsize++;
    }

    public void add(int index, double[] items, int offset, int count){
        expandIfNeeded(count);

        VLArrayUtils.addInPlace(index, offset, vsize, backend, items, count);
        vsize++;
    }

    public void add(int index, VLListDouble items, int offset, int count){
        expandIfNeeded(count);
        VLArrayUtils.spaceOut(backend, index, count);

        set(index, items, offset, count);
    }

    public void set(int index, double item){
        checkOperableRange(index, 1);
        backend[index] = item;
    }

    public void set(int index, double[] items, int offset, int count){
        checkOperableRange(index, 1);
        System.arraycopy(items, offset, backend, index, count);
    }

    public void set(int index, VLListDouble items, int offset, int count){
        checkOperableRange(index, 1);
        int endpoint = offset + count;

        for(int i = offset; i < endpoint; i++){
            backend[index++] = items.get(i);
        }
    }

    public double get(int index){
        checkOperableRange(index, 1);
        return backend[index];
    }

    public int indexOf(double item){
        return VLArrayUtils.indexOf(backend, 0, vsize, item);
    }

    public int indexOf(double item, int searchoffset, int searchcount){
        checkOperableRange(searchoffset, searchcount);
        return VLArrayUtils.indexOf(backend, searchoffset, searchcount, item);
    }

    public void remove(double item){
        int index = indexOf(item);

        if(index != -1){
            remove(index);
        }
    }

    public void remove(double item, int searchoffset, int searchcount){
        int index = indexOf(item, searchoffset, searchcount);

        if(index != -1){
            remove(index);
        }
    }

    @Override
    public int realSize(){
        return backend.length;
    }

    @Override
    public void reverse(){
        int cap = backend.length - 1;

        for(int i = 0, i2 = cap; i < i2; i++, i2--){
            backend[i] = backend[i2];
        }
    }

    @Override
    public void resize(int size){
        if(size < 0){
            throw new RuntimeException("Invalid size[" + size + "]");
        }
        if(vsize > size){
            vsize = size;
        }

        double[] newarray = new double[size];
        System.arraycopy(backend, 0, newarray, 0, vsize);
        backend = newarray;
    }


    @Override
    public void reinitialize(int capacity){
        backend = new double[capacity];
        vsize = 0;
    }

    @Override
    public void reinitialize(int capacity, int resizeoverhead){
        reinitialize(capacity);
        this.resizeoverhead = resizeoverhead;
    }

    @Override
    public void nullify(){
        nullify(0, vsize);
    }

    @Override
    public void nullify(int index, int count){
        for(; index < count; index++){
            backend[index] = 0;
        }
    }

    @Override
    public VLListDouble duplicate(long flags){
        return new VLListDouble(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(backend));
        log.append("]");
    }
}
