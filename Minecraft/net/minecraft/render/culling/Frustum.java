package net.minecraft.render.culling;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Frustum extends FrustumData
{
    private static FloatBuffer _proj = BufferUtils.createFloatBuffer(16), _modl = BufferUtils.createFloatBuffer(16),
            _clip = BufferUtils.createFloatBuffer(16);
    
    public static void normalizePlane(float[][] frustum, int side)
    {
        float magnitude = (float) Math.sqrt(frustum[side][0] * frustum[side][0] + frustum[side][1] * frustum[side][1] + frustum[side][2] * frustum[side][2]);
        frustum[side][0] /= magnitude;
        frustum[side][1] /= magnitude;
        frustum[side][2] /= magnitude;
        frustum[side][3] /= magnitude;
    }
    
    public static void calculate()
    {
        _proj.clear();
        _modl.clear();
        _clip.clear();
        glGetFloatv(GL_PROJECTION_MATRIX, _proj);
        glGetFloatv(GL_MODELVIEW_MATRIX, _modl);
        _proj.flip().limit(16);
        _proj.get(proj);
        _modl.flip().limit(16);
        _modl.get(modl);
        clip[0] = modl[0] * proj[0] + modl[1] * proj[4] + modl[2] * proj[8]
                + modl[3] * proj[12];
        clip[1] = modl[0] * proj[1] + modl[1] * proj[5] + modl[2] * proj[9]
                + modl[3] * proj[13];
        clip[2] = modl[0] * proj[2] + modl[1] * proj[6] + modl[2] * proj[10]
                + modl[3] * proj[14];
        clip[3] = modl[0] * proj[3] + modl[1] * proj[7] + modl[2] * proj[11]
                + modl[3] * proj[15];
        clip[4] = modl[4] * proj[0] + modl[5] * proj[4] + modl[6] * proj[8]
                + modl[7] * proj[12];
        clip[5] = modl[4] * proj[1] + modl[5] * proj[5] + modl[6] * proj[9]
                + modl[7] * proj[13];
        clip[6] = modl[4] * proj[2] + modl[5] * proj[6] + modl[6] * proj[10]
                + modl[7] * proj[14];
        clip[7] = modl[4] * proj[3] + modl[5] * proj[7] + modl[6] * proj[11]
                + modl[7] * proj[15];
        clip[8] = modl[8] * proj[0] + modl[9] * proj[4] + modl[10] * proj[8]
                + modl[11] * proj[12];
        clip[9] = modl[8] * proj[1] + modl[9] * proj[5] + modl[10] * proj[9]
                + modl[11] * proj[13];
        clip[10] = modl[8] * proj[2] + modl[9] * proj[6] + modl[10] * proj[10]
                + modl[11] * proj[14];
        clip[11] = modl[8] * proj[3] + modl[9] * proj[7] + modl[10] * proj[11]
                + modl[11] * proj[15];
        clip[12] = modl[12] * proj[0] + modl[13] * proj[4] + modl[14] * proj[8]
                + modl[15] * proj[12];
        clip[13] = modl[12] * proj[1] + modl[13] * proj[5] + modl[14] * proj[9]
                + modl[15] * proj[13];
        clip[14] = modl[12] * proj[2] + modl[13] * proj[6] + modl[14] * proj[10]
                + modl[15] * proj[14];
        clip[15] = modl[12] * proj[3] + modl[13] * proj[7] + modl[14] * proj[11]
                + modl[15] * proj[15];
        m_Frustum[0][0] = clip[3] - clip[0];
        m_Frustum[0][1] = clip[7] - clip[4];
        m_Frustum[0][2] = clip[11] - clip[8];
        m_Frustum[0][3] = clip[15] - clip[12];
        normalizePlane(m_Frustum, 0);
        m_Frustum[1][0] = clip[3] + clip[0];
        m_Frustum[1][1] = clip[7] + clip[4];
        m_Frustum[1][2] = clip[11] + clip[8];
        m_Frustum[1][3] = clip[15] + clip[12];
        normalizePlane(m_Frustum, 1);
        m_Frustum[2][0] = clip[3] + clip[1];
        m_Frustum[2][1] = clip[7] + clip[5];
        m_Frustum[2][2] = clip[11] + clip[9];
        m_Frustum[2][3] = clip[15] + clip[13];
        normalizePlane(m_Frustum, 2);
        m_Frustum[3][0] = clip[3] - clip[1];
        m_Frustum[3][1] = clip[7] - clip[5];
        m_Frustum[3][2] = clip[11] - clip[9];
        m_Frustum[3][3] = clip[15] - clip[13];
        normalizePlane(m_Frustum, 3);
        m_Frustum[4][0] = clip[3] - clip[2];
        m_Frustum[4][1] = clip[7] - clip[6];
        m_Frustum[4][2] = clip[11] - clip[10];
        m_Frustum[4][3] = clip[15] - clip[14];
        normalizePlane(m_Frustum, 4);
        m_Frustum[5][0] = clip[3] + clip[2];
        m_Frustum[5][1] = clip[7] + clip[6];
        m_Frustum[5][2] = clip[11] + clip[10];
        m_Frustum[5][3] = clip[15] + clip[14];
        normalizePlane(m_Frustum, 5);
    }
}
