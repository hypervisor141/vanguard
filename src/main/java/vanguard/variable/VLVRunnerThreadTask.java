package vanguard.variable;

import vanguard.utils.VLLog;
import vanguard.concurrency.VLThread;
import vanguard.concurrency.VLThreadTaskType;

public class VLVRunnerThreadTask implements VLThreadTaskType {

    public VLVTypeRunner root;
    public VLLog log;

    protected long freqmillis;
    protected int freqextrananos;
    protected boolean enablecompensator;

    protected PostReporter reporter;

    public VLVRunnerThreadTask(VLVTypeRunner root, long freqmillis, int freqextrananos, boolean enablecompensator, PostReporter reporter, boolean debug){
        this.root = root;
        this.freqmillis = freqmillis;
        this.freqextrananos = freqextrananos;
        this.enablecompensator = enablecompensator;
        this.reporter = reporter;

        if(debug){
            log = new VLLog(3);
            log.addTag(VLLog.LOGTAG);
            log.addTag(root.getClass().getSimpleName());
            log.addTag(null);

        }else{
            log = null;
        }
    }

    protected VLVRunnerThreadTask(){

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

        while(worker.running() && !worker.locked()){
            long offsettime = System.nanoTime();
            int changes;

            synchronized(worker.internallock){
                changes = root.next();
                reporter.completed(changes);

                if(changes == 0){
                    try{
                        worker.internallock.wait();
                        continue;

                    }catch(InterruptedException ex){
                        //
                    }
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

            }else if(log != null){
                log.setTag(2, worker.getName());

                log.append("[WARNING] [VLV processor thread falling behind pre-set frequency of ");
                log.append(frequencynanos);
                log.append("ns by ");
                log.append(elapsednanos - frequencynanos);
                log.append("ns]");
                log.printInfo();
            }
        }
    }

    private void runDirectly(VLThread worker){
        while(worker.running() && !worker.locked()){
            synchronized(worker.internallock){
                int changes = root.next();
                reporter.completed(changes);

                if(changes == 0){
                    try{
                        worker.internallock.wait();
                        continue;

                    }catch(InterruptedException ex){
                        //
                    }
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
