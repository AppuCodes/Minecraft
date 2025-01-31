package net.minecraft.render.culling;

public class FrustumData
{
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int BOTTOM = 2;
    public static final int TOP = 3;
    public static final int BACK = 4;
    public static final int FRONT = 5;
    public static final int A = 0;
    public static final int B = 1;
    public static final int C = 2;
    public static final int D = 3;
    public static float[][] m_Frustum = new float[16][16];
    public static float[] proj = new float[16];
    public static float[] modl = new float[16];
    public static float[] clip = new float[16];

    public static boolean pointInFrustum(float x, float y, float z)
    {
        for (int i = 0; i < 6; ++i)
        {
            if (m_Frustum[i][0] * x + m_Frustum[i][1] * y + m_Frustum[i][2] * z
                    + m_Frustum[i][3] <= 0.0F)
            {
                return false;
            }
        }

        return true;
    }

    public static boolean sphereInFrustum(float x, float y, float z, float radius)
    {
        for (int i = 0; i < 6; ++i)
        {
            if (m_Frustum[i][0] * x + m_Frustum[i][1] * y + m_Frustum[i][2] * z
                    + m_Frustum[i][3] <= -radius)
            {
                return false;
            }
        }

        return true;
    }

    public static boolean cubeFullyInFrustum(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        for (int i = 0; i < 6; ++i)
        {
            if (!((double) m_Frustum[i][0] * x1 + (double) m_Frustum[i][1] * y1
                    + (double) m_Frustum[i][2] * z1 + (double) m_Frustum[i][3] > 0.0D))
            {
                return false;
            }

            if (!((double) m_Frustum[i][0] * x2 + (double) m_Frustum[i][1] * y1
                    + (double) m_Frustum[i][2] * z1 + (double) m_Frustum[i][3] > 0.0D))
            {
                return false;
            }

            if (!((double) m_Frustum[i][0] * x1 + (double) m_Frustum[i][1] * y2
                    + (double) m_Frustum[i][2] * z1 + (double) m_Frustum[i][3] > 0.0D))
            {
                return false;
            }

            if (!((double) m_Frustum[i][0] * x2 + (double) m_Frustum[i][1] * y2
                    + (double) m_Frustum[i][2] * z1 + (double) m_Frustum[i][3] > 0.0D))
            {
                return false;
            }

            if (!((double) m_Frustum[i][0] * x1 + (double) m_Frustum[i][1] * y1
                    + (double) m_Frustum[i][2] * z2 + (double) m_Frustum[i][3] > 0.0D))
            {
                return false;
            }

            if (!((double) m_Frustum[i][0] * x2 + (double) m_Frustum[i][1] * y1
                    + (double) m_Frustum[i][2] * z2 + (double) m_Frustum[i][3] > 0.0D))
            {
                return false;
            }

            if (!((double) m_Frustum[i][0] * x1 + (double) m_Frustum[i][1] * y2
                    + (double) m_Frustum[i][2] * z2 + (double) m_Frustum[i][3] > 0.0D))
            {
                return false;
            }

            if (!((double) m_Frustum[i][0] * x2 + (double) m_Frustum[i][1] * y2
                    + (double) m_Frustum[i][2] * z2 + (double) m_Frustum[i][3] > 0.0D))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean cubeInFrustum(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        for (int i = 0; i < 6; ++i)
        {
            if (!((double) m_Frustum[i][0] * x1 + (double) m_Frustum[i][1] * y1
                    + (double) m_Frustum[i][2] * z1 + (double) m_Frustum[i][3] > 0.0D)
                    && !((double) m_Frustum[i][0] * x2 + (double) m_Frustum[i][1] * y1
                            + (double) m_Frustum[i][2] * z1 + (double) m_Frustum[i][3] > 0.0D)
                    && !((double) m_Frustum[i][0] * x1 + (double) m_Frustum[i][1] * y2
                            + (double) m_Frustum[i][2] * z1 + (double) m_Frustum[i][3] > 0.0D)
                    && !((double) m_Frustum[i][0] * x2 + (double) m_Frustum[i][1] * y2
                            + (double) m_Frustum[i][2] * z1 + (double) m_Frustum[i][3] > 0.0D)
                    && !((double) m_Frustum[i][0] * x1 + (double) m_Frustum[i][1] * y1
                            + (double) m_Frustum[i][2] * z2 + (double) m_Frustum[i][3] > 0.0D)
                    && !((double) m_Frustum[i][0] * x2 + (double) m_Frustum[i][1] * y1
                            + (double) m_Frustum[i][2] * z2 + (double) m_Frustum[i][3] > 0.0D)
                    && !((double) m_Frustum[i][0] * x1 + (double) m_Frustum[i][1] * y2
                            + (double) m_Frustum[i][2] * z2 + (double) m_Frustum[i][3] > 0.0D)
                    && !((double) m_Frustum[i][0] * x2 + (double) m_Frustum[i][1] * y2
                            + (double) m_Frustum[i][2] * z2 + (double) m_Frustum[i][3] > 0.0D))
            {
                return false;
            }
        }

        return true;
    }
}
