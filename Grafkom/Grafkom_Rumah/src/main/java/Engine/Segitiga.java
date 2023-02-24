package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Segitiga extends Object2d{
    double alas,tinggi,cx,cy;


    public Segitiga(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                    double alas,double tinggi,double cx, double cy) {
        super(shaderModuleDataList, vertices, color);
        this.cx = cx;
        this.cy = cy;
        this.alas = alas;
        this.tinggi = tinggi;
        CreateSegitiga();
        setupVAOVBO();
    }

    public void CreateSegitiga(){
        vertices.clear();

        vertices.add(new Vector3f((float)cx,(float)(cy + (tinggi/2f)),0.0f));
        vertices.add(new Vector3f((float)(cx - (alas/2f)),(float)(cy-(tinggi/2f)),0.0f));
        vertices.add(new Vector3f((float)(cx + (alas/2f)),(float)(cy-(tinggi/2f)),0.0f));

    }

    public void draw(){
        drawSetup();

        glDrawArrays(GL_TRIANGLE_FAN,0,vertices.size());
    }
}
