package net.minecraft.level;

import java.util.ArrayList;

import net.minecraft.Minecraft;
import net.minecraft.level.blocks.Blocks;
import net.minecraft.level.gen.LevelGen;
import net.minecraft.level.lighting.LightEngine;
import net.minecraft.options.GameOptions;
import net.minecraft.render.LevelRenderer;
import net.minecraft.render.global.RenderGlobal;
import net.minecraft.utils.BoundingBox;
import net.minecraft.utils.vectors.Vec3i;

public class Level
{
    public volatile ArrayList<Chunk> chunks = new ArrayList<>();
    public LevelRenderer renderer;
    public long seed, time;
    public LevelGen gen;
    
    public Level(long seed)
    {
        renderer = new LevelRenderer(this);
        gen = new LevelGen(this);
        this.seed = seed;
    }
    
    public void draw(float partialTicks)
    {
        RenderGlobal.draw(partialTicks);
        renderer.draw(partialTicks);
    }
    
    public void tick()
    {
        time++;
    }
    
    public void build()
    {
        Vec3i pos = new Vec3i((int) Minecraft.player.x, (int) Minecraft.player.y, (int) Minecraft.player.z).chunkify();
        int distance = (int) (GameOptions.RENDER_DISTANCE.getValue() / (Chunk.CHUNK_SIZE / 16F));
        
        for (int i = 0; i < chunks.size(); i++)
        {
            Chunk chunk = chunks.get(i);
            
            if (chunk != null)
            {
                if (chunk.pos.distanceTo(pos) > (distance * 1.25) + 1)
                    chunks.remove(chunk.unload());
                else if (chunk.update) chunk.build();
            }   
        }
        
        if (getChunk(pos) == null) generateChunk(pos);
        
        for (int x = pos.x - distance; x < pos.x + distance; x++)
        {
            for (int z = pos.z - distance; z < pos.z + distance; z++)
            {
                Vec3i chunk = new Vec3i(x, pos.y, z);
                
                if (getChunk(chunk) == null)
                    chunks.add(generateChunk(chunk));
            }
        }
        
        int light = LightEngine.skyLight(this);
        
        if (light != 16 && time % 200 == 0)
        {
            for (Chunk chunk : chunks)
            {
                if (chunk.light != light)
                {
                    chunk.update = true;
                    chunk.light = light;
                }
            }
        }
    }
    
    public ArrayList<BoundingBox> collisions(BoundingBox range)
    {
        int cx1 = (int) (Math.floor(range.x) - 1), cx2 = (int) (Math.ceil(range.length) + 1),
            cy1 = (int) (Math.floor(range.y) - 1), cy2 = (int) (Math.ceil(range.height) + 1),
            cz1 = (int) (Math.floor(range.z) - 1), cz2 = (int) (Math.ceil(range.width) + 1);
        
        ArrayList<BoundingBox> boxes = new ArrayList<>();
        
        for (int x = cx1; x < cx2; x++)
        {
            for (int y = cy1; y < cy2; y++)
            {
                for (int z = cz1; z < cz2; z++)
                {
                    byte block = getBlock(x, y, z);
                    
                    if (block != 0 && !Blocks.getBlock(block).passable)
                        boxes.add(new BoundingBox(x, y, z, x + 1, y + 1, z + 1));
                }
            }
        }
        
        return boxes;
    }
    
    public byte getBlock(int x, int y, int z)
    {
        Vec3i pos = new Vec3i(x, y, z).chunkify();
        Chunk chunk = getChunk(pos);
        return chunk != null ? chunk.getBlock(x, y, z) : 0;
    }
    
    public void setBlock(int x, int y, int z, byte block)
    {
        Vec3i pos = new Vec3i(x, y, z).chunkify();
        Chunk chunk = getChunk(pos);
        
        if (chunk == null)
        {
            chunk = generateChunk(pos);
            chunk.setBlock(x, y, z, block);
        }
        
        else chunk.setBlock(x, y, z, block);
    }
    
    public Chunk generateChunk(Vec3i pos)
    {
        Chunk chunk = new Chunk(pos);
        chunks.add(gen.handle(chunk));
        return chunk;
    }
    
    public Chunk getChunk(Vec3i pos)
    {
        for (int i = 0; i < chunks.size(); i++)
        {
            Chunk chunk = chunks.get(i);
            
            if (chunk != null && chunk.pos.equals(pos))
                return chunk;
        }
        
        return null;
    }
}
