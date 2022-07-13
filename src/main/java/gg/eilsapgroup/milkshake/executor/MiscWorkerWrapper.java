package gg.eilsapgroup.milkshake.executor;

import gg.eilsapgroup.milkshake.handler.ServerWorkerExceptionHandler;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MiscWorkerWrapper implements ThreadFactory {
    private final ServerWorkerExceptionHandler handler = new ServerWorkerExceptionHandler();
    private final AtomicInteger threadId = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r,"MilkShake-Worker # "+threadId.getAndIncrement());
        handler.onThreadRegisted();
        thread.setUncaughtExceptionHandler(handler);
        thread.setDaemon(true);
        return thread;
    }

    public ServerWorkerExceptionHandler getHandler(){
        return this.handler;
    }
}
