package vanguard;

public class VLThread extends Thread{

    private final VLListType<VLThreadTaskType> tasks;
    private final VLListType<VLThreadTaskType> active;
    public final Object lock;

    private volatile boolean enabled;
    private volatile boolean lockdown;
    private volatile boolean waiting;

    public VLThread(int resizer){
        tasks = new VLListType<>(resizer, resizer);
        active = new VLListType<>(resizer, resizer);
        lock = new Object();

        enabled = true;
        lockdown = false;
        waiting = false;
    }

    @Override
    public void run(){
        super.run();

        while(true){
            synchronized(lock){
                while(enabled && (lockdown || countQueuedTasks() == 0)){
                    waiting = true;

                    try{
                        lock.notifyAll();
                        lock.wait();

                    }catch(InterruptedException ex){
                        //
                    }

                    waiting = false;
                }

                if(!enabled){
                    tasks.nullify();
                    tasks.virtualSize(0);

                    return;

                }else{
                    active.add(tasks);

                    tasks.nullify();
                    tasks.virtualSize(0);
                }
            }

            int size = active.size();

            for(int i = 0; i < size && !lockdown; i++){
                active.get(i).run(this);
            }

            active.nullify();
            active.virtualSize(0);
        }
    }

    public void requestStart(){
        start();

        synchronized(lock){
            while(!enabled){
                try{
                    lock.wait();

                }catch(InterruptedException ex){
                    //
                }
            }
        }
    }

    public boolean enabled(){
        return enabled;
    }

    public boolean locked(){
        return lockdown;
    }

    public boolean waiting(){
        return waiting;
    }

    public int countQueuedTasks(){
        synchronized(lock){
            return tasks.size();
        }
    }

    public void waitTillFree(){
        synchronized(lock){
            while(!waiting){
                try{
                    lock.wait();

                }catch(InterruptedException ex){
                    return;
                }
            }
        }
    }

    public void post(VLThreadTaskType task){
        if(!lockdown){
            synchronized(lock){
                tasks.add(task);
                lock.notifyAll();
            }
        }
    }

    public void post(VLListType<VLThreadTaskType> list){
        if(!lockdown){
            synchronized(lock){
                tasks.add(list);
                lock.notifyAll();
            }
        }
    }

    public void lockdown(){
        if(!lockdown){
            lockdown = true;

            synchronized(lock){
                tasks.nullify();
                tasks.virtualSize(0);

                while(!waiting){
                    try{
                        lock.wait();

                    }catch(InterruptedException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public void unlock(){
        lockdown = false;

        synchronized(lock){
            lock.notifyAll();
        }
    }

    public void requestDestruction(){
        enabled = false;

        synchronized(lock){
            tasks.nullify();
            tasks.virtualSize(0);

            if(waiting){
                lock.notifyAll();
            }
        }

        while(isAlive()){
            try{
                join();

            }catch(InterruptedException ex){
                //
            }
        }
    }
}
