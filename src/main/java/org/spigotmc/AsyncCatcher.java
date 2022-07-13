package org.spigotmc;

import net.minecraft.server.MinecraftServer;

public class AsyncCatcher
{

    public static boolean enabled = true;

    public static void catchOp(String reason)
    {
        if ( enabled && Thread.currentThread() != MinecraftServer.getServerInst().primaryThread && !catserver.server.AsyncCatcher.server_workers.contains(Thread.currentThread()))
        {
            throw new IllegalStateException( "Asynchronous " + reason + "!" );
        }
    }
}
