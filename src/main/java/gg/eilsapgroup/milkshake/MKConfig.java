package gg.eilsapgroup.milkshake;

import gg.eilsapgroup.milkshake.executor.MiscWorkerWrapper;
import gg.eilsapgroup.milkshake.executor.ServerWorkerWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Emmmm，说实话我也不知道是不是我写的出问题了，每次初始化配置文件的时候写到文件里的就总是只有两个大括号
 */
public class MKConfig {
    public static final ServerWorkerWrapper serverWorkerFactory = new ServerWorkerWrapper();
    public static final MiscWorkerWrapper serverMiscWorkerWrapper = new MiscWorkerWrapper();
    public static final YamlConfiguration config = new YamlConfiguration();
    public static ConfigurationSection executorConfig = null;
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean disableAsyncCatcher = false;

    public static void init(){
        try{
            File configFile = new File("MKConfig.yml");
            if (!configFile.exists()){
                configFile.createNewFile();
                executorConfig = config.createSection("executors");
                executorConfig.set("entities-worker-threads",Runtime.getRuntime().availableProcessors()+2);
                executorConfig.set("tracker-worker-threads",Runtime.getRuntime().availableProcessors());
                executorConfig.set("env-worker-threads",Runtime.getRuntime().availableProcessors());
                executorConfig.set("disable-async-catcher",false);
                config.save(configFile);
                return;
            }
            config.load(configFile);
            executorConfig = config.getConfigurationSection("executors");
            disableAsyncCatcher = executorConfig.getBoolean("disable-async-catcher");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
