package vanguard;

public class VLThreadManager{

    private final Object masterlock;
    private VLListType<VLThread> workers;

    public VLThreadManager(int capacity){
        masterlock = new Object();
        workers = new VLListType<>(capacity, capacity);
    }

    public void enable(int index){
        synchronized (masterlock){
            workers.get(index).start();
        }
    }

    public void enableAll(){
        synchronized (masterlock){
            int size = workers.size();

            for(int i = 0; i < size; i++){
                workers.get(i).start();
            }
        }
    }

    public void add(VLThread worker){
        synchronized (masterlock){
            workers.add(worker);
        }
    }

    public VLListType<VLThread> workers(){
        return workers;
    }

    public Object masterLock(){
        return masterlock;
    }

    public VLThread waitForFreeWorker(long waittime){
        VLThread free;

        synchronized (masterlock){
            while((free = checkForFreeWorker()) == null){
                try{
                    masterlock.wait(waittime);

                }catch(InterruptedException ex){

                }
            }

            return free;
        }
    }

    public VLThread checkForFreeWorker(){
        synchronized(masterlock){
            int size = workers.size();

            for(int i = 0; i < size; i++){
                VLThread worker = workers.get(i);

                if(worker.sizeQueuedTasks() == 0){
                    return worker;
                }
            }

            return null;
        }
    }

    public void destroy(){
        int size = workers.size();

        for(int i = 0; i < size; i++){
            workers.get(i).requestDestruction();
        }

        workers = null;
    }
}
