package vanguard;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class VLBufferInt extends VLBuffer<Integer, IntBuffer> {

    public VLBufferInt(int capacity){
        super(capacity);
    }

    public VLBufferInt(){

    }

    @Override
    public VLBufferInt initialize(ByteBuffer b){
        buffer = b.asIntBuffer();
        position(0);

        return this;
    }

    @Override
    public void put(int data){
        buffer.put(data);
    }

    @Override
    public void put(VLVTypeVariable data){
        buffer.put((int)data.get());
    }

    @Override
    public void put(VLListType<VLVTypeVariable> data, int offset, int count){
        int limit = offset + count;

        for(int i = offset; i < limit; i++){
            buffer.put((int)data.get(i).get());
        }
    }

    @Override
    public void put(int[] data, int offset, int count){
        buffer.put(data, offset, count);
    }

    @Override
    public Integer read(int index){
        return buffer.get(index);
    }

    @Override
    public void read(int[] data, int offset, int count){
        buffer.get(data, offset, count);
    }

    @Override
    public void remove(int offset, int size){
        IntBuffer b = buffer;
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
    public void removeInterleaved(int offset, int unitsize, int size, int stride){
        IntBuffer b = buffer;
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
        IntBuffer b = buffer;
        initialize(size);
        b.position(0);

        if(b.hasArray()){
            if(b.capacity() <= buffer.capacity()){
                buffer.put(b.array());

            }else{
                buffer.put(b.array(), 0, buffer.capacity());
            }

        }else{
            int[] data;

            if(b.capacity() <= buffer.capacity()){
                data = new int[b.capacity()];

            }else{
                data = new int[buffer.capacity()];
            }

            b.get(data);
            buffer.put(data);
        }

        buffer.position(0);
    }

    @Override
    public int getTypeBytes(){
        return Integer.SIZE / Byte.SIZE;
    }

    @Override
    public int sizeBytes(){
        return buffer.capacity() * getTypeBytes();
    }
}