package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;


public class Kotak extends Object2d{
    double cx,cy,panjang,lebar;
    double x,y;


    public Kotak(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                 double cx, double cy, double panjang, double lebar) {
        super(shaderModuleDataList, vertices, color);
        this.cx = cx;
        this.cy = cy;
        this.panjang = panjang;
        this.lebar = lebar;
        CreateKotak();
        setupVAOVBO();



    }

    public double getCx() {
        return cx;
    }

    public double getCy() {
        return cy;
    }

    public double getPanjang() {
        return panjang;
    }

    public double getLebar() {
        return lebar;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void CreateKotak(){

        //clear vertices
        vertices.clear();

        for (float i = 45; i < 405; i+=90) {
            x = cx + ((panjang) * Math.cos(Math.toRadians(i)));
            y = cy + ((lebar) * Math.sin(Math.toRadians(i)));
            vertices.add(new Vector3f((float) x, (float) y, 0.0f));
        }



        }

    public void draw(){
        drawSetup();

//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
//        glDrawElements(GL_TRIANGLE_FAN, index.size(),GL_UNSIGNED_INT, 0);
        glDrawArrays(GL_TRIANGLE_FAN,0,vertices.size());


        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT

    }
}
