package gg.eilsapgroup.milkshake.executor.group;

import net.himeki.mcmtfabric.parallelised.ConcurrentDoublyLinkedList;
import java.util.List;

/**
 * 一个简单的线程组，不过功能更多，主要给AsyncCatchers使用
 */
public class MKThreadGroup{
    private final List<Thread> threads = new ConcurrentDoublyLinkedList<>();

    public boolean contains(Thread thread){
        return this.threads.contains(thread);
    }

    public boolean contains(String threadName){
        return this.threads.parallelStream().noneMatch(thread -> thread.getName().equals(threadName));
    }

    public boolean contains(){
        return this.threads.contains(Thread.currentThread());
    }

    public void regThread(Thread thread){
        this.threads.add(thread);
    }

    public void regThread(){
        this.threads.add(Thread.currentThread());
    }

    public void removeThread(){
        this.threads.remove(Thread.currentThread());
    }

    public void removeThread(Thread thread){
        this.threads.remove(thread);
    }

    public int size(){
        return this.threads.size();
    }

    public void stopAll(){
        this.threads.parallelStream().forEach(Thread::stop);
    }
    
    public void interruptAll(){
        this.threads.parallelStream().forEach(Thread::interrupt);
    }

    public List<Thread> getAllThreads(){
        return this.threads;
    }

    public void notifyAllThreads(){
        this.threads.parallelStream().forEach(Thread::notify);
    }

    public boolean isAllAlive(){
        return this.threads.parallelStream().anyMatch(Thread::isAlive);
    }

    public void setAllProp(int newPropIn){
        this.threads.parallelStream().forEach(thread-> thread.setPriority(newPropIn));
    }
}
