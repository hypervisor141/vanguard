package vanguard;

public class VLBufferManager extends VLBufferManagerBase<VLBufferManagerBase.EntryType, VLBufferManager, VLBufferAddress<VLBufferManager>>{

    public VLBufferManager(int capacity, int resizer){
        super(capacity, resizer);
    }

    @Override
    public void fillBufferAddress(VLBufferAddress<VLBufferManager> results, int bufferindex, int offset, int unitoffset, int unitsize, int stride, int count){
        results.fill(this, bufferindex, offset, unitoffset, unitsize, stride, count);
    }

    public static class EntryShort extends EntryType<VLArrayShort>{

        public EntryShort(VLBuffer buffer){
            super(buffer);
        }

        @Override
        protected int put(VLArrayShort array){
            buffer.put(array.provider());
            return buffer.position();
        }

        @Override
        protected int put(VLArrayShort array, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
            return buffer.putInterleaved(array.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }

    public static class EntryInt extends EntryType<VLArrayInt> {

        public EntryInt(VLBuffer buffer){
            super(buffer);
        }

        @Override
        protected int put(VLArrayInt array){
            buffer.put(array.provider());
            return buffer.position();
        }

        @Override
        protected int put(VLArrayInt array, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
            return buffer.putInterleaved(array.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }

    public static class EntryLong extends EntryType<VLArrayLong> {

        public EntryLong(VLBuffer buffer){
            super(buffer);
        }

        @Override
        protected int put(VLArrayLong array){
            buffer.put(array.provider());
            return buffer.position();
        }

        @Override
        protected int put(VLArrayLong array, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
            return buffer.putInterleaved(array.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }

    public static class EntryFloat extends EntryType<VLArrayFloat> {

        public EntryFloat(VLBuffer buffer){
            super(buffer);
        }

        @Override
        protected int put(VLArrayFloat array){
            buffer.put(array.provider());
            return buffer.position();
        }

        @Override
        protected int put(VLArrayFloat array, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
            return buffer.putInterleaved(array.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }

    public static class EntryDouble extends EntryType<VLArrayDouble> {

        public EntryDouble(VLBuffer buffer){
            super(buffer);
        }

        @Override
        protected int put(VLArrayDouble array){
            buffer.put(array.provider());
            return buffer.position();
        }

        @Override
        protected int put(VLArrayDouble array, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
            return buffer.putInterleaved(array.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }
}
