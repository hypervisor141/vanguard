package vanguard.list;

import vanguard.utils.VLLog;
import vanguard.array.VLArrayUtils;

import java.util.Arrays;

public class VLListByte extends VLList<byte[]>{

    public VLListByte(int initialsize, int resizercount){
        super(resizercount, 0);
        array = new byte[initialsize];
    }

    public VLListByte(byte[] data, int resizercount){
        super(resizercount, data.length);
        array = data;
    }

    public VLListByte(VLListByte src, long flags){
        copy(src, flags);
    }

    protected VLListByte(){

    }

    public void add(byte item){
        if(currentsize >= array.length){
            resize(array.length + resizer);
        }

        array[currentsize++] = item;
    }

    public void add(byte[] items){
        int target = currentsize + items.length;

        if(target >= array.length){
            resize(target + resizer);
        }

        for(int i = 0; i < items.length; i++){
            array[currentsize++] = items[i];
        }
    }

    public void add(VLListByte items){
        int target = currentsize + items.size();

        if(target >= array.length){
            resize(target + resizer);
        }

        for(int i = 0; i < items.size(); i++){
            array[currentsize++] = items.get(i);
        }
    }

    public void add(int index, byte item){
        if(currentsize >= array.length){
            resize(array.length + resizer);
        }

        VLArrayUtils.addInPlace(index, currentsize, array, item);
        currentsize++;
    }

    public void set(int index, byte item){
        checkIndex(index, 1);
        array[index] = item;
    }

    public byte get(int index){
        checkIndex(index, 1);
        return array[index];
    }

    public int indexOf(byte item){
        int size = size();

        for(int i = 0; i < size; i++){
            if(array[i] == item){
                return i;
            }
        }

        return -1;
    }

    public void remove(byte item){
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

        byte[] newarray = new byte[size];
        System.arraycopy(array, 0, newarray, 0, currentsize);
        array = newarray;
    }


    @Override
    public void reinitialize(int capacity){
        array = new byte[capacity];
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
    public VLListByte duplicate(long flags){
        return new VLListByte(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(array));
        log.append("]");
    }
}
