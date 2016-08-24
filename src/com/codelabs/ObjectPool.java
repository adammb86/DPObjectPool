package com.codelabs;

import java.util.concurrent.*;

/**
 * Created by adammb on 8/16/16.
 */
public abstract class ObjectPool <T>{
    private ConcurrentLinkedQueue<T> pool;

    //digunakan untuk menjalankan thread dan mengecek kondisi object pool
    private ScheduledExecutorService executeService;

    //inisialisasi object pool
    public ObjectPool(final int minObject){
        initialize(minObject);
    }

    //memeriksa object pool setiap periode tertentu
    public ObjectPool(final int minObject,final int maxObject,final long validationInterval){
        initialize(minObject);
        executeService= Executors.newSingleThreadScheduledExecutor();
        executeService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                int size =pool.size();

                //jika jumlah objek lebih kecil dari minObject
                //create terus sampai minObject terpenuhi
                if(size<minObject){
                    int sizeToBeAdded =minObject+size;
                    for(int i=0;i<sizeToBeAdded;i++){
                        pool.add(createObject());
                    }
                }

                //hapus object jika melebihi maxObject sampai
                //maxObejct terpenuhi
                if(size>maxObject){
                    int sizeToBeAdded=size-maxObject;
                    for(int i=0;i<sizeToBeAdded;i++){
                        pool.poll();
                    }
                }
            }
        },validationInterval,validationInterval, TimeUnit.SECONDS);
    }

    //mengambil object dari pool
    public T borrowObject(){
        T object;
        if((object = pool.poll())==null){
            object=createObject();
        }

        return object;
    }

    //mengembalikan object ke pool
    public void returnObject(T object){
        if(object==null){
            return;
        }

        this.pool.offer(object);
    }

    //mematikan variable execute service
    public void shutDown(){
        if(executeService!=null){
            executeService.shutdown();
        }
    }

    //untuk membuat objek yang akan dimasukkan ke dalam object pool
    public abstract T createObject();

    //menginisialisasi object agar minimal sejumlah minObject
    public void initialize(final int minObject){
        pool=new ConcurrentLinkedQueue<T>();
        for(int i=0;i<minObject;i++){
            pool.add(createObject());
        }
    }
}
