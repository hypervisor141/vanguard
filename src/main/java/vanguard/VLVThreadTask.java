package vanguard;

public class VLVThreadTask implements VLThreadTaskType<VLThreadWorker>{

    public final VLVTypeRunner root;

    private final long freqmillis;
    private final long freqextrananos;
    private final boolean debug;

    private final PostReporter reporter;

    public VLVThreadTask(VLVTypeRunner root, long freqmillis, long freqextrananos, boolean debug, PostReporter reporter){
        this.root = root;
        this.freqmillis = freqmillis;
        this.freqextrananos = freqextrananos;
        this.debug = debug;
        this.reporter = reporter;
    }

    @Override
    public void run(VLThreadWorker worker){
        long offsettime = 0;
        long elapsednanos = 0;
        long frequencynanos = freqmillis * 1000000 + freqextrananos;
        long compensationnanos = 0;
        long sleepmillis = 0;
        int changes = 0;

        while(worker.isEnabled()){
            offsettime = System.nanoTime();

            synchronized(root){
                changes = root.next();
            }

            reporter.completed(changes);

            if(changes == 0){
                try{
                    synchronized(root){
                        root.wait();
                    }

                }catch(InterruptedException ex){
                    //
                }
            }

            elapsednanos = System.nanoTime() - offsettime;

            if(elapsednanos < frequencynanos){
                try{
                    compensationnanos = frequencynanos - elapsednanos;
                    sleepmillis = (long)Math.floor(compensationnanos / 1000000F);

                    Thread.sleep(sleepmillis, (int)(compensationnanos - (sleepmillis * 1000000)));

                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }

            }else if(debug){
                VLDebug.append("[WARNING] [");
                VLDebug.append(worker.getName());
                VLDebug.append("] [VLV processor thread falling behind pre-set frequency of ");
                VLDebug.append(frequencynanos);
                VLDebug.append("ns by ");
                VLDebug.append(elapsednanos - frequencynanos);
                VLDebug.append("ns]");
                VLDebug.printD();
            }
        }
    }

    public interface PostReporter{

        void completed(int changes);
    }
}
