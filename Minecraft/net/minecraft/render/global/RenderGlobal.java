package net.minecraft.render.global;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import net.minecraft.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.options.GameOptions;
import net.minecraft.render.Tessellator;
import net.minecraft.utils.vectors.Vec3f;

public class RenderGlobal
{
//    private static Tessellator sun = new Tessellator(16 * 16), moon = new Tessellator(16 * 16);
    private static FloatBuffer fog = BufferUtils.createFloatBuffer(16);
    private static Tessellator sky = new Tessellator(8 * 8);
    private static Vec3f prevColor;
    
    public static void draw(float partialTicks)
    {
        Vec3f color = getSkyColor(Minecraft.level, partialTicks), brightened = brighten(color);
        
        if (!color.equals(prevColor))
        {
            glClearColor(color.x, color.y, color.z, 1);
            updateFog(brightened);
            updateSky(color, brightened);
        }
        
        updateSky(color, brightened);
        drawSky(partialTicks);
        prevColor = color;
    }
    
    private static void updateFog(Vec3f color)
    {
        fog.clear();
        fog.put(new float[] {color.x, color.y, color.z, 1}).flip();
        glFogi(GL_FOG_MODE, GL_LINEAR);
        glFogf(GL_FOG_START, 16);
        glFogf(GL_FOG_END, (GameOptions.RENDER_DISTANCE.getValue() - 1) * 16);
        glFogfv(GL_FOG_COLOR, fog);
    }
    
    private static void drawSky(float partialTicks)
    {
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_FOG);
        glPushMatrix();
        glRotatef(Minecraft.player.pitch, 1, 0, 0);
        sky.draw();
        glPopMatrix();
        drawCelestials(Minecraft.level, partialTicks);
        glEnable(GL_FOG);
        glEnable(GL_DEPTH_TEST);
    }
    
    private static void updateSky(Vec3f color, Vec3f brightened)
    {
        sky.begin();
        sky.color(brightened.x, brightened.y, brightened.z);
        sky.vertex(1, 0, -0.2F);
        sky.vertex(-1, 0, -0.2F);
        sky.vertex(-1, -1, -0.2F);
        sky.vertex(-1, -1, -0.2F);
        sky.vertex(1, -1, -0.2F);
        sky.vertex(1, 0, -0.2F);
        
        sky.color(color.x, color.y, color.z);
        sky.vertex(1, 0.05F, -0.2F);
        sky.vertex(-1, 0.05F, -0.2F);
        sky.color(brightened.x, brightened.y, brightened.z);
        sky.vertex(-1, 0, -0.2F);
        sky.vertex(-1, 0, -0.2F);
        sky.vertex(1, 0, -0.2F);
        sky.color(color.x, color.y, color.z);
        sky.vertex(1, 0.05F, -0.2F);
        
        sky.color(brightened.x, brightened.y, brightened.z);
        sky.vertex(-1, -0.2F, 1);
        sky.vertex(1, -0.2F, 1);
        sky.vertex(1, -0.2F, -1);
        sky.vertex(1, -0.2F, -1);
        sky.vertex(-1, -0.2F, -1);
        sky.vertex(-1, -0.2F, 1);
        sky.end();
    }
    
    private static void drawCelestials(Level level, float partialTicks)
    {
//        glPushMatrix();
//        glRotatef(Minecraft.player.pitch, 1, 0, 0);
//        glRotatef(Minecraft.player.yaw, 0, 1, 0);
//        glRotatef(-90, 0, 1, 0);
//        glRotatef(level.renderer.celestialAngle(partialTicks) * 360, 1, 0, 0);
//        Textures.load("env/sun");
//        float f17 = 30;
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
//        glEnable(GL_TEXTURE_2D);
//        glBegin(GL_QUADS);
//        glVertex3f((-f17), 100, (-f17)); glTexCoord2i(0, 0);
//        glVertex3f(f17, 100, (-f17)); glTexCoord2i(1, 0);
//        glVertex3f(f17, 100, f17); glTexCoord2i(1, 1);
//        glVertex3f((-f17), 100, f17); glTexCoord2i(0, 1);
//        glEnd();
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glDisable(GL_TEXTURE_2D);
//        glPopMatrix();
    }
    
    private static Vec3f getSkyColor(Level level, float partialTicks)
    {
        float mul = getBrightness(level, partialTicks);
        return new Vec3f(0.4F * mul, 0.65F * mul, mul);
    }
    
    public static float getBrightness(Level level, float partialTicks)
    {
        float angle = level.renderer.celestialAngle(partialTicks);
        return (float) Math.clamp((Math.cos(angle * Math.PI * 2) * 2) + 0.5, 0, 1);
    }
    
    private static Vec3f brighten(Vec3f color)
    {
        Vec3f newColor = color.clone();
        newColor.x /= 0.8F; newColor.y /= 0.8F; newColor.z /= 0.8F;
        if (newColor.x > 1) newColor.x = 1; if (newColor.y > 1) newColor.y = 1; if (newColor.z > 1) newColor.z = 1;
        return newColor;
    }
    
    public static void enableStates(int... states)
    {
        for (int state : states)
            glEnableClientState(state);
    }
    
    public static void disableStates(int... states)
    {
        for (int state : states)
            glDisableClientState(state);
    }
    
//    static
//    {
//        float f17 = 30;
//        sun.begin();
//        sun.vertex((-f17), 100, (-f17)); sun.tex(0, 0);
//        sun.vertex(f17, 100, (-f17)); sun.tex(1, 0);
//        sun.vertex(f17, 100, f17); sun.tex(1, 1);
//        sun.vertex((-f17), 100, f17); sun.tex(0, 1);
//        sun.end();
//    }
}
