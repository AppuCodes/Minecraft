package net.minecraft.utils.vectors;

import net.minecraft.level.Chunk;

public class Vec3f
{
    public float x, y, z;
    
    public Vec3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /* converts to chunk coordinates */
    public Vec3f chunkify()
    {
        if (x < 0) x -= Chunk.CHUNK_SIZE; if (z < 0) z -= Chunk.CHUNK_SIZE;
        x /= Chunk.CHUNK_SIZE; y = 0; z /= Chunk.CHUNK_SIZE;
        return this;
    }
    
    public float distanceTo(Vec3f vector)
    {
        float x = this.x - vector.x,
              y = this.y - vector.y,
              z = this.z - vector.z;
        x *= x; y *= y; z *= z;
        return (float) Math.sqrt(x + y + z);
    }
    
    public boolean equals(Object obj)
    {
        return obj instanceof Vec3f vector && (vector.x == x && vector.y == y && vector.z == z);
    }
    
    /* debugging purposes */
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        return builder.append(x).append(", ").append(y).append(", ").append(z).toString();
    }
    
    public Vec3f clone() { return new Vec3f(x, y, z); }
}
