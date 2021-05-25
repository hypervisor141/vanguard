package vanguard;

public class VLThreadManager{

    protected Object mainlock;
    protected VLListType<VLThread> workers;

    public VLThreadManager(int capacity){
        mainlock = new Object();
        workers = new VLListType<>(capacity, capacity);
    }

    protected VLThreadManager(){

    }

    public void enable(int index){
        synchronized(mainlock){
            workers.get(index).start();
        }
    }

    public void enableAll(){
        synchronized(mainlock){
            int size = workers.size();

            for(int i = 0; i < size; i++){
                workers.get(i).start();
            }
        }
    }

    public void add(VLThread worker){
        synchronized(mainlock){
            workers.add(worker);
        }
    }

    public VLListType<VLThread> workers(){
        return workers;
    }

    public Object masterLock(){
        return mainlock;
    }

    public VLThread waitForFreeWorker(long waittime){
        VLThread free;

        synchronized(mainlock){
            while((free = checkForFreeWorker()) == null){
                try{
                    mainlock.wait(waittime);

                }catch(InterruptedException ex){

                }
            }

            return free;
        }
    }

    public VLThread checkForFreeWorker(){
        synchronized(mainlock){
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
