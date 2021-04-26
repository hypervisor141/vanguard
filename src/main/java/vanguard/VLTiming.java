package vanguard;

public class VLTiming{

    private static long NANOTIME;
    private static long ACCUMULATETIME;
    private static String TARGET;
    private static VLLog log;

    public static void startTiming(String target){
        TARGET = target;
        NANOTIME = System.nanoTime();

        log = new VLLog(new String[]{
                VLGlobal.LOGTAG, "GLOBALTIMING"
        });
    }

    public static void finishAccumulateTime(String prefix){
        log.append("(");
        log.append(prefix);
        log.append(")");
        log.append("ACCUMULATE(");
        log.append(ACCUMULATETIME);
        log.append("ns, ");
        log.append((long)Math.floor(ACCUMULATETIME / 1000000f));
        log.append("ms) ");
        log.printInfo();

        ACCUMULATETIME = 0;
    }

    public static void finishTiming(String prefix, boolean accumulate){
        long time = System.nanoTime();
        long diff = time - NANOTIME;

        log.append("(");
        log.append(prefix);
        log.append(")");
        log.append(TARGET);
        log.append("(");
        log.append(diff);
        log.append("ns, ");
        log.append((long)Math.floor(diff / 1000000f));
        log.append("ms) ");
        log.printInfo();

        if(accumulate){
            ACCUMULATETIME += diff;
        }
    }

    public static long timeFunction(Runnable task, int testcount, int reportpercetile, boolean enablelog){
        long time;
        long diff;
        long avg = 0;
        long max = -Long.MAX_VALUE;
        long min = Long.MAX_VALUE;
        long testperc = 0;
        long threshold = reportpercetile == 0 ? 0 : (long)(testcount * (reportpercetile / 100f));

        VLLog log = new VLLog(new String[]{
                VLGlobal.LOGTAG, "TIMING"
        });

        if(enablelog && threshold != 0){
            log.printInfo("Test progress : "+ testperc + "%");
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

            if(enablelog && threshold != 0 && e != 0 && e % threshold == 0){
                testperc += reportpercetile;
                log.printInfo("Test progress : " + testperc + "%");
            }
        }

        if(enablelog){
            log.printInfo("Test progress : 100%");
            log.printInfo("Avg : " + avg + "ns (" + (avg / 1000000f) + "ms)" + " Max : " + max + "ns (" + (max / 1000000f) + "ms)" + " Min : " + min + "ns (" + (min / 1000000f) + "ms)");
        }

        return avg;
    }

}
