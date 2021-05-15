package vanguard;

import java.util.Arrays;

public final class VLListType<TYPE> extends VLList<Object[]>{

    public static final long FLAG_FORCE_REFERENCE_ARRAY = 0x1F;
    public static final long FLAG_FORCE_DUPLICATE_ARRAY = 0x2F;

    public VLListType(int initialsize, int resizercount){
        super(resizercount, 0);
        array = new Object[initialsize];
    }

    public VLListType(TYPE[] data, int resizercount){
        super(resizercount, data.length);
        array = data;
    }

    public VLListType(VLListType<TYPE> src, long flags){
        copy(src, flags);
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
        nullify(0, currentsize);
    }

    @Override
    public void nullify(int index, int count){
        for(; index < count; index++){
            array[index] = null;
        }
    }

    @Override
    public void copy(VLList<Object[]> src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            array = src.array;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            array = src.array.clone();

        }else if((flags & FLAG_CUSTOM) == FLAG_CUSTOM){
            Object[] srcarray = src.array;
            int size = srcarray.length;
            array = new Object[size];

            if((flags & FLAG_FORCE_REFERENCE_ARRAY) == FLAG_FORCE_REFERENCE_ARRAY){
                if(array[0] instanceof VLListType){
                    for(int i = 0; i < size; i++){
                        array[i] = ((VLListType<?>)srcarray[i]).duplicate(FLAG_CUSTOM | FLAG_FORCE_REFERENCE_ARRAY);
                    }

                }else if(array[0] instanceof VLCopyable){
                    VLCopyable<?>[] carray = (VLCopyable<?>[])srcarray;

                    for(int i = 0; i < size; i++){
                        array[i] = carray[i].duplicate(FLAG_REFERENCE);
                    }

                }else{
                    throw new RuntimeException("List element type is not a VLCopyable type.");
                }

            }else if((flags & FLAG_FORCE_DUPLICATE_ARRAY) == FLAG_FORCE_DUPLICATE_ARRAY){
                if(array[0] instanceof VLListType){
                    for(int i = 0; i < size; i++){
                        array[i] = ((VLListType<?>)srcarray[i]).duplicate(FLAG_CUSTOM | FLAG_FORCE_DUPLICATE_ARRAY);
                    }

                }else if(array[0] instanceof VLCopyable){
                    VLCopyable<?>[] carray = (VLCopyable<?>[])srcarray;

                    for(int i = 0; i < size; i++){
                        array[i] = carray[i].duplicate(FLAG_DUPLICATE);
                    }

                }else{
                    throw new RuntimeException("List element type is not a VLCopyable type.");
                }

            }else{
                Helper.throwMissingSubFlags("FLAG_CUSTOM", "FLAG_FORCE_REFERENCE_ARRAY", "FLAG_FORCE_DUPLICATE_ARRAY");
            }

        }else{
            Helper.throwMissingAllFlags();
        }

        resizercount = src.resizercount;
        currentsize = src.currentsize;
    }

    @Override
    public VLListType<TYPE> duplicate(long flags){
        return new VLListType<>(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(array));
        log.append("]");
    }
}
