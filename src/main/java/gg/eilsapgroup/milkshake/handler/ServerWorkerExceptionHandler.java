package gg.eilsapgroup.milkshake.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerWorkerExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AtomicInteger spawnedThreads = new AtomicInteger(0);
    private final AtomicInteger errorDetected = new AtomicInteger(0);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.warn("Error detected in workers!",e);
        errorDetected.getAndIncrement();
    }

    public void onThreadRegisted(){
        spawnedThreads.getAndIncrement();
    }
}
