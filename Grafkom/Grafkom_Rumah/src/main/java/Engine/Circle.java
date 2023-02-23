package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

public class Circle extends Object2d {

    double r,cx,cy;
    double x,y;


    public Circle(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, double r, double cx, double cy){
        super(shaderModuleDataList, vertices, color);
        this.r =  r;
        this.cx = cx;
        this.cy = cy;
        createCircle();
        setupVAOVBO();



    }

    public void createCircle()
    {
        //clear vertices
        vertices.clear();

        for (float i = 0; i < 360; i+=0.1)
        {
            x = cx + r * Math.cos(Math.toRadians(i));
            y = cy + r * Math.sin(Math.toRadians(i));
            vertices.add(new Vector3f((float) x, (float) y, 0.0f));
        }
    }


}



