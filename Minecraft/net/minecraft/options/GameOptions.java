package net.minecraft.options;

import static org.lwjgl.glfw.GLFW.*;

public class GameOptions
{
    public static final KeyBinding KEY_FORWARD = new KeyBinding("Forward", GLFW_KEY_W),
            KEY_BACKWARD = new KeyBinding("Backward", GLFW_KEY_S),
            KEY_LEFT = new KeyBinding("Left", GLFW_KEY_A),
            KEY_RIGHT = new KeyBinding("Right", GLFW_KEY_D),
            KEY_JUMP = new KeyBinding("Jump", GLFW_KEY_SPACE);
    
    public static final SliderOption RENDER_DISTANCE = new SliderOption("Render Distance", 2, 6, 12);
    
    public static final KeyBinding[] KEYS = new KeyBinding[]
    {
        KEY_FORWARD, KEY_BACKWARD, KEY_LEFT, KEY_RIGHT, KEY_JUMP
    };
}
