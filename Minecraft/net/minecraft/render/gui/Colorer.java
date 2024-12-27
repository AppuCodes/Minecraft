package net.minecraft.render.gui;

public class Colorer
{
    public static int RGB(int red, int green, int blue, int alpha)
    {
        return ((alpha & 0xFF) << 24) |
                ((red & 0xFF) << 16) |
                ((green & 0xFF) << 8)  |
                (blue & 0xFF);
    }
    
    public static int[] extract(int RGB)
    {
        return new int[]
        {
            (RGB >> 16) & 0xFF,
            (RGB >> 8) & 0xFF,
            RGB & 0xFF,
            (RGB >> 24) & 0xFF,
        };
    }
    
    public static int RGB(float red, float green, float blue, float alpha)
    {
        return RGB((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255));
    }
    
    public static int RGB(int red, int green, int blue)
    {
        return RGB(red, green, blue, 255);
    }
    
    public static int blend(float mixture, int color1, int color2)
    {
        mixture = Math.min(1, Math.max(0, mixture));
        
        int[] valuesColor1 = extract(color1), valuesColor2 = extract(color2);
        int red = valuesColor1[0], green = valuesColor1[1], blue = valuesColor1[2];
        
        /* If the values of the 1st color are lower than the 2nd color */
        if (red < valuesColor2[0])
            red += (int) ((valuesColor2[0] - valuesColor1[0]) * mixture);
        
        if (green < valuesColor2[1])
            green += (int) ((valuesColor2[1] - valuesColor1[1]) * mixture);
        
        if (blue < valuesColor2[2])
            blue += (int) ((valuesColor2[2] - valuesColor1[2]) * mixture);
        
        /* If the values of the 2nd color are lower than the 1st color */
        if (red > valuesColor2[0])
            red -= (int) ((valuesColor1[0] - valuesColor2[0]) * mixture);
        
        if (green > valuesColor2[1])
            green -= (int) ((valuesColor1[1] - valuesColor2[1]) * mixture);
        
        if (blue > valuesColor2[2])
            blue -= (int) ((valuesColor1[2] - valuesColor2[2]) * mixture);

        return RGB(red, green, blue, valuesColor1[3]);
    }
}
