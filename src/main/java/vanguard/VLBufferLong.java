package vanguard;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

public class VLBufferLong extends VLBuffer<Long, LongBuffer> {

    public VLBufferLong(int capacity){
        super(capacity);
    }

    public VLBufferLong(){

    }

    @Override
    public VLBufferLong initialize(ByteBuffer b){
        buffer = b.asLongBuffer();
        position(0);

        return this;
    }

    @Override
    public void put(long data){
        buffer.put(data);
    }

    @Override
    public void put(VLVTypeVariable data){
        buffer.put((long)data.get());
    }

    @Override
    public void put(VLListType<VLVTypeVariable> data, int offset, int count){
        int limit = offset + count;

        for(int i = offset; i < limit; i++){
            buffer.put((long)data.get(i).get());
        }
    }

    @Override
    public void put(long[] data, int offset, int count){
        buffer.put(data, offset, count);
    }

    @Override
    public Long read(int index){
        return buffer.get(index);
    }

    @Override
    public void read(long[] data, int offset, int count){
        buffer.get(data, offset, count);
    }

    @Override
    public void remove(int offset, int size){
        LongBuffer b = buffer;
        initialize(VLIOUtils.makeDirectByteBuffer(buffer.capacity() - size));
        int cap = b.capacity();

        for(int i = 0; i < offset; i++){
            buffer.put(b.get(i));
        }
        for(int i = offset + size; i < cap; i++){
            buffer.put(b.get(i));
        }
    }

    @Override
    public void removeInterleaved(int offset, int unitsize, int stride, int size){
        LongBuffer b = buffer;
        initialize(VLIOUtils.makeDirectByteBuffer(buffer.capacity() - size));

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
        LongBuffer b = buffer;
        initialize(size);
        b.position(0);

        if(b.hasArray()){
            if(b.capacity() <= buffer.capacity()){
                buffer.put(b.array());

            }else{
                buffer.put(b.array(), 0, buffer.capacity());
            }

        }else{
            long[] data;

            if(b.capacity() <= buffer.capacity()){
                data = new long[b.capacity()];

            }else{
                data = new long[buffer.capacity()];
            }

            b.get(data);
            buffer.put(data);
        }

        buffer.position(0);
    }

    @Override
    public int getTypeBytes(){
        return Long.SIZE / Byte.SIZE;
    }

    @Override
    public int sizeBytes(){
        return buffer.capacity() * getTypeBytes();
    }
}
