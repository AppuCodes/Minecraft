package net.minecraft.level.blocks;

import net.minecraft.level.blocks.Blocks.Block;

public class WaterLiquid extends Block
{
    public WaterLiquid(String name, int id, int texture)
    {
        super(name, id, texture);
        passable = true;
    }
    
    protected float blockHeight() { return 14.5F / 16; }
}