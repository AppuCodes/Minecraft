package net.minecraft.level.entities;

import net.minecraft.level.Level;
import net.minecraft.options.GameOptions;

public class Player extends Entity
{
    public String name;
    
    public Player(Level level, String name)
    {
        super(level);
        this.name = name;
        eyeHeight = 1.62;
    }
    
    public void tick()
    {
        super.tick();
        float forward = 0, strafe = 0;
        
        if (GameOptions.KEY_FORWARD.isDown()) forward--;
        if (GameOptions.KEY_BACKWARD.isDown()) forward++;
        if (GameOptions.KEY_LEFT.isDown()) strafe--;
        if (GameOptions.KEY_RIGHT.isDown()) strafe++;
        if (GameOptions.KEY_JUMP.isDown() && grounded) motionY = 0.5F;
        
        goForward(strafe, forward, grounded ? 0.1F : 0.02F);
        motionY -= 0.08F;
        
        move(motionX, motionY, motionZ);
        motionX *= 0.91F;
        motionY *= 0.98F;
        motionZ *= 0.91F;
        
        if (grounded)
        {
            motionX *= 0.7F;
            motionZ *= 0.7F;
        }
    }
    
    public void turn(double x, double y)
    {
        yaw += x * 0.1F;
        pitch += y * 0.1F;
        yaw %= 360;
        if (pitch > 90) pitch = 90;
        if (pitch < -90) pitch = -90;
    }
}
