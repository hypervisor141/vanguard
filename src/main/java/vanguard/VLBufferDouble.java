package vanguard;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

public class VLBufferDouble extends VLBuffer<Double, DoubleBuffer> {
    
    public VLBufferDouble(int capacity) {
        super(capacity);
    }

    public VLBufferDouble(){

    }

    @Override
    public VLBuffer initialize(ByteBuffer b){
        buffer = b.asDoubleBuffer();
        position(0);

        return this;
    }

    @Override
    public void put(double data) {
        buffer.put(data);
    }

    @Override
    public void put(VLV data) {
        buffer.put((double)data.get());
    }

    @Override
    public void put(VLListType<VLV> data, int offset, int count) {
        int limit = offset + count;

        for (int i = offset; i < limit; i++) {
            buffer.put((double)data.get(i).get());
        }
    }

    @Override
    public void put(double[] data, int offset, int count) {
        buffer.put(data, offset, count);
    }

    @Override
    public Double read(int index) {
        return buffer.get(index);
    }

    @Override
    public void read(double[] data, int offset, int count) {
        buffer.get(data, offset, count);
    }

    @Override
    public void remove(int offset, int size) {
        DoubleBuffer b = buffer;
        initialize(VLIOUtils.makeDirectByteBuffer(buffer.capacity() - size));
        int cap = b.capacity();

        for (int i = 0; i < offset; i++) {
            buffer.put(b.get(i));
        }
        for (int i = offset + size; i < cap; i++) {
            buffer.put(b.get(i));
        }
    }

    @Override
    public void removeInterleaved(int offset, int unitsize, int stride, int size) {
        DoubleBuffer b = buffer;
        initialize(VLIOUtils.makeDirectByteBuffer(buffer.capacity() - size));

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
        DoubleBuffer b = buffer;
        initialize(size);
        b.position(0);

        if (b.hasArray()) {
            if (b.capacity() <= buffer.capacity()) {
                buffer.put(b.array());

            } else {
                buffer.put(b.array(), 0, buffer.capacity());
            }

        } else {
            double[] data;

            if (b.capacity() <= buffer.capacity()) {
                data = new double[b.capacity()];

            } else {
                data = new double[buffer.capacity()];
            }

            b.get(data);
            buffer.put(data);
        }

        buffer.position(0);
    }

    @Override
    public int getTypeBytes() {
        return Double.SIZE / Byte.SIZE;
    }

    @Override
    public int sizeBytes() {
        return buffer.capacity() * getTypeBytes();
    }
}

