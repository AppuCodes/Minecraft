package net.minecraft.utils.vectors;

import net.minecraft.level.Chunk;

public class Vec3i
{
    public int x, y, z;
    
    public Vec3i(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /* converts to chunk coordinates */
    public Vec3i chunkify()
    {
        if (x < 0) x -= Chunk.CHUNK_SIZE; if (z < 0) z -= Chunk.CHUNK_SIZE;
        x /= Chunk.CHUNK_SIZE; y = 0; z /= Chunk.CHUNK_SIZE;
        return this;
    }
    
    public int distanceTo(Vec3i vector)
    {
        int x = this.x - vector.x,
            y = this.y - vector.y,
            z = this.z - vector.z;
        x *= x; y *= y; z *= z;
        return (int) Math.sqrt(x + y + z);
    }
    
    public boolean equals(Object obj)
    {
        return obj instanceof Vec3i vector && (vector.x == x && vector.y == y && vector.z == z);
    }
    
    /* debugging purposes */
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        return builder.append(x).append(", ").append(y).append(", ").append(z).toString();
    }
}
