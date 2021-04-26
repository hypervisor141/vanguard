package vanguard;

import sun.misc.GThreadHelper;

public class VLVRunnerThreadTask implements VLThreadTaskType{

    public final VLVTypeRunner root;
    public final VLLog log;

    private final long freqmillis;
    private final int freqextrananos;

    private final boolean debug;
    private final boolean enablecompensator;

    private final PostReporter reporter;

    public VLVRunnerThreadTask(VLVTypeRunner root, long freqmillis, int freqextrananos, boolean debug, boolean enablecompensator, PostReporter reporter){
        this.root = root;
        this.freqmillis = freqmillis;
        this.freqextrananos = freqextrananos;
        this.debug = debug;
        this.enablecompensator = enablecompensator;
        this.reporter = reporter;

        log = debug ? new VLLog() : null;
    }

    @Override
    public void run(VLThread thread){
        if(enablecompensator){
            runWithCompensator(thread);

        }else{
            runDirectly(thread);
        }
    }

    private void runWithCompensator(VLThread worker){
        long frequencynanos = freqmillis * 1000000 + freqextrananos;

        while(worker.enabled() && !worker.locked()){
            long offsettime = System.nanoTime();
            int changes;

            synchronized(worker.internallock){
                changes = root.next();
                reporter.completed(changes);

                if(changes == 0){
                    try{
                        worker.internallock.wait();

                    }catch(InterruptedException ex){
                        //
                    }

                    continue;
                }
            }

            long elapsednanos = System.nanoTime() - offsettime;

            if(elapsednanos < frequencynanos){
                try{
                    long compensationnanos = frequencynanos - elapsednanos;
                    long sleepmillis = (long)Math.floor(compensationnanos / 1000000F);

                    Thread.sleep(sleepmillis, (int)(compensationnanos - (sleepmillis * 1000000)));

                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }

            }else if(debug){
                log.tag(worker.getName());
                log.append("[WARNING] [");
                log.append(worker.getName());
                log.append("] [VLV processor thread falling behind pre-set frequency of ");
                log.append(frequencynanos);
                log.append("ns by ");
                log.append(elapsednanos - frequencynanos);
                log.append("ns]");
                log.printInfo();
            }
        }
    }

    private void runDirectly(VLThread worker){
        while(worker.enabled()){
            synchronized(worker.internallock){
                int changes = root.next();
                reporter.completed(changes);

                if(changes == 0){
                    try{
                        worker.internallock.wait();

                    }catch(InterruptedException ex){
                        //
                    }

                    continue;
                }
            }

            try{
                Thread.sleep(freqmillis, freqextrananos);

            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    public interface PostReporter{

        void completed(int changes);
    }
}
