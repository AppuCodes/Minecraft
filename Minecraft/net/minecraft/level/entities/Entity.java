package net.minecraft.level.entities;

import java.util.ArrayList;

import net.minecraft.level.Level;
import net.minecraft.utils.BoundingBox;

public class Entity
{
    public float yaw, pitch, motionX, motionY, motionZ, health = 20, maxHealth = 20;
    public double x, y, z, dX, dY, dZ, eyeHeight;
    public boolean grounded;
    public BoundingBox box;
    private Level level;
    
    public Entity(Level level)
    {
        this.level = level;
        respawn();
    }
    
    public void respawn()
    {
        double coord = 4096 + (4096 * Math.random());
        setPosition(coord, 64, coord);
    }
    
    public void setPosition(double x, double y, double z)
    {
        this.x = x; this.y = y; this.z = z;
        float width = 0.3F, height = 0.9F;
        box = new BoundingBox(x, y, z, x, y, z).grow(width, height, width);
    }
    
    public void move(double x, double y, double z)
    {
        double dX = x, dY = y, dZ = z;
        ArrayList<BoundingBox> boxes = level.collisions(box.expand(x, y, z));
        
        for (BoundingBox box : boxes)
            y = box.yCollide(this.box, y);
        
        box.move(0, y, 0);
        
        for (BoundingBox box : boxes)
            x = box.xCollide(this.box, x);
        
        box.move(x, 0, 0);
        
        for (BoundingBox box : boxes)
            z = box.zCollide(this.box, z);
        
        box.move(0, 0, z);
        
        grounded = dY != y && dY < 0;
        if (dX != x) motionX = 0;
        if (dY != y) motionY = 0;
        if (dZ != z) motionZ = 0;
        
        this.x = (box.x + box.length) / 2; 
        this.y = box.y;
        this.z = (box.z + box.width) / 2;
    }
    
    protected void goForward(float x, float z, float speed)
    {
        float distance = (x * x) + (z * z);
        
        if (distance != 0)
        {
            double sin = Math.sin(Math.toRadians(yaw)), cos = Math.cos(Math.toRadians(yaw));
            distance = speed / (float) Math.sqrt(distance);
            x *= distance;
            z *= distance;
            motionX += (x * cos) - (z * sin);
            motionZ += (z * cos) + (x * sin);
        }
    }
    
    public void tick() { dX = x; dY = y; dZ = z; }
}
