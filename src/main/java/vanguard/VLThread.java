package vanguard;

public class VLThread extends Thread{

    protected VLListType<VLThreadTaskType> tasks;
    protected VLListType<VLThreadTaskType> active;

    protected Object mainlock;
    public Object internallock;

    protected boolean running;
    protected boolean lockdown;
    protected volatile boolean waiting;

    public VLThread(int capacity){
        tasks = new VLListType<>(capacity, capacity);
        active = new VLListType<>(capacity, capacity);

        mainlock = new Object();
        internallock = new Object();

        running = false;
        lockdown = false;
        waiting = false;
    }

    protected VLThread(){

    }

    @Override
    public void run(){
        super.run();

        synchronized(mainlock){
            running = true;
            mainlock.notifyAll();
        }

        while(true){
            synchronized(mainlock){
                if(running){
                    if(tasks.size() == 0){
                        try{
                            waiting = true;
                            mainlock.notifyAll();

                            mainlock.wait();

                            waiting = false;
                            mainlock.notifyAll();

                        }catch(InterruptedException ex){
                            //
                        }
                    }

                    active.add(tasks);

                    tasks.nullify();
                    tasks.virtualSize(0);

                }else{
                    lockdown = false;

                    tasks.nullify();
                    tasks.virtualSize(0);

                    return;
                }
            }

            int size = active.size();

            for(int i = 0; i < size && !locked(); i++){
                active.get(i).run(this);
            }

            active.nullify();
            active.virtualSize(0);
        }
    }

    public void requestStart(){
        start();

        synchronized(mainlock){
            while(!running){
                try{
                    mainlock.wait();

                }catch(InterruptedException ex){
                    //
                }
            }
        }
    }

    public void post(VLThreadTaskType task){
        synchronized(mainlock){
            if(!lockdown){
                tasks.add(task);
                mainlock.notifyAll();
            }
        }
    }

    public void post(VLListType<VLThreadTaskType> list){
        synchronized(mainlock){
            if(!lockdown){
                tasks.add(list);
                mainlock.notifyAll();
            }
        }
    }

    public void waitTillFree(){
        synchronized(mainlock){
            while(!waiting){
                try{
                    mainlock.wait();

                }catch(InterruptedException ex){
                    return;
                }
            }
        }
    }

    public void lockdown(){
        synchronized(mainlock){
            if(!lockdown){
                lockdown = true;

                tasks.nullify();
                tasks.virtualSize(0);

                while(!waiting){
                    try{
                        synchronized(internallock){
                            internallock.notifyAll();
                        }

                        mainlock.wait();

                    }catch(InterruptedException ex){
                        //
                    }
                }
            }
        }
    }

    public void unlock(){
        lockdown = false;

        synchronized(mainlock){
            mainlock.notifyAll();
        }
    }

    public void requestDestruction(){
        running = false;

        synchronized(mainlock){
            tasks.nullify();
            tasks.virtualSize(0);

            if(waiting){
                mainlock.notifyAll();
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

    public boolean running(){
        synchronized(mainlock){
            return running;
        }
    }

    public void notifyInternalLock(){
        synchronized(internallock){
            internallock.notifyAll();
        }
    }

    public boolean locked(){
        synchronized(mainlock){
            return lockdown;
        }
    }

    public boolean waiting(){
        synchronized(mainlock){
            return waiting;
        }
    }

    public int sizeQueuedTasks(){
        synchronized(mainlock){
            return tasks.size();
        }
    }
}
