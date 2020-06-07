package game;

import javax.swing.*;
import java.awt.*;

class Test extends Frame {

    static int xPixel = 20;
    static int yPixel = 20;

    Image myImage, offScreenImage;
    Graphics offScreenGraphics;

    public Test() {
        try {
            myImage = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\game\\img\\background1.jpg");
        }
        catch(Exception e) {}
        setSize(800,600);
        setVisible(true);
        moveImage();

    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {

        int width  = getWidth();
        int height = getHeight();

        if (offScreenImage == null) {
            offScreenImage    = createImage(width, height);
            offScreenGraphics = offScreenImage.getGraphics();
        }

        // clear the off screen image
        offScreenGraphics.clearRect(0, 0, width -1, height );

        // draw your image off screen
        offScreenGraphics.drawImage(myImage, xPixel, yPixel, this);

        // show the off screen image
        g.drawImage(offScreenImage, 0, 0, this);

    }

    void moveImage() {

        for ( int i = 0 ; i < 500 ; i++ ){

            System.out.println("next set of Pixels " + xPixel);

            xPixel -=1;
            repaint();

            // then sleep for a bit for your animation
            try { Thread.sleep(50); }   /* this will pause for 50 milliseconds */
            catch (InterruptedException e) { System.err.println("sleep exception"); }

        }
    }

    public static void main(String args[]){

        Test me = new Test();

    }

}

