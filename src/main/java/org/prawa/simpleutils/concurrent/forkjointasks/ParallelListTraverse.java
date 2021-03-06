package org.prawa.simpleutils.concurrent.forkjointasks;

import catserver.server.AsyncCatcher;
import com.google.common.collect.Lists;
import gg.eilsapgroup.milkshake.MKConfig;
import gg.eilsapgroup.milkshake.TaskException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

public class ParallelListTraverse<E> extends RecursiveAction {

    private final List<E> list;
    private final int start;
    private final int end;
    private final Consumer<E> action;
    private final int threshold;

    public ParallelListTraverse(List<E> list, Consumer<E> action, int taskPerThread) {
        this.action = action;
        this.list = list;
        this.threshold = taskPerThread;
        this.start = 0;
        this.end = list.size();
    }

    private ParallelListTraverse(List<E> list, Consumer<E> action, int start, int end, int taskPerThread) {
        this.action = action;
        this.list = list;
        this.threshold = taskPerThread;
        this.start = start;
        this.end = end;
    }

    public ParallelListTraverse(Set<E> list, int threads,Consumer<E> action) {
        this(Lists.newArrayList(list),threads,action);
    }

    public ParallelListTraverse(List<E> list, int threads,Consumer<E> action) {
        if (threads <=0){
            throw new TaskException("Invalid arg input!");
        }
        this.action = action;
        this.list = list;
        int taskPerThread = list.size() / threads;
        if (taskPerThread < 2) {
            taskPerThread = 2;
        }
        this.threshold = taskPerThread;
        this.start = 0;
        this.end = list.size();
    }

    @Override
    protected void compute() {
        MKConfig.workerGroup.regThread();
        try {
            if (this.end - this.start < this.threshold) {
                for (int i = this.start; i < this.end; i++) {
                    try {
                        this.action.accept(list.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                int middle = (this.start + this.end) / 2;
                invokeAll(new ParallelListTraverse<>(this.list, this.action, this.start, middle, this.threshold), new ParallelListTraverse<>(this.list, this.action, middle, this.end, this.threshold));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            MKConfig.workerGroup.removeThread();
        }
    }
}
