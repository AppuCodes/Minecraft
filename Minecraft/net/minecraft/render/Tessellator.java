package net.minecraft.render;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Tessellator
{
    private FloatBuffer vertexBuffer = null, texBuffer = null, colorBuffer = null;
    private boolean colored = false, textured = false, building = false;
    private float red = 1, green = 1, blue = 1, texU = 0, texV = 0;
    private int vertices = 0, size = 0;
    
    public Tessellator(int size)
    {
        vertexBuffer = BufferUtils.createFloatBuffer(size * 3);
        texBuffer = BufferUtils.createFloatBuffer(size * 2);
        colorBuffer = BufferUtils.createFloatBuffer(size * 3);
        this.size = size;
    }
    
    public void vertex(float x, float y, float z)
    {
        vertexBuffer.put((vertices * 3), x);
        vertexBuffer.put((vertices * 3) + 1, y);
        vertexBuffer.put((vertices * 3) + 2, z);
        
        if (textured)
        {
            texBuffer.put((vertices * 2), texU);
            texBuffer.put((vertices * 2) + 1, texV);
        }
        
        if (colored)
        {
            colorBuffer.put((vertices * 3), red);
            colorBuffer.put((vertices * 3) + 1, green);
            colorBuffer.put((vertices * 3) + 2, blue);
        }
        
        if (++vertices == size) begin();
    }
    
    public void draw()
    {
        glVertexPointer(3, GL_FLOAT, GL_POINTS, vertexBuffer);
        
        if (textured)
            glTexCoordPointer(2, GL_FLOAT, GL_POINTS, texBuffer);
        
        if (colored)
            glColorPointer(3, GL_FLOAT, GL_POINTS, colorBuffer);
        
        glDrawArrays(GL_TRIANGLES, GL_POINTS, vertices);
    }
    
    public Tessellator tex(float texU, float texV)
    {
        textured = true;
        this.texU = texU; this.texV = texV;
        return this;
    }
    
    public void color(float red, float green, float blue)
    {
        colored = true;
        this.red = red; this.green = green; this.blue = blue;
    }
    
    public void begin()
    {
        vertexBuffer.clear(); texBuffer.clear(); colorBuffer.clear();
        textured = colored = !(building = true);
        vertices = 0;
    }
    
    public void end()
    {
        vertexBuffer.flip();
        texBuffer.flip();
        building = false;
    }
    
    public void copyState(Tessellator tessel)
    {
        vertexBuffer = tessel.vertexBuffer; colorBuffer = tessel.colorBuffer; texBuffer = tessel.texBuffer;
        colored = tessel.colored; textured = tessel.textured; building = tessel.building;
        red = tessel.red; green = tessel.green; blue = tessel.blue;
        vertices = tessel.vertices; size = tessel.size;
        texU = tessel.texU; texV = tessel.texV;
    }
}
