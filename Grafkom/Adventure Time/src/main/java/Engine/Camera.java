package Engine;

import org.joml.*;

public class Camera {

    private Vector3f direction;
    private Vector3f position;
    private Vector3f right;
    private Vector2f rotation;
    private Vector3f up;
    private Matrix4f viewMatrix;
    private Vector3f koor;

    public Camera() {
        direction = new Vector3f();
        right = new Vector3f();
        up = new Vector3f();
        position = new Vector3f();
        viewMatrix = new Matrix4f();
        rotation = new Vector2f();
        koor = new Vector3f();
    }

    public void addRotation(float x, float y) {
        rotation.add(x, y);
        recalculate();
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRight() {
        return right;
    }

    public void setRight(Vector3f right) {
        this.right = right;
    }

    public Vector2f getRotation() {
        return rotation;
    }

    public void setRotation(Vector2f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }

    public Vector3f getKoor() {
        return koor;
    }

    public void setKoor(Vector3f koor) {
        this.koor = koor;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void moveBackwards(float inc) {
        viewMatrix.positiveZ(direction).negate().mul(inc);
        position.sub(direction);
        recalculate();
    }
    public void rotate(float x,float y, Vector3f koor){
        viewMatrix.translate(koor.x, koor.y, koor.z).rotateX(x).rotateY(y).translate(-koor.x, -koor.y, -koor.z);

    }

    public void moveDown(float inc) {
        viewMatrix.positiveY(up).mul(inc);
        position.sub(up);
        recalculate();
    }

    public void moveForward(float inc) {
        viewMatrix.positiveZ(direction).negate().mul(inc);
        position.add(direction);
        recalculate();
    }

    public void moveLeft(float inc) {
        viewMatrix.positiveX(right).mul(inc);
        position.sub(right);
        recalculate();
    }

    public void moveRight(float inc) {
        viewMatrix.positiveX(right).mul(inc);
        position.add(right);
        recalculate();
    }

    public void moveUp(float inc) {
        viewMatrix.positiveY(up).mul(inc);
        position.add(up);
        recalculate();
    }

    private void recalculate() {
        viewMatrix.identity().rotateX(rotation.x)
                .rotateY(rotation.y)
                .translate(-position.x, -position.y, -position.z );

    }




    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        recalculate();
    }

    public void setRotation(float x, float y) {
        rotation.set(x, y);
        recalculate();
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

}