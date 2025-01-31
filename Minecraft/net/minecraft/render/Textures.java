package net.minecraft.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL14.GL_GENERATE_MIPMAP;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import net.minecraft.Minecraft;

public class Textures
{
    private static HashMap<String, Integer> textureIDs = new HashMap<>();
    
    public static int load(String name, boolean mipmap)
    {
        if (textureIDs.containsKey(name))
        {
            int id = textureIDs.get(name);
            glBindTexture(GL_TEXTURE_2D, id);
            return id;
        }
        
        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, mipmap ? GL_NEAREST_MIPMAP_LINEAR : GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
        if (mipmap)
        {
            glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 4);
        }
        
        try (InputStream stream = Textures.class.getClassLoader().getResourceAsStream("assets/" + name + ".png"))
        {
            BufferedImage image = ImageIO.read(stream);
            int width = image.getWidth(), height = image.getHeight();
            int[] pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
            
            for (int i = 0; i < pixels.length; i++)
            {
                int alpha = pixels[i] >> 24 & 0xFF, red = pixels[i] >> 16 & 0xFF,
                    green = pixels[i] >> 8 & 0xFF, blue = pixels[i] & 0xFF;
                pixels[i] = alpha << 24 | blue << 16 | green << 8 | red;
            }
            
            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
            buffer.asIntBuffer().put(pixels);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        }
        
        catch (IOException e)
        {
            Minecraft.LOGGER.error("Could not load texture " + name + ".");
        }
        
        textureIDs.put(name, id);
        return id;
    }
    
    public static int load(String name)
    {
        return load(name, false);
    }
}
