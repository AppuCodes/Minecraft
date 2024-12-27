package net.minecraft.level.lighting;

import net.minecraft.level.Level;
import net.minecraft.render.global.RenderGlobal;

public class LightEngine
{
    /* 0 = dark, 16 = bright */
    public static int getLight(int x, int y, int z, Level level)
    {
        int light = level.getBlock(x, y + 1, z) == (byte) 0 ? skyLight(level) : (skyLight(level) / 2);
        return light;
    }
    
    public static int skyLight(Level level)
    {
        return (int) (13 * RenderGlobal.getBrightness(level, 0)) + 3;
    }
}
