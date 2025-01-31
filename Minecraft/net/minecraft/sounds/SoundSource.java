package net.minecraft.sounds;

import static org.lwjgl.openal.AL10.*;

public class SoundSource
{
    private int id, buffer;
    public long length;
    
    public SoundSource(int buffer, long length)
    {
        this.buffer = buffer;
        this.length = length;
        id = alGenSources();
        alSourcef(id, AL_GAIN, 1);
        alSourcef(id, AL_PITCH, 1);
        alSourcef(id, AL_POSITION, 1);
    }
    
    public void play(boolean block)
    {
        alSourcei(id, AL_BUFFER, buffer);
        alSourcePlay(id);
        
        if (block)
        {
            try
            {
                Thread.sleep(length);
            } catch (InterruptedException e) {}
        }
    }
    
    public void close()
    {
        alDeleteSources(id);
    }
}
