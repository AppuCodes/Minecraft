package net.minecraft;

import java.util.Random;

import net.minecraft.level.Level;
import net.minecraft.level.entities.Player;
import net.minecraft.options.*;
import net.minecraft.render.global.*;
import net.minecraft.sounds.SoundMaster;
import net.minecraft.sounds.music.MusicHandler;
import net.minecraft.utils.*;

public class Minecraft
{
    public static final Logger LOGGER = new Logger("Minecraft");
    public static Random random;
    public static Player player;
    public static Level level;
    
    protected static void init(Session session)
    {
        LOGGER.info("Booting up with player " + session.name() + ".");
        Window.create("Minecraft 0.1");
        SoundMaster.init(); MusicHandler.init();
        
        random = new Random();
        level = new Level(random.nextLong());
        player = new Player(level, session.name());
        TickyThread.start();
        BuildyThread.start();
        
        Window.lockMouse();
        handleKeyboard();
        
        while (Window.isOpen())
        {
            Window.startFrame();
            float partialTicks = Ticker.partialTicks;
            
            Window.worldScreen();
            level.draw(partialTicks);
            
            // Window.guiScreen();
            Window.endFrame();
        }
        
        SoundMaster.close();
    }
    
    protected static void tick()
    {
        player.tick();
        level.tick();
    }
    
    protected static void handleKeyboard()
    {
        Window.keyboardEvent((key, scanCode, action, mods) ->
        {
            if (action == 1)
            {
                for (KeyBinding bind : GameOptions.KEYS)
                {
                    if (key == bind.key)
                        bind.press();
                }
            }
            
            else if (action == 0)
            {
                for (KeyBinding bind : GameOptions.KEYS)
                {
                    if (key == bind.key)
                        bind.release();
                }
            }
        });
    }
    
    public static void main(String[] args)
    {
        init(new Session("Notch"));
    }
}
