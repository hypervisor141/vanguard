package vanguard;

import java.util.Arrays;

public final class VLListDouble extends VLList<double[]>{

    public VLListDouble(int initialsize, int resizercount){
        super(resizercount, 0);
        array = new double[initialsize];
    }

    public VLListDouble(double[] data, int resizercount){
        super(resizercount, data.length);
        array = data;
    }

    public VLListDouble(VLListDouble src, long flags){
        copy(src, flags);
    }

    public void add(double item){
        if(currentsize >= array.length){
            resize(array.length + resizercount);
        }

        array[currentsize++] = item;
    }

    public void add(double[] items){
        int target = currentsize + items.length;

        if(target >= array.length){
            resize(target + resizercount);
        }

        for(int i = 0; i < items.length; i++){
            array[currentsize++] = items[i];
        }
    }

    public void add(VLListDouble items){
        int target = currentsize + items.size();

        if(target >= array.length){
            resize(target + resizercount);
        }

        for(int i = 0; i < items.size(); i++){
            array[currentsize++] = items.get(i);
        }
    }

    public void add(int index, double item){
        if(currentsize >= array.length){
            resize(array.length + resizercount);
        }

        VLArrayUtils.addInPlace(index, currentsize, array, item);
        currentsize++;
    }

    public void set(int index, double item){
        checkIndex(index, 1);
        array[index] = item;
    }

    public double get(int index){
        checkIndex(index, 1);
        return array[index];
    }

    public int indexOf(double item){
        int size = size();

        for(int i = 0; i < size; i++){
            if(array[i] == item){
                return i;
            }
        }

        return -1;
    }

    public void remove(double item){
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

        double[] newarray = new double[size];
        System.arraycopy(array, 0, newarray, 0, currentsize);
        array = newarray;
    }

    @Override
    public void clear(){
        array = new double[resizercount];
        currentsize = 0;
    }

    @Override
    public void clear(int capacity){
        array = new double[capacity];
        currentsize = 0;
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
    public VLListDouble duplicate(long flags){
        return new VLListDouble(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(array));
        log.append("]");
    }
}
