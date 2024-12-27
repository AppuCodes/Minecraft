package net.minecraft.level.gen;

import net.minecraft.level.*;
import net.minecraft.level.blocks.Blocks;
import net.minecraft.level.lighting.LightEngine;

public class LevelGen
{
    private SimplexNoise a, b;
    private Level level;
    
    public LevelGen(Level level)
    {
        a = new SimplexNoise(level.seed);
        b = new SimplexNoise(level.seed + 1);
        this.level = level;
    }
    
    public Chunk handle(Chunk chunk)
    {
        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 64; y++)
            {
                for (int z = 0; z < 16; z++)
                {
                    double realX = chunk.pos.x * 16 + x, realY = chunk.pos.y * 16 + y, realZ = chunk.pos.z * 16 + z;
                    realX /= 16D; realY /= 16D; realZ /= 16D;
                    
                    double factor = 1 * a.eval(1 * realX, 1 * realY, 1 * realZ)
                                  + 0.5 * b.eval(2 * realX, 2 * realY, 2 * realZ);
                    
                    factor /= 1.5;
                    factor = Math.pow(factor, 0.1);
                    int yLevel = (int) (y * factor);
                    // 56 = water
                    chunk.setBlock(x, yLevel, z, y < 58 ? Blocks.STONE.id :
                        (y == 63 && yLevel > 56 ? (yLevel == 57 ? Blocks.SAND.id : Blocks.GRASS.id) : Blocks.DIRT.id));
                    
                    if (y < 58 && y > 48 && chunk.getBlock(x, y, z) == 0)
                        chunk.setBlock(x, y, z, Blocks.WATER.id);
                }
            }
        }
        
        chunk.light = LightEngine.skyLight(level);
        chunk.update = true;
        return chunk;
    }
}
