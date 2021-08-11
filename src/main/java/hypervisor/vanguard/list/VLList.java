package hypervisor.vanguard.list;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;
import hypervisor.vanguard.utils.VLLoggable;

public abstract class VLList<TYPE> implements VLLoggable, VLCopyable<VLList<TYPE>>{

    protected TYPE backend;

    protected int resizeoverhead;
    protected int vsize;

    public VLList(int vsize, int resizeoverhead){
        this.vsize = vsize;
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
        if(vsize < 0){
            throw new RuntimeException("Invalid virtualSize[" + size + "]");

        }else if(vsize > realSize()){
            resize(vsize + resizeoverhead);
        }

        vsize = size;
    }

    public void remove(int index){
        remove(index, 1);
    }

    public abstract void remove(int index, int count);

    public abstract void resize(int size);

    public void fitIntoVirtualSize(){
        resize(vsize);
    }

    public void exposeRealSize(){
        virtualSize(realSize());
    }

    public int resizeOverhead(){
        return resizeoverhead;
    }

    public int size(){
        return vsize;
    }

    public abstract void reverse();

    public abstract int realSize();

    public TYPE backend(){
        return backend;
    }

    public abstract void reinitialize(int capacity, int resizeoverhead);

    public abstract void reinitialize(int capacity);

    public void clear(){
        nullify();
        vsize = 0;
    }

    public abstract void nullify();

    public abstract void nullify(int index, int count);

    protected final void checkOperableRange(int index, int count){
        if(index < 0 || count < 0 || (index + count > vsize)){
            throw new IndexOutOfBoundsException("Invalid values for operation : requestedIndex[" + index + "], requestedCount["
                    + count + "], virtualSize[" + vsize + "], realSize[" + realSize() + "]");
        }
    }

    protected final void expandIfNeeded(int expansionsize){
        int newsize = vsize + expansionsize;

        if(newsize >= realSize()){
            resize(newsize + resizeoverhead);
        }
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("realSize[");
        log.append(realSize());
        log.append("] size[");
        log.append(vsize);
        log.append("] resizeoverhead[");
        log.append(resizeoverhead);
        log.append("]");
    }
}
