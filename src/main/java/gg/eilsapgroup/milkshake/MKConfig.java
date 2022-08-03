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
import java.util.concurrent.ForkJoinPool;

/**
 * 配置文件和错误日志记录器都在这个类里
 */
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


    public static ForkJoinPool entitiesExecutor = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),serverWorkerFactory,null,false);
    public static ForkJoinPool trackerPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),serverWorkerFactory,null,false);
    public static ForkJoinPool serverWorldExecutor = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),serverWorkerFactory,null,false);

    /**
     * 加载所有的配置或者初始化配置
     */
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
            entitiesExecutor = new ForkJoinPool(executorConfig.getInt("entities-worker-threads"),serverWorkerFactory,null,false);
            trackerPool = new ForkJoinPool(executorConfig.getInt("tracker-worker-threads"),serverWorkerFactory,null,false);
            serverWorldExecutor = new ForkJoinPool(executorConfig.getInt("env-worker-threads"),serverWorkerFactory,null,false);
            disableAsyncCatcher = executorConfig.getBoolean("disable-async-catcher");
            debugConfig = config.getConfigurationSection("debug");
            if (debugConfig.getBoolean("enable-error-logs")){
                initErrorRecoder();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 初始化错误记录器，只有当enable-error-logs为true时才会调用此方法
     */
    public static void initErrorRecoder(){
        Thread errorLogRecoer = new Thread(errorLogsRecoder,"Milk-Shake-Worker-Error-Recoder");
        errorLogRecoer.setDaemon(true);
        errorLogRecoer.start();
    }
}
