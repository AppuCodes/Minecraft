package net.minecraft.options;

public class KeyBinding
{
    private boolean down, pressed;
    public final String name;
    public final int key;
    
    public KeyBinding(String name, int key)
    {
        this.name = name;
        this.key = key;
    }
    
    public boolean isDown()
    {
        return down;
    }
    
    public boolean isPressed()
    {
        if (pressed)
        {
            pressed = false;
            return true;
        }
        
        return false;
    }
    
    public void press()
    {
        down = pressed = true;
    }
    
    public void release()
    {
        down = pressed = false;
    }
}
