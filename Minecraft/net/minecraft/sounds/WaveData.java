package net.minecraft.sounds;

import java.io.*;
import java.nio.ByteBuffer;

import javax.sound.sampled.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import net.minecraft.Minecraft;

public class WaveData
{
    public int size, bytesPerFrame, format, sampleRate;
    private AudioInputStream stream;
    private byte[] dataArray;
    public ByteBuffer bytes;
    public long length;
    
    private WaveData(AudioInputStream stream)
    {
        this.stream = stream;
        AudioFormat audioFormat = stream.getFormat();
        format = getFormat(audioFormat.getChannels(), audioFormat.getSampleSizeInBits());
        bytesPerFrame = audioFormat.getFrameSize();
        sampleRate = (int) audioFormat.getSampleRate();
        size = (int) (stream.getFrameLength() * bytesPerFrame);
        length = (long) ((1000 * stream.getFrameLength()) / stream.getFormat().getFrameRate());
        bytes = BufferUtils.createByteBuffer(size);
        dataArray = new byte[size];
        loadData();
    }
    
    public void dispose()
    {
        try
        {
            stream.close();
        } catch (IOException e) {}
    }
    
    private ByteBuffer loadData()
    {
        try
        {
            int bytesRead = stream.read(dataArray, 0, size);
            bytes.clear();
            bytes.put(dataArray, 0, bytesRead);
            bytes.flip();
        }
        
        catch (IOException e)
        {
            Minecraft.LOGGER.error("Couldn't read bytes from audio stream!");
        }
        
        return bytes;
    }
    
    public static WaveData create(String sound)
    {
        try (InputStream stream = WaveData.class.getClassLoader().getResourceAsStream("assets/sounds/" + sound + ".wav");
             AudioInputStream audio = AudioSystem.getAudioInputStream(stream))
        {
            return new WaveData(audio);
        }
        
        catch (IOException | UnsupportedAudioFileException e)
        {
            Minecraft.LOGGER.error("Stream is of unsupported format.");
            return null;
        }
    }
    
    private static int getFormat(int channels, int bitsPerSample)
    {
        if (channels == 1)
            return bitsPerSample == 8 ? AL10.AL_FORMAT_MONO8 : AL10.AL_FORMAT_MONO16;
        else
            return bitsPerSample == 8 ? AL10.AL_FORMAT_STEREO8 : AL10.AL_FORMAT_STEREO16;
    }
}
