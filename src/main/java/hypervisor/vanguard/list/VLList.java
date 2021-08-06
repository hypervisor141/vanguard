package hypervisor.vanguard.list;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;
import hypervisor.vanguard.utils.VLLoggable;

public abstract class VLList<TYPE> implements VLLoggable, VLCopyable<VLList<TYPE>>{

    protected TYPE array;

    protected int resizeoverhead;
    protected int currentsize;

    public VLList(int resizeoverhead, int currentsize){
        this.currentsize = currentsize;
        this.resizeoverhead = resizeoverhead;
    }

    protected VLList(){

    }

    public void resizeOverhead(int count){
        if(count < 0){
            throw new RuntimeException("Invalid resizeoverhead[" + count + "]");
        }

        resizeoverhead = count;
    }

    public void virtualSize(int size){
        if(currentsize < 0){
            throw new RuntimeException("Invalid virtualSize[" + size + "]");

        }else if(currentsize > realSize()){
            resize(currentsize + resizeoverhead);
        }

        currentsize = size;
    }

    public void remove(int index){
        remove(index, 1);
    }

    public void remove(int index, int count){
        checkOperableRange(index, count);

        if(index + count == currentsize){
            nullify(index, count);

        }else{
            int endpoint = index + count;

            TYPE array = array();
            System.arraycopy(array, endpoint, array, index, currentsize - endpoint);
            nullify(currentsize - count, currentsize);
        }

        currentsize -= count;
    }

    public abstract void resize(int size);

    public void fitIntoVirtualSize(){
        resize(currentsize);
    }

    public void exposeRealSize(){
        virtualSize(realSize());
    }

    public int resizeOverhead(){
        return resizeoverhead;
    }

    public int size(){
        return currentsize;
    }

    public abstract void reverse();

    public abstract int realSize();

    public TYPE array(){
        return array;
    }

    public abstract void reinitialize(int capacity, int resizeoverhead);

    public abstract void reinitialize(int capacity);

    public void clear(){
        nullify();
        currentsize = 0;
    }

    public abstract void nullify();

    public abstract void nullify(int index, int count);

    protected final void checkOperableRange(int index, int count){
        if(index < 0 || count < 0 || (index + count > currentsize)){
            throw new IndexOutOfBoundsException("Invalid values for operation : requestedIndex[" + index + "], requestedCount["
                    + count + "], virtualSize[" + currentsize + "], realSize[" + realSize() + "]");
        }
    }

    protected final void expandIfNeeded(int expansionsize){
        int newsize = currentsize + expansionsize;

        if(newsize >= realSize()){
            resize(newsize + resizeoverhead);
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

        resizeoverhead = src.resizeoverhead;
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
        log.append("] resizeoverhead[");
        log.append(resizeoverhead);
        log.append("]");
    }
}
