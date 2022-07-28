package gg.eilsapgroup.milkshake.errorrecoder;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

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
            if(errorMessages.size()>100){
                for (;;){
                    String errorMessage = errorMessages.poll();
                    if (errorMessage==null)break;

                }
                continue;
            }
            try{
                Thread.sleep(0,1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

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
