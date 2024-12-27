package net.minecraft.render.gui;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

public class Gui
{
    public static void drawRect(float x, float y, float width, float height, int color)
    {
        int[] colors = Colorer.extract(color);
        float red = colors[0] / 255F, green = colors[1] / 255F, blue = colors[2] / 255F, alpha = colors[3] / 255F;
        glColor4f(red, green, blue, alpha);
        glBegin(GL_TRIANGLES);
        glVertex2f(x, y);
        glVertex2f(x, height);
        glVertex2f(width, height);
        glVertex2f(width, y);
        glVertex2f(width, height);
        glVertex2f(x, y);
        glEnd();
    }
    
    public static void drawGradient(float x, float y, float width, float height, int topColor, int bottomColor)
    {
        drawGradient(x, y, width, height, new Color(topColor, true), new Color(bottomColor, true));
    }
    
    public static void drawGradient(float x, float y, float width, float height, Color topColor, Color bottomColor)
    {
        float topRed = topColor.getRed() / 255F, topGreen = topColor.getGreen() / 255F, topBlue = topColor.getBlue() / 255F, topAlpha = topColor.getAlpha() / 255F;
        float bottomRed = bottomColor.getRed() / 255F, bottomGreen = bottomColor.getGreen() / 255F, bottomBlue = bottomColor.getBlue() / 255F, bottomAlpha = bottomColor.getAlpha() / 255F;
        glColor4f(topRed, topGreen, topBlue, topAlpha);
        glBegin(GL_TRIANGLES);
        glVertex2f(x, y);
        glColor4f(bottomRed, bottomGreen, bottomBlue, bottomAlpha);
        glVertex2f(x, height);
        glVertex2f(width, height);
        glColor4f(topRed, topGreen, topBlue, topAlpha);
        glVertex2f(width, y);
        glColor4f(bottomRed, bottomGreen, bottomBlue, bottomAlpha);
        glVertex2f(width, height);
        glColor4f(topRed, topGreen, topBlue, topAlpha);
        glVertex2f(x, y);
        glEnd();
    }
}
