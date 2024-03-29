
import Engine.*;
import Engine.Object;
import Engine.Rectangle;
import Engine.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;


public class Main {


    private Window window = new Window(800,600,"Hello World");
    private Kotak DragKotak = null;
    private int SimpanIndex = 0;
    private Object2d garis = null;
    private ArrayList<Object2d> objects = new ArrayList<>();
    private ArrayList<Object2d> objectsRectangle = new ArrayList<>();
    private ArrayList<Object2d> objectsCircle =  new ArrayList<>();
    private ArrayList<Object2d> Kotak = new ArrayList<>();
    private ArrayList<Object2d> Segitiga =  new ArrayList<>();
    private ArrayList<Object2d> Star =  new ArrayList<>();

    private ArrayList<Object2d> objectsPointsControl =  new ArrayList<>();
    private ArrayList<Kotak> KotakKhusus = new ArrayList<>();
    private ArrayList<Object2d> titikBerzier = new ArrayList<>();
    private ArrayList<Object2d> objectsRectCircle = new ArrayList<>();
    private ArrayList<Object2d> objectSphere =  new ArrayList<>();
    private ArrayList<Object> objectSphere3D =  new ArrayList<>();

    int tes;
    float rot = 0;

    public int factorial(int angka){
        int hasil = 1;
        for (int i=2;i<=angka;i++){
            hasil = hasil * i;
        }
        return hasil;
    }

    public int kombinasi(int n, int r){
        if (r < 0 || r > n) {
            return 0;
        }return factorial(n)/(factorial(r)*factorial(n-r));
    }

    public void berzier(ArrayList<Object2d> objects){
        int indexBerzier = 0;
        for (float t=0;t<=1;t+=0.01f){
            float x = 0;
            float y = 0;
            int n = objects.size()-1;
            for (int i=0;i<=n;i++){
                int koefisien = kombinasi(n, i);
                float term = koefisien * (float) Math.pow(1-t, n-i) * (float) Math.pow(t, i);
//                System.out.println("cek: " + objects.get(i).getCenterx() + " " + objects.get(i).getCentery());
                x += term * objects.get(i).getCenterx();
                y += term * objects.get(i).getCentery();
            }
//            System.out.println(x + " " + y);
//            System.out.println(titikBerzier.get(0).getCenterx() + " " + titikBerzier.get(0).getCentery());
//            System.out.println(titikBerzier.get(titikBerzier.size()-1).getCenterx() + " " + titikBerzier.get(titikBerzier.size()-1).getCentery());
            if (tes == 0){
                titikBerzier.get(0).addVertices(new Vector3f(x, y, 0));
            }
            if (tes == 1){
                titikBerzier.get(0).move(x,y,indexBerzier);
                indexBerzier += 1;
            }
        }
        if (tes == 0){
            tes = 1;
        }
//        titikBerzier.get(0).addVertices(new Vector3f(objects.get(objects.size()-1).getCenterx(), objects.get(objects.size()-1).getCentery(), 0));
//        System.out.println(titikBerzier.toString());
    }

    public void init(){
        window.init();
        GL.createCapabilities();

        List<ShaderProgram.ShaderModuleData> shader = Arrays.asList(
                //shaderFile lokasi menyesuaikan objectnya
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        );
        List<Integer> starIndex = Arrays.asList(0,3,3,1,1,4,4,2,2,0);

        //code
        objectsPointsControl.add(new Object2d(
                shader,new ArrayList<>(),new Vector4f(0.0f,1.0f,1.0f,1.0f)
        ));


        titikBerzier.add(new Object2d(Arrays.asList(
                new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
        ), new ArrayList<>(),
                new Vector4f(1.0f, 1.0f, 1.0f, 1.0f) // ini untuk warna garisnya
        ));

//        objects.add(new Object2d(
//                Arrays.asList(
//                        //shaderFile lokasi menyesuaikan objectnya
//                        new ShaderProgram.ShaderModuleData
//                                ("resources/shaders/sceneWithVerticesColor.vert"
//                                        , GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData
//                                ("resources/shaders/sceneWithVerticesColor.frag"
//                                        , GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(
//                        List.of(
//                                new Vector3f(0.0f,0.5f,0.0f),
//                                new Vector3f(-0.5f,-0.5f,0.0f),
//                                new Vector3f(0.5f,-0.5f,0.0f)
//                        )
//                ), new ArrayList<>(
//                List.of(
//                        new Vector3f(1.0f,0.0f,0.0f),
//                        new Vector3f(0.0f,1.0f,0.0f),
//                        new Vector3f(0.0f,0.0f,1.0f)
//                )
//        )
//        ));

        //Background Biru
        objectsRectangle.add(new Rectangle(
                shader,
                new ArrayList<>(
                        List.of(
                                new Vector3f(1.0f,1.0f,0.0f),
                                new Vector3f(-1.0f,1.0f,0.0f),
                                new Vector3f(-1.0f,-1.0f,0.0f),
                                new Vector3f(1.0f,-1.0f,0.0f)

                        )
                ), new Vector4f(0.0f,0.0235f,0.7176f,1.0f),
        Arrays.asList(0,1,2,1,2,3)

        ));


        //Background Hijau
        objectsRectangle.add(new Rectangle(
                shader,
                new ArrayList<>(
                        List.of(
                                new Vector3f(1.0f,-0.5f,0.0f),
                                new Vector3f(-1.0f,-0.5f,0.0f),
                                new Vector3f(-1.0f,-1.0f,0.0f),
                                new Vector3f(1.0f,-1.0f,0.0f)

                        )
                ), new Vector4f(0.0f,0.5019f,0.0f,1.0f),
                Arrays.asList(0,1,2,1,2,3)

        ));

        //Body Rumah Kuning
        objectsRectangle.add(new Rectangle(
                shader,
                new ArrayList<>(
                        List.of(
                                new Vector3f(0.65f,0.0f,0.0f), // Top right
                                new Vector3f(-0.65f,0.0f,0.0f),// Top Left
                                new Vector3f(-0.65f,-0.6f,0.0f), // Bot left
                                new Vector3f(0.65f,-0.6f,0.0f) //Bot Right

                        )
                ), new Vector4f(0.5019f,0.5019f,0.0f,1.0f),
                Arrays.asList(0,1,2,1,2,3)

        ));

        //Trapesium Merah
        objectsRectangle.add(new Rectangle(
                shader,
                new ArrayList<>(
                        List.of(
                                new Vector3f(0.6f,0.2f,0.0f),//Top Right
                                new Vector3f(-0.6f,0.2f,0.0f),//Top Left
                                new Vector3f(-0.73f,-0.2f,0.0f),//Bot Left
                                new Vector3f(0.7f,-0.2f,0.0f)// Bot Right

                        )
                ), new Vector4f(1.0f,0.0f,0.0f,1.0f),
                Arrays.asList(0,1,2,3)

        ));

        //Segitiga Atap Kuning
        objectsRectangle.add(new Rectangle(
                shader,
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.565f,0.15f,0.0f), // Top
                                //new Vector3f(-0.65f,0.0f,0.0f),// Top Left
                                new Vector3f(-0.65f,-0.1f,0.0f), // Bot left
//                                new Vector3f(-0.4f,-0.1f,0.0f), //Bot Right
                                new Vector3f(-0.65f,-0.2f,0.0f), //Bot bot Left
                                new Vector3f(-0.45f,-0.2f,0.0f) //Bot bot Right




                        )
                ), new Vector4f(0.5019f,0.5019f,0.0f,1.0f),
                Arrays.asList(0,1,2,3)

        ));


        //Cerobong Asap
        objectsRectangle.add(new Rectangle(
                shader,
                new ArrayList<>(
                        List.of(
                                new Vector3f(0.5f,0.3f,0.0f),//Top Right
                                new Vector3f(0.4f,0.3f,0.0f),//Top Left
                                new Vector3f(0.4f,0.1f,0.0f),//Bot Left
                                new Vector3f(0.5f,0.1f,0.0f)// Bot Right

                        )
                ), new Vector4f(1.0f,1.0f,0.4f,1.0f),
                Arrays.asList(0,1,2,3)

        ));

        objectsRectangle.add(new Rectangle(
                shader,
                new ArrayList<>(
                        List.of(
                                new Vector3f(0.525f,0.4f,0.0f),//Top Right
                                new Vector3f(0.375f,0.4f,0.0f),//Top Left
                                new Vector3f(0.375f,0.3f,0.0f),//Bot Left
                                new Vector3f(0.525f,0.3f,0.0f)// Bot Right

                        )
                ), new Vector4f(1.0f,0.0f,0.0f,1.0f),
                Arrays.asList(0,1,2,3)

        ));
        objectsCircle.add(new Circle(
                shader, new ArrayList<>(List.of()),
                new Vector4f(1.0f,1.0f,0.0f,1.0f),
                0.15,-0.7,0.7,0,0
        ));

        objectsRectangle.add(new Rectangle(
                shader,
                new ArrayList<>(
                        List.of(
                                new Vector3f(0.525f,0.4f,0.0f),//Top Right
                                new Vector3f(0.375f,0.4f,0.0f),//Top Left
                                new Vector3f(0.375f,0.3f,0.0f),//Bot Left
                                new Vector3f(0.525f,0.3f,0.0f)// Bot Right

                        )
                ), new Vector4f(1.0f,0.0f,0.0f,1.0f),
                Arrays.asList(0,1,2,3)

        ));
        objectsCircle.add(new Circle(
                shader, new ArrayList<>(List.of()),
                new Vector4f(0.0f,0.0235f,0.7176f,1.0f),
                0.15,-0.65,0.7,0,0
        ));

//        objectsCircle.add(
//                new Circle(
//                shader,
//                new ArrayList<>(),
//                new Vector4f(1.0f,0.0f,0.0f,1.0f),
//                0.4f,0.5f,0.0f,0
//        ));
//
        Kotak.add(new Kotak(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,0.0f,1.0f),
                0.0f,0.0f,0.5f,0.5f
                ));
//
        Segitiga.add(new Segitiga(
                shader,
                new ArrayList<>(),
                new Vector4f(1.0f,1.0f,0.0f,1.0f),
                0.2,0.3,0.0,0.0
        ));

        Star.add(new Star(shader,
                new ArrayList<>(),
                new Vector4f((204f/255f),(204f/255f),0.0f,1.0f),
                starIndex,-0.3f,0.5f,0.05f));

        Star.add(new Star(shader,
                new ArrayList<>(),
                new Vector4f((204f/255f),(204f/255f),0.0f,1.0f),
                starIndex,-0.1f,0.7f,0.03f));

        Star.add(new Star(shader,
                new ArrayList<>(),
                new Vector4f((204f/255f),(204f/255f),0.0f,1.0f),
                starIndex,0.5f,0.85f,0.03f));

        objectsCircle.add(new Circle(
                shader, new ArrayList<>(List.of()),
                new Vector4f((128f/255f),(128f/255f),(128f/255f),1.0f),
                0.05,0.58,0.55,1,0
        ));
        objectsCircle.add(new Circle(
                shader, new ArrayList<>(List.of()),
                new Vector4f((128f/255f),(128f/255f),(128f/255f),1.0f),
                0.03,0.52,0.51,1,0.02
        ));
        objectsCircle.add(new Circle(
                shader, new ArrayList<>(List.of()),
                new Vector4f((128f/255f),(128f/255f),(128f/255f),1.0f),
                -0.01,0.46,0.48,1,0.04
        ));

        objectSphere.add(new Sphere(shader,new ArrayList<>(),
                new Vector4f((1f),(226f/255f),(74f/255f),1.0f),
                        new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)),0.1f,0.1f,0.1f,100,50,0));

//        objectSphere.add(new Sphere(shader,new ArrayList<>(),
//                new Vector4f((128f/255f),(128f/255f),(128f/255f),1.0f),
//                new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)),0.1f,0.1f,0.1f,100,50,0));
//
//        objectSphere.add(new Sphere(shader,new ArrayList<>(),
//                new Vector4f(1.0f,(128f/255f),(128f/255f),1.0f),
//                new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)),0.1f,0.1f,0.1f,100,50,0));
//
//        objectSphere.get(0).scaleObject(3.0f,3.0f,3.0f);
//        objectSphere.get(1).scaleObject(1.5f,1.5f,1.0f);
//
//        objectSphere.get(1).translateObject(0.4f,0.4f,0.4f);
//        objectSphere.get(2).translateObject(0.4f,-0.4f,0.4f);
//
//        objectSphere.add(new Sphere(shader,new ArrayList<>(),
//                new Vector4f(1.0f,(128f/255f),(128f/255f),1.0f),
//                new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)),0.1f,0.1f,0.1f,100,50,0));
//        objectSphere.get(3).translateObject(-0.4f,0.4f,0.4f);
//        objectSphere.add(new Sphere(shader,new ArrayList<>(),
//                new Vector4f(1.0f,(128f/255f),(128f/255f),1.0f),
//                new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)),0.1f,0.1f,0.1f,100,50,0));
//        objectSphere.get(4).translateObject(0.0f,-0.6f,0.4f);
//        objectSphere.add(new Sphere(shader,new ArrayList<>(),
//                new Vector4f(1.0f,(128f/255f),(128f/255f),1.0f),
//                new ArrayList<>(List.of(objectSphere.get(4).getMatrix().get(3,0),
//                        objectSphere.get(4).getMatrix().get(3,1),
//                        objectSphere.get(4).getMatrix().get(3,2))),0.1f,0.1f,0.1f,100,50,0));
//        objectSphere.get(5).translateObject(-0.2f,-0.2f,-0.2f);
//        objectSphere.get(5).scaleObject(0.5f,0.5f,0.5f);

//        rot+=0.0001;
        objectSphere.get(0).getChildObject().add(new Sphere(shader,new ArrayList<>(),
                new Vector4f((128f/255f),(128f/255f),(128f/255f),1.0f),
                new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)),0.1f,0.1f,0.1f,100,50,0)
        );
        objectSphere.get(0).getChildObject().get(0).translateObject(0.3f,0.3f,0.3f);
        objectSphere.get(0).getChildObject().add(new Sphere(shader, new ArrayList<>(),
                new Vector4f((128f/255f),(128f/255f),(128f/255f),1.0f),
                        new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.1f,0.1f,0.1f,100,50,0));


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
//            for(Object2d object: objects){
//                object.draw();
//            }

//            for(Object2d object: objects){
//                object.drawWithVerticesColor();
//            }
//            for(Object2d object: objectsRectangle){
//                object.draw();
//            }
//            for(Object2d object: objectsCircle){
//                object.draw();
//            }
//            for(Object2d object: Segitiga){
//                object.draw();
//            }

//            for(Object2d object: Kotak){
//                object.draw();
//            }

//            for(Object2d object: Star){
//                object.draw();
//            }

//            for(Object2d object: objectsPointsControl){
//                object.drawLine();
//            }
//            for(Object2d object: KotakKhusus){
//                object.draw();
//            }
//            for (Object2d object : titikBerzier){
//                object.drawLineBerzier();
//            }
            for(Object2d object: objectSphere){
                object.draw();
            }






//                objectSphere.get(2).rotateObject(rot,0.0f,1.0f,0.0f);







            // Restore state
            glDisableVertexAttribArray(0);

            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public void input(){
        Kotak h;
        boolean found = false;
        List<Vector3f> vec ;
        int save = 0;

        List<ShaderProgram.ShaderModuleData> shader = Arrays.asList(
                //shaderFile lokasi menyesuaikan objectnya
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        );
        if(window.isKeyPressed(GLFW_KEY_W)){
            objectSphere.get(0).rotateObject((float) Math.toRadians(0.5f),0.0f,0.0f,1.0f);
            for(Object2d child:objectSphere.get(0).getChildObject()){
                child.translateObject(child.getVertices().get(0).x *-1,
                        child.getVertices().get(0).y *-1,
                        child.getVertices().get(0).z *-1);
            }


        }
        if(window.isKeyPressed(GLFW_KEY_F)){
            objectSphere.get(2).rotateObject((float)Math.toRadians(0.2f),0.0f,0.0f,1.0f);
            objectSphere.get(1).rotateObject((float)Math.toRadians(0.1f),0.0f,0.0f,1.0f);
            objectSphere.get(3).rotateObject(0.3f,0.0f,0.0f,1.0f);
            objectSphere.get(4).rotateObject(0.1f,0.0f,0.0f,1.0f);
//            objectSphere.get(5).update(objectSphere.get(4).getMatrix().get(3,0),
//                    objectSphere.get(4).getMatrix().get(3,1),
//                    objectSphere.get(4).getMatrix().get(3,2));

            System.out.println(objectSphere.get(4).getMatrix());
        }
        if(window.isKeyPressed(GLFW_KEY_G)){
            float x1 = objectSphere.get(1).getMatrix().get(3,0);
            float x2 = objectSphere.get(1).getMatrix().get(3,1);
            float x3 = objectSphere.get(1).getMatrix().get(3,2);
            objectSphere.get(1).translateObject(-x1,-x2,-x3);
            objectSphere.get(1).rotateObject(0.005f,0.0f,0.0f,1.0f);
            objectSphere.get(1).translateObject(x1,x2,x3);

//            objectSphere.get(2).translateObject(-0.4f,0.4f,-0.4f);
//            objectSphere.get(2).rotateObject(0.003f,0.0f,1.0f,0.0f);
//            objectSphere.get(2).translateObject(0.4f,-0.4f,0.4f);
        }
        if(window.isKeyPressed(GLFW_KEY_H)){
//            objectSphere.get(5).rotateObject(0.1f,0.0f,0.0f,1.0f);
        }
        if(window.getMouseInput().isLeftButtonPressed()){
            Vector2f pos = window.getMouseInput().getCurrentPos();
//            System.out.println("x : "+ pos.x + " y : "+pos.y);
            pos.x = (pos.x - (window.getWidth())/2.0f)/(window.getWidth()/2.0f);
            pos.y = (pos.y - (window.getHeight())/2.0f)/(-window.getHeight()/2.0f);
            if((!(pos.x > 1 || pos.x < -0.97)&&!(pos.y >0.97 || pos.y < -1))){
                System.out.println("x : "+ pos.x + " y : "+pos.y);



                for (Kotak Kotak : KotakKhusus){
                    if (Kotak.contains(pos.x,pos.y)){
                        DragKotak = Kotak;
                        found = true;
                        SimpanIndex = save;

                        break;
                    }

                    save += 1;
                }
                if(!found && DragKotak == null){
                    KotakKhusus.add(new Kotak(shader,new ArrayList<>(List.of())
                            ,new Vector4f(1.0f,0.0f,0.0f,1.0f),
                            pos.x,pos.y,0.05,0.05));
                    objectsPointsControl.get(0).addVertices(new Vector3f(pos.x, pos.y,0));
                    objectsRectCircle.add(new RectangleCircle(Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ), new ArrayList<>(
                            List.of(
                                    new Vector3f(),
                                    new Vector3f(),
                                    new Vector3f(),
                                    new Vector3f()
                            )
                    ), new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                            pos.x, pos.y, 0.1f
                    ));
                    berzier(objectsRectCircle);
                }else if(DragKotak != null){
                    objectsPointsControl.get(0).move(pos.x,pos.y,SimpanIndex);
                    objectsRectCircle.get(SimpanIndex).moves(pos.x, pos.y);
                    DragKotak.move(pos.x,pos.y);
                    berzier(objectsRectCircle);

                }



            }

        }
        else if(window.getMouseInput().isleftButtonRelease()){
            DragKotak = null;
            save = 0;
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