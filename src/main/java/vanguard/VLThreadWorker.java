package vanguard;

import java.util.ArrayList;

public class VLThreadWorker extends Thread{

    private final ArrayList<VLThreadTaskType<VLThreadWorker>> tasks;
    private final Object masterlock;
    public final Object lock;

    private volatile boolean enabled;

    public VLThreadWorker(Object masterlock){
        this.masterlock = masterlock;

        tasks = new ArrayList<>(10);
        lock = new Object();

        enabled = true;
    }

    @Override
    public void run(){
        super.run();

        ArrayList<VLThreadTaskType<VLThreadWorker>> currenttasks = new ArrayList<>();

        while(enabled){
            synchronized(lock){
                while(tasks.isEmpty()){
                    try{
                        lock.wait();

                    }catch(InterruptedException ex){
                        if(!enabled){
                            return;
                        }
                    }
                }

                currenttasks.addAll(tasks);
                tasks.clear();
            }

            int size = currenttasks.size();

            for(int i = 0; i < size; i++){
                currenttasks.get(i).run(this);
            }

            currenttasks.clear();

            synchronized(masterlock){
                masterlock.notify();
            }
        }
    }

    public boolean isTaskQueueEmpty(){
        synchronized(lock){
            return tasks.isEmpty();
        }
    }

    public void waitTillFree(){
        synchronized(lock){
            while(true){
                try{
                    lock.wait();

                } catch(InterruptedException ex){
                    return;
                }
            }
        }
    }

    public void post(VLThreadTaskType<VLThreadWorker> task){
        synchronized(lock){
            tasks.add(task);
            lock.notify();
        }
    }

    public void post(ArrayList<VLThreadTaskType<VLThreadWorker>> tasklist){
        synchronized(lock){
            tasks.addAll(tasklist);
            lock.notify();
        }
    }

    public void disable(){
        synchronized(lock){
            enabled = false;
            lock.notify();
        }
    }

    public boolean isEnabled(){
        return enabled;
    }
}
