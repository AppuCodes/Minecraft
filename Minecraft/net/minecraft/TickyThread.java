package net.minecraft;

import net.minecraft.utils.Ticker;

public class TickyThread
{
    public static void start()
    {
        Thread ticky = new Thread(() ->
        {
            while (Window.isOpen())
            {
                Ticker.advance();
                
                for (int i = 0; i < Ticker.ticks; i++)
                    Minecraft.tick();
            }
        });
        
        ticky.setName("Ticky Thread");
        ticky.setDaemon(true);
        ticky.start();
    }
}
