package net.minecraft.render.global;

import net.minecraft.*;
import net.minecraft.level.Level;

public class BuildyThread
{
    public static void start()
    {
        Thread buildy = new Thread(() ->
        {
            while (Window.isOpen())
            {
                Level level = Minecraft.level;
                
                if (level != null)
                    Minecraft.level.build();
                
                try { Thread.sleep(25); } catch (InterruptedException e) {}
            }
        });
        
        buildy.setName("Buildy Thread");
        buildy.setDaemon(true);
        buildy.start();
    }
}
