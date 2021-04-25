package vanguard;

public class VLThread extends Thread{

    private final VLListType<VLThreadTaskType> tasks;
    private final VLListType<VLThreadTaskType> taskcache;
    public final Object lock;

    private volatile boolean enabled;
    private boolean lockdown;
    private boolean waiting;

    public VLThread(int resizer){
        tasks = new VLListType<>(resizer, resizer);
        taskcache = new VLListType<>(resizer, resizer);
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
                if(!enabled){
                    tasks.clear();
                    taskcache.clear();
                    return;
                }

                while(lockdown || tasks.size() == 0){
                    try{
                        waiting = true;
                        lock.notifyAll();
                        lock.wait();
                        waiting = false;

                    }catch(InterruptedException ex){
                        //
                    }

                    if(!enabled){
                        tasks.clear();
                        taskcache.clear();
                        return;
                    }
                }

                synchronized (taskcache){
                    taskcache.add(tasks);
                }

                tasks.clear();
            }

            int size = taskcache.size();

            for(int i = 0; i < size; i++){
                taskcache.get(i).run(this);
            }

            taskcache.clear();
        }
    }

    public void requestStart(){
        start();

        synchronized(lock){
            while(!enabled){
                try{
                    lock.wait();
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public boolean enabled(){
        synchronized(lock){
            return enabled;
        }
    }

    public int countQueuedTasks(){
        synchronized(lock){
            return tasks.size();
        }
    }

    public int countActiveTasks(){
        synchronized(taskcache){
            return taskcache.size();
        }
    }

    public void waitTillFree(){
        synchronized(lock){
            while(true){
                try{
                    lock.wait();

                }catch(InterruptedException ex){
                    return;
                }
            }
        }
    }

    public void post(VLThreadTaskType task){
        synchronized(lock){
            if(!lockdown){
                tasks.add(task);
                lock.notifyAll();
            }
        }
    }

    public void post(VLListType<VLThreadTaskType> tasklist){
        synchronized(lock){
            if(!lockdown){
                tasks.add(tasklist);
                lock.notifyAll();
            }
        }
    }

    public void lockdown(){
        synchronized(lock){
            lockdown = true;

            tasks.clear();
            taskcache.clear();

            while(!waiting){
                try{
                    lock.wait();

                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public void unlock(){
        synchronized(lock){
            lockdown = false;
            lock.notifyAll();
        }
    }

    public void requestDestruction(){
        int size = 0;

        synchronized (taskcache){
            size = taskcache.size();

            for(int i = 0; i < size; i++){
                taskcache.get(i).requestDestruction();
            }
        }

        synchronized(lock){
            size = tasks.size();

            for(int i = 0; i < size; i++){
                tasks.get(i).requestDestruction();
            }

            enabled = false;
            lock.notifyAll();
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
