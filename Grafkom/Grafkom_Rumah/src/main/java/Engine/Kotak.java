package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Kotak extends Object2d{
    double cx,cy,panjang,lebar;
    double panjang2,lebar2;


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

    public void CreateKotak(){

        vertices.clear();

        panjang2 = (float)(this.panjang/2f);
        lebar2 = (float)(this.lebar/2f);

        vertices.add(new Vector3f((float)(panjang2 + this.cx),(float)(lebar2 + this.cy),0.0f));

        vertices.add(new Vector3f((float)(panjang2 + this.cx),(float)(lebar2 - this.cy),0.0f));

        vertices.add(new Vector3f((float)(panjang2 - this.cx),(float)(lebar2 - this.cy),0.0f));

        vertices.add(new Vector3f((float)(panjang2 - this.cx),(float)(lebar2 + this.cy),0.0f));


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
