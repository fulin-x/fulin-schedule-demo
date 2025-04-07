package com.fulin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Fulin
 * @Description: 主函数
 * @DateTime: 2025/4/7 下午10:39
 **/
public class Main {
    public static void main(String[] args) throws InterruptedException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss SSS");

        ScheduleService scheduleService = new ScheduleService();
        scheduleService.schedule(() -> {
            System.out.println(LocalDateTime.now().format(dateTimeFormatter) + ":Hello, World!");
        }, 100);

        Thread.sleep(1000);
        System.out.println("添加每200毫秒打印yes的定时任务");
        scheduleService.schedule(() -> {
            System.out.println(LocalDateTime.now().format(dateTimeFormatter) + ":yes");
        },200);
    }
}
