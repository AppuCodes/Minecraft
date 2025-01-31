package net.minecraft.sounds.music;

import java.util.Random;

import net.minecraft.Window;
import net.minecraft.sounds.*;

public class MusicHandler
{
    public static String[] survivalMusic = new String[] {"clark", "danny", "dry_hands", "haggstrom", "key", "living_mice", "mice_on_venus", "oxygene", "subwoofer_lullaby", "sweden", "wet_hands"},
            creativeMusic = new String[] {"aria_math", "biome_fest", "blind_spots", "dreiton", "haunt_muskie", "taswell"},
            festiveMusic = new String[] {"flake", "kyoto"};
    
    private static Random random = new Random();
    
    public static void init()
    {
        Thread music = new Thread(() ->
        {
            while (Window.isOpen())
            {
                pause(random.nextInt(300, 600));
                SoundSource source = SoundMaster.loadSound(survivalMusic[random.nextInt(survivalMusic.length)]);
                source.play(true);
            }
        });
        
        music.setName("Music Thread");
        music.setDaemon(true);
        music.start();
    }
    
    private static void pause(long seconds)
    {
        try
        {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }
}
