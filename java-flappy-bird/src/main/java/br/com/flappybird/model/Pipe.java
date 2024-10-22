package br.com.flappybird.model;

import java.awt.Image;

/**
 *
 * @author Guilherme Monteiro
 */
public class Pipe {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean passed;
    Image img;

    public Pipe(int x, int y, int width, int height, boolean passed, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.passed = passed;
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

}
