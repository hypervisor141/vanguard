package vanguard;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class VLBufferFloat extends VLBuffer<Float, FloatBuffer>{

    public VLBufferFloat(){

    }

    @Override
    public void initialize(ByteBuffer buffer){
        this.buffer = buffer.asFloatBuffer();
        buffer.position(0);
    }

    @Override
    public void put(float data) {
        buffer.put(data);
    }

    @Override
    public void put(VLVTypeVariable data) {
        buffer.put(data.get());
    }

    @Override
    public void put(VLListType<VLVTypeVariable> data, int offset, int count) {
        int limit = offset + count;

        for (int i = offset; i < limit; i++) {
            buffer.put(data.get(i).get());
        }
    }

    @Override
    public void put(float[] data, int offset, int count) {
        buffer.put(data, offset, count);
    }

    @Override
    public Float read(int index) {
        return buffer.get(index);
    }

    @Override
    public void read(float[] data, int offset, int count) {
        buffer.get(data, offset, count);
    }

    @Override
    public void remove(int offset, int size) {
        FloatBuffer b = buffer;
        initialize(buffer.capacity() - size, buffer.order());
        int cap = b.capacity();

        for (int i = 0; i < offset; i++) {
            buffer.put(b.get(i));
        }
        for (int i = offset + size; i < cap; i++) {
            buffer.put(b.get(i));
        }
    }

    @Override
    public void removeInterleaved(int offset, int unitsize, int size, int stride) {
        FloatBuffer b = buffer;
        initialize(buffer.capacity() - size, buffer.order());

        int max = offset + ((size / unitsize) * stride);
        int chunksize = stride - unitsize;

        for (int i = 0; i < offset; i++) {
            buffer.put(b.get(i));
        }
        for (int i = offset + unitsize; i < max; i += stride) {
            for (int i2 = 0; i2 < chunksize; i2++) {
                buffer.put(b.get(i + i2));
            }
        }
        for (int i = max; i < b.capacity(); i++) {
            buffer.put(b.get(i));
        }
    }

    @Override
    public void resize(int size) {
        FloatBuffer b = buffer;
        initialize(size, buffer.order());
        b.position(0);

        if (b.hasArray()) {
            if (b.capacity() <= buffer.capacity()) {
                buffer.put(b.array());

            } else {
                buffer.put(b.array(), 0, buffer.capacity());
            }

        } else {
            float[] data;

            if (b.capacity() <= buffer.capacity()) {
                data = new float[b.capacity()];

            } else {
                data = new float[buffer.capacity()];
            }

            b.get(data);
            buffer.put(data);
        }

        buffer.position(0);
    }

    @Override
    public int getTypeBytes() {
        return Float.SIZE / Byte.SIZE;
    }

    @Override
    public int sizeBytes() {
        return buffer.capacity() * getTypeBytes();
    }

    public static class Normal extends VLBufferFloat{

        public Normal(){

        }

        @Override
        public ByteBuffer initialize(int capacity, ByteOrder order){
            buffer = FloatBuffer.allocate(capacity);
            buffer.position(0);

            return null;
        }
    }

    public static class Direct extends VLBufferFloat{

        public Direct(){

        }

        @Override
        public ByteBuffer initialize(int capacity, ByteOrder order){
            ByteBuffer buffer = ByteBuffer.allocateDirect(capacity * getTypeBytes());
            buffer.order(order);
            buffer.position(0);

            this.buffer = buffer.asFloatBuffer();

            return buffer;
        }
    }
}
