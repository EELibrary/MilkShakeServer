package gg.eilsapgroup.milkshake.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ThreadsCommand extends Command {
    public ThreadsCommand(){
        super("threads");
    }

    public Thread[] getThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        int slackSize = topGroup.activeCount() * 2;
        Thread[] slackThreads = new Thread[slackSize];
        int actualSize = topGroup.enumerate(slackThreads);
        Thread[] atualThreads = new Thread[actualSize];
        System.arraycopy(slackThreads, 0, atualThreads, 0, actualSize);
        return atualThreads;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.isOp()) {
            Thread[] threads = getThreads();
            sender.sendMessage("Thread count: " + threads.length);
            sender.sendMessage("Threads:");
            for (Thread t : threads) {
                sender.sendMessage(t.getName());
            }
        }
        return true;
    }
}
