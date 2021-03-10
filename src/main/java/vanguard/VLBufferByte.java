package vanguard;

import java.nio.ByteBuffer;

public class VLBufferByte extends VLBuffer<Byte, ByteBuffer> {

    public VLBufferByte(int capacity){
        super(capacity);
    }

    public VLBufferByte(){

    }

    @Override
    public VLBufferByte initialize(ByteBuffer b){
        buffer = b;
        position(0);

        return this;
    }

    @Override
    public void put(byte data){
        buffer.put(data);
    }

    @Override
    public void put(VLVTypeVariable data){
        buffer.put((byte)data.get());
    }

    @Override
    public void put(VLListType<VLVTypeVariable> data, int offset, int count){
        int limit = offset + count;

        for(int i = offset; i < limit; i++){
            buffer.put((byte)data.get(i).get());
        }
    }

    @Override
    public void put(byte[] data, int offset, int count){
        buffer.put(data, offset, count);
    }

    @Override
    public Byte read(int index){
        return buffer.get(index);
    }

    @Override
    public void read(byte[] data, int offset, int count){
        buffer.get(data, offset, count);
    }

    @Override
    public void remove(int offset, int size){
        ByteBuffer b = buffer;
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
        ByteBuffer b = buffer;
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
        ByteBuffer b = buffer;
        initialize(size);
        b.position(0);

        if(b.hasArray()){
            if(b.capacity() <= buffer.capacity()){
                buffer.put(b.array());

            }else{
                buffer.put(b.array(), 0, buffer.capacity());
            }

        }else{
            byte[] data;

            if(b.capacity() <= buffer.capacity()){
                data = new byte[b.capacity()];

            }else{
                data = new byte[buffer.capacity()];
            }

            b.get(data);
            buffer.put(data);
        }

        buffer.position(0);
    }

    @Override
    public int getTypeBytes(){
        return 1;
    }

    @Override
    public int sizeBytes(){
        return buffer.capacity() * getTypeBytes();
    }
}
