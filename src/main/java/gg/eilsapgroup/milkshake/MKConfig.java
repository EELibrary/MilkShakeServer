package gg.eilsapgroup.milkshake;

import gg.eilsapgroup.milkshake.errorrecoder.ErrorRecoder;
import gg.eilsapgroup.milkshake.executor.MiscWorkerWrapper;
import gg.eilsapgroup.milkshake.executor.ServerWorkerWrapper;
import gg.eilsapgroup.milkshake.executor.group.MKThreadGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MKConfig {
    public static final ServerWorkerWrapper serverWorkerFactory = new ServerWorkerWrapper();
    public static final MiscWorkerWrapper serverMiscWorkerWrapper = new MiscWorkerWrapper();
    public static final YamlConfiguration config = new YamlConfiguration();
    public static ConfigurationSection executorConfig;
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean disableAsyncCatcher = false;
    public static ConfigurationSection debugConfig;
    public static final MKThreadGroup workerGroup = new MKThreadGroup();
    public static ErrorRecoder errorLogsRecoder = new ErrorRecoder();

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
                debugConfig = config.createSection("debug");
                debugConfig.set("enable-error-logs",false);
                config.save(configFile);
                return;
            }
            config.load(configFile);
            executorConfig = config.getConfigurationSection("executors");
            disableAsyncCatcher = executorConfig.getBoolean("disable-async-catcher");
            debugConfig = config.getConfigurationSection("debug");
            if (debugConfig.getBoolean("enable-error-logs")){
                initErrorRecoder();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void initErrorRecoder(){
        Thread errorLogRecoer = new Thread(errorLogsRecoder,"Milk-Shake-Worker-Error-Recoder");
        errorLogRecoer.start();
        errorLogRecoer.setDaemon(true);
    }
}
