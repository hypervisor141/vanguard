package vanguard;

public class VLVThreadTask implements VLThreadTaskType{

    public final VLVTypeRunner root;

    private final long freqmillis;
    private final int freqextrananos;

    private final boolean debug;
    private final boolean enablecompensator;

    private final PostReporter reporter;

    public VLVThreadTask(VLVTypeRunner root, long freqmillis, int freqextrananos, boolean debug, boolean enablecompensator, PostReporter reporter){
        this.root = root;
        this.freqmillis = freqmillis;
        this.freqextrananos = freqextrananos;
        this.debug = debug;
        this.enablecompensator = enablecompensator;
        this.reporter = reporter;
    }

    @Override
    public void run(VLThread thread){
        if(enablecompensator){
            runWithCompensator(thread);

        }else{
            runDirect(thread);
        }
    }

    @Override
    public void requestDestruction(){
        synchronized(root){
            root.notifyAll();
        }
    }

    private void runWithCompensator(VLThread worker){
        long frequencynanos = freqmillis * 1000000 + freqextrananos;

        while(worker.enabled()){
            long offsettime = System.nanoTime();
            int changes;

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

    private void runDirect(VLThread worker){
        int changes = 0;

        while(worker.enabled()){
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
