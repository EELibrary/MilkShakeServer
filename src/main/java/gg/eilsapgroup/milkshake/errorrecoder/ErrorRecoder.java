package gg.eilsapgroup.milkshake.errorrecoder;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 错误记录器
 */
public class ErrorRecoder implements Runnable {
    private final Queue<String> errorMessages = new ConcurrentLinkedDeque<>();
    private FileOutputStream currentOutStream;

    @Override
    public void run() {
        this.init("MilkShakeExecutorErrorLog-"+new Date());
        for(;;){
            if(Thread.currentThread().isInterrupted()){
                this.close();
                break;
            }
            //当错误消息队列的大小大于100时才会写入文件,不过感觉这样并不太好
            if(errorMessages.size()>100){
                for (;;){
                    String errorMessage = errorMessages.poll();
                    if (errorMessage==null)break;
                    this.writeErrorMessage(errorMessage);
                }
                continue;
            }
            //空闲的时候休眠线程
            try{
                Thread.sleep(0,1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化文件
     * @param recordFileName
     */
    private void init(String recordFileName){
        File file = new File(new File("mklogs"),recordFileName);
        try{
            if (!file.exists()){
                file.mkdirs();
                file.createNewFile();
            }else {
                file.delete();
            }
            this.currentOutStream = new FileOutputStream(file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 向文件写入错误信息
     * @param message
     */
    private void writeErrorMessage(String message){
        if (message!=null){
            String newMessageWithNewLine = message + "\n";
            try {
                this.currentOutStream.write(newMessageWithNewLine.getBytes(StandardCharsets.UTF_8));
                this.currentOutStream.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭记录器
     */
    private void close(){
        try {
            if (!this.errorMessages.isEmpty()) {
                for (String message = this.errorMessages.poll(); ; ) {
                    if (message == null) break;
                    this.writeErrorMessage(message);
                }
            }
            this.currentOutStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addErrorMessage(String message){
        this.errorMessages.add(message);
    }
}
