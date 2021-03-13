package vanguard;

import java.util.ArrayList;

public class VLThreadManager{

    private final Object masterlock;
    private final VLListType<VLThreadWorker> workers;

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

    public void add(VLThreadWorker worker){
        synchronized (masterlock){
            workers.add(worker);
        }
    }

    public VLListType<VLThreadWorker> workers(){
        return workers;
    }

    public Object masterLock(){
        return masterlock;
    }

    public VLThreadWorker waitForFreeWorker(long waittime){
        VLThreadWorker free;

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

    public VLThreadWorker checkForFreeWorker(){
        synchronized (masterlock){
            int size = workers.size();
            VLThreadWorker worker;

            for(int i = 0; i < size; i++){
                worker = workers.get(i);

                synchronized (worker.lock){
                    if(worker.isTaskQueueEmpty()){
                        return worker;
                    }
                }
            }

            return null;
        }
    }

    public void destroy(){
        int size = workers.size();
        VLThreadWorker worker;

        for(int i = 0; i < size; i++){
            worker = workers.get(i);

            try{
                worker.disable();
                worker.join();

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
