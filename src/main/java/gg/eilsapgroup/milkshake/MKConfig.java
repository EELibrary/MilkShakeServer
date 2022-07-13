package gg.eilsapgroup.milkshake;

import com.google.gson.internal.Streams;
import gg.eilsapgroup.milkshake.executor.MiscWorkerWrapper;
import gg.eilsapgroup.milkshake.executor.ServerWorkerWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.CraftServer;
import org.lwjgl.Sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class MKConfig {
    public static int parallelCount = Runtime.getRuntime().availableProcessors();
    public static final ServerWorkerWrapper serverWorkerFactory = new ServerWorkerWrapper();
    public static final MiscWorkerWrapper serverMiscWorkerWrapper = new MiscWorkerWrapper();
    public static final YamlConfiguration config = new YamlConfiguration();
    public static ConfigurationSection executorConfig = null;
    public static final Logger LOGGER = LogManager.getLogger();

    public static void init(){
        /*try{
            FileInputStream stream = readFile("mkconfig.yml");
            if (stream!=null){
                LOGGER.info("Config file detected!Reading");
                byte[] buffer = new byte[stream.available()];
                stream.read(buffer);
                config.loadFromString(new String(buffer));
                executorConfig = config.getConfigurationSection("executor");
                parallelCount = executorConfig.getInt("threadcount");
                return;
            }
            LOGGER.info("Creating config server");
            File file = new File("mkconfig.yml");
            file.createNewFile();
            executorConfig = config.createSection("executor");
            executorConfig.addDefault("threadcount",Runtime.getRuntime().availableProcessors());
            String s = config.saveToString();
            FileWriter writer = new FileWriter(file);
            writer.write(s);
            writer.flush();
            writer.close();
        }catch (Exception e){
            LOGGER.error(e);
            System.exit(-1);
        }
         */
    }

    public static FileInputStream readFile(String fileName){
        File file = new File(fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            return fis;
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
