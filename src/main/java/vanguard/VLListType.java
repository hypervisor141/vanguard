package vanguard;

import java.util.Arrays;

public final class VLListType<TYPE> extends VLList<Object[]>{

    public VLListType(int initialsize, int resizercount){
        super(resizercount, 0);
        array = new Object[initialsize];
    }

    public VLListType(TYPE[] data, int resizercount){
        super(resizercount, data.length);
        array = data;
    }

    public VLListType(VLListType<TYPE> src, int depth){
        copy(src, depth);
    }

    public void add(TYPE item){
        if(currentsize >= array.length){
            resize(array.length + resizercount);
        }

        array[currentsize++] = item;
    }

    public void add(TYPE[] items){
        int target = currentsize + items.length;

        if(target >= array.length){
            resize(target + resizercount);
        }

        for(int i = 0; i < items.length; i++){
            array[currentsize++] = items[i];
        }
    }

    public void add(VLListType<TYPE> items){
        int target = currentsize + items.size();

        if(target >= array.length){
            resize(target + resizercount);
        }

        for(int i = 0; i < items.size(); i++){
            array[currentsize++] = items.get(i);
        }
    }

    public void add(int index, TYPE item){
        if(currentsize >= array.length){
            resize(array.length + resizercount);
        }

        VLArrayUtils.addInPlace(index, currentsize, array, item);
        currentsize++;
    }

    public void set(int index, TYPE item){
        checkIndex(index, 1);
        array[index] = item;
    }

    public TYPE get(int index){
        checkIndex(index, 1);
        return (TYPE)array[index];
    }

    public int indexOf(Object target){
        int size = size();

        for(int i = 0; i < size; i++){
            if(array[i].equals(target)){
                return i;
            }
        }

        return -1;
    }

    public void remove(TYPE item){
        int index = VLArrayUtils.indexOf(array, item);

        if(index != -1){
            remove(index);
        }
    }

    @Override
    public void remove(int index, int count){
        super.remove(index, count);

        int cap = currentsize + count;

        for(int i = currentsize; i < cap; i++){
            array[i] = null;
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

        Object[] newarray = new Object[size];
        System.arraycopy(array, 0, newarray, 0, currentsize);
        array = newarray;
    }

    @Override
    public void clear(){
        array = new Object[resizercount];
        currentsize = 0;
    }

    @Override
    public void clear(int capacity){
        array = new Object[capacity];
        currentsize = 0;
    }

    @Override
    public void nullify(){
        for(int i = 0; i < currentsize; i++){
            array[i] = null;
        }
    }

    @Override
    public void nullify(int index, int count){
        for(; index < count; index++){
            array[index] = null;
        }
    }

    @Override
    public VLListType<TYPE> duplicate(int depth){
        return new VLListType<>(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}
