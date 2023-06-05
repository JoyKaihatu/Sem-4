import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class DummyTest {
    private Window window = new Window(800, 800, "Dummy Main");
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());

    ArrayList<Object> importX = new ArrayList<Object>();
    List<ShaderProgram.ShaderModuleData> shaderModuleDataList = Arrays.asList(
            new ShaderProgram.ShaderModuleData(
                    "resources\\shaders\\scene.vert", GL_VERTEX_SHADER),
            new ShaderProgram.ShaderModuleData(
                    "resources\\shaders\\scene.frag", GL_FRAGMENT_SHADER)
    );
    Model m = null;
    public void run() {
        init();
        loop();
        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
        glDepthRange(0.0f, 1.0f);

        camera.setPosition(-0.12f, -0.33999997f, 8.54f);
        camera.setRotation((float) Math.toRadians(0.0f), (float) Math.toRadians(0.0f));

        try{
            m = ObjLoader.loadModel(new File("D:\\Data Kuliah\\Sem. 4\\Grafkom\\Obj3\\src\\blenderAssets\\rubiks_cube.obj"));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        importX.add(new Sphere(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
                0.0,
                new ArrayList<>(List.of(0f, 0f, 0f)),
                4.0f,
                0.1f,
                8.0f,
                15, // Stack -->
                30, // Sector --> Titik
                m));
    }

    public void input() {

//        if (window.isKeyPressed(GLFW_KEY_U)) {
//            for (LGM_Object i : LGMO) {
//                i.translateObject(0f, 0f, 0.001f);
//            }
//        }
//
//        if (window.isKeyPressed(GLFW_KEY_O)) {
//            for (LGM_Object i : LGMO) {
//                i.translateObject(0f, 0f, -0.001f);
//            }
//        }
//
//        if (window.isKeyPressed(GLFW_KEY_I)) {
//            for (LGM_Object i : LGMO) {
//                i.translateObject(0f, 0.001f, 0f);
//            }
//        }
//
//        if (window.isKeyPressed(GLFW_KEY_K)) {
//            for (LGM_Object i : LGMO) {
//                i.translateObject(0f, -0.001f, 0f);
//            }
//        }
//
//        if (window.isKeyPressed(GLFW_KEY_J)) {
//            for (LGM_Object i : LGMO) {
//                i.translateObject(-0.001f, 0f, 0f);
//            }
//        }
//
//        if (window.isKeyPressed(GLFW_KEY_L)) {
//            for (LGM_Object i : LGMO) {
//                i.translateObject(0.001f, 0f, 0f);
//            }
//        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_ALT)) {
            camera.moveForward(0.12f);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveBackwards(0.12f);
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveDown(0.12f);
        }

        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveUp(0.12f);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.moveLeft(0.12f);
        }

        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.moveRight(0.12f);
        }

        if (window.getMouseInput().isLeftButtonPressed()){
            Vector2f displayVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float)Math.toRadians(displayVec.x * 0.1f), (float) Math.toRadians(displayVec.y * 0.1f));
        }

        if (window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()-(window.getMouseInput().getScroll().y*0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }

        if(window.isKeyPressed(GLFW_KEY_Z)){
            camera.rotateObjectCamera(0f,(float)(Math.toRadians(1)));
        }

        if(window.isKeyPressed(GLFW_KEY_COMMA))
        {
            camera.addRotation((float) Math.toRadians(0f), (float) Math.toRadians(-1f));
        }
        if(window.isKeyPressed(GLFW_KEY_SLASH))
        {
            camera.addRotation((float) Math.toRadians(0f), (float) Math.toRadians(1f));
        }
        if(window.isKeyPressed(GLFW_KEY_CAPS_LOCK))
        {
            camera.addRotation((float) Math.toRadians(1f), (float) Math.toRadians(0f));
        }
        if(window.isKeyPressed(GLFW_KEY_TAB))
        {
            camera.addRotation((float) Math.toRadians(-1f), (float) Math.toRadians(0f));
        }
    }



    public void loop() {

        while (window.isOpen()) {
            window.update();
            glClearColor(0f,0f,0f, 1.0f); // RapidTables.com (RGB color code chart)
            GL.createCapabilities();
            glClearDepth(1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            input();


            for (Object obj3D : importX) {
                obj3D.draw(camera, projection);
            }

            System.out.println("X"+camera.getPosition().get(0));
            System.out.println("Y"+camera.getPosition().get(1));
            System.out.println("Z"+camera.getPosition().get(2));



            System.out.println((float)(Math.toRadians(1)));




            //Restore State
            glDisableVertexAttribArray(0);
            // Pull for window events
            // The key callback above will only be
            // invoked during this call
            glfwPollEvents();
        }
    }


    public static void main (String[]args){
        new DummyTest().run();
    }
}

