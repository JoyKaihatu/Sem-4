import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window =
            new Window
    (800,800,"Hello World");
    private ArrayList<Object> objects
            = new ArrayList<>();
    private ArrayList<Object> objectsRectangle
            = new ArrayList<>();

    private ArrayList<Object> objectsPointsControl
            = new ArrayList<>();

    private MouseInput mouseInput;
    int countDegree = 0;
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();
    public void init(){
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
        glDepthRange(0.0f, 0.5f);

        mouseInput = window.getMouseInput();
        camera.setPosition(0,0,1.0f);
        camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(30.0f));
        //code
//        objects.add(new Object2d(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.vert"
//                , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.frag"
//                , GL_FRAGMENT_SHADER)
//            ),
//            new ArrayList<>(
//                List.of(
//                    new Vector3f(0.0f,0.5f,0.0f),
//                    new Vector3f(-0.5f,-0.5f,0.0f),
//                    new Vector3f(0.5f,-0.5f,0.0f)
//                )
//            ),
//            new Vector4f(0.0f,1.0f,1.0f,1.0f)
//        ));
//        objects.add(new Object(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/" +
//                    "sceneWithVerticesColor.vert"
//                        , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                    ("resources/shaders/" +
//                    "sceneWithVerticesColor.frag"
//                            , GL_FRAGMENT_SHADER)
//        ),
//        new ArrayList<>(
//                List.of(
//                    new Vector3f(0.0f,0.5f,0.0f),
//                    new Vector3f(-0.5f,-0.5f,0.0f),
//                    new Vector3f(0.5f,-0.5f,0.0f)
//                )
//            ),
//        new ArrayList<>(
//            List.of(
//                new Vector3f(1.0f,0.0f,0.0f),
//                new Vector3f(0.0f,1.0f,0.0f),
//                new Vector3f(0.0f,0.0f,1.0f)
//            )
//        )
//        ));
//        objectsRectangle.add(new Rectangle(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.vert"
//                , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.frag"
//                , GL_FRAGMENT_SHADER)
//            ),
//            new ArrayList<>(
//                List.of(
//                    new Vector3f(0.0f,0.0f,0.0f),
//                    new Vector3f(0.5f,0.0f,0.0f),
//                    new Vector3f(0.0f,0.5f,0.0f),
//                    new Vector3f( 0.5f,0.5f,0.0f)
//                )
//            ),
//            new Vector4f(0.0f,1.0f,1.0f,1.0f),
//            Arrays.asList(0,1,2,1,2,3)
//
//        ));
//        objectsPointsControl.add(new Object(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.vert"
//                , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.frag"
//                , GL_FRAGMENT_SHADER)
//            ),
//            new ArrayList<>(),
//            new Vector4f(0.0f,1.0f,1.0f,1.0f)
//        ));
        List<ShaderProgram.ShaderModuleData> shader = Arrays.asList(
                //shaderFile lokasi menyesuaikan objectnya
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        );
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Dalam/a.obj"

        ));
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Dalam/b.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Dalam/c.obj"

        ));
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Dalam/d.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Dalam/e.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Dalam/f.obj"

        ));

        // Bangunan Luar

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/0.obj"

        ));
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/0-5.obj"

        ));
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/a.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/b.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/c.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/d.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/e.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/f.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/g.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/h.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Env/Bangunan Luar/i.obj"

        ));

        // Lemari Kayu
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Lemari Kayu/Lemari Kayu.obj"

        ));

        // Lemari Pakaian
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Lemari Pakaian/Lemari Pakaian.obj"

        ));

        // Meja Gaming
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Gaming/CPU.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Gaming/headset.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Gaming/keyboard.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Gaming/monitor.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Gaming/mouse.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Gaming/mousepad.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Gaming/table.obj"

        ));

        // Meja Makan

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Makan/Bunga.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Makan/Kursi.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Makan/Meja Besar.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Makan/Meja 1.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Makan/Tempat Minum 1.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Makan/Tempat Minum 2.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja Makan/Vas.obj"

        ));

        // Meja TV

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Meja TV/Meja TV.obj"

        ));

        // Sofa

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Sofa/Bantal Sofa.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Sofa/Sofa.obj"

        ));

        // Tempat Tidur

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Tempat Tdr/Bantal.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Tempat Tdr/Kasur.obj"

        ));


        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Tempat Tdr/Penyangga.obj"

        ));

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/Tempat Tdr/Seprai.obj"

        ));

        // TV

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(),
                "resources/Aset/ABlend/TV/TV.obj"

        ));








    }
    public void input(){
        float move = 0.1f;
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveForward(move);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (window.isKeyPressed(GLFW_KEY_W)){
            camera.moveUp(move);
        }
        if (window.isKeyPressed(GLFW_KEY_S)){
            camera.moveDown(move);
        }
        if(mouseInput.isLeftButtonPressed()){
            Vector2f displayVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float)Math.toRadians(displayVec.x * 0.1f),
                    (float)Math.toRadians(displayVec.y * 0.1f));
        }
        if(window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()- (window.getMouseInput().getScroll().y*0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }
    }
    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f,
                    0.0f, 0.0f,
                    0.0f);
            GL.createCapabilities();
            input();

            //code
            for(Object object: objects){
                object.draw(camera,projection);
            }
//            for(Object object: objectsRectangle){
//                object.draw();
//            }
//            for(Object object: objectsPointsControl){
//                object.drawLine();
//            }

            // Restore state
            glDisableVertexAttribArray(0);

            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    public void run() {

        init();
        loop();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public static void main(String[] args) {
        new Main().run();
    }
}