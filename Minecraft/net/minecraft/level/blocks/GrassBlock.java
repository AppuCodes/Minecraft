package net.minecraft.level.blocks;

import net.minecraft.level.blocks.Blocks.Block;

public class GrassBlock extends Block
{
    public GrassBlock(String name, int id, int texture)
    {
        super(name, id, texture);
    }
    
    protected int[] getTex()
    {
        return new int[] {tex + 2, tex, tex + 3, tex + 3, tex + 3, tex + 3};
    }
}
