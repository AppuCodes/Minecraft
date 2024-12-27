package net.minecraft.utils;

import java.util.concurrent.TimeUnit;

public class Ticker
{
    private static final long NS_PER_SECOND = TimeUnit.SECONDS.toNanos(1);
    public static float fps, passedTime, partialTicks, scale = 1;
    private static long lastTime = System.nanoTime();
    private static final float TPS = 20;
    public static int ticks;
    
    public static void advance()
    {
        long now = System.nanoTime();
        long passedNs = now - lastTime;
        lastTime = now;
        
        passedNs = Math.min(NS_PER_SECOND, Math.max(0, passedNs));
        fps = NS_PER_SECOND / (float) passedNs;
        passedTime += passedNs * scale * TPS / NS_PER_SECOND;
        ticks = Math.min(100, (int) passedTime);
        
        passedTime -= ticks;
        partialTicks = passedTime;
    }
}
