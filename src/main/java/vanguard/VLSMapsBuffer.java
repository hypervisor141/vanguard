package vanguard;

public class VLSMapsBuffer{

    public static abstract class Base<TYPE extends VLArray<?, ?>> extends VLSyncMap<TYPE, VLBuffer<?, ?>>{

        public int bufferoffset;

        public Base(VLBuffer<?, ?> target, int bufferoffset){
            super(target);
            this.bufferoffset = bufferoffset;
        }
    }

    public static abstract class BaseInterleaved<TYPE extends VLArray<?, ?>> extends VLSyncMap<TYPE, VLBuffer<?, ?>>{

        public int bufferoffset;
        public int arrayoffset;
        public int arraycount;
        public int unitoffset;
        public int unitsize;
        public int unitsubcount;
        public int stride;

        public BaseInterleaved(VLBuffer<?, ?> target, int bufferoffset, int arrayoffset, int arraycount, int unitoffset,
                               int unitsize, int unitsubcount, int stride){
            super(target);

            this.bufferoffset = bufferoffset;
            this.arrayoffset = arrayoffset;
            this.arraycount = arraycount;
            this.unitoffset = unitoffset;
            this.unitsize = unitsize;
            this.unitsubcount = unitsubcount;
            this.stride = stride;
        }
    }

    public static class Matrix<TYPE extends VLVMatrix> extends VLSyncMap<TYPE, VLBuffer<?, ?>>{

        public int rowindex;
        public int bufferoffset;
        public int setarraystartindex;
        public int setarrayendindex;
        public int unitoffset;
        public int unitsize;
        public int unitsubcount;
        public int stride;

        public Matrix(VLBuffer<?, ?> target, int rowindex, int bufferoffset, int setarraystartindex, int setarrayendindex,
                      int unitoffset, int unitsize, int unitsubcount, int stride){
            super(target);

            this.rowindex = rowindex;
            this.bufferoffset = bufferoffset;
            this.setarraystartindex = setarraystartindex;
            this.setarrayendindex = setarrayendindex;
            this.unitoffset = unitoffset;
            this.unitsize = unitsize;
            this.unitsubcount = unitsubcount;
            this.stride = stride;
        }

        @Override
        public void sync(TYPE source){
            target.position(bufferoffset + setarraystartindex);
            target.put(source.getRow(rowindex), setarraystartindex, setarrayendindex - setarraystartindex, unitsize, unitoffset, unitsubcount, stride);
        }
    }

    public static class ShortArray extends Base<VLArray<Short, short[]>>{
        
        public ShortArray(VLBuffer<?, ?> target, int bufferoffset){
            super(target, bufferoffset);
            this.bufferoffset = bufferoffset;
        }

        @Override
        public void sync(VLArray<Short, short[]> source){
            target.position(bufferoffset);
            target.put(source.provider());
        }
    }

    public static class ShortArrayI extends BaseInterleaved<VLArray<Short, short[]>>{

        public ShortArrayI(VLBuffer<?, ?> target, int bufferoffset, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
            super(target, bufferoffset, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }

        @Override
        public void sync(VLArray<Short, short[]> source){
            target.position(bufferoffset);
            target.put(source.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }

    public static class IntArray extends Base<VLArray<Integer, int[]>>{

        public IntArray(VLBuffer<?, ?> target, int bufferoffset){
            super(target, bufferoffset);
            this.bufferoffset = bufferoffset;
        }

        @Override
        public void sync(VLArray<Integer, int[]> source){
            target.position(bufferoffset);
            target.put(source.provider());
        }
    }

    public static class IntArrayI extends BaseInterleaved<VLArray<Integer, int[]>>{

        public IntArrayI(VLBuffer<?, ?> target, int bufferoffset, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
            super(target, bufferoffset, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }

        @Override
        public void sync(VLArray<Integer, int[]> source){
            target.position(bufferoffset);
            target.put(source.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }

    public static class LongArray extends Base<VLArray<Long, long[]>>{

        public LongArray(VLBuffer<?, ?> target, int bufferoffset){
            super(target, bufferoffset);
            this.bufferoffset = bufferoffset;
        }

        @Override
        public void sync(VLArray<Long, long[]> source){
            target.position(bufferoffset);
            target.put(source.provider());
        }
    }

    public static class LongArrayI extends BaseInterleaved<VLArray<Long, long[]>>{

        public LongArrayI(VLBuffer<?, ?> target, int bufferoffset, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
            super(target, bufferoffset, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }

        @Override
        public void sync(VLArray<Long, long[]> source){
            target.position(bufferoffset);
            target.put(source.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }

    public static class FloatArray extends Base<VLArray<Float, float[]>>{

        public FloatArray(VLBuffer<?, ?> target, int bufferoffset){
            super(target, bufferoffset);
            this.bufferoffset = bufferoffset;
        }

        @Override
        public void sync(VLArray<Float, float[]> source){
            target.position(bufferoffset);
            target.put(source.provider());
        }
    }

    public static class FloatArrayI extends BaseInterleaved<VLArray<Float, float[]>>{

        public FloatArrayI(VLBuffer<?, ?> target, int bufferoffset, int arrayoffset, int arraycount, int unitoffset,
                           int unitsize, int unitsubcount, int stride){

            super(target, bufferoffset, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }

        @Override
        public void sync(VLArray<Float, float[]> source){
            target.position(bufferoffset);
            target.put(source.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }

    public static class DoubleArray extends Base<VLArray<Double, double[]>>{

        public DoubleArray(VLBuffer<?, ?> target, int bufferoffset){
            super(target, bufferoffset);
            this.bufferoffset = bufferoffset;
        }

        @Override
        public void sync(VLArray<Double, double[]> source){
            target.position(bufferoffset);
            target.put(source.provider());
        }
    }

    public static class DoubleArrayI extends BaseInterleaved<VLArray<Double, double[]>>{

        public DoubleArrayI(VLBuffer<?, ?> target, int bufferoffset, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride){
            super(target, bufferoffset, arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }

        @Override
        public void sync(VLArray<Double, double[]> source){
            target.position(bufferoffset);
            target.put(source.provider(), arrayoffset, arraycount, unitoffset, unitsize, unitsubcount, stride);
        }
    }
}
