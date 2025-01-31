package net.minecraft.utils;

public class BoundingBox
{
    public double x, y, z, length, height, width;
    
    public BoundingBox(double x, double y, double z, double length, double height, double width)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.length = length;
        this.height = height;
        this.width = width;
    }
    
    public double xCollide(BoundingBox box, double x)
    {
        /* if the boxes are colliding on the Y axis */
        if (box.height <= y || box.y >= height)
            return x;
        
        /* if the boxes are colliding on the Z axis */
        if (box.width <= z || box.z >= width)
            return x;
        
        /* if the X axis of the box is bigger */
        if (x > 0 && box.length <= this.x)
        {
            double max = this.x - box.length;
            
            if (max < x)
                x = max;
        }
        
        /* if the X axis of the box is smaller */
        if (x < 0 && box.x >= length)
        {
            double max = length - box.x;
            
            if (max > x)
                x = max;
        }
        
        return x;
    }
    
    public double yCollide(BoundingBox box, double y)
    {
        /* if the boxes are colliding on the X axis */
        if (box.length <= x || box.x >= length)
            return y;
        
        /* if the boxes are colliding on the Z axis */
        if (box.width <= z || box.z >= width)
            return y;
        
        /* if the Y axis of the box is bigger */
        if (y > 0 && box.height <= this.y)
        {
            double max = this.y - box.height;
            
            if (max < y)
                y = max;
        }
        
        /* if the Y axis of the box is smaller */
        if (y < 0 && box.y >= height)
        {
            double max = height - box.y;
            
            if (max > y)
                y = max;
        }
        
        return y;
    }
    
    public double zCollide(BoundingBox box, double z)
    {
        /* if the boxes are colliding on the X axis */
        if (box.length <= x || box.x >= length)
            return z;
        
        /* if the boxes are colliding on the Y axis */
        if (box.height <= y || box.y >= height)
            return z;
        
        /* if the Z axis of the box is bigger */
        if (z > 0 && box.width <= this.z)
        {
            double max = this.z - box.width;
            
            if (max < z)
                z = max;
        }
        
        /* if the Z axis of the box is smaller */
        if (z < 0 && box.z >= width)
        {
            double max = width - box.z;
            
            if (max > z)
                z = max;
        }
        
        return z;
    }
    
    public BoundingBox expand(double x, double y, double z)
    {
        BoundingBox box = new BoundingBox(this.x, this.y, this.z, length, height, width);
        
        if (x < 0)
            box.x += x;
        else
            box.length += x;
        
        if (y < 0)
            box.y += y;
        else
            box.height += y;
        
        if (z < 0)
            box.z += z;
        else
            box.width += z;
        
        return box;
    }
    
    public void move(double x, double y, double z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
        length += x;
        height += y;
        width += z;
    }
    
    public BoundingBox grow(double x, double y, double z)
    {
        return new BoundingBox(this.x - x, this.y - y, this.z - z, length + x, height + y, width + z);
    }
}
