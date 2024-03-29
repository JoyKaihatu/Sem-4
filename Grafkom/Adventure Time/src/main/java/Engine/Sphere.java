package Engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengles.GLES20.glGenerateMipmap;

public class Sphere extends CircleNew{
    int sectorCount;
    int stackCount;
    Float radiusZ;
    List<Vector3f> normal;
    List<Vector2f> texture;
    int nbo;
    int data;
    List<Float> centerpoint;
    String path;

    int tekstur;

    public Sphere(List<ShaderModuleData> shaderModuleDataList,
                  List<Vector3f> vertices, Vector4f color,
                  List<Float> centerPoint,String path){
        super(shaderModuleDataList, vertices, color, centerPoint);

        this.color = color;
        this.centerpoint = centerPoint;
        this.path = path;

//        if (pilih == 0){
//            Ellipsoid();
//        }
//        else if (pilih == 1){
//            Hyperboloid();
//        }
//        else if (pilih == 2){
//            Hyperboloid2();
//        }
//        else if (pilih == 3){
//            EllipticCone();
//        }
//        else if(pilih == 4){
//            EllipticParaboloid();
//        }
//        else if(pilih == 5){
//            HyperboloidParaboloid();
//        }
//        else if(pilih == 6){
//            createBoxVertices();
//        }
//        else if(pilih == 7){
//            createCylinder();
//        }
//        else if (pilih == 8){
//            createTriangle();
//        }
//        else if (pilih == 9){
//            createCornerlessBox();
//        }
//        else if(pilih == 10){
//            createCylinderStengah();
//        }
//        else if (pilih == 11){
//            createParaboloid();
//        }
//        else if (pilih == 12){
//            createCylinderBerdiri();
//        }
//        else if(pilih == 13){
//            halfEllipsoid();
//        }
        loadObject();
//        createBoxBaru();
        setupVAOVBO();
    }

    public ArrayList<Vector3f> createCircles(Vector3f Centre, float radiusZ, int pick){
        ArrayList<Vector3f> temp1 = new ArrayList<>();
        float j = 0;
        float k = 0;
        if(pick == 0){
            j = 90;
            k = 0;
        }
        if(pick == 1){
            j = 0;
            k = -90;
        }
        if(pick == 2){
            j = -90;
            k = -180;
        }
        if(pick == 3){
            j = -180;
            k = -270;
        }

        for (float i = j; i >= k; i = i - 2) {

            x = Centre.x() + (radiusZ/16  * Math.cos(Math.toRadians(i)));
            y = Centre.y() + (radiusZ/16 * Math.sin(Math.toRadians(i)));
            temp1.add(new Vector3f((float)x, (float)y, Centre.z()));
        }


        return (temp1);
    }
    public ArrayList<Vector3f> generateBezierPoints(float firstX, float firstY, float firstZ, float secondX, float secondY, float secondZ, float thirdX, float thirdY, float thirdZ)
    {
        ArrayList<Vector3f> result = new ArrayList<>();
        float newX, newY, newZ;
        for(double i = 0; i <=1; i+= 0.01)
        {
            newX = (float) ((Math.pow((1-i), 2) * firstX) + (2 * (1-i) * i * secondX) + (Math.pow(i, 2) * thirdX));
            newY = (float) ((Math.pow((1-i), 2) * firstY) + (2 * (1-i) * i * secondY) + (Math.pow(i, 2) * thirdY));
            newZ = (float) ((Math.pow((1-i), 2) * firstZ) + (2 * (1-i) * i * secondZ) + (Math.pow(i, 2) * thirdZ));
            result.add(new Vector3f(newX, newY, newZ));
        }
        return result;
    }

    public void createCornerlessBox(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        Integer count = 0;

        ArrayList<Vector3f> berzier1 = generateBezierPoints(centerPoint.get(0) + radiusX/2 - radiusX/16,
                centerPoint.get(1) + radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) + radiusX/2,centerPoint.get(1) + radiusY/2,centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) + radiusX/2,centerPoint.get(1) + radiusY/2 - radiusY/16,centerPoint.get(2) + radiusZ/2);
        ArrayList<Vector3f> berzier2 = generateBezierPoints(centerPoint.get(0) + radiusX/2 - radiusX/16, centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) + radiusX/2, centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) + radiusX/2 , centerPoint.get(1) + radiusY/2 - radiusY/16, centerPoint.get(2) - radiusZ/2);
        temp.addAll(berzier1);
        temp.addAll(berzier2);

        for(int i = 0; i < berzier1.size(); i++){
            if(count == 0){
                temp.add(berzier1.get(i));
                temp.add(berzier2.get(i));
                count = 1;
            }
            else {
                temp.add(berzier2.get(i));
                temp.add(berzier1.get(i));
                count = 0;
            }
        }
        count = 0;
        temp.add(berzier2.get(berzier2.size()-1));
        temp.add(berzier1.get(berzier1.size() - 1));


        ArrayList<Vector3f> berzier3 =  generateBezierPoints(centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2 + radiusY/16, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) + radiusX/2 - radiusX/16, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2);
        ArrayList<Vector3f> berzier4 =  generateBezierPoints(centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2 + radiusY/16, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) + radiusX/2 - radiusX/16, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2);
        temp.addAll(berzier3);
        temp.addAll(berzier4);

        for(int i = 0; i < berzier3.size(); i++){

            if(count == 0){
                temp.add(berzier4.get(i));
                temp.add(berzier3.get(i));
                count = 1;
            }
            else {
                temp.add(berzier3.get(i));
                temp.add(berzier4.get(i));
                count = 0;
            }
        }
        count = 0;
        temp.add(berzier3.get(berzier3.size() - 1));


        ArrayList<Vector3f> berzier5 = generateBezierPoints(centerPoint.get(0) - radiusX/2  + radiusX/16, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2 + radiusY/16, centerPoint.get(2) + radiusZ/2);
        ArrayList<Vector3f> berzier6 = generateBezierPoints(centerPoint.get(0) - radiusX/2  + radiusX/16, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2 + radiusY/16, centerPoint.get(2) - radiusZ/2);

        temp.addAll(berzier5);
        temp.addAll(berzier6);

        for(int i = 0; i < berzier5.size(); i++){

            if(count == 0){
                temp.add(berzier5.get(i));
                temp.add(berzier6.get(i));
                count = 1;
            }
            else {
                temp.add(berzier6.get(i));
                temp.add(berzier5.get(i));
                count = 0;
            }
        }
        count = 0;
        temp.add(berzier6.get(berzier6.size()- 1));
        ArrayList<Vector3f> berzier7 = generateBezierPoints(centerPoint.get(0) - radiusX/2 , centerPoint.get(1) + radiusY/2 - radiusY/16, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) + radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) - radiusX/2 + radiusX/16, centerPoint.get(1) + radiusY/2, centerPoint.get(2) + radiusZ/2);
        ArrayList<Vector3f> berzier8 = generateBezierPoints(centerPoint.get(0) - radiusX/2 , centerPoint.get(1) + radiusY/2 - radiusY/16, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) - radiusX/2 + radiusX/16, centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2);
        temp.addAll(berzier7);
        temp.addAll(berzier8);

        for(int i = 0; i < berzier7.size(); i++){

            if(count == 0){
                temp.add(berzier7.get(i));
                temp.add(berzier8.get(i));
                count = 1;
            }
            else {
                temp.add(berzier8.get(i));
                temp.add(berzier8.get(i));
                count = 0;
            }
        }
        temp.add(berzier8.get(berzier8.size() - 1));
        temp.add(berzier1.get(0));
        temp.add(berzier7.get(berzier7.size() - 1));
        temp.add(berzier2.get(0));
        temp.add(berzier8.get(berzier8.size() - 1));
        temp.add(berzier4.get(0));
        temp.add(berzier1.get(berzier1.size() - 1));
        temp.add(berzier3.get(0));
        temp.add(berzier2.get(berzier2.size()-1));
        temp.add(berzier6.get(0));
        temp.add(berzier8.get(berzier8.size() - 1));
        temp.add(berzier8.get(0));
        temp.add(berzier4.get(berzier4.size() - 1));
        temp.add(berzier2.get(0));
        temp.add(berzier6.get(berzier6.size() - 1));
        temp.add(berzier8.get(0));
        temp.add(berzier5.get(berzier5.size() - 1));
        temp.add(berzier7.get(0));
        temp.add(berzier1.get(berzier1.size() - 1));
        temp.add(berzier5.get(0));
        temp.add(berzier7.get(berzier7.size() - 1));
        temp.add(berzier3.get(0));
        temp.add(berzier5.get(berzier5.size() - 1));
        temp.add(berzier1.get(0));
        temp.add(berzier6.get(berzier6.size() - 1));
        temp.add(berzier4.get(0));
        temp.add(berzier2.get(berzier2.size() - 1));
        temp.add(berzier8.get(0));
        temp.add(berzier8.get(berzier8.size() - 1));
        temp.add(berzier5.get(0));
        temp.add(berzier4.get(berzier4.size() - 1));
        temp.add(berzier6.get(0));
        temp.add(berzier3.get(berzier3.size() - 1));
        temp.add(berzier7.get(0));
        temp.add(berzier1.get(0));
        temp.add(berzier7.get(berzier7.size() - 1));
        temp.add(berzier6.get(0));
        temp.add(berzier5.get(0));
        temp.add(berzier7.get(berzier7.size() - 1));
        temp.add(berzier7.get(0));
        temp.add(berzier5.get(berzier5.size() - 1));




        vertices = temp;
    }
    public void createTriangle(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        temp.add(new Vector3f(centerPoint.get(0), centerPoint.get(1) + radiusY/2, centerPoint.get(2) + radiusZ/2 ));
        temp.add(new Vector3f(centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2));
        temp.add(new Vector3f(centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2));
        temp.add(new Vector3f(centerPoint.get(0), centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2 ));
        temp.add(new Vector3f(centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2));
        temp.add(new Vector3f(centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2));


        vertices.add(temp.get(0));
        vertices.add(temp.get(1));
        vertices.add(temp.get(2));
        vertices.add(temp.get(0));
        vertices.add(temp.get(3));
        vertices.add(temp.get(4));
        vertices.add(temp.get(5));
        vertices.add(temp.get(3));
        vertices.add(temp.get(1));
        vertices.add(temp.get(5));
        vertices.add(temp.get(2));
        vertices.add(temp.get(4));
        vertices.add(temp.get(0));
        vertices.add(temp.get(1));
        vertices.add(temp.get(3));
        vertices.add(temp.get(2));
        vertices.add(temp.get(0));
        vertices.add(temp.get(5));
        vertices.add(temp.get(4));
        vertices.add(temp.get(1));
    }

    public void createCylinderStengah()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for (double i = 180; i < 360; i+=0.1)
        {
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));

            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2)));
            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2) - radiusZ));
        }
        temp.add(temp.get(1));
        temp.add(temp.get(0));
        temp.add(temp.get(temp.size() - 4));

        vertices = temp;
    }
    public void createCylinder()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        ArrayList<Vector3f> temp1 =  new ArrayList<>();
        ArrayList<Vector3f> temp2 = new ArrayList<>();

//        for (double i = 0; i < 360; i+=0.1)
//        {
//            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
//            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));
//
//            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2)));
//            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2) - radiusZ));
//        }

        for (double i = 0; i < 360; i+=1){
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));

            temp1.add(new Vector3f((float)x, (float)y, centerPoint.get(2)));
        }

        for (double i = 0; i < 360; i+=1){
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));

            temp2.add(new Vector3f((float)x, (float)y, centerPoint.get(2) - radiusZ));
        }

        temp.addAll(temp1);
        temp.addAll(temp2);

        Integer count = 0;

        for(int i = 0; i < temp1.size(); i++){
            if(count == 0){
                temp.add(temp1.get(i));
                temp.add(temp2.get(i));
                count = 1;
            }
            else {
                temp.add(temp1.get(i));
                temp.add(temp2.get(i));
                count = 0;
            }
        }
        temp.add(temp2.get(0));
        temp.add(temp1.get(0));
        temp.add(temp1.get(temp1.size() - 1));


        vertices = temp;
    }

    public void createCylinderBerdiri()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        ArrayList<Vector3f> temp1 =  new ArrayList<>();
        ArrayList<Vector3f> temp2 = new ArrayList<>();

//        for (double i = 0; i < 360; i+=0.1)
//        {
//            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
//            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));
//
//            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2)));
//            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2) - radiusZ));
//        }

        for (double i = 0; i < 360; i+=1){
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(2) + radiusZ * (float)Math.sin(Math.toRadians(i));

            temp1.add(new Vector3f((float)x, (float)y, centerPoint.get(1)));
        }

        for (double i = 0; i < 360; i+=1){
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(2) + radiusZ * (float)Math.sin(Math.toRadians(i));

            temp2.add(new Vector3f((float)x, (float)y, centerPoint.get(1) - radiusY));
        }

        temp.addAll(temp1);
        temp.addAll(temp2);

        Integer count = 0;

        for(int i = 0; i < temp1.size(); i++){
            if(count == 0){
                temp.add(temp1.get(i));
                temp.add(temp2.get(i));
                count = 1;
            }
            else {
                temp.add(temp1.get(i));
                temp.add(temp2.get(i));
                count = 0;
            }
        }
        temp.add(temp2.get(0));
        temp.add(temp1.get(0));
        temp.add(temp1.get(temp1.size() - 1));


        vertices = temp;
    }

    public void Ellipsoid(){

        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/90){
                float x = radiusX * (float)(Math.cos(v) * Math.cos(u));
                float y = radiusY * (float)(Math.cos(v) * Math.sin(u));
                float z = radiusZ * (float)(Math.sin(v));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void Hyperboloid(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = radiusX * (float)((1/Math.cos(v)) * Math.cos(u));
                float z = radiusY * (float)((1/Math.cos(v)) * Math.sin(u));
                float y = radiusZ * (float)(Math.tan(v));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void Hyperboloid2(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI/2; u<= Math.PI/2; u+=Math.PI/60){
                float x = radiusX * (float)(Math.tan(v) * Math.cos(u));
                float z = radiusY * (float)(Math.tan(v) * Math.sin(u));
                float y = radiusZ * (float)((1/Math.cos(v)));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }

            for(double u = Math.PI/2; u<= (3 * (Math.PI/2)); u+=Math.PI/60){
                float x = radiusX * (float)(Math.tan(v) * Math.cos(u));
                float z = radiusY * (float)(Math.tan(v) * Math.sin(u));
                float y = radiusZ * (float)((1/Math.cos(v)));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }

        vertices=temp;
    }

    public void EllipticCone(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float)((radiusX * v) * Math.cos(u));
                float z = (float)((radiusY * v) * Math.sin(u));
                float y = (float)(radiusZ * v);
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void halfEllipsoid(){

        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI/80; u+=Math.PI/90){
                float x = radiusX * (float)(Math.cos(v) * Math.cos(u));
                float y = radiusY * (float)(Math.cos(v) * Math.sin(u));
                float z = radiusZ * (float)(Math.sin(v));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void EllipticParaboloid(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float)((radiusX * v) * Math.cos(u));
                float z = (float)((radiusZ * v) * Math.sin(u));
                float y = (float)(radiusY * v * v);
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void createParaboloid(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float)((radiusX * v) * Math.cos(u));
                float y = (float)((radiusY * v) * Math.sin(u));
                float z = (float)(radiusZ * v * v);
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void HyperboloidParaboloid(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float)((radiusX * v) * Math.tan(u));
                float y = (float)((radiusY * v) * (1/Math.cos(u)));
                float z = (float)(radiusZ* v * v);
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void createBoxVertices()
    {
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //TITIK 1
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 2
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 3
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 4
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 5
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 6
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 7
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 8
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();

        vertices.clear();
        //kotak1
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        //kotak2
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(5));
        //kotak3
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(4));
        //kotak4
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));
        //kotak5
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        //kotak6
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
    }


    public void loadObject(){
        System.out.println("Code done");
        vertices.clear();
        normal = new ArrayList<>();
        texture = new ArrayList<>();
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();

        Model n = null;

        try {
            n = ObjLoader.loadModel(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }




        for (Face face : n.faces){
            Vector3f n1 = n.normals.get((int) face.normal.x - 1);
//            n1.x = n1.x + centerpoint.get(0);
//            n1.y = n1.y + centerpoint.get(1);
//            n1.z = n1.z + centerpoint.get(2);
            normal.add(n1);
            Vector3f v1 = n.vertices.get((int) face.vertex.x - 1);
//            v1.x = v1.x + centerpoint.get(0);
//            v1.y = v1.y + centerpoint.get(1);
//            v1.z = v1.z + centerpoint.get(2);
            vertices.add(v1);
//            Vector2f t1 = n.textures.get((int) face.texture.x - 1);
//            texture.add(t1);
            Vector3f n2 = n.normals.get((int) face.normal.y - 1);
//            n2.x = n2.x + centerpoint.get(0);
//            n2.y = n2.y + centerpoint.get(1);
//            n2.z = n2.z + centerpoint.get(2);
            normal.add(n2);
            Vector3f v2 = n.vertices.get((int) face.vertex.y - 1);
//            v2.x = v2.x + centerpoint.get(0);
//            v2.y = v2.y + centerpoint.get(1);
//            v2.z = v2.z + centerpoint.get(2);
            vertices.add(v2);
//            Vector2f t2 = n.textures.get((int) face.texture.y - 1);
//            texture.add(t2);
            Vector3f n3 = n.normals.get((int) face.normal.z - 1);
//            n3.x = n3.x + centerpoint.get(0);
//            n3.y = n3.y + centerpoint.get(1);
//            n3.z = n3.z + centerpoint.get(2);
            normal.add(n3);
            Vector3f v3 = n.vertices.get((int) face.vertex.z - 1);
//            v3.x = v3.x + centerpoint.get(0);
//            v3.y = v3.y + centerpoint.get(1);
//            v3.z = v3.z + centerpoint.get(2);
            vertices.add(v3);
//            Vector2f t3 = n.textures.get((int) face.texture.z - 1);
//            texture.add(t3);
        }

    }


    public void createBoxBaru(){
        System.out.println("code");
        vertices.clear();
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();

        //Titik 1 kiri atas belakang
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 2 kiri bawah belakang
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 3 kanan bawah belakang
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 4 kanan atas belakang
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 5 kiri atas depan
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 6 kiri bawah depan
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 7 kanan bawah depan
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 8 kanan atas depan
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //kotak belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));
        //kotak depan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(4));
        //kotak samping kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(4));

        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        //kotak samping kanan
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(7));
        //kotak bawah
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(1));
        //kotak atas
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(3));

        normal = new ArrayList<>(Arrays.asList(
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),

                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),

                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),

                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),

                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f( 0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),

                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f)
        ));

    }

    @Override
    public void setupVAOVBO() {
        super.setupVAOVBO();

        nbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(normal), GL_STATIC_DRAW);
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat2f(texture), GL_STATIC_DRAW);


//        uniformsMap.createUniform("lightColor");
//        uniformsMap.createUniform("lightPos");
    }

    @Override
    public void drawSetup(Camera camera, Projection projection) {
        super.drawSetup(camera, projection);

        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);



        uniformsMap.setUniform("uni_color",color);

        //directional Light
        uniformsMap.setUniform("dirLight.direction", new Vector3f(0.0f,0.0f,0.0f));
        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f,0.05f,0.05f));
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.4f,0.4f,0.4f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.5f,0.5f,0.5f));

        //Posisi Point Light
        Vector3f[] _pointLightPositions ={
                new Vector3f(0.7f, 0.2f,2.0f),
                new Vector3f(2.3f,-3.3f,-4.0f),
                new Vector3f(-4.0f,2.0f,-12.0f),
                new Vector3f(0.0f,0.0f,-3.0f)
        };
        for(int i = 0; i< _pointLightPositions.length;i++){
            uniformsMap.setUniform("pointLights["+i+"].position",_pointLightPositions[i]);
            uniformsMap.setUniform("pointLights["+i+"].ambient", new Vector3f(0.05f,0.05f,0.05f));
            uniformsMap.setUniform("pointLights["+i+"].diffuse", new Vector3f(0.8f,0.8f,0.8f));
            uniformsMap.setUniform("pointLights["+i+"].specular", new Vector3f(1.0f,1.0f,1.0f));
            uniformsMap.setUniform("pointLights["+i+"].constant", 1.0f );
            uniformsMap.setUniform("pointLights["+i+"].linear", 0.09f);
            uniformsMap.setUniform("pointLights["+i+"].quadratic", 0.032f);

        }

        //spotLight

        uniformsMap.setUniform("spotLight.position",camera.getPosition()); // Buat mengikuti camera
        uniformsMap.setUniform("spotLight.direction", camera.getDirection());
        uniformsMap.setUniform("spotLight.ambient",new Vector3f(0.0f,0.0f,0.0f));
        uniformsMap.setUniform("spotLight.diffuse", new Vector3f(1.0f,1.0f,1.0f));
        uniformsMap.setUniform("spotLight.specular", new Vector3f(1.0f,1.0f,1.0f));
        uniformsMap.setUniform("spotLight.cutOff", (float)Math.cos(Math.toRadians(12.5f)));
        uniformsMap.setUniform("spotLight.outerCutOff",(float)Math.cos(Math.toRadians(12.5f)));

        uniformsMap.setUniform("spotLight.constant", 1.0f );
        uniformsMap.setUniform("spotLight.linear", 0.09f);
        uniformsMap.setUniform("spotLight.quadratic", 0.032f);



//        uniformsMap.setUniform("lightColor",new Vector3f(1.0f,1.0f,0.0f));
//        uniformsMap.setUniform("lightPos",new Vector3f(1.0f,1.0f,0.0f));
        uniformsMap.setUniform("viewPos", camera.getPosition());

    }



//    public void draw(Camera camera, Projection projection){
//        if(this.flag) {
//            drawSetup(camera, projection);
//            // Draw the vertices
//            glLineWidth(1);
//            glPointSize(2);
//            glDrawArrays(GL_POLYGON, 0, vertices.size());
//            for (Object2d child : childObject) {
//                child.draw(camera, projection);
//            }
//        }
//    }

    @Override
    public void update(float x, float y, float z) {
        centerPoint.set(0,x);
        centerPoint.set(1,y);
        centerPoint.set(2,z);
    }

}