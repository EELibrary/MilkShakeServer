package org.prawa.simpleutils.concurrent.forkjointasks;

import java.util.Spliterator;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

/**
 * 一个简单地利用ForkJoinPool进行遍历的简单工具类，它并不像并行流遍历那样把集合拆成一份一份的遍历，
 * 而是根据当前线程数量和集合大小自动分配每个线程的任务数量进行遍历
 * @param <E>
 */
public class ConcurrentlyTraverse<E> extends RecursiveAction {
    private final Spliterator<E> spliterator;
    private final Consumer<E> action;
    private final long threshold;

    public ConcurrentlyTraverse(Iterable<E> iterable,int threads,Consumer<E> action){
        this.spliterator = iterable.spliterator();
        this.action = action;
        this.threshold = (int)iterable.spliterator().getExactSizeIfKnown() / threads;
    }

    private ConcurrentlyTraverse(Spliterator<E> spliterator,Consumer<E> action,long t) {
        this.spliterator = spliterator;
        this.action = action;
        this.threshold = t;
    }

    @Override
    protected void compute() {
        if (this.spliterator.getExactSizeIfKnown() <= this.threshold) {
            this.spliterator.forEachRemaining(o->{
                try {
                    this.action.accept(o);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        } else {
            new ConcurrentlyTraverse<>(this.spliterator.trySplit(), this.action, this.threshold).fork();
            new ConcurrentlyTraverse<>(this.spliterator, this.action, this.threshold).fork();
        }
    }
}
