package vanguard.list;

import vanguard.utils.VLCopyable;
import vanguard.utils.VLLog;
import vanguard.utils.VLLoggable;

public abstract class VLList<TYPE> implements VLLoggable, VLCopyable<VLList<TYPE>>{

    protected TYPE array;

    protected int resizer;
    protected int currentsize;

    public VLList(int resizer, int currentsize){
        this.currentsize = currentsize;
        this.resizer = resizer;
    }

    protected VLList(){

    }

    public void resizerCount(int count){
        resizer = count;
    }

    public void virtualSize(int size){
        currentsize = size;
    }

    public void remove(int index){
        remove(index, 1);
    }

    public void remove(int index, int count){
        checkIndex(index, count);

        if(index + count == currentsize){
            nullify(index, count);

        }else{
            TYPE array = array();
            System.arraycopy(array, index + count, array, index, count);
        }

        currentsize -= count;
    }

    public abstract void resize(int size);

    public void restrictSize(){
        resize(currentsize);
    }

    public void maximizeVirtualSize(){
        virtualSize(realSize());
    }

    public int resizerCount(){
        return resizer;
    }

    public int size(){
        return currentsize;
    }

    public abstract void reverse();

    public abstract int realSize();

    public TYPE array(){
        return array;
    }

    public abstract void reinitialize(int capacity, int resizer);

    public abstract void reinitialize(int capacity);

    public void clear(){
        nullify();
        currentsize = 0;
    }

    public abstract void nullify();

    public abstract void nullify(int index, int count);

    protected final void checkIndex(int index, int count){
        if(index < 0 || index + count > currentsize){
            throw new IndexOutOfBoundsException("Index[" + index + "] out of bounds, size[" + currentsize + "]");
        }
    }

    @Override
    public void copy(VLList<TYPE> src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            array = src.array;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            System.arraycopy(src.array, 0, array, 0, realSize());

        }else{
            VLCopyable.Helper.throwMissingDefaultFlags();
        }

        resizer = src.resizer;
        currentsize = src.currentsize;
    }

    @Override
    public abstract VLList<TYPE> duplicate(long flags);

    @Override
    public void log(VLLog log, Object data){
        log.append("realSize[");
        log.append(realSize());
        log.append("] size[");
        log.append(currentsize);
        log.append("] resizer[");
        log.append(resizer);
        log.append("]");
    }
}
