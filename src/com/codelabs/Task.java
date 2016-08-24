package com.codelabs;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by adammb on 8/16/16.
 */
public class Task implements Runnable {
    private ObjectPool<ExportingProcess> pool;
    private int threadNo;


    public Task(ObjectPool<ExportingProcess> pool,int threadNo){
        this.pool=pool;
        this.threadNo=threadNo;
    }

    @Override
    public void run(){
        ExportingProcess exportingProcess=pool.borrowObject();
        System.out.println("Thread ke-"+threadNo+" : objek dengan proses nomor "+exportingProcess.getProcessNo()+" dipinjam");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.returnObject(exportingProcess);
        System.out.println("Thread ke-"+threadNo+" : objek dengan proses nomor "+exportingProcess.getProcessNo()+" dikembalikan");
    }
}
