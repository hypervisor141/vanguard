package hypervisor.vanguard.buffer;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.list.arraybacked.VLListType;
import hypervisor.vanguard.variable.VLVTypeVariable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public abstract class VLBufferShort extends VLBuffer<Short, ShortBuffer>{

    public VLBufferShort(VLBufferShort src, long flags){
        copy(src, flags);
    }

    protected VLBufferShort(){

    }

    @Override
    public void generateBuffer(ByteBuffer buffer){
        this.buffer = buffer.asShortBuffer();
        buffer.position(0);
    }

    @Override
    public void put(short data){
        expandIfNeeded(1);
        buffer.put(data);
    }

    @Override
    public void put(VLVTypeVariable data){
        expandIfNeeded(1);
        buffer.put((short)data.get());
    }

    @Override
    public void put(VLListType<VLVTypeVariable> data, int offset, int count){
        expandIfNeeded(count);

        int limit = offset + count;

        for(int i = offset; i < limit; i++){
            buffer.put((short)data.get(i).get());
        }
    }

    @Override
    public void put(short[] data, int offset, int count){
        expandIfNeeded(count);
        buffer.put(data, offset, count);
    }

    @Override
    public Short read(int index){
        return buffer.get(index);
    }

    @Override
    public void read(short[] results, int offset, int count){
        buffer.get(results, offset, count);
    }

    @Override
    public void read(VLBufferTracker tracker, short[] results, int offset){
        int unitsubcount = tracker.unitsubcount;
        int stride = tracker.stride;
        int endposition = tracker.endposition;

        for(int i = tracker.offset, i2 = offset; i < endposition; i += stride, i2 += unitsubcount){
            buffer.position(i);
            buffer.get(results, i2, unitsubcount);
        }
    }

    @Override
    public void remove(int offset, int size){
        ShortBuffer b = buffer;
        generateBuffer(buffer.capacity() - size, buffer.order());
        int cap = b.capacity();

        for(int i = 0; i < offset; i++){
            buffer.put(b.get(i));
        }

        for(int i = offset + size; i < cap; i++){
            buffer.put(b.get(i));
        }
    }

    @Override
    public void remove(int offset, int unitsize, int stride, int size){
        ShortBuffer b = buffer;
        generateBuffer(buffer.capacity() - size, buffer.order());

        int max = offset + ((size / unitsize) * stride);
        int chunksize = stride - unitsize;

        for(int i = 0; i < offset; i++){
            buffer.put(b.get(i));
        }

        for(int i = offset + unitsize; i < max; i += stride){
            for(int i2 = 0; i2 < chunksize; i2++){
                buffer.put(b.get(i + i2));
            }
        }

        for(int i = max; i < b.capacity(); i++){
            buffer.put(b.get(i));
        }
    }

    @Override
    public void resize(int size){
        int orgposition = position();

        ShortBuffer b = buffer;
        generateBuffer(size, buffer.order());
        b.position(0);

        if(b.hasArray()){
            if(b.capacity() <= buffer.capacity()){
                buffer.put(b.array());

            }else{
                buffer.put(b.array(), 0, buffer.capacity());
            }

        }else{
            short[] data;

            if(b.capacity() <= buffer.capacity()){
                data = new short[b.capacity()];

            }else{
                data = new short[buffer.capacity()];
            }

            b.get(data);
            buffer.put(data);
        }

        if(orgposition > size){
            position(size);

        }else{
            position(orgposition);
        }
    }

    @Override
    public int getTypeBytes(){
        return Short.SIZE / Byte.SIZE;
    }

    @Override
    public int sizeBytes(){
        return buffer.capacity() * getTypeBytes();
    }

    @Override
    public void copy(VLBuffer<Short, ShortBuffer> src, long flags){
        ShortBuffer target = src.buffer;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            buffer = target;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            generateBuffer(target.capacity(), target.order());

            if(target.hasArray()){
                buffer.put(target.array());

            }else{
                int size = target.capacity();

                for(int i = 0; i < size; i++){
                    buffer.put(target.get(i));
                }
            }

            buffer.position(0);

        }else{
            VLCopyable.Helper.throwMissingDefaultFlags();
        }

        preInitCapacity = src.preInitCapacity;
    }

    public static class Normal extends VLBufferShort{

        public Normal(Normal src, long flags){
            super(src, flags);
        }

        public Normal(){

        }

        @Override
        public ByteBuffer generateBuffer(int capacity, ByteOrder order){
            buffer = ShortBuffer.allocate(capacity);
            buffer.position(0);

            return null;
        }

        @Override
        public Normal duplicate(long flags){
            return new Normal(this, flags);
        }
    }

    public static class Direct extends VLBufferShort{

        public Direct(Direct src, long flags){
            super(src, flags);
        }

        public Direct(){

        }

        @Override
        public ByteBuffer generateBuffer(int capacity, ByteOrder order){
            ByteBuffer buffer = ByteBuffer.allocateDirect(capacity * getTypeBytes());
            buffer.order(order);
            buffer.position(0);

            this.buffer = buffer.asShortBuffer();

            return buffer;
        }

        @Override
        public Direct duplicate(long flags){
            return new Direct(this, flags);
        }
    }
}

