package vanguard;

public abstract class VLBufferManagerBase<ENTRYTYPE extends VLBufferManagerBase.EntryType, MANAGERTYPE extends VLBufferManagerBase, ADDRESSTYPE extends VLBufferAddress<MANAGERTYPE>>{

    protected VLListType<ENTRYTYPE> entries;

    public VLBufferManagerBase(int capacity, int resizer){
        entries = new VLListType<>(capacity, resizer);
    }

    public void initialize(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initialize();
        }
    }

    public void initialize(int index){
        entries.get(index).initialize();
    }

    public void initializeLast(){
        entries.get(entries.size() - 1).initialize();
    }

    public int add(ENTRYTYPE entry){
        entries.add(entry);
        return entries.size() - 1;
    }

    public void adjustCapacity(int index, int count){
        entries.get(index).adjustCapacity(count);
    }

    public void buffer(ADDRESSTYPE results, int index, VLArray array){
        EntryType e = entries.get(index);
        fillBufferAddress(results, index, e.buffer.position(), 0, -1, -1, array.size());

        e.put(array);
    }

    public void buffer(ADDRESSTYPE results, int index, VLArray array, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        EntryType e = entries.get(index);
        fillBufferAddress(results, index, e.buffer.position(), unitoffset, unitsize, stride, arraycount / unitsize);

        e.put(array, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
    }

    public abstract void fillBufferAddress(ADDRESSTYPE results, int bufferindex, int offset, int unitoffset, int unitsize, int stride, int count);
    
    public void release(){
        for(int i = 0; i < entries.size(); i++){
            entries.get(i).release();
        }

        entries.nullify();
    }

    public int position(int index){
        return entries.get(index).buffer.position();
    }

    public int size(int index){
        return entries.get(index).buffer.size();
    }

    public EntryType get(int index){
        return entries.get(index);
    }

    public void remove(int index){
        entries.remove(index);
    }

    public int size(){
        return entries.size();
    }

    public static abstract class EntryType<ARRAYTYPE extends VLArray>{

        protected VLBuffer buffer;
        protected int targetcapacity;

        protected EntryType(VLBuffer buffer){
            this.buffer = buffer;
        }

        public VLBuffer buffer(){
            return buffer;
        }

        public int targetCapacity(){
            return targetcapacity;
        }

        public void adjustCapacity(int count){
            targetcapacity += count;
        }

        protected void initialize(){
            buffer.initialize(targetcapacity);
        }

        protected abstract int put(ARRAYTYPE array);

        protected abstract int put(ARRAYTYPE array, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride);

        public void release(){
            buffer.release();
            targetcapacity = -1;
        }
    }
}
