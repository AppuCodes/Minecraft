package net.minecraft.render;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import net.minecraft.Minecraft;
import net.minecraft.level.*;
import net.minecraft.level.Chunk.WaterPos;
import net.minecraft.level.blocks.Blocks;
import net.minecraft.level.entities.Player;
import net.minecraft.render.culling.Frustum;

public class LevelRenderer
{
    private Tessellator tessel = new Tessellator(512 * 256), cache = new Tessellator(512 * 256);
    public ArrayList<WaterPos> waters = new ArrayList<>();
    private Player player;
    private Level level;
    
    public void draw(float partialTicks)
    {
        player = Minecraft.player;
        translateCamera(partialTicks);
        drawBlocks();
    }
    
    public void translateCamera(float partialTicks)
    {
        glRotatef(player.pitch, 1, 0, 0);
        glRotatef(player.yaw, 0, 1, 0);
        
        double x = player.dX + (player.x - player.dX) * partialTicks,
               y = player.dY + (player.y - player.dY) * partialTicks,
               z = player.dZ + (player.z - player.dZ) * partialTicks;
        
        glTranslated(-x, -y - player.eyeHeight, -z);
    }
    
    public void drawBlocks()
    {
        glEnable(GL_TEXTURE_2D);
        Textures.load("blocks", true);
        Frustum.calculate();
        
        for (int i = 0; i < level.chunks.size(); i++)
        {
            Chunk chunk = level.chunks.get(i);
            
            if (chunk != null && Frustum.cubeInFrustum(chunk.pos.x * Chunk.CHUNK_SIZE, chunk.bottom, chunk.pos.z * Chunk.CHUNK_SIZE, (chunk.pos.x + 1) * Chunk.CHUNK_SIZE, chunk.top + 1, (chunk.pos.z + 1) * Chunk.CHUNK_SIZE))
                chunk.draw();
        }
        
        cache.draw();
        glDisable(GL_TEXTURE_2D);
    }
    
    public void buildWaters()
    {
        tessel.begin();
        
        for (int i = 0; i < waters.size(); i++)
        {
            WaterPos water = waters.get(i);
            
            if (water != null)
            {
                if (!level.chunks.contains(water.chunk()))
                    waters.remove(water);
                else Blocks.getBlock(Blocks.WATER.id).draw(water.x(), water.y(), water.z(), tessel, Minecraft.level);
            }
        }
        
        tessel.end();
        cache.copyState(tessel);
    }
    
    public float celestialAngle(float partialTicks)
    {
        int cycle = (int) (level.time % 24000L);
        float angle = ((cycle + partialTicks) / 24000);
        
        if (angle < 0) angle = 1;
        else if (angle > 1) angle = 0;
        
        angle = (float) (1 - (Math.cos(angle * Math.PI) + 1) / 2);
        return angle;
    }
    
    public LevelRenderer(Level level)
    {
        this.level = level;
    }
}
