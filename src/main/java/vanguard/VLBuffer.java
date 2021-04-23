package vanguard;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class VLBuffer<ELEMENT extends Number, BUFFER extends Buffer> implements VLStringify{

    protected BUFFER buffer;
    protected int preInitCapacity;

    public VLBuffer(){
        preInitCapacity = 0;
    }

    public abstract ByteBuffer initialize(int capacity, ByteOrder order);

    public abstract void initialize(ByteBuffer buffer);

    public final ByteBuffer initialize(ByteOrder order){
        return initialize(preInitCapacity, order);
    }

    public void initialize(BUFFER buffer){
        this.buffer = buffer;
    }

    public void put(byte data){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(short data){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(int data){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(long data){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(float data){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(double data){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(VLVTypeVariable data){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(byte[] data){
        put(data, 0, data.length);
    }

    public void put(short[] data){
        put(data, 0, data.length);
    }

    public void put(int[] data){
        put(data, 0, data.length);
    }

    public void put(long[] data){
        put(data, 0, data.length);
    }

    public void put(float[] data){
        put(data, 0, data.length);
    }

    public void put(double[] data){
        put(data, 0, data.length);
    }

    public void put(VLListType<VLVTypeVariable> data){
        put(data, 0, data.size());
    }

    public void put(byte[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(short[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(int[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(long[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(float[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(double[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(VLListType<VLVTypeVariable> data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public int put(byte[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        if(unitsize == stride && unitsize == unitsubcount){
            put(data, arrayoffset, arraycount);
            return buffer.position();
        }

        int offset = buffer.position();
        int arrayend = unitoffset + unitsubcount;
        int pos = offset;

        for(int i = arrayoffset; i < arraycount; i += unitsize){
            buffer.position(pos);

            for(int i2 = unitoffset; i2 < arrayend; i2++){
                put(data[i + i2]);
            }

            pos += stride;
        }

        return offset + unitsubcount;
    }

    public int put(short[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        if(unitsize == stride && unitsize == unitsubcount){
            put(data, arrayoffset, arraycount);
            return buffer.position();
        }

        int offset = buffer.position();
        int arrayend = unitoffset + unitsubcount;
        int pos = offset;

        for(int i = arrayoffset; i < arraycount; i += unitsize){
            buffer.position(pos);

            for(int i2 = unitoffset; i2 < arrayend; i2++){
                put(data[i + i2]);
            }

            pos += stride;
        }

        return offset + unitsubcount;
    }

    public int put(int[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        if(unitsize == unitsubcount && unitsize == stride){
            put(data, arrayoffset, arraycount);
            return buffer.position();
        }

        int offset = buffer.position();
        int arrayend = unitoffset + unitsubcount;
        int pos = offset;

        for(int i = arrayoffset; i < arraycount; i += unitsize){
            buffer.position(pos);

            for(int i2 = unitoffset; i2 < arrayend; i2++){
                put(data[i + i2]);
            }

            pos += stride;
        }

        return offset + unitsubcount;
    }

    public int put(long[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        if(unitsize == unitsubcount && unitsize == stride){
            put(data, arrayoffset, arraycount);
            return buffer.position();
        }

        int offset = buffer.position();
        int arrayend = unitoffset + unitsubcount;
        int pos = offset;

        for(int i = arrayoffset; i < arraycount; i += unitsize){
            buffer.position(pos);

            for(int i2 = unitoffset; i2 < arrayend; i2++){
                put(data[i + i2]);
            }

            pos += stride;
        }

        return offset + unitsubcount;
    }

    public int put(float[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        if(unitsize == unitsubcount && unitsize == stride){
            put(data, arrayoffset, arraycount);
            return buffer.position();
        }

        int offset = buffer.position();
        int arrayend = unitoffset + unitsubcount;
        int pos = offset;

        for(int i = arrayoffset; i < arraycount; i += unitsize){
            buffer.position(pos);

            for(int i2 = unitoffset; i2 < arrayend; i2++){
                put(data[i + i2]);
            }

            pos += stride;
        }

        return offset + unitsubcount;
    }

    public int put(double[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        if(unitsize == unitsubcount && unitsize == stride){
            put(data, arrayoffset, arraycount);
            return buffer.position();
        }

        int offset = buffer.position();
        int arrayend = unitoffset + unitsubcount;
        int pos = offset;

        for(int i = arrayoffset; i < arraycount; i += unitsize){
            buffer.position(pos);

            for(int i2 = unitoffset; i2 < arrayend; i2++){
                put(data[i + i2]);
            }

            pos += stride;
        }

        return offset + unitsubcount;
    }

    public int put(VLListType<VLVTypeVariable> data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        if(unitsize == unitsubcount && unitsize == stride){
            put(data, arrayoffset, arraycount);
            return buffer.position();
        }

        int offset = buffer.position();
        int arrayend = unitoffset + unitsubcount;
        int pos = offset;

        for(int i = arrayoffset; i < arraycount; i += unitsize){
            buffer.position(pos);

            for(int i2 = unitoffset; i2 < arrayend; i2++){
                put(data.get(i + i2));
            }

            pos += stride;
        }

        return offset + unitsubcount;
    }

    public void put(VLBufferTracker tracker, byte data){
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.endposition = tracker.offset + 1;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, short data){
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.endposition = tracker.offset + 1;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, int data){
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.endposition = tracker.offset + 1;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, long data){
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.endposition = tracker.offset + 1;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, float data){
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.endposition = tracker.offset + 1;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, double data){
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.endposition = tracker.offset + 1;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, VLVTypeVariable data){
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.endposition = tracker.offset + 1;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, byte[] data){
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.endposition = tracker.offset + tracker.count;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, short[] data){
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.endposition = tracker.offset + tracker.count;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, int[] data){
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.endposition = tracker.offset + tracker.count;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, long[] data){
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.endposition = tracker.offset + tracker.count;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, float[] data){
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.endposition = tracker.offset + tracker.count;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, double[] data){
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.endposition = tracker.offset + tracker.count;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, VLListType<VLVTypeVariable> data){
        tracker.offset = buffer.position();
        tracker.count = data.size();
        tracker.endposition = tracker.offset + tracker.count;
        tracker.typebytesize = getTypeBytes();

        put(data);
    }

    public void put(VLBufferTracker tracker, byte[] data, int offset, int count){
        tracker.offset = offset;
        tracker.count = count;
        tracker.endposition = offset + count;
        tracker.typebytesize = getTypeBytes();

        put(data, offset, count);
    }

    public void put(VLBufferTracker tracker, short[] data, int offset, int count){
        tracker.offset = offset;
        tracker.count = count;
        tracker.endposition = offset + count;
        tracker.typebytesize = getTypeBytes();

        put(data, offset, count);
    }

    public void put(VLBufferTracker tracker, int[] data, int offset, int count){
        tracker.offset = offset;
        tracker.count = count;
        tracker.endposition = offset + count;
        tracker.typebytesize = getTypeBytes();

        put(data, offset, count);
    }

    public void put(VLBufferTracker tracker, long[] data, int offset, int count){
        tracker.offset = offset;
        tracker.count = count;
        tracker.endposition = offset + count;
        tracker.typebytesize = getTypeBytes();

        put(data, offset, count);
    }

    public void put(VLBufferTracker tracker, float[] data, int offset, int count){
        tracker.offset = offset;
        tracker.count = count;
        tracker.endposition = offset + count;
        tracker.typebytesize = getTypeBytes();

        put(data, offset, count);
    }

    public void put(VLBufferTracker tracker, double[] data, int offset, int count){
        tracker.offset = offset;
        tracker.count = count;
        tracker.endposition = offset + count;
        tracker.typebytesize = getTypeBytes();

        put(data, offset, count);
    }

    public void put(VLBufferTracker tracker, VLListType<VLVTypeVariable> data, int offset, int count){
        tracker.offset = offset;
        tracker.count = count;
        tracker.endposition = offset + count;
        tracker.typebytesize = getTypeBytes();

        put(data, offset, count);
    }

    public int put(VLBufferTracker tracker, byte[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.offset = buffer.position();
        tracker.inputoffest = arrayoffset;
        tracker.count = arraycount;
        tracker.unitoffset = unitoffset;
        tracker.unitsize = unitsize;
        tracker.unitsubcount = unitsubcount;
        tracker.stride = stride;
        tracker.typebytesize = getTypeBytes();

        int nextoffset = put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);

        tracker.endposition = buffer.position() - 1;

        return nextoffset;
    }

    public int put(VLBufferTracker tracker, short[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.offset = buffer.position();
        tracker.inputoffest = arrayoffset;
        tracker.count = arraycount;
        tracker.unitoffset = unitoffset;
        tracker.unitsize = unitsize;
        tracker.unitsubcount = unitsubcount;
        tracker.stride = stride;
        tracker.typebytesize = getTypeBytes();

        int nextoffset = put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);

        tracker.endposition = buffer.position() - 1;

        return nextoffset;
    }

    public int put(VLBufferTracker tracker, int[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.offset = buffer.position();
        tracker.inputoffest = arrayoffset;
        tracker.count = arraycount;
        tracker.unitoffset = unitoffset;
        tracker.unitsize = unitsize;
        tracker.unitsubcount = unitsubcount;
        tracker.stride = stride;
        tracker.typebytesize = getTypeBytes();

        int nextoffset = put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);

        tracker.endposition = buffer.position() - 1;

        return nextoffset;
    }

    public int put(VLBufferTracker tracker, long[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.offset = buffer.position();
        tracker.inputoffest = arrayoffset;
        tracker.count = arraycount;
        tracker.unitoffset = unitoffset;
        tracker.unitsize = unitsize;
        tracker.unitsubcount = unitsubcount;
        tracker.stride = stride;
        tracker.typebytesize = getTypeBytes();

        int nextoffset = put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);

        tracker.endposition = buffer.position() - 1;

        return nextoffset;
    }

    public int put(VLBufferTracker tracker, float[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.offset = buffer.position();
        tracker.inputoffest = arrayoffset;
        tracker.count = arraycount;
        tracker.unitoffset = unitoffset;
        tracker.unitsize = unitsize;
        tracker.unitsubcount = unitsubcount;
        tracker.stride = stride;
        tracker.typebytesize = getTypeBytes();

        int nextoffset = put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);

        tracker.endposition = buffer.position() - 1;

        return nextoffset;
    }

    public int put(VLBufferTracker tracker, double[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.offset = buffer.position();
        tracker.inputoffest = arrayoffset;
        tracker.count = arraycount;
        tracker.unitoffset = unitoffset;
        tracker.unitsize = unitsize;
        tracker.unitsubcount = unitsubcount;
        tracker.stride = stride;
        tracker.typebytesize = getTypeBytes();

        int nextoffset = put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);

        tracker.endposition = buffer.position() - 1;

        return nextoffset;
    }

    public int put(VLBufferTracker tracker, VLListType<VLVTypeVariable> data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.offset = buffer.position();
        tracker.inputoffest = arrayoffset;
        tracker.count = arraycount;
        tracker.unitoffset = unitoffset;
        tracker.unitsize = unitsize;
        tracker.unitsubcount = unitsubcount;
        tracker.stride = stride;
        tracker.typebytesize = getTypeBytes();

        int nextoffset = put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);

        tracker.endposition = buffer.position() - 1;

        return nextoffset;
    }

    public void update(VLBufferTracker tracker, byte[] data){
        buffer.position(tracker.offset);
        put(data, 0, tracker.count);
    }

    public void update(VLBufferTracker tracker, short[] data){
        buffer.position(tracker.offset);
        put(data, 0, tracker.count);
    }

    public void update(VLBufferTracker tracker, int[] data){
        buffer.position(tracker.offset);
        put(data, 0, tracker.count);
    }

    public void update(VLBufferTracker tracker, long[] data){
        buffer.position(tracker.offset);
        put(data, 0, tracker.count);
    }

    public void update(VLBufferTracker tracker, float[] data){
        buffer.position(tracker.offset);
        put(data, 0, tracker.count);
    }

    public void update(VLBufferTracker tracker, double[] data){
        buffer.position(tracker.offset);
        put(data, 0, tracker.count);
    }

    public void update(VLBufferTracker tracker, VLListType<VLVTypeVariable> data){
        buffer.position(tracker.offset);
        put(data, 0, tracker.count);
    }

    public int updateInterleaved(VLBufferTracker tracker, byte[] data){
        buffer.position(tracker.offset);
        return put(data, tracker.inputoffest, tracker.count, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public int updateInterleaved(VLBufferTracker tracker, short[] data){
        buffer.position(tracker.offset);
        return put(data, tracker.inputoffest, tracker.count, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public int updateInterleaved(VLBufferTracker tracker, int[] data){
        buffer.position(tracker.offset);
        return put(data, tracker.inputoffest, tracker.count, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public int updateInterleaved(VLBufferTracker tracker, long[] data){
        buffer.position(tracker.offset);
        return put(data, tracker.inputoffest, tracker.count, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public int updateInterleaved(VLBufferTracker tracker, float[] data){
        buffer.position(tracker.offset);
        return put(data, tracker.inputoffest, tracker.count, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public int updateInterleaved(VLBufferTracker tracker, double[] data){
        buffer.position(tracker.offset);
        return put(data, tracker.inputoffest, tracker.count, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public int updateInterleaved(VLBufferTracker tracker, VLListType<VLVTypeVariable> data){
        buffer.position(tracker.offset);
        return put(data, tracker.inputoffest, tracker.count, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public ELEMENT read(int index){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(byte[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(short[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(int[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(long[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(float[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(double[] data, int offset, int count){
        throw new RuntimeException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public abstract void remove(int offset, int size);

    public abstract void removeInterleaved(int offset, int unitsize, int stride, int size);

    public void provider(BUFFER p){
        buffer = p;
    }

    public void position(int pos){
        buffer.position(pos);
    }

    public abstract void resize(int size);

    public void adjustPreInitCapacity(int amount){
        preInitCapacity += amount;
    }

    public BUFFER provider(){
        return buffer;
    }

    public int position(){
        return buffer.position();
    }

    public abstract int getTypeBytes();

    public void release(){
        buffer = null;
    }

    public int size(){
        return buffer.capacity();
    }

    public abstract int sizeBytes();

    public int preInitCapacity(){
        return preInitCapacity;
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        int size = size();
        int count = hint == null ? size : (int) hint;

        if(count > size){
            count = size;
        }

        src.append("[");
        src.append(getClass().getSimpleName());
        src.append("] capacity[");
        src.append(size);

        if(size == 0){
            src.append("] content[NONE]");

        }else{
            src.append("] content[");

            for(int i = 0; i < count - 1; i++){
                src.append(read(i));
                src.append(", ");
            }

            src.append(read(count - 1));
            src.append("]");
        }
    }
}