import Engine.ShaderProgram;
import Engine.SphereBabi;
import Engine.Wheezy_Object;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;


public class WheezyClass {

    public WheezyClass() {
        this.bodyPart = new ArrayList<>();
    }

    ArrayList<Wheezy_Object> bodyPart;
    List<ShaderProgram.ShaderModuleData> shaderModuleDataList = Arrays.asList(
            new ShaderProgram.ShaderModuleData(
                    "Project Grafkom-22\\Main\\resources\\shaders\\scene.vert", GL_VERTEX_SHADER),
            new ShaderProgram.ShaderModuleData(
                    "Project Grafkom-22\\Main\\resources\\shaders\\scene.frag", GL_FRAGMENT_SHADER)
    );
    public ArrayList<Wheezy_Object> makeWheezy(){
        bodyPart.add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0f, 0f, 0f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.32f,// titik x
                0.48f,// titik y
                0.40f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                1));// option
        bodyPart.get(0).rotateObject(-0.40f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).translateObject(-0.3f, 0f, 0f); // POSISI

        //bagian perut yg putih
        bodyPart.get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0.89f, 0.87f, 0.74f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.16f,// titik x
                0.22f,// titik y
                0.05f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                2));// option
        bodyPart.get(0).getChildObject().get(0).rotateObject(-0.40f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(0).translateObject(-0.41f, -0.18f, 0.33f); // POSISI

        //tangan kanan
        bodyPart.get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0f, 0f, 0f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.16f,// titik x
                0.26f,// titik y
                0.2f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                3));// option
        bodyPart.get(0).getChildObject().get(1).rotateObject(2.2f, 1.3f, 0f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(1).rotateObject(1.2f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(1).translateObject(-0.138f, 0.16f, 0.28f); // POSISI

        //tangan Kiri
        bodyPart.get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0f, 0f, 0f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.16f,// titik x
                0.26f,// titik y
                0.2f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                3));// option

        bodyPart.get(0).getChildObject().get(2).rotateObject(2.2f, 1.3f, 0f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(2).rotateObject(4.4f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(2).translateObject(-0.59f, 0.16f, 0.08f); // POSISI


        //kelopak mata kanan
        bodyPart.get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0.95f, 1f, 1f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.05f,// titik x
                0.038f,// titik y
                0.02f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                4));// option

        bodyPart.get(0).getChildObject().get(3).rotateObject(-0.40f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(3).translateObject(-0.3f, 0.46f, 0.2f); // POSISI

        //kelopak mata kiri
        bodyPart.get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0.95f, 1f, 1f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.05f,// titik x
                0.038f,// titik y
                0.02f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                4));// option

        bodyPart.get(0).getChildObject().get(4).rotateObject(-0.40f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(4).translateObject(-0.42f, 0.465f, 0.12f); // POSISI

        //mata kanan
        bodyPart.get(0).getChildObject().get(3).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0f, 0f, 0f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.03f,// titik x
                0.02f,// titik y
                0.01f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                4));// option

        bodyPart.get(0).getChildObject().get(3).getChildObject().get(0).rotateObject(-0.40f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(3).getChildObject().get(0).translateObject(-0.31f, 0.45f, 0.21f); // POSISI

        //mata kiri
        bodyPart.get(0).getChildObject().get(4).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0f, 0f, 0f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.03f,// titik x
                0.02f,// titik y
                0.01f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                4));// option

        bodyPart.get(0).getChildObject().get(4).getChildObject().get(0).rotateObject(-0.40f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(4).getChildObject().get(0).translateObject(-0.43f, 0.455f, 0.13f);// POSISI

        // paruh atas
        bodyPart.get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0.95f, 0.75f, 0.09f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.01f,// titik x
                0.017f,// titik y
                0.023f,// titik z
                15, // Stack -->
                30, // Sector --> TitikW
                5));// option

        bodyPart.get(0).getChildObject().get(5).rotateObject(3.13f, 1f, 0f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(5).translateObject(-0.44f, 0.3f, 0.51f); // POSISI

        // paruh bwh
        bodyPart.get(0).getChildObject().get(5).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0.95f, 0.75f, 0.09f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.008f,// titik x
                0.007f,// titik y
                0.02f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                5));// option

        bodyPart.get(0).getChildObject().get(5).getChildObject().get(0).rotateObject(3.13f, 1f, 0f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(5).getChildObject().get(0).translateObject(-0.44f, 0.26f, 0.5f); // POSISI

        //buletan dasi
        bodyPart.get(0).getChildObject().get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(1f, 0f, 0f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.03f,// titik x
                0.02f,// titik y
                0.06f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                4));// option

        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).rotateObject(-0.40f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).translateObject(-0.35f, -0.009f, 0.19f);// POSISI


        //dasi kanan
        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(1f, 0f, 0f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.07f,// titik x
                0.068f,// titik y
                0.09f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                6));// option

        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).getChildObject().get(0).rotateObject(29.84f, 1f, 0f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).getChildObject().get(0).rotateObject(-30.40f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).getChildObject().get(0).translateObject(-0.415f, -0.009f, 0.41f);// POSISI

        //dasi kiri
        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(1f, 0f, 0f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.07f,// titik x
                0.068f,// titik y
                0.09f,// titik z
                15, // Stack -->
                30, // Sector --> Titik
                6));// option

        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).getChildObject().get(1).rotateObject(29.81f, 1f, 0f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).getChildObject().get(1).rotateObject(29.3f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(0).getChildObject().get(0).getChildObject().get(1).translateObject(-0.465f, -0.007f, 0.39f);// POSISI

        //kaki kiri
        bodyPart.get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0.89f, 0.87f, 0.74f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.2f,// titik x
                0.3f,// titik y
                0.11f,// titik z
                150, // Stack -->
                150, // Sector --> Titik
                4));// option

        bodyPart.get(0).getChildObject().get(6).rotateObject(1.57f, 1f, 0f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(6).rotateObject(90f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(6).translateObject(-0.5f, -0.41f, 0.1f);

        //kaki kanan
        bodyPart.get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0.89f, 0.87f, 0.74f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.2f,// titik x
                0.3f,// titik y
                0.11f,// titik z
                150, // Stack -->
                150, // Sector --> Titik
                4));// option

        bodyPart.get(0).getChildObject().get(7).rotateObject(1.57f, 1f, 0f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(7).rotateObject(-60f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(7).translateObject(-0.2f, -0.41f, 0.3f);

        //pantat penguin
        bodyPart.get(0).getChildObject().add(new Wheezy_Object(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new ArrayList<>(
                ),
                new Vector4f(0f, 0f, 0f, 1.0f),// color
                0.00,// radius
                new ArrayList<>(List.of(0f, 0f, 0f)),// center point
                0.2f,// titik x
                0.3f,// titik y
                0.15f,// titik z
                150, // Stack -->
                150, // Sector --> Titik
                4));// option

        bodyPart.get(0).getChildObject().get(8).rotateObject(-0.40f, 0f, 1f, 0f);// putar biar bgs kliatannya
        bodyPart.get(0).getChildObject().get(8).translateObject(-0.23f, -0.21f, -0.14f);

        //alis penguin kanan
        bodyPart.get(0).getChildObject().add(new SphereBabi(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                List.of(0.0f, 0.0f, 0.0f),
                0.24f,
                0.24f,
                Arrays.asList(
                        new Vector3f(-0.095f,0.19f,0.60f),//TITIK1
                        new Vector3f(-0.05f,0.22f,0.60f), //TITIK2
                        new Vector3f(-0.01f,0.19f,0.60f) //TITIK3
                )
        ));
        bodyPart.get(0).getChildObject().get(9).rotateObject(0.6f,1f, 0.0f, 0.0f);
        bodyPart.get(0).getChildObject().get(9).rotateObject(-0.28f,0f, 1.0f, 0.0f);
        bodyPart.get(0).getChildObject().get(9).translateObject(-0.16f, 0.7f, -0.18f);


        //alis penguin Kiri
        bodyPart.get(0).getChildObject().add(new SphereBabi(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                List.of(0.0f, 0.0f, 0.0f),
                0.24f,
                0.24f,
                Arrays.asList(
                        new Vector3f(-0.095f,0.19f,0.60f),//TITIK1
                        new Vector3f(-0.05f,0.22f,0.60f), //TITIK2
                        new Vector3f(-0.01f,0.19f,0.60f) //TITIK3
                )
        ));
        bodyPart.get(0).getChildObject().get(10).rotateObject(0.6f,1f, 0.0f, -0.0f);
        bodyPart.get(0).getChildObject().get(10).rotateObject(-0.28f,0f, 1.0f, -0.0f);
        bodyPart.get(0).getChildObject().get(10).translateObject(-0.285f, 0.7f, -0.25f);

        return bodyPart;
    }
}
