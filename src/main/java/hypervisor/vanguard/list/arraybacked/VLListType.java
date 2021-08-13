package hypervisor.vanguard.list.arraybacked;

import hypervisor.vanguard.array.VLArrayUtils;
import hypervisor.vanguard.list.VLList;
import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;

import java.util.Arrays;

public final class VLListType<TYPE> extends VLListArrayBacked<Object[]> {

    public VLListType(int capacity, int resizeoverhead){
        super(0, resizeoverhead);
        backend = new Object[capacity];
    }

    public VLListType(TYPE[] data, int resizeoverhead){
        super(data.length, resizeoverhead);
        backend = data;
    }

    public VLListType(VLListType<TYPE> src, long flags){
        copy(src, flags);
    }

    protected VLListType(){

    }

    public void add(TYPE item){
        expandIfNeeded(1);
        backend[vsize++] = item;
    }

    public void add(TYPE[] items){
        int size = items.length;
        expandIfNeeded(size);

        for(int i = 0; i < size; i++){
            backend[vsize++] = items[i];
        }
    }

    public void add(VLListType<TYPE> items){
        int size = items.size();
        expandIfNeeded(size);

        size = items.size();

        for(int i = 0; i < size; i++){
            backend[vsize++] = items.get(i);
        }
    }

    public void add(int index, TYPE item){
        expandIfNeeded(1);
        VLArrayUtils.addInPlace(index, vsize, backend, item);
        vsize++;
    }

    public void add(int index, TYPE[] items, int offset, int count){
        expandIfNeeded(count);

        VLArrayUtils.addInPlace(index, offset, vsize, backend, items, count);
        vsize++;
    }

    public void add(int index, VLListType<TYPE> items, int offset, int count){
        expandIfNeeded(count);
        VLArrayUtils.spaceOut(backend, index, count);

        set(index, items, offset, count);
    }

    public void set(int index, TYPE item){
        checkOperableRange(index, 1);
        backend[index] = item;
    }

    public void set(int index, TYPE[] items, int offset, int count){
        checkOperableRange(index, 1);
        System.arraycopy(items, offset, backend, index, count);
    }

    public void set(int index, VLListType<TYPE> items, int offset, int count){
        checkOperableRange(index, 1);
        int endpoint = offset + count;

        for(int i = offset; i < endpoint; i++){
            backend[index++] = items.get(i);
        }
    }

    public TYPE get(int index){
        checkOperableRange(index, 1);
        return (TYPE) backend[index];
    }

    public int indexOf(Object item){
        return VLArrayUtils.indexOf(backend, 0, vsize, item);
    }

    public int indexOf(Object item, int searchoffset, int searchcount){
        checkOperableRange(searchoffset, searchcount);
        return VLArrayUtils.indexOf(backend, searchoffset, searchcount, item);
    }

    public void remove(Object item){
        int index = indexOf(item);

        if(index != -1){
            remove(index);
        }
    }

    public void remove(Object item, int searchoffset, int searchcount){
        int index = indexOf(item, searchoffset, searchcount);

        if(index != -1){
            remove(index);
        }
    }

    @Override
    public void remove(int index, int count){
        super.remove(index, count);

        int cap = vsize + count;

        for(int i = vsize; i < cap; i++){
            backend[i] = null;
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

        Object[] newarray = new Object[size];
        System.arraycopy(backend, 0, newarray, 0, vsize);
        backend = newarray;
    }

    @Override
    public void reinitialize(int capacity){
        backend = new Object[capacity];
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
            backend[index] = null;
        }
    }

    @Override
    public void copy(VLList<Object[]> src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            backend = src.backend();

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            Object[] srcarray = src.backend();
            backend = new Object[srcarray.length];

            int size = src.size();

            for(int i = 0; i < size; i++){
                Object item = srcarray[i];

                if(item instanceof VLListType){
                    backend[i] = ((VLListType<?>)item).duplicate(FLAG_DUPLICATE);

                }else if(item instanceof VLCopyable){
                    backend[i] = ((VLCopyable<?>)item).duplicate(FLAG_DUPLICATE);

                }else{
                    backend[i] = item;
                }
            }

        }else{
            VLCopyable.Helper.throwMissingAllFlags();
        }

        resizeoverhead = src.resizeOverhead();
        vsize = src.size();
    }

    @Override
    public VLListType<TYPE> duplicate(long flags){
        return new VLListType<>(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        super.log(log, data);

        log.append(" content[");
        log.append(Arrays.toString(backend));
        log.append("]");
    }
}
