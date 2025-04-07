package com.fulin;

/**
 * @Author: Fulin
 * @Description: 任务类
 * @DateTime: 2025/4/7 下午11:46
 **/
public class Job implements Comparable<Job>{

    private Runnable task;

    private long startTime;

    private long delay;

    public Runnable getTask() {
        return task;
    }

    public Job setTask(Runnable task) {
        this.task = task;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public Job setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long getDelay() {
        return delay;
    }

    public Job setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    @Override
    public int compareTo(Job o) {
        return Long.compare(this.startTime, o.startTime);
    }
}
