package hypervisor.vanguard.buffer;

import hypervisor.vanguard.list.arraybacked.VLListType;
import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;
import hypervisor.vanguard.utils.VLLoggable;
import hypervisor.vanguard.variable.VLVTypeVariable;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public abstract class VLBuffer<ELEMENT extends Number, BUFFER extends Buffer> implements VLLoggable, VLCopyable<VLBuffer<ELEMENT, BUFFER>> {

    public BUFFER buffer;
    protected int preInitCapacity;
    protected int vsize;
    protected int resizeoverhead;

    protected VLBuffer(int resizeoverhead){
        this.resizeoverhead = resizeoverhead;
        vsize = 0;
        preInitCapacity = 0;
    }

    protected VLBuffer(){

    }

    public abstract ByteBuffer initialize(int capacity, ByteOrder order);

    public ByteBuffer initialize(ByteOrder order){
        return initialize(preInitCapacity, order);
    }

    public abstract void initialize(ByteBuffer buffer);

    public void initialize(BUFFER buffer){
        this.buffer = buffer;
    }

    public void put(byte data){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(short data){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(int data){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(long data){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(float data){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(double data){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(VLVTypeVariable data){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
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

    public void put(byte[] data, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(short[] data, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(int[] data, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(long[] data, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(float[] data, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(double[] data, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void put(VLListType<VLVTypeVariable> data, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
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
        tracker.srcoffset = 0;
        tracker.srccount = 1;
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + 1;

        put(data);
    }

    public void put(VLBufferTracker tracker, short data){
        tracker.srcoffset = 0;
        tracker.srccount = 1;
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + 1;

        put(data);
    }

    public void put(VLBufferTracker tracker, int data){
        tracker.srcoffset = 0;
        tracker.srccount = 1;
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + 1;

        put(data);
    }

    public void put(VLBufferTracker tracker, long data){
        tracker.srcoffset = 0;
        tracker.srccount = 1;
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + 1;

        put(data);
    }

    public void put(VLBufferTracker tracker, float data){
        tracker.srcoffset = 0;
        tracker.srccount = 1;
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + 1;

        put(data);
    }

    public void put(VLBufferTracker tracker, double data){
        tracker.srcoffset = 0;
        tracker.srccount = 1;
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + 1;

        put(data);
    }

    public void put(VLBufferTracker tracker, VLVTypeVariable data){
        tracker.srcoffset = 0;
        tracker.srccount = 1;
        tracker.offset = buffer.position();
        tracker.count = 1;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + 1;

        put(data);
    }

    public void put(VLBufferTracker tracker, byte[] data){
        tracker.srcoffset = 0;
        tracker.srccount = data.length;
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + tracker.count;
        put(data);
    }

    public void put(VLBufferTracker tracker, short[] data){
        tracker.srcoffset = 0;
        tracker.srccount = data.length;
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + tracker.count;

        put(data);
    }

    public void put(VLBufferTracker tracker, int[] data){
        tracker.srcoffset = 0;
        tracker.srccount = data.length;
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + tracker.count;

        put(data);
    }

    public void put(VLBufferTracker tracker, long[] data){
        tracker.srcoffset = 0;
        tracker.srccount = data.length;
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + tracker.count;

        put(data);
    }

    public void put(VLBufferTracker tracker, float[] data){
        tracker.srcoffset = 0;
        tracker.srccount = data.length;
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + tracker.count;

        put(data);
    }

    public void put(VLBufferTracker tracker, double[] data){
        tracker.srcoffset = 0;
        tracker.srccount = data.length;
        tracker.offset = buffer.position();
        tracker.count = data.length;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + tracker.count;

        put(data);
    }

    public void put(VLBufferTracker tracker, VLListType<VLVTypeVariable> data){
        tracker.srcoffset = 0;
        tracker.srccount = data.size();
        tracker.offset = buffer.position();
        tracker.count = data.size();
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + tracker.count;

        put(data);
    }

    public void put(VLBufferTracker tracker, byte[] data, int arrayoffset, int arraycount){
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = arraycount;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + arraycount;

        put(data, arrayoffset, arraycount);
    }

    public void put(VLBufferTracker tracker, short[] data, int arrayoffset, int arraycount){
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = arraycount;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + arraycount;

        put(data, arrayoffset, arraycount);
    }

    public void put(VLBufferTracker tracker, int[] data, int arrayoffset, int arraycount){
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = arraycount;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + arraycount;

        put(data, arrayoffset, arraycount);
    }

    public void put(VLBufferTracker tracker, long[] data, int arrayoffset, int arraycount){
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = arraycount;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + arraycount;

        put(data, arrayoffset, arraycount);
    }

    public void put(VLBufferTracker tracker, float[] data, int arrayoffset, int arraycount){
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = arraycount;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + arraycount;

        put(data, arrayoffset, arraycount);
    }

    public void put(VLBufferTracker tracker, double[] data, int arrayoffset, int arraycount){
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = arraycount;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + arraycount;

        put(data, arrayoffset, arraycount);
    }

    public void put(VLBufferTracker tracker, VLListType<VLVTypeVariable> data, int arrayoffset, int arraycount){
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = arraycount;
        tracker.unitoffset = 0;
        tracker.unitsize = 1;
        tracker.unitsubcount = 1;
        tracker.stride = 1;
        tracker.typebytesize = getTypeBytes();
        tracker.endposition = tracker.offset + arraycount;

        put(data, arrayoffset, arraycount);
    }

    public int put(VLBufferTracker tracker, byte[] data, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = unitsubcount * (arraycount / stride);
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
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = unitsubcount * (arraycount / stride);
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
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = unitsubcount * (arraycount / stride);
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
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = unitsubcount * (arraycount / stride);
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
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = unitsubcount * (arraycount / stride);
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
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = unitsubcount * (arraycount / stride);
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
        tracker.srcoffset = arrayoffset;
        tracker.srccount = arraycount;
        tracker.offset = buffer.position();
        tracker.count = unitsubcount * (arraycount / stride);
        tracker.unitoffset = unitoffset;
        tracker.unitsize = unitsize;
        tracker.unitsubcount = unitsubcount;
        tracker.stride = stride;
        tracker.typebytesize = getTypeBytes();

        int nextoffset = put(data, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);

        tracker.endposition = buffer.position() - 1;

        return nextoffset;
    }

    public void update(VLBufferTracker tracker, byte data){
        buffer.position(tracker.offset);
        put(data);
    }

    public void update(VLBufferTracker tracker, short data){
        buffer.position(tracker.offset);
        put(data);
    }

    public void update(VLBufferTracker tracker, int data){
        buffer.position(tracker.offset);
        put(data);
    }

    public void update(VLBufferTracker tracker, long data){
        buffer.position(tracker.offset);
        put(data);
    }

    public void update(VLBufferTracker tracker, float data){
        buffer.position(tracker.offset);
        put(data);
    }

    public void update(VLBufferTracker tracker, double data){
        buffer.position(tracker.offset);
        put(data);
    }

    public void update(VLBufferTracker tracker, VLVTypeVariable data){
        buffer.position(tracker.offset);
        put(data);
    }

    public void update(VLBufferTracker tracker, byte[] data){
        buffer.position(tracker.offset);
        put(data, tracker.srcoffset, tracker.srccount, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public void update(VLBufferTracker tracker, short[] data){
        buffer.position(tracker.offset);
        put(data, tracker.srcoffset, tracker.srccount, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public void update(VLBufferTracker tracker, int[] data){
        buffer.position(tracker.offset);
        put(data, tracker.srcoffset, tracker.srccount, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public void update(VLBufferTracker tracker, long[] data){
        buffer.position(tracker.offset);
        put(data, tracker.srcoffset, tracker.srccount, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public void update(VLBufferTracker tracker, float[] data){
        buffer.position(tracker.offset);
        put(data, tracker.srcoffset, tracker.srccount, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public void update(VLBufferTracker tracker, double[] data){
        buffer.position(tracker.offset);
        put(data, tracker.srcoffset, tracker.srccount, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public void update(VLBufferTracker tracker, VLListType<VLVTypeVariable> data){
        buffer.position(tracker.offset);
        put(data, tracker.srcoffset, tracker.srccount, tracker.unitoffset, tracker.unitsize, tracker.unitsubcount, tracker.stride);
    }

    public ELEMENT read(int index){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(byte[] results, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(short[] results, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(int[] results, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(long[] results, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(float[] results, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(double[] results, int arrayoffset, int arraycount){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(VLBufferTracker tracker, byte[] results, int offset){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(VLBufferTracker tracker, short[] results, int offset){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(VLBufferTracker tracker, int[] results, int offset){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(VLBufferTracker tracker, long[] results, int offset){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(VLBufferTracker tracker, float[] results, int offset){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    public void read(VLBufferTracker tracker, double[] results, int offset){
        throw new UnsupportedOperationException("This method is not meant for this buffer type, current buffer type operates on " + buffer.getClass().getSimpleName());
    }

    protected final void checkVirtualAttributes(int expansionsize){
        int currentsize = size();
        int finalposition = position() + expansionsize;

        if(vsize <= finalposition){
            vsize = finalposition;
        }
        if(resizeoverhead > 0 && finalposition > currentsize){
            resize(currentsize + expansionsize + resizeoverhead);
        }
    }

    public abstract void remove(int offset, int size);

    public abstract void remove(int offset, int unitsize, int stride, int size);

    public void remove(VLBufferTracker tracker){
        remove(tracker.offset, tracker.count);
    }

    public void removeInterleaved(VLBufferTracker tracker){
        remove(tracker.offset, tracker.unitsize, tracker.stride, tracker.count);
    }

    public void position(int pos){
        buffer.position(pos);
    }

    public void clear(){
        buffer.clear();
        vsize = 0;
    }

    public void positionVirtualSize(){
        position(vsize);
    }

    public void virtualSize(int size){
        vsize = size;
    }

    public void resizeOverhead(int overhead){
        resizeoverhead = overhead;
    }

    public abstract void resize(int size);

    public int position(){
        return buffer.position();
    }

    public int resizeOverhead(){
        return resizeoverhead;
    }

    public int virtualSize(){
        return vsize;
    }

    public void adjustPreInitCapacity(int amount){
        preInitCapacity += amount;
    }

    public abstract int getTypeBytes();

    public void destroy(){
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
    public void log(VLLog log, Object data){
        int size = size();
        int count = data == null ? size : (int) data;

        if(count > size){
            count = size;
        }

        log.append("[");
        log.append(getClass().getSimpleName());
        log.append("] capacity[");
        log.append(size);

        if(size == 0){
            log.append("] content[NONE]");

        }else{
            log.append("] content[");

            for(int i = 0; i < count - 1; i++){
                log.append(read(i));
                log.append(", ");
            }

            log.append(read(count - 1));
            log.append("]");
        }
    }
}