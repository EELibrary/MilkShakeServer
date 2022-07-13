package gg.eilsapgroup.milkshake.executor;

import gg.eilsapgroup.milkshake.handler.ServerWorkerExceptionHandler;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerWorkerWrapper implements ForkJoinPool.ForkJoinWorkerThreadFactory {
    private final AtomicInteger threadId = new AtomicInteger(0);
    private final ServerWorkerExceptionHandler handler = new ServerWorkerExceptionHandler();

    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        ForkJoinWorkerThread fjwt = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        fjwt.setDaemon(true);
        fjwt.setUncaughtExceptionHandler(handler);
        handler.onThreadRegisted();
        fjwt.setName("MilkShake-Worker # "+threadId.getAndIncrement());
        return fjwt;
    }

    public ServerWorkerExceptionHandler getHandler(){
        return this.handler;
    }
}
