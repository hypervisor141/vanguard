package hypervisor.vanguard.array;

@SuppressWarnings("unused")
public final class VLArrayUtils{

    public static void addInPlace(int index, int virtualsize, boolean[] array, boolean target){
        spaceOutInPlace(array, virtualsize, index, 1);
        array[index] = target;
    }

    public static void addInPlace(int index, int virtualsize, byte[] array, byte target){
        spaceOutInPlace(array, virtualsize, index, 1);
        array[index] = target;
    }

    public static void addInPlace(int index, int virtualsize, short[] array, short target){
        spaceOutInPlace(array, virtualsize, index, 1);
        array[index] = target;
    }

    public static void addInPlace(int index, int virtualsize, int[] array, int target){
        spaceOutInPlace(array, virtualsize, index, 1);
        array[index] = target;
    }

    public static void addInPlace(int index, int virtualsize, long[] array, long target){
        spaceOutInPlace(array, virtualsize, index, 1);
        array[index] = target;
    }

    public static void addInPlace(int index, int virtualsize, float[] array, float target){
        spaceOutInPlace(array, virtualsize, index, 1);
        array[index] = target;
    }

    public static void addInPlace(int index, int virtualsize, double[] array, double target){
        spaceOutInPlace(array, virtualsize, index, 1);
        array[index] = target;
    }

    public static void addInPlace(int index, int virtualsize, Object[] array, Object target){
        spaceOutInPlace(array, virtualsize, index, 1);
        array[index] = target;
    }

    public static void addInPlace(int arrayoffset, int targetoffset, int virtualsize, boolean[] array, boolean[] target, int count){
        spaceOutInPlace(array, virtualsize, arrayoffset, count);
        System.arraycopy(target, targetoffset, array, arrayoffset, count);
    }

    public static void addInPlace(int arrayoffset, int targetoffset, int virtualsize, byte[] array, byte[] target, int count){
        spaceOutInPlace(array, virtualsize, arrayoffset, count);
        System.arraycopy(target, targetoffset, array, arrayoffset, count);
    }

    public static void addInPlace(int arrayoffset, int targetoffset, int virtualsize, short[] array, short[] target, int count){
        spaceOutInPlace(array, virtualsize, arrayoffset, count);
        System.arraycopy(target, targetoffset, array, arrayoffset, count);
    }

    public static void addInPlace(int arrayoffset, int targetoffset, int virtualsize, int[] array, int[] target, int count){
        spaceOutInPlace(array, virtualsize, arrayoffset, count);
        System.arraycopy(target, targetoffset, array, arrayoffset, count);
    }

    public static void addInPlace(int arrayoffset, int targetoffset, int virtualsize, long[] array, long[] target, int count){
        spaceOutInPlace(array, virtualsize, arrayoffset, count);
        System.arraycopy(target, targetoffset, array, arrayoffset, count);
    }

    public static void addInPlace(int arrayoffset, int targetoffset, int virtualsize, float[] array, float[] target, int count){
        spaceOutInPlace(array, virtualsize, arrayoffset, count);
        System.arraycopy(target, targetoffset, array, arrayoffset, count);
    }

    public static void addInPlace(int arrayoffset, int targetoffset, int virtualsize, double[] array, double[] target, int count){
        spaceOutInPlace(array, virtualsize, arrayoffset, count);
        System.arraycopy(target, targetoffset, array, arrayoffset, count);
    }

    public static void addInPlace(int arrayoffset, int targetoffset, int virtualsize, Object[] array, Object[] target, int count){
        spaceOutInPlace(array, virtualsize, arrayoffset, count);
        System.arraycopy(target, targetoffset, array, arrayoffset, count);
    }

    public static boolean[] slice(boolean[] array, int from, int to){
        boolean[] sliced = new boolean[to - from];
        System.arraycopy(array, from, sliced, 0, sliced.length);

        return sliced;
    }

    public static byte[] slice(byte[] array, int from, int to){
        byte[] sliced = new byte[to - from];
        System.arraycopy(array, from, sliced, 0, sliced.length);

        return sliced;
    }

    public static short[] slice(short[] array, int from, int to){
        short[] sliced = new short[to - from];
        System.arraycopy(array, from, sliced, 0, sliced.length);

        return sliced;
    }

    public static int[] slice(int[] array, int from, int to){
        int[] sliced = new int[to - from];
        System.arraycopy(array, from, sliced, 0, sliced.length);

        return sliced;
    }

    public static long[] slice(long[] array, int from, int to){
        long[] sliced = new long[to - from];
        System.arraycopy(array, from, sliced, 0, sliced.length);

        return sliced;
    }

    public static float[] slice(float[] array, int from, int to){
        float[] sliced = new float[to - from];
        System.arraycopy(array, from, sliced, 0, sliced.length);

        return sliced;
    }

    public static double[] slice(double[] array, int from, int to){
        double[] sliced = new double[to - from];
        System.arraycopy(array, from, sliced, 0, sliced.length);

        return sliced;
    }

    public static Object[] slice(Object[] array, int from, int to){
        Object[] sliced = new Object[to - from];
        System.arraycopy(array, from, sliced, 0, sliced.length);

        return sliced;
    }

    public static boolean[] spaceOut(boolean[] array, int index, int count){
        boolean[] newarray = new boolean[array.length + count];

        System.arraycopy(array, 0, newarray, 0, index);
        System.arraycopy(array, index, newarray, index + count, array.length - index);

        return newarray;
    }

    public static byte[] spaceOut(byte[] array, int index, int count){
        byte[] newarray = new byte[array.length + count];

        System.arraycopy(array, 0, newarray, 0, index);
        System.arraycopy(array, index, newarray, index + count, array.length - index);

        return newarray;
    }

    public static short[] spaceOut(short[] array, int index, int count){
        short[] newarray = new short[array.length + count];

        System.arraycopy(array, 0, newarray, 0, index);
        System.arraycopy(array, index, newarray, index + count, array.length - index);

        return newarray;
    }

    public static int[] spaceOut(int[] array, int index, int count){
        int[] newarray = new int[array.length + count];

        System.arraycopy(array, 0, newarray, 0, index);
        System.arraycopy(array, index, newarray, index + count, array.length - index);

        return newarray;
    }

    public static long[] spaceOut(long[] array, int index, int count){
        long[] newarray = new long[array.length + count];

        System.arraycopy(array, 0, newarray, 0, index);
        System.arraycopy(array, index, newarray, index + count, array.length - index);

        return newarray;
    }

    public static float[] spaceOut(float[] array, int index, int count){
        float[] newarray = new float[array.length + count];

        System.arraycopy(array, 0, newarray, 0, index);
        System.arraycopy(array, index, newarray, index + count, array.length - index);

        return newarray;
    }

    public static double[] spaceOut(double[] array, int index, int count){
        double[] newarray = new double[array.length + count];

        System.arraycopy(array, 0, newarray, 0, index);
        System.arraycopy(array, index, newarray, index + count, array.length - index);

        return newarray;
    }

    public static Object[] spaceOut(Object[] array, int index, int count){
        Object[] newarray = new Object[array.length + count];

        System.arraycopy(array, 0, newarray, 0, index);
        System.arraycopy(array, index, newarray, index + count, array.length - index);

        return newarray;
    }

    public static void spaceOutInPlace(Object array, int dynamicsize, int index, int count){
        System.arraycopy(array, index, array, index + count, dynamicsize - index);
    }

    public static int indexOf(boolean[] array, boolean element){
        int size = array.length;

        for(int i = 0; i < size; i++){
            if(array[i] == element){
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(byte[] array, byte element){
        int size = array.length;

        for(int i = 0; i < size; i++){
            if(array[i] == element){
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(short[] array, short element){
        int size = array.length;

        for(int i = 0; i < size; i++){
            if(array[i] == element){
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(int[] array, int element){
        int size = array.length;

        for(int i = 0; i < size; i++){
            if(array[i] == element){
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(long[] array, long element){
        int size = array.length;

        for(int i = 0; i < size; i++){
            if(array[i] == element){
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(float[] array, float element){
        int size = array.length;

        for(int i = 0; i < size; i++){
            if(array[i] == element){
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(double[] array, double element){
        int size = array.length;

        for(int i = 0; i < size; i++){
            if(array[i] == element){
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(Object[] array, Object element){
        int size = array.length;

        for(int i = 0; i < size; i++){
            if(array[i].equals(element)){
                return i;
            }
        }

        return -1;
    }

    public static boolean[] remove(boolean[] array, int index, int count){
        boolean[] newarray = new boolean[array.length - count];
        int lastindex = array.length - 1;

        if(index < lastindex && index > 0){
            System.arraycopy(array, 0, newarray, 0, index);
            System.arraycopy(array, index + count, newarray, index, newarray.length - index);

        }else if(index == lastindex){
            System.arraycopy(array, 0, newarray, 0, newarray.length);

        }else{
            System.arraycopy(array, count, newarray, 0, newarray.length);
        }

        return newarray;
    }

    public static byte[] remove(byte[] array, int index, int count){
        byte[] newarray = new byte[array.length - count];
        int lastindex = array.length - 1;

        if(index < lastindex && index > 0){
            System.arraycopy(array, 0, newarray, 0, index);
            System.arraycopy(array, index + count, newarray, index, newarray.length - index);

        }else if(index == lastindex){
            System.arraycopy(array, 0, newarray, 0, newarray.length);

        }else{
            System.arraycopy(array, count, newarray, 0, newarray.length);
        }

        return newarray;
    }

    public static short[] remove(short[] array, int index, int count){
        short[] newarray = new short[array.length - count];
        int lastindex = array.length - 1;

        if(index < lastindex && index > 0){
            System.arraycopy(array, 0, newarray, 0, index);
            System.arraycopy(array, index + count, newarray, index, newarray.length - index);

        }else if(index == lastindex){
            System.arraycopy(array, 0, newarray, 0, newarray.length);

        }else{
            System.arraycopy(array, count, newarray, 0, newarray.length);
        }

        return newarray;
    }

    public static int[] remove(int[] array, int index, int count){
        int[] newarray = new int[array.length - count];
        int lastindex = array.length - 1;

        if(index < lastindex && index > 0){
            System.arraycopy(array, 0, newarray, 0, index);
            System.arraycopy(array, index + count, newarray, index, newarray.length - index);

        }else if(index == lastindex){
            System.arraycopy(array, 0, newarray, 0, newarray.length);

        }else{
            System.arraycopy(array, count, newarray, 0, newarray.length);
        }

        return newarray;
    }

    public static long[] remove(long[] array, int index, int count){
        long[] newarray = new long[array.length - count];
        int lastindex = array.length - 1;

        if(index < lastindex && index > 0){
            System.arraycopy(array, 0, newarray, 0, index);
            System.arraycopy(array, index + count, newarray, index, newarray.length - index);

        }else if(index == lastindex){
            System.arraycopy(array, 0, newarray, 0, newarray.length);

        }else{
            System.arraycopy(array, count, newarray, 0, newarray.length);
        }

        return newarray;
    }

    public static float[] remove(float[] array, int index, int count){
        float[] newarray = new float[array.length - count];
        int lastindex = array.length - 1;

        if(index < lastindex && index > 0){
            System.arraycopy(array, 0, newarray, 0, index);
            System.arraycopy(array, index + count, newarray, index, newarray.length - index);

        }else if(index == lastindex){
            System.arraycopy(array, 0, newarray, 0, newarray.length);

        }else{
            System.arraycopy(array, count, newarray, 0, newarray.length);
        }

        return newarray;
    }

    public static double[] remove(double[] array, int index, int count){
        double[] newarray = new double[array.length - count];
        int lastindex = array.length - 1;

        if(index < lastindex && index > 0){
            System.arraycopy(array, 0, newarray, 0, index);
            System.arraycopy(array, index + count, newarray, index, newarray.length - index);

        }else if(index == lastindex){
            System.arraycopy(array, 0, newarray, 0, newarray.length);

        }else{
            System.arraycopy(array, count, newarray, 0, newarray.length);
        }

        return newarray;
    }

    public static Object[] remove(Object[] array, int index, int count){
        Object[] newarray = new Object[array.length - count];
        int lastindex = array.length - 1;

        if(index < lastindex && index > 0){
            System.arraycopy(array, 0, newarray, 0, index);
            System.arraycopy(array, index + count, newarray, index, newarray.length - index);

        }else if(index == lastindex){
            System.arraycopy(array, 0, newarray, 0, newarray.length);

        }else{
            System.arraycopy(array, count, newarray, 0, newarray.length);
        }

        return newarray;
    }

    public static boolean[] remove(boolean[] array, boolean element){
        int index = indexOf(array, element);

        if(index != -1){
            return remove(array, index, 1);

        }else{
            return array;
        }
    }

    public static byte[] remove(byte[] array, byte element){
        int index = indexOf(array, element);

        if(index != -1){
            return remove(array, index, 1);

        }else{
            return array;
        }
    }

    public static short[] remove(short[] array, short element){
        int index = indexOf(array, element);

        if(index != -1){
            return remove(array, index, 1);

        }else{
            return array;
        }
    }

    public static int[] remove(int[] array, Integer element){
        int index = indexOf(array, element);

        if(index != -1){
            return remove(array, index, 1);

        }else{
            return array;
        }
    }

    public static long[] remove(long[] array, long element){
        int index = indexOf(array, element);

        if(index != -1){
            return remove(array, index, 1);

        }else{
            return array;
        }
    }

    public static float[] remove(float[] array, float element){
        int index = indexOf(array, element);

        if(index != -1){
            return remove(array, index, 1);

        }else{
            return array;
        }
    }

    public static double[] remove(double[] array, double element){
        int index = indexOf(array, element);

        if(index != -1){
            return remove(array, index, 1);

        }else{
            return array;
        }
    }

    public static Object[] remove(Object[] array, Object element){
        int index = indexOf(array, element);

        if(index != -1){
            return remove(array, index, 1);

        }else{
            return array;
        }
    }

    public static boolean[] resize(boolean[] array, int count){
        boolean[] resized = new boolean[count];
        System.arraycopy(array, 0, resized, 0, array.length);

        return resized;
    }

    public static byte[] resize(byte[] array, int count){
        byte[] resized = new byte[count];
        System.arraycopy(array, 0, resized, 0, array.length);

        return resized;
    }

    public static short[] resize(short[] array, int count){
        short[] resized = new short[count];
        System.arraycopy(array, 0, resized, 0, array.length);

        return resized;
    }

    public static int[] resize(int[] array, int count){
        int[] resized = new int[count];
        System.arraycopy(array, 0, resized, 0, array.length);

        return resized;
    }

    public static long[] resize(long[] array, int count){
        long[] resized = new long[count];
        System.arraycopy(array, 0, resized, 0, array.length);

        return resized;
    }

    public static float[] resize(float[] array, int count){
        float[] resized = new float[count];
        System.arraycopy(array, 0, resized, 0, array.length);

        return resized;
    }

    public static double[] resize(double[] array, int count){
        double[] resized = new double[count];
        System.arraycopy(array, 0, resized, 0, array.length);

        return resized;
    }

    public static Object[] resize(Object[] array, int count){
        Object[] resized = new Object[count];
        System.arraycopy(array, 0, resized, 0, array.length);

        return resized;
    }
}
