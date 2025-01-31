package net.minecraft;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.DoubleBuffer;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import net.minecraft.level.entities.Player;
import net.minecraft.options.GameOptions;
import net.minecraft.render.global.RenderGlobal;
import net.minecraft.utils.GLU;

public class Window
{
    private static double odX, odY, dX, dY;
    public static float cursorX, cursorY;
    private static int width, height;
    private static long id;
    
    public static void create(String title)
    {
        width = 1280; height = 720;
        GLFWErrorCallback.createPrint(System.err).set();
        
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        // glfwWindowHint(GLFW_RESIZABLE, 1);
        id = glfwCreateWindow(width, height, title, 0, 0);
        
        if (id == 0)
            throw new RuntimeException("Failed to create the GLFW window");
        
        glfwMakeContextCurrent(id);
        glfwSwapInterval(0);
        baseCallbacks();
        setupOpenGL();
        glfwShowWindow(id);
    }
    
    public static void startFrame()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            DoubleBuffer cursorX = stack.mallocDouble(1), cursorY = stack.mallocDouble(1);
            glfwGetCursorPos(id, cursorX, cursorY);
            Window.cursorX = (float) cursorX.get();
            Window.cursorY = (float) cursorY.get();
        }
    }
    
    public static void endFrame()
    {
        glfwSwapBuffers(id);
        glfwPollEvents();
    }
    
    public static void guiScreen()
    {
        glLoadIdentity();
        glDisable(GL_FOG);
        glDisable(GL_DEPTH_TEST);
        glViewport(0, 0, width, height);
        glOrtho(0, width, height, 0, 1, 0);
    }
    
    public static void worldScreen()
    {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glViewport(0, 0, width, height);
        GLU.perspective(70, width / (float) height, 0.05F, Math.max((GameOptions.RENDER_DISTANCE.getValue() + 1) * 16, 1024));
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_FOG);
    }
    
    private static void setupOpenGL()
    {
        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        RenderGlobal.enableStates(GL_VERTEX_ARRAY, GL_TEXTURE_COORD_ARRAY, GL_COLOR_ARRAY);
    }
    
    private static void baseCallbacks()
    {
        glfwSetWindowSizeCallback(id, new GLFWWindowSizeCallback()
        {
            public void invoke(long id, int width, int height)
            {
                Window.width = width;
                Window.height = height;
            }
        });
        
        glfwSetCursorPosCallback(id, new GLFWCursorPosCallback()
        {
            public void invoke(long id, double dX, double dY)
            {
                Window.dX = dX; Window.dY = dY;
                Player player = Minecraft.player;
                if (player != null) player.turn(mouseDX(), mouseDY());
            }
        });
    }
    
    public static void mouseEvent(MouseButtonListener listener)
    {
        glfwSetMouseButtonCallback(id, new GLFWMouseButtonCallback()
        {
            public void invoke(long id, int button, int action, int mods)
            {
                listener.onClick(button, action, mods);
            }
        });
    }
    
    public static void keyboardEvent(KeyboardListener listener)
    {
        glfwSetKeyCallback(id, new GLFWKeyCallback()
        {
            public void invoke(long id, int key, int scanCode, int action, int mods)
            {
                listener.onKey(key, scanCode, action, mods);
            }
        });
    }
    
    public static void scrollEvent(ScrollListener listener)
    {
        glfwSetScrollCallback(id, new GLFWScrollCallback()
        {
            @Override
            public void invoke(long id, double xOffset, double yOffset)
            {
                listener.onScroll(xOffset, yOffset);
            }
        });
    }
    
    public static double mouseDX()
    {
        double delta = dX - odX;
        odX = dX;
        return delta;
    }
    
    public static double mouseDY()
    {
        double delta = dY - odY;
        odY = dY;
        return delta;
    }
    
    public static void lockMouse()
    {
        glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSetInputMode(id, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
    }
    
    public static void unlockMouse()
    {
        glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }
    
    public static boolean isOpen()
    {
        return !glfwWindowShouldClose(id);
    }
    
    public static int getWidth() { return width; }
    public static int getHeight() { return height; }
    
    public interface MouseButtonListener
    {
        void onClick(int button, int action, int mods);
    }
    
    public interface ScrollListener
    {
        void onScroll(double xOffset, double yOffset);
    }
    
    public interface KeyboardListener
    {
        void onKey(int key, int scanCode, int action, int mods);
    }
}
