package net.minecraft.utils;

import java.time.LocalTime;

public record Logger(String name)
{
    public void info(String msg)
    {
        System.out.println("(" + time() + ") " + name + ": " + msg);
    }
    
    public void error(String msg)
    {
        System.err.println("(" + time() + ") " + name + ": " + msg);
    }
    
    private String time()
    {
        LocalTime time = LocalTime.now();
        StringBuilder buf = new StringBuilder(18);
        buf.append(time.getHour() < 10 ? "0" : "").append(time.getHour()).append(time.getMinute() < 10 ? ":0" : ":")
                .append(time.getMinute()).append(time.getSecond() < 10 ? ":0" : ":").append(time.getSecond());
        return buf.toString();
    }
}
