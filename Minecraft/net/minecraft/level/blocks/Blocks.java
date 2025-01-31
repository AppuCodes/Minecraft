package net.minecraft.level.blocks;

import net.minecraft.level.Level;
import net.minecraft.level.lighting.LightEngine;
import net.minecraft.render.Tessellator;

public class Blocks
{
    public static Block GRASS = new GrassBlock("Grass Block", 1, 0),
                        STONE = new Block("Stone", 2, 1),
                        DIRT = new Block("Dirt", 3, 2),
                        WATER = new WaterLiquid("Water", 4, 4),
                        SAND = new Block("Sand", 5, 5);
    
    public static Block getBlock(byte block)
    {
        switch (block)
        {
            case 1: return GRASS;
            case 2: return STONE;
            case 3: return DIRT;
            case 4: return WATER;
            case 5: return SAND;
        }
        
        return null;
    }
    
    public static class Block
    {
        public boolean passable = false;
        public String name;
        public int tex;
        public byte id;
        
        public Block(String name, int id, int texture)
        {
            this.name = name;
            this.id = (byte) id;
            tex = texture;
        }
        
        public void draw(int x, int y, int z, Tessellator tessel, Level level)
        {
            byte block;
            int[] texes = getTex();
            float rx1 = x, rx2 = x + 1, ry1 = y, ry2 = y + blockHeight(), rz1 = z, rz2 = z + 1;
            
            float u1 = texes[0] / 16F, u2 = u1 + 16 / 256F,
                  v1 = 0, v2 = v1 + 16 / 256F,
                  shadeX = 0.6F, shadeY = 1, shadeZ = 0.8F;
            
            if ((block = level.getBlock(x, y - 1, z)) == 0 || (block == Blocks.WATER.id && !(this instanceof WaterLiquid)))
            {
                shadeY = LightEngine.getLight(x, y - 1, z, level) / 16F;
                tessel.color(shadeY, shadeY, shadeY);
                tessel.tex(u1, v2).vertex(rx1, ry1, rz2);
                tessel.tex(u1, v1).vertex(rx1, ry1, rz1);
                tessel.tex(u2, v1).vertex(rx2, ry1, rz1);
                tessel.tex(u2, v1).vertex(rx2, ry1, rz1);
                tessel.tex(u2, v2).vertex(rx2, ry1, rz2);
                tessel.tex(u1, v2).vertex(rx1, ry1, rz2);
            }
            
            if (texes[1] != texes[0])
            {
                u1 = texes[1] / 16F;
                u2 = u1 + 16 / 256F;
            }
            
            if ((block = level.getBlock(x, y + 1, z)) == 0 || (block == Blocks.WATER.id && !(this instanceof WaterLiquid)))
            {
                shadeY = LightEngine.getLight(x, y + 1, z, level) / 16F;
                tessel.color(shadeY, shadeY, shadeY);
                tessel.tex(u2, v2).vertex(rx2, ry2, rz2);
                tessel.tex(u2, v1).vertex(rx2, ry2, rz1);
                tessel.tex(u1, v1).vertex(rx1, ry2, rz1);
                tessel.tex(u1, v1).vertex(rx1, ry2, rz1);
                tessel.tex(u1, v2).vertex(rx1, ry2, rz2);
                tessel.tex(u2, v2).vertex(rx2, ry2, rz2);
            }
            
            if (texes[2] != texes[1])
            {
                u1 = texes[2] / 16F;
                u2 = u1 + 16 / 256F;
            }
            
            if ((block = level.getBlock(x, y, z - 1)) == 0 || (block == Blocks.WATER.id && !(this instanceof WaterLiquid)))
            {
                shadeZ = (LightEngine.getLight(x, y, z - 1, level) / 16F) * 0.8F;
                tessel.color(shadeZ, shadeZ, shadeZ);
                tessel.tex(u2, v1).vertex(rx1, ry2, rz1);
                tessel.tex(u1, v1).vertex(rx2, ry2, rz1);
                tessel.tex(u1, v2).vertex(rx2, ry1, rz1);
                tessel.tex(u1, v2).vertex(rx2, ry1, rz1);
                tessel.tex(u2, v2).vertex(rx1, ry1, rz1);
                tessel.tex(u2, v1).vertex(rx1, ry2, rz1);
            }
            
            if (texes[3] != texes[2])
            {
                u1 = texes[3] / 16F;
                u2 = u1 + 16 / 256F;
            }
            
            if ((block = level.getBlock(x, y, z + 1)) == 0 || (block == Blocks.WATER.id && !(this instanceof WaterLiquid)))
            {
                shadeZ = (LightEngine.getLight(x, y, z + 1, level) / 16F) * 0.8F;
                tessel.color(shadeZ, shadeZ, shadeZ);
                tessel.tex(u1, v1).vertex(rx1, ry2, rz2);
                tessel.tex(u1, v2).vertex(rx1, ry1, rz2);
                tessel.tex(u2, v2).vertex(rx2, ry1, rz2);
                tessel.tex(u2, v2).vertex(rx2, ry1, rz2);
                tessel.tex(u2, v1).vertex(rx2, ry2, rz2);
                tessel.tex(u1, v1).vertex(rx1, ry2, rz2);
            }
            
            if (texes[4] != texes[3])
            {
                u1 = texes[4] / 16F;
                u2 = u1 + 16 / 256F;
            }
            
            if ((block = level.getBlock(x - 1, y, z)) == 0 || (block == Blocks.WATER.id && !(this instanceof WaterLiquid)))
            {
                shadeX = (LightEngine.getLight(x - 1, y, z, level) / 16F) * 0.6F;
                tessel.color(shadeX, shadeX, shadeX);
                tessel.tex(u2, v1).vertex(rx1, ry2, rz2);
                tessel.tex(u1, v1).vertex(rx1, ry2, rz1);
                tessel.tex(u1, v2).vertex(rx1, ry1, rz1);
                tessel.tex(u1, v2).vertex(rx1, ry1, rz1);
                tessel.tex(u2, v2).vertex(rx1, ry1, rz2);
                tessel.tex(u2, v1).vertex(rx1, ry2, rz2);
            }
            
            if (texes[5] != texes[4])
            {
                u1 = texes[5] / 16F;
                u2 = u1 + 16 / 256F;
            }
            
            if ((block = level.getBlock(x + 1, y, z)) == 0 || (block == Blocks.WATER.id && !(this instanceof WaterLiquid)))
            {
                shadeX = (LightEngine.getLight(x + 1, y, z, level) / 16F) * 0.6F;
                tessel.color(shadeX, shadeX, shadeX);
                tessel.tex(u1, v2).vertex(rx2, ry1, rz2);
                tessel.tex(u2, v2).vertex(rx2, ry1, rz1);
                tessel.tex(u2, v1).vertex(rx2, ry2, rz1);
                tessel.tex(u2, v1).vertex(rx2, ry2, rz1);
                tessel.tex(u1, v1).vertex(rx2, ry2, rz2);
                tessel.tex(u1, v2).vertex(rx2, ry1, rz2);
            }
        }
        
        /* for all 6 faces */
        protected int[] getTex()
        {
            return new int[] {tex, tex, tex, tex, tex, tex};
        }
        
        protected float blockHeight() { return 1; }
    }
}
