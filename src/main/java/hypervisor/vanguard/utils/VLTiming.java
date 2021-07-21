package hypervisor.vanguard.utils;

public class VLTiming{

    private static long NANOTIME;
    private static long ACCUMULATETIME;
    private static VLLog log;

    public static void startTiming(String tag){
        log = new VLLog(1);
        log.addTag(tag);

        NANOTIME = System.nanoTime();
    }

    public static void finishAccumulateTime(String prefix){
        log.append("[");
        log.append(prefix);
        log.append("]");
        log.append("ACCUMULATE[");
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

        log.append("[");
        log.append(prefix);
        log.append("]");
        log.append(diff);
        log.append("ns, ");
        log.append((long)Math.floor(diff / 1000000f));
        log.append("ms) ");
        log.printInfo();

        if(accumulate){
            ACCUMULATETIME += diff;
        }
    }

    public static long timeFunction(String tag, Runnable task, int testcount, float reportpercetile){
        if(reportpercetile <= 0){
            throw new RuntimeException("Report percentile should be larger than 0.");
        }
        if(testcount <= 0){
            throw new RuntimeException("Test count should be larger than 0.");
        }

        long time;
        long diff;
        long avg = 0;
        long max = -Long.MAX_VALUE;
        long min = Long.MAX_VALUE;
        long progress = 0;
        long threshold = (long)(testcount * (reportpercetile / 100F));

        VLLog log = new VLLog(1);
        log.addTag(tag);
        log.printInfo("Test progress : "+ progress + "%");

        for(int i = 0; i < testcount; i++){
            time = System.nanoTime();
            task.run();
            diff = System.nanoTime() - time;

            if(max < diff){
                max = diff;
            }
            if(min > diff){
                min = diff;
            }
            if(i == 0){
                avg = diff;

            }else{
                avg = (avg + diff) / 2;
            }

            if(i % threshold == 0){
                progress += reportpercetile;

                log.append("[ ");
                log.append(progress);
                log.append("% ]");

                log.printInfo();
            }
        }

        log.printInfo("[Average] [" + avg + "ns] [" + (avg / 1000000f) + "ms]");
        log.printInfo("[Maximum] [" + max + "ns] [" + (max / 1000000f) + "ms]");
        log.printInfo("[Minimum] [" + min + "ns] [" + (min / 1000000f) + "ms]");

        return avg;
    }

}
