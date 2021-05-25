package vanguard.list;

import vanguard.utils.VLLog;
import vanguard.array.VLArrayUtils;

import java.util.Arrays;

public final class VLListShort extends VLList<short[]>{

    public VLListShort(int initialsize, int resizercount){
        super(resizercount, 0);
        array = new short[initialsize];
    }

    public VLListShort(short[] data, int resizercount){
        super(resizercount, data.length);
        array = data;
    }

    public VLListShort(VLListShort src, long flags){
        copy(src, flags);
    }

    protected VLListShort(){

    }

    public void add(short item){
        if(currentsize >= array.length){
            resize(array.length + resizer);
        }

        array[currentsize++] = item;
    }

    public void add(short[] items){
        int target = currentsize + items.length;

        if(target >= array.length){
            resize(target + resizer);
        }

        for(int i = 0; i < items.length; i++){
            array[currentsize++] = items[i];
        }
    }

    public void add(VLListShort items){
        int target = currentsize + items.size();

        if(target >= array.length){
            resize(target + resizer);
        }

        for(int i = 0; i < items.size(); i++){
            array[currentsize++] = items.get(i);
        }
    }

    public void add(int index, short item){
        if(currentsize >= array.length){
            resize(array.length + resizer);
        }

        VLArrayUtils.addInPlace(index, currentsize, array, item);
        currentsize++;
    }

    public void set(int index, short item){
        checkIndex(index, 1);
        array[index] = item;
    }

    public short get(int index){
        checkIndex(index, 1);
        return array[index];
    }

    public int indexOf(short target){
        int size = size();

        for(int i = 0; i < size; i++){
            if(array[i] == target){
                return i;
            }
        }

        return -1;
    }

    public void remove(short item){
        int index = VLArrayUtils.indexOf(array, item);

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
        if(currentsize > size){
            currentsize = size;
        }

        short[] newarray = new short[size];
        System.arraycopy(array, 0, newarray, 0, currentsize);
        array = newarray;
    }

    @Override
    public void reinitialize(int capacity){
        array = new short[capacity];
        currentsize = 0;
    }

    @Override
    public void reinitialize(int capacity, int resizer){
        reinitialize(capacity);
        this.resizer = resizer;
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
    public VLListShort duplicate(long flags){
        return new VLListShort(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(array));
        log.append("]");
    }
}