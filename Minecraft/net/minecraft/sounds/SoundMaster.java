package net.minecraft.sounds;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

import java.nio.IntBuffer;
import java.util.*;

import org.lwjgl.openal.*;
import org.lwjgl.system.MemoryUtil;

public class SoundMaster
{
    private static HashMap<String, SoundSource> sources = new HashMap<>();
    private static ArrayList<Integer> buffers = new ArrayList<>();
    private static ALCCapabilities capabilities;
    private static long device, context;
    
    public static void init()
    {
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        device = alcOpenDevice(defaultDeviceName);
        capabilities = ALC.createCapabilities(device);
        context = alcCreateContext(device, (IntBuffer) null);
        alcMakeContextCurrent(context);
        AL.createCapabilities(capabilities);
        setProperties(0, 0, 0);
    }
    
    public static SoundSource loadSound(String name)
    {
        if (!sources.containsKey(name))
        {
            int buffer = alGenBuffers();
            buffers.add(buffer);
            WaveData wave = WaveData.create(name);
            alBufferData(buffer, wave.format, wave.bytes, wave.sampleRate);
            wave.dispose();
            SoundSource source = new SoundSource(buffer, wave.length);
            sources.put(name, source);
            return source;
        }
        
        return sources.get(name);
    }
    
    public static void setProperties(float x, float y, float z)
    {
        alListener3f(AL_POSITION, x, y, z);
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }
    
    public static void close()
    {
        for (SoundSource sound : sources.values())
            sound.close();
        
        for (int buffer : buffers)
            alDeleteBuffers(buffer);
        
        alcMakeContextCurrent(MemoryUtil.NULL);
        alcDestroyContext(context);
        alcCloseDevice(device);
    }
}
