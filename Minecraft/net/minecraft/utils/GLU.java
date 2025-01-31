package net.minecraft.utils;

import static org.lwjgl.opengl.GL11.*;

import java.nio.*;

import org.lwjgl.BufferUtils;

public class GLU
{
    private static final float[] IDENTITY_MATRIX = new float[]
    {
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1
    };
    
    private static final FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
    
    public static void perspective(float fov, float aspect, float zNear, float zFar)
    {
        float sine, cotangent, deltaZ, radians = (float) (fov / 2 * Math.PI / 180);
        deltaZ = zFar - zNear;
        sine = (float) Math.sin(radians);
        
        if (deltaZ != 0 && sine != 0 && aspect != 0)
        {
            cotangent = (float) Math.cos(radians) / sine;
            makeIdentity(matrix);
            matrix.put(0, cotangent / aspect);
            matrix.put(5, cotangent);
            matrix.put(10, -(zFar + zNear) / deltaZ);
            matrix.put(11, -1);
            matrix.put(14, -2 * zNear * zFar / deltaZ);
            matrix.put(15, 0);
            glMultMatrixf(matrix);
        }
    }
    
    private static void makeIdentity(FloatBuffer buffer)
    {
        int oldPos = buffer.position();
        buffer.put(IDENTITY_MATRIX);
        buffer.position(oldPos);
    }
}
