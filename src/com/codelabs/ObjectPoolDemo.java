package com.codelabs;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class ObjectPoolDemo {
    private ObjectPool<ExportingProcess> pool;
    private AtomicLong processNo=new AtomicLong(0);

    //mengeset min dan max Object dalam ObjectPool
    public void setUp(){
        pool=new ObjectPool<ExportingProcess>(1,3,5) {

            @Override
            public ExportingProcess createObject() {
                return new ExportingProcess(processNo.incrementAndGet());
            }
        };
    }

    //shutdown executorService kalau sudah beres
    public void tearDown(){
        pool.shutDown();
    }

    public void testObjectPool(){
        //membuat services sejumlah thread yang mengakses object pool
        ExecutorService executor= Executors.newFixedThreadPool(2);

        executor.execute(new Task(pool,1));
        executor.execute(new Task(pool,2));
        executor.shutdown();
    }

    public static void main(String[] args) {
	// write your code here
        ObjectPoolDemo op=new ObjectPoolDemo();
        op.setUp();
        op.tearDown();
        op.testObjectPool();
    }
}
