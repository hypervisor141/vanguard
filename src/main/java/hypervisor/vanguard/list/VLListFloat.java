package hypervisor.vanguard.list;

import hypervisor.vanguard.utils.VLLog;
import hypervisor.vanguard.array.VLArrayUtils;

import java.util.Arrays;

public final class VLListFloat extends VLList<float[]>{

    public VLListFloat(int capacity, int resizeoverhead){
        super(resizeoverhead, 0);
        array = new float[capacity];
    }

    public VLListFloat(float[] data, int resizeoverhead){
        super(resizeoverhead, data.length);
        array = data;
    }

    public VLListFloat(VLListFloat src, long flags){
        copy(src, flags);
    }

    protected VLListFloat(){

    }

    public void add(float item){
        expandIfNeeded(1);
        array[currentsize++] = item;
    }

    public void add(float[] items){
        int size = items.length;
        expandIfNeeded(size);

        for(int i = 0; i < size; i++){
            array[currentsize++] = items[i];
        }
    }

    public void add(VLListFloat items){
        int size = items.size();
        expandIfNeeded(size);

        size = items.size();

        for(int i = 0; i < size; i++){
            array[currentsize++] = items.get(i);
        }
    }

    public void add(int index, float item){
        expandIfNeeded(1);
        VLArrayUtils.addInPlace(index, currentsize, array, item);
        currentsize++;
    }

    public void add(int index, float[] items, int offset, int count){
        expandIfNeeded(count);

        VLArrayUtils.addInPlace(index, offset, currentsize, array, items, count);
        currentsize++;
    }

    public void add(int index, VLListFloat items, int offset, int count){
        expandIfNeeded(count);
        VLArrayUtils.spaceOut(array, index, count);

        set(index, items, offset, count);
    }

    public void set(int index, float item){
        checkOperableRange(index, 1);
        array[index] = item;
    }

    public void set(int index, float[] items, int offset, int count){
        checkOperableRange(index, 1);
        System.arraycopy(items, offset, array, index, count);
    }

    public void set(int index, VLListFloat items, int offset, int count){
        checkOperableRange(index, 1);
        int endpoint = offset + count;

        for(int i = offset; i < endpoint; i++){
            array[index++] = items.get(i);
        }
    }

    public float get(int index){
        checkOperableRange(index, 1);
        return array[index];
    }

    public int indexOf(float item){
        return VLArrayUtils.indexOf(array, 0, currentsize, item);
    }

    public int indexOf(float item, int searchoffset, int searchcount){
        checkOperableRange(searchoffset, searchcount);
        return VLArrayUtils.indexOf(array, searchoffset, searchcount, item);
    }

    public void remove(float item){
        int index = indexOf(item);

        if(index != -1){
            remove(index);
        }
    }

    public void remove(float item, int searchoffset, int searchcount){
        int index = indexOf(item, searchoffset, searchcount);

        if(index != -1){
            remove(index);
        }
    }

    @Override
    public int realSize(){
        return array.length;
    }

    @Override
    public void reverse(){
        int cap = array.length - 1;

        for(int i = 0, i2 = cap; i < i2; i++, i2--){
            array[i] = array[i2];
        }
    }

    @Override
    public void resize(int size){
        if(size < 0){
            throw new RuntimeException("Invalid size[" + size + "]");
        }
        if(currentsize > size){
            currentsize = size;
        }

        float[] newarray = new float[size];
        System.arraycopy(array, 0, newarray, 0, currentsize);
        array = newarray;
    }


    @Override
    public void reinitialize(int capacity){
        array = new float[capacity];
        currentsize = 0;
    }

    @Override
    public void reinitialize(int capacity, int resizeoverhead){
        reinitialize(capacity);
        this.resizeoverhead = resizeoverhead;
    }

    @Override
    public void nullify(){
        nullify(0, currentsize);
    }

    @Override
    public void nullify(int index, int count){
        for(; index < count; index++){
            array[index] = 0;
        }
    }

    @Override
    public VLListFloat duplicate(long flags){
        return new VLListFloat(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(array));
        log.append("]");
    }
}
