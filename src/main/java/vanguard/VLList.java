package vanguard;

public abstract class VLList implements VLStringify{

    protected int resizercount;
    protected int currentsize;

    public VLList(int resizercount, int currentsize){
        this.currentsize = currentsize;
        this.resizercount = resizercount;
    }

    public void resizerCount(int count){
        resizercount = count;
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
            Object o = array();
            System.arraycopy(o, index + count, o, index, count);
        }

        currentsize -= count;
    }

    public abstract void resize(int size);

    public void restrictSize(){
        resize(currentsize);
    }

    public int resizerCount(){
        return resizercount;
    }

    public int size(){
        return currentsize;
    }

    public abstract void reverse();

    public abstract int realSize();

    public abstract Object array();

    public abstract void clear();

    public abstract void clear(int capacity);

    public abstract void nullify();

    public abstract void nullify(int index, int count);

    protected final void checkIndex(int index, int count){
        if(index < 0 || index + count > currentsize){
            throw new IndexOutOfBoundsException("Index[" + index + "] out of bounds, size[" + currentsize + "]");
        }
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("realSize[");
        src.append(realSize());
        src.append("] size[");
        src.append(currentsize);
        src.append("] resizer[");
        src.append(resizercount);
        src.append("]");
    }
}
