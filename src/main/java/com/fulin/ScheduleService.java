package com.fulin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: Fulin
 * @Description: 定时任务
 * @DateTime: 2025/4/7 下午11:39
 **/
public class ScheduleService {

    Trigger trigger = new Trigger();

    ExecutorService executorService = Executors.newFixedThreadPool(6);

    void schedule(Runnable task, long delay) {
        Job job = new Job();
        job.setStartTime(System.currentTimeMillis() + delay);
        job.setTask(task);
        job.setDelay(delay);
        trigger.queue.offer(job);
        trigger.wakeUp();
    }

    // 等待合适的时间，把对应的任务扔到线程池中执行
    class Trigger {

        PriorityBlockingQueue<Job> queue = new PriorityBlockingQueue<>();

        Thread thread = new Thread(() -> {
            while (true) {
                while(queue.isEmpty()){
                    LockSupport.park();
                }
                // TODO 多线程环境下， peek() 和 poll() 出来的 job 可能不是同一个
                Job latelyJob = queue.peek();
                if(latelyJob.getStartTime() < System.currentTimeMillis()) {
                    latelyJob = queue.poll();
                    executorService.execute(latelyJob.getTask());
                    Job nextJob = new Job();
                    nextJob.setStartTime(System.currentTimeMillis() + latelyJob.getDelay());
                    nextJob.setTask(latelyJob.getTask());
                    nextJob.setDelay(latelyJob.getDelay());
                    queue.offer(nextJob);
                }else {
                    LockSupport.parkUntil(latelyJob.getStartTime());
                }
            }
        });

        {
            thread.start();
            System.out.println("trigger启动了!");
        }

        void wakeUp(){
            LockSupport.unpark(thread);
        }

    }
}
