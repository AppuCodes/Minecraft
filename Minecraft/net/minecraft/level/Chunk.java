package net.minecraft.level;

import net.minecraft.Minecraft;
import net.minecraft.level.blocks.Blocks;
import net.minecraft.render.*;
import net.minecraft.utils.vectors.Vec3i;

public class Chunk
{
    private byte[][][] blocks = new byte[CHUNK_SIZE][128][CHUNK_SIZE];
    private Tessellator tessel = new Tessellator(512 * 256);
    public int light = 16, top = 0, bottom = 128;
    public static final int CHUNK_SIZE = 32;
    public boolean update = false;
    public Vec3i pos;
    
    /* pos is in chunk coordinates */
    public Chunk(Vec3i pos)
    {
        this.pos = pos;
    }
    
    public void draw()
    {
        tessel.draw();
    }
    
    public void build()
    {
        LevelRenderer renderer = Minecraft.level.renderer;
        top = 0; bottom = 128;
        tessel.begin();
        
        for (int x = 0; x < CHUNK_SIZE; x++)
        {
            for (int y = 0; y < 128; y++)
            {
                for (int z = 0; z < CHUNK_SIZE; z++)
                {
                    byte block = getBlock(x, y, z);
                    
                    if (block != 0)
                    {
                        int renderX = pos.x * CHUNK_SIZE, renderY = pos.y * CHUNK_SIZE, renderZ = pos.z * CHUNK_SIZE;
                        if (top < y) top = y; if (bottom > y) bottom = y;
                        
                        if (block == Blocks.WATER.id)
                            renderer.waters.add(new WaterPos(renderX + x, renderY + y, renderZ + z, this));
                        else Blocks.getBlock(block).draw(renderX + x, renderY + y, renderZ + z, tessel, Minecraft.level);
                    }
                }
            }
        }
        
        renderer.buildWaters();
        tessel.end();
        update = false;
    }
    
    public byte getBlock(int x, int y, int z)
    {
        x %= CHUNK_SIZE; z %= CHUNK_SIZE;
        if (x < 0) x = CHUNK_SIZE + x; if (z < 0) z = CHUNK_SIZE + z;
        return y < 0 ? 0 : blocks[x][y][z];
    }
    
    public void setBlock(int x, int y, int z, byte block)
    {
        x %= CHUNK_SIZE; z %= CHUNK_SIZE;
        if (x < 0) x = CHUNK_SIZE + x; if (z < 0) z = CHUNK_SIZE + z;
        if (y >= 0) blocks[x][y][z] = block;
    }
    
    public Chunk unload()
    {
        LevelRenderer renderer = Minecraft.level.renderer;
        
        for (int i = 0; i < renderer.waters.size(); i++)
        {
            WaterPos water = renderer.waters.get(i);
            
            if (water != null && water.chunk.equals(this))
                renderer.waters.remove(water);
        }
        
        return this;
    }
    
    public boolean equals(Object obj)
    {
        return obj instanceof Chunk chunk && pos.equals(chunk.pos);
    }
    
    public static record WaterPos(int x, int y, int z, Chunk chunk) {}
}
