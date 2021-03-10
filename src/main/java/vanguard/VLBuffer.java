package vanguard;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public abstract class VLBuffer<ELEMENT extends Number, BUFFER extends Buffer> implements VLStringify{

    protected BUFFER buffer;

    public VLBuffer(int capacity){
        initialize(capacity);
    }

    public VLBuffer(){

    }

    public final VLBuffer<ELEMENT, BUFFER> initialize(int capacity){
        return initialize(VLIOUtils.makeDirectByteBuffer(capacity * getTypeBytes()));
    }

    public abstract VLBuffer<ELEMENT, BUFFER> initialize(ByteBuffer buffer);

    public void put(byte data){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(short data){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(int data){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(long data){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(float data){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(double data){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(VLVTypeVariable data){
        throw new RuntimeException("Invalid operation.");
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
        throw new RuntimeException("Invalid operation.");
    }

    public void put(short[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(int[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(long[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(float[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(double[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void put(VLListType<VLVTypeVariable> data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
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

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, byte data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(1);
        
        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, short data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(1);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, int data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(1);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, long data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(1);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, float data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(1);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, double data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(1);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, VLVTypeVariable data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(1);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, byte[] data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(data.length);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, short[] data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(data.length);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, int[] data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(data.length);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, long[] data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(data.length);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, float[] data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(data.length);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, double[] data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(data.length);

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, VLListType<VLVTypeVariable> data){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.count(data.size());

        put(data);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, byte[] data, int offset, int count){
        tracker.buffer(this);
        tracker.offset(offset);
        tracker.count(count);

        put(data, offset, count);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, short[] data, int offset, int count){
        tracker.buffer(this);
        tracker.offset(offset);
        tracker.count(count);

        put(data, offset, count);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, int[] data, int offset, int count){
        tracker.buffer(this);
        tracker.offset(offset);
        tracker.count(count);

        put(data, offset, count);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, long[] data, int offset, int count){
        tracker.buffer(this);
        tracker.offset(offset);
        tracker.count(count);

        put(data, offset, count);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, float[] data, int offset, int count){
        tracker.buffer(this);
        tracker.offset(offset);
        tracker.count(count);

        put(data, offset, count);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, double[] data, int offset, int count){
        tracker.buffer(this);
        tracker.offset(offset);
        tracker.count(count);

        put(data, offset, count);
    }

    public void put(VLBufferTracker<VLBuffer<ELEMENT, BUFFER>> tracker, VLListType<VLVTypeVariable> data, int offset, int count){
        tracker.buffer(this);
        tracker.offset(offset);
        tracker.count(count);

        put(data, offset, count);
    }

    public int put(VLBufferTrackerDetailed<VLBuffer<ELEMENT, BUFFER>> tracker, byte[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.inputoffest(arrayoffset);
        tracker.count(arraycount);
        tracker.unitoffset(unitoffset);
        tracker.unitsize(unitsize);
        tracker.unitsubcount(unitsubcount);
        tracker.stride(stride);

        return put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
    }

    public int put(VLBufferTrackerDetailed<VLBuffer<ELEMENT, BUFFER>> tracker, short[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.inputoffest(arrayoffset);
        tracker.count(arraycount);
        tracker.unitoffset(unitoffset);
        tracker.unitsize(unitsize);
        tracker.unitsubcount(unitsubcount);
        tracker.stride(stride);

        return put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
    }

    public int put(VLBufferTrackerDetailed<VLBuffer<ELEMENT, BUFFER>> tracker, int[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.inputoffest(arrayoffset);
        tracker.count(arraycount);
        tracker.unitoffset(unitoffset);
        tracker.unitsize(unitsize);
        tracker.unitsubcount(unitsubcount);
        tracker.stride(stride);

        return put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
    }

    public int put(VLBufferTrackerDetailed<VLBuffer<ELEMENT, BUFFER>> tracker, long[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.inputoffest(arrayoffset);
        tracker.count(arraycount);
        tracker.unitoffset(unitoffset);
        tracker.unitsize(unitsize);
        tracker.unitsubcount(unitsubcount);
        tracker.stride(stride);

        return put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
    }

    public int put(VLBufferTrackerDetailed<VLBuffer<ELEMENT, BUFFER>> tracker, float[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.inputoffest(arrayoffset);
        tracker.count(arraycount);
        tracker.unitoffset(unitoffset);
        tracker.unitsize(unitsize);
        tracker.unitsubcount(unitsubcount);
        tracker.stride(stride);

        return put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
    }

    public int put(VLBufferTrackerDetailed<VLBuffer<ELEMENT, BUFFER>> tracker, double[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.inputoffest(arrayoffset);
        tracker.count(arraycount);
        tracker.unitoffset(unitoffset);
        tracker.unitsize(unitsize);
        tracker.unitsubcount(unitsubcount);
        tracker.stride(stride);

        return put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
    }

    public int put(VLBufferTrackerDetailed<VLBuffer<ELEMENT, BUFFER>> tracker, VLListType<VLVTypeVariable> data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.buffer(this);
        tracker.offset(buffer.position());
        tracker.inputoffest(arrayoffset);
        tracker.count(arraycount);
        tracker.unitoffset(unitoffset);
        tracker.unitsize(unitsize);
        tracker.unitsubcount(unitsubcount);
        tracker.stride(stride);

        return put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
    }

    public ELEMENT read(int index){
        throw new RuntimeException("Invalid operation.");
    }

    public void read(byte[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void read(short[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void read(int[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void read(long[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void read(float[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
    }

    public void read(double[] data, int offset, int count){
        throw new RuntimeException("Invalid operation.");
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