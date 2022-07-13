package org.prawa.simpleutils.concurrent.executor;

import catserver.server.AsyncCatcher;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class SimpleQueuedExecutor implements Executor {
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final void init(int threads){
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(() -> {
                AsyncCatcher.server_workers.add(Thread.currentThread());
                while (true) {
                    Runnable task = tasks.poll();
                    if (task != null) {
                        try {
                            task.run();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            Thread.sleep(0,1);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.setDaemon(true);
            thread.setPriority(8);
            thread.start();
        }
    }

    public SimpleQueuedExecutor(int threads){
        init(threads);
    }

    public SimpleQueuedExecutor(){
        init(1);
    }

    @Override
    public void execute(Runnable command) {
        tasks.add(command);
    }

    public void awaitCompletion(){
        while (!tasks.isEmpty()){
            try {
                Thread.sleep(0,1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
