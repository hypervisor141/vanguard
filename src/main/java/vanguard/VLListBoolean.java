package vanguard;

import java.util.Arrays;

public class VLListBoolean  extends VLList<boolean[]>{

    public VLListBoolean(int initialsize, int resizercount){
        super(resizercount, 0);
        array = new boolean[initialsize];
    }

    public VLListBoolean(boolean[] data, int resizercount){
        super(resizercount, data.length);
        array = data;
    }

    public VLListBoolean(VLListBoolean src, long flags){
        copy(src, flags);
    }

    public void add(boolean item){
        if(currentsize >= array.length){
            resize(array.length + resizercount);
        }

        array[currentsize++] = item;
    }

    public void add(boolean[] items){
        int target = currentsize + items.length;

        if(target >= array.length){
            resize(target + resizercount);
        }

        for(int i = 0; i < items.length; i++){
            array[currentsize++] = items[i];
        }
    }

    public void add(VLListBoolean items){
        int target = currentsize + items.size();

        if(target >= array.length){
            resize(target + resizercount);
        }

        for(int i = 0; i < items.size(); i++){
            array[currentsize++] = items.get(i);
        }
    }

    public void add(int index, boolean item){
        if(currentsize >= array.length){
            resize(array.length + resizercount);
        }

        VLArrayUtils.addInPlace(index, currentsize, array, item);
        currentsize++;
    }

    public void set(int index, boolean item){
        checkIndex(index, 1);
        array[index] = item;
    }

    public boolean get(int index){
        checkIndex(index, 1);
        return array[index];
    }

    public int indexOf(boolean item){
        int size = size();

        for(int i = 0; i < size; i++){
            if(array[i] == item){
                return i;
            }
        }

        return -1;
    }

    public void remove(boolean item){
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

        boolean[] newarray = new boolean[size];
        System.arraycopy(array, 0, newarray, 0, currentsize);
        array = newarray;
    }

    @Override
    public void clear(){
        array = new boolean[resizercount];
        currentsize = 0;
    }

    @Override
    public void clear(int capacity){
        array = new boolean[capacity];
        currentsize = 0;
    }

    @Override
    public void nullify(){
        nullify(0, currentsize);
    }

    @Override
    public void nullify(int index, int count){
        for(; index < count; index++){
            array[index] = false;
        }
    }

    @Override
    public VLListBoolean duplicate(long flags){
        return new VLListBoolean(this, flags);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}

