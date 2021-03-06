package org.spigotmc;

import gg.eilsapgroup.milkshake.MKConfig;
import net.minecraft.server.MinecraftServer;

public class AsyncCatcher
{

    public static boolean enabled = true;

    public static void catchOp(String reason)
    {
        if ( enabled && Thread.currentThread() != MinecraftServer.getServerInst().primaryThread && !MKConfig.workerGroup.contains())
        {
            if (MKConfig.disableAsyncCatcher){
                return;
            }
            throw new IllegalStateException( "Asynchronous " + reason + "!" );
        }
    }
}
