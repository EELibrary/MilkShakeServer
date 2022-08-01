package org.prawa.simpleutils.concurrent.forkjointasks;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

/**
 * 一个简单地利用ForkJoinPool进行遍历的简单工具类，它并不像并行流遍历那样把集合拆成一份一份的遍历，
 * 而是根据当前线程数量和集合大小自动分配每个线程的任务数量进行遍历
 * @param <E>
 */
public class ConcurrentlyTraverse<E> extends RecursiveAction {
    private final List<E> list;
    private final int start;
    private final int end;
    private final Consumer<E> action;
    private final int threshold;

    public ConcurrentlyTraverse(List<E> list, Consumer<E> action, int taskPerThread) {
        this.action = action;
        this.list = list;
        this.threshold = taskPerThread;
        this.start = 0;
        this.end = list.size();
    }

    private ConcurrentlyTraverse(List<E> list, Consumer<E> action, int start, int end, int taskPerThread) {
        this.action = action;
        this.list = list;
        this.threshold = taskPerThread;
        this.start = start;
        this.end = end;
    }

    public ConcurrentlyTraverse(List<E> list, int threads,Consumer<E> action) {
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

    public ConcurrentlyTraverse(Set<E> set,int nthreads,Consumer<E> action){
        this(new ArrayList<>(set),nthreads,action);
    }

    public ConcurrentlyTraverse(Iterable<E> iterable, int threads,Consumer<E> action) {
        this.action = action;
        this.list = Lists.newArrayList(iterable);
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
        try {
            if (end - start < this.threshold) {
                for (int i = start; i < end; i++) {
                    try {
                        this.action.accept(list.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                int middle = (start + end) / 2;
                invokeAll(new ConcurrentlyTraverse<>(list, this.action, start, middle, this.threshold), new ConcurrentlyTraverse<>(list, this.action, middle, end, this.threshold));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
