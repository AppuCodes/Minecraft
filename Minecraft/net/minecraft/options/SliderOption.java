package net.minecraft.options;

public class SliderOption
{
    private float min, value, max;
    public final String name;
    
    public SliderOption(String name, float min, float value, float max)
    {
        this.name = name;
        this.min = min;
        this.value = value;
        this.max = max;
    }
    
    public float getValue()
    {
        return value;
    }
    
    public void setValue(float value)
    {
        this.value = value;
    }
    
    public float getMin() { return min; }
    public float getMax() { return max; }
}
