package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Object2d extends ShaderProgram{

    List<Vector3f> vertices;
    List<Vector3f>verticesColor;

    int vao;

    int vbo;

    int vbocolor;
    int vaocolor;

    UniformsMap uniformsMap;
    Matrix4f model;

    Vector4f color;



    public Object2d(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color){
        super(shaderModuleDataList);
        this.vertices = vertices;
        setupVAOVBO();
        uniformsMap = new UniformsMap(getProgramId());
        uniformsMap.createUniform("uni_color");
        uniformsMap.createUniform("model");
        this.color = color;
        model = new Matrix4f().identity();
    }

    public Object2d(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, List<Vector3f> verticesColor){
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.verticesColor = verticesColor;
        setupVAOVBOWithVerticesColor();

    }

    public void setupVAOVBO(){
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,Utils.listoFloat(vertices),GL_STATIC_DRAW);

        vbocolor = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbocolor);
        glBufferData(GL_ARRAY_BUFFER,Utils.listoFloat(vertices),GL_STATIC_DRAW);

    }

    public void setupVAOVBOWithVerticesColor(){
        vaocolor = glGenVertexArrays();
        glBindVertexArray(vaocolor);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,Utils.listoFloat(vertices),GL_STATIC_DRAW);

        vbocolor = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbocolor);
        glBufferData(GL_ARRAY_BUFFER,Utils.listoFloat(verticesColor),GL_STATIC_DRAW);

    }

    public void drawSetup(){
        bind();

        uniformsMap.setUniform("uni_color",color);
        uniformsMap.setUniform(
                "model", model);
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false,0,0);

    }

    public void drawSetupWithVerticesColor(){
        bind();


        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false,0,0);

        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER,vbocolor);
        glVertexAttribPointer(1, 3, GL_FLOAT, false,0,0);

    }

    public void draw(){
        drawSetup();

        glLineWidth(10);
        glPointSize(10);

        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT

        glDrawArrays(GL_TRIANGLES,0,vertices.size());
    }

    public void drawLine(){
        drawSetup();

        glLineWidth(3);
        glPointSize(3);

        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT

        glDrawArrays(GL_LINE_STRIP,0,vertices.size());
    }
    public void addVertices(Vector3f newVertices){
        vertices.add(newVertices);
        setupVAOVBO();
    }


    public void drawWithVerticesColor(){
        drawSetupWithVerticesColor();

        glLineWidth(10);
        glPointSize(10);

        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT

        glDrawArrays(GL_TRIANGLES,0,vertices.size());
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }
    public void move(double x, double y, int save){
        vertices.set(save,new Vector3f((float)x,(float)y,0.f));
        setupVAOVBO();

    }
    public int  getIndex(float x, float y){
        int index = 0;
        for(int i = 0; i < vertices.size(); i++){
            System.out.println(vertices.get(i).x + "Vertices X");
            System.out.println(vertices.get(i).y + "Vertices Y");
            if(vertices.get(i).x == x && vertices.get(i).y == y){
                break;
            }
            index++;
        }
        System.out.println(index);
        return index;

    }
    public void drawLineBerzier(){
        drawSetup();
        // draw the vertices
        glLineWidth(5); // ketebalan garis
        glPointSize(1); // besar kecil vertex
        //wajib
        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT

        glDrawArrays(GL_LINE_STRIP,0, vertices.size());
//        glDrawArrays(GL_LINE_LOOP,0, vertices.size());
    }
    public float getCenterx(){
        return 0;
    }

    public float getCentery(){
        return 0;
    }

    public void moves(float centerx, float centery){
    }
    public Matrix4f getMatrix(){
        return model;
    }

    public void translateObject(Float offsetX, Float offsetY, Float offsetZ){
        model = new Matrix4f().translate(offsetX,offsetY,offsetZ).mul(new Matrix4f(model));
    }

    public void rotateObject(Float degree, Float x, Float y, Float z){
        model = new Matrix4f().rotate(degree,x,y,z).mul(new Matrix4f(model));
    }

    public void scaleObject(Float scaleX, Float scaleY, Float scaleZ){
        model = new Matrix4f().scale(scaleX,scaleY,scaleZ).mul(new Matrix4f(model));
    }

}
