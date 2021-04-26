package vanguard;

public class VLTiming{

    public static final String TAG = "VANGUARD";

    private static long NANOTIME;
    private static long ACCUMULATETIME;
    private static String TARGET;

    public static void startTiming(String target){
        TARGET = target;
        NANOTIME = System.nanoTime();
    }

    public static String finishAccumulateTime(String prefix, String tag){
        String str = "(" + prefix + ")" + "ACCUMULATE(" + ACCUMULATETIME + "ns, " + (long)Math.floor(ACCUMULATETIME / 1000000f) + "ms) ";
        ACCUMULATETIME = 0;

        if(tag != null){
            System.out.print("[");
            System.out.print(TAG);
            System.out.print("] ");
            System.out.print(str);
        }

        return str;
    }

    public static String finishTiming(String prefix, String tag, boolean accumulate){
        long time = System.nanoTime();
        long diff = time - NANOTIME;
        String str = "(" + prefix + ")" + TARGET + "(" + diff + "ns, " + (long)Math.floor(diff / 1000000f) + "ms) ";

        if(accumulate){
            ACCUMULATETIME += diff;
        }

        if(tag != null){
            System.out.print("[");
            System.out.print(TAG);
            System.out.print("] ");
            System.out.print(str);
        }

        return str;
    }

    public static long timeFunction(Runnable task, int testcount, int reportpercetile, boolean log){
        long time;
        long diff;
        long avg = 0;
        long max = -Long.MAX_VALUE;
        long min = Long.MAX_VALUE;
        long testperc = 0;
        long threshold = reportpercetile == 0 ? 0 : (long)(testcount * (reportpercetile / 100f));

        VLLog logger = new VLLog(TAG);

        if(log && threshold != 0){
            logger.printInfo("Test progress : "+ testperc + "%");
        }

        for(int e = 0; e < testcount; e++){
            time = System.nanoTime();
            task.run();
            diff = System.nanoTime() - time;

            if(max < diff){
                max = diff;
            }

            if(min > diff){
                min = diff;
            }

            if(e == 0){
                avg = diff;

            }else{
                avg = (avg + diff) / 2;
            }

            if(log && threshold != 0 && e != 0 && e % threshold == 0){
                testperc += reportpercetile;
                logger.printInfo("Test progress : " + testperc + "%");
            }
        }

        if(log){
            logger.printInfo("Test progress : 100%");
            logger.printInfo("Avg : " + avg + "ns (" + (avg / 1000000f) + "ms)" + " Max : " + max + "ns (" + (max / 1000000f) + "ms)" + " Min : " + min + "ns (" + (min / 1000000f) + "ms)");
        }

        return avg;
    }

}
