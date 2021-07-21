package hypervisor.vanguard.list;

import hypervisor.vanguard.utils.VLLog;
import hypervisor.vanguard.array.VLArrayUtils;

import java.util.Arrays;

public final class VLListInt extends VLList<int[]>{

    public VLListInt(int capacity, int resizer){
        super(resizer, 0);
        array = new int[capacity];
    }

    public VLListInt(int[] data, int resizer){
        super(resizer, data.length);
        array = data;
    }

    public VLListInt(VLListInt src, long flags){
        copy(src, flags);
    }

    protected VLListInt(){

    }

    public void add(int item){
        expandIfNeeded(1);
        array[currentsize++] = item;
    }

    public void add(int[] items){
        int size = items.length;
        expandIfNeeded(size);

        for(int i = 0; i < size; i++){
            array[currentsize++] = items[i];
        }
    }

    public void add(VLListInt items){
        int size = items.size();
        expandIfNeeded(size);

        size = items.size();

        for(int i = 0; i < size; i++){
            array[currentsize++] = items.get(i);
        }
    }

    public void add(int index, int item){
        expandIfNeeded(1);
        VLArrayUtils.addInPlace(index, currentsize, array, item);
        currentsize++;
    }

    public void add(int index, int[] items, int offset, int count){
        expandIfNeeded(count);

        VLArrayUtils.addInPlace(index, offset, currentsize, array, items, count);
        currentsize++;
    }

    public void add(int index, VLListInt items, int offset, int count){
        expandIfNeeded(count);
        VLArrayUtils.spaceOut(array, index, count);

        set(index, items, offset, count);
    }

    public void set(int index, int item){
        checkOperableRange(index, 1);
        array[index] = item;
    }

    public void set(int index, int[] items, int offset, int count){
        checkOperableRange(index, 1);
        System.arraycopy(items, offset, array, index, count);
    }

    public void set(int index, VLListInt items, int offset, int count){
        checkOperableRange(index, 1);
        int endpoint = offset + count;

        for(int i = offset; i < endpoint; i++){
            array[index++] = items.get(i);
        }
    }

    public int get(int index){
        checkOperableRange(index, 1);
        return array[index];
    }

    public int indexOf(int item){
        int size = size();

        for(int i = 0; i < size; i++){
            if(array[i] == item){
                return i;
            }
        }

        return -1;
    }

    public void remove(Integer item){
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
        if(size < 0){
            throw new RuntimeException("Invalid size[" + size + "]");
        }
        if(currentsize > size){
            currentsize = size;
        }

        int[] newarray = new int[size];
        System.arraycopy(array, 0, newarray, 0, currentsize);
        array = newarray;
    }


    @Override
    public void reinitialize(int capacity){
        array = new int[capacity];
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
    public VLListInt duplicate(long flags){
        return new VLListInt(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(array));
        log.append("]");
    }
}