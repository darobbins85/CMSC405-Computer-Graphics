/*
 * File: 
 * Author: David Robbins
 * Date: 2018.01.10
 * Purpose: 
 */
package cmsc405project1;

/**
 *
 * @author darob
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CMSC405Project1 extends JPanel {   
    
    private int frameNumber;
    private long elapsedTimeMillis;
    private float pixelSize;
    
    Image axeImage = new Image();
    BufferedImage axe = axeImage.getImageAxe(Image.axe);
    static int axeTranslateX = 0;
    static int axeTranslateY = 0;
    static double axeRotation = 0.0;
    static double axeScaleX = 1.0;
    static double axeScaleY = 1.0;

    Image tImage = new Image();
    BufferedImage letterT = tImage.getImageT(Image.letterT);
    static int tTranslateX = 0;
    static int tTranslateY = 0;
    static double tRotation = 0.0;
    static double tScaleX = 1.0;
    static double tScaleY = 1.0;
    
    Image pacImage = new Image();
    BufferedImage pac = pacImage.getImagePac(Image.pacman);
    static int pacTranslateX = 0;
    static int pacTranslateY = 0;
    static double pacRotation = 0.0;
    static double pacScaleX = 1.0;
    static double pacScaleY = 1.0;
    
    Image pac2Image = new Image();
    BufferedImage pac2 = pac2Image.getImagePac(Image.pacman2);
    static int pac2TranslateX = 0;
    static int pac2TranslateY = 0;
    static double pac2Rotation = 0.0;
    static double pac2ScaleX = 1.0;
    static double pac2ScaleY = 1.0;
    
    Image dotImage = new Image();
    BufferedImage dot = dotImage.getImageDot(Image.dot);
    static int dotTranslateX = 0;
    static int dotTranslateY = 0;
    static double dotRotation = 0.0;
    static double dotScaleX = 1.0;
    static double dotScaleY = 1.0;

    public static void main(String[] args) {
        
        JFrame window;
        window = new JFrame("David's First Animation");
        final CMSC405Project1 panel = new CMSC405Project1();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screen.width - window.getWidth()) / 2, (screen.height - window.getHeight()) / 2);
        Timer animationTimer;
        final long startTime = System.currentTimeMillis();
        animationTimer = new Timer(1600, new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(panel.frameNumber > 6){
                    panel.frameNumber = 0;
                } else {
                    panel.frameNumber++;
                }
                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });
        
        window.setVisible(true);
        animationTimer.start();
    }
        

    public CMSC405Project1(){
        setPreferredSize(new Dimension(800, 600));
        
    }
    
    protected void paintComponent(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);
        AffineTransform savedTransform = g2.getTransform();
        System.out.println("Frame is " + frameNumber);
        switch(frameNumber){
            case 1:
                //axe actions
                axeTranslateX = -70;
                axeTranslateY = 0;
                axeScaleX = 1.0;
                axeScaleY = 1.0;
                axeRotation = 0;
                
                //rocket actions
                tTranslateX = 10;
                tTranslateY = -20;
                tScaleX = 1.0;
                tScaleX = 1.0;
                tRotation = 270 * Math.PI / 180.0;
                
                //pac actions
                pacTranslateX = -90;
                pacTranslateY = -60;
                pacScaleX = 1.0;
                pacScaleX = 1.0;
                pacRotation = 270 * Math.PI / 180.0;
                
                //pac2 actions
                pac2ScaleX = 0.0;
                pac2ScaleX = 0.0;
                
                //dot actions
                dotTranslateX = -50;
                dotTranslateY = -40;
                dotScaleX = 1.0;
                dotScaleY = 1.0;     
                break;
            case 2:
                //axe actions
                axeTranslateX = -60;
                axeTranslateY = 0;
                axeScaleX = 1.1;
                axeScaleY = 1.1;
                axeRotation = 270 * Math.PI / 180.0;
                
                //rocket actions
                tTranslateX = 20;
                tTranslateY = -10;
                tScaleX = .8;
                tScaleY = .8;
                tRotation = 260 * Math.PI / 180.0;
                
                //pac actions
                pacScaleX = 0.0;
                pacScaleX = 0.0;
                
                //pac2 actions
                pac2TranslateX = -70;
                pac2TranslateY = -60;
                pac2ScaleX = 1.0;
                pac2ScaleX = 1.0;
                pac2Rotation = 270 * Math.PI / 180.0;

                //dot actions
                dotScaleX = 0.0;
                dotScaleY = 0.0;                
                break;
            case 3:
                //axe actions
                axeTranslateX = -45;
                axeTranslateY = 10;
                axeScaleX = 1.2;
                axeScaleY = 1.2;
                axeRotation = 180 * Math.PI / 180.0;
                
                //rocket actions
                tTranslateX = 30;
                tTranslateY = 0;
                tScaleX = .6;
                tScaleY = .6;
                tRotation = 250 * Math.PI / 180.0;
                
                //pac actions
                pacTranslateX = -50;
                pacTranslateY = -60;
                pacScaleX = 1.0;
                pacScaleX = 1.0;
                pacRotation = 270 * Math.PI / 180.0;
                
                //pac2 actions
                pac2ScaleX = 0.0;
                pac2ScaleY = 0.0;
                
                //dot actions
                dotTranslateX = -30;
                dotTranslateY = -40;
                dotScaleX = 1.0;
                dotScaleY = 1.0; 
                break;
            case 4:
                //axe actions
                axeTranslateX = -20;
                axeTranslateY = 10;
                axeScaleX = 1.3;
                axeScaleY = 1.3;
                axeRotation = 90 * Math.PI / 180.0;
                
                //rocket actions
                tTranslateX = 40;
                tTranslateY = 10;
                tScaleX = .4;
                tScaleY = .4;
                tRotation = 240 * Math.PI / 180.0;

                //pac actions
                pacScaleX = 0.0;
                pacScaleX = 0.0;
                
                //pac2 actions
                pac2TranslateX = -30;
                pac2TranslateY = -60;
                pac2ScaleX = 1.0;
                pac2ScaleX = 1.0;
                pac2Rotation = 270 * Math.PI / 180.0;
                
                //dot actions
                dotScaleX = 0.0;
                dotScaleY = 0.0;
                break;
            case 5:
                //axe actions
                axeTranslateX = 0;
                axeTranslateY = 0;
                axeScaleX = 1.4;
                axeScaleY = 1.4;
                axeRotation = 0;
                
                //rocket actions
                tTranslateX = 50;
                tTranslateY = 20;
                tScaleX = .2;
                tScaleY = .2;
                tRotation = 230 * Math.PI / 180.0;
                
                //pac actions
                pacTranslateX = -10;
                pacTranslateY = -60;
                pacScaleX = 1.0;
                pacScaleX = 1.0;
                pacRotation = 270 * Math.PI / 180.0;
                
                //pac2 actions
                pac2ScaleX = 0.0;
                pac2ScaleX = 0.0;
                
                //dot actions
                dotTranslateX = -10;
                dotTranslateY = -40;
                dotScaleX = 1.0;
                dotScaleY = 1.0; 
                break;
            case 6:
                //axe actions
                axeTranslateX = 10;
                axeTranslateY = 10;
                axeScaleX = 1.5;
                axeScaleY = 1.5;
                axeRotation = 270 * Math.PI / 180.0;
                
                //rocket actions
                tTranslateX = 60;
                tTranslateY = 30;
                tScaleX = .1;
                tScaleY = .1;
                tRotation = 220 * Math.PI / 180.0;

                //pac actions
                pacScaleX = 0.0;
                pacScaleX = 0.0;
                
                //pac2 actions
                pac2TranslateX = 10;
                pac2TranslateY = -60;
                pac2ScaleX = 1.0;
                pac2ScaleX = 1.0;
                pac2Rotation = 270 * Math.PI / 180.0;
                
                //dot actions
                dotScaleX = 0.0;
                dotScaleY = 0.0;
                break;
            default:
                break;
        }
        
        g2.translate(axeTranslateX, axeTranslateY);
        g2.rotate(axeRotation, 20, 10);
        g2.scale(axeScaleX, axeScaleY);
        g2.drawImage(axe, 0, 0, this);
        g2.setTransform(savedTransform);
        
        g2.translate(tTranslateX, tTranslateY);
        g2.rotate(tRotation, 20, 10);
        g2.scale(tScaleX, tScaleY);
        g2.drawImage(letterT, 0, 0, this);
        g2.setTransform(savedTransform);
        
        g2.translate(pacTranslateX, pacTranslateY);
        g2.rotate(pacRotation, 20, 10);
        g2.scale(pacScaleX, pacScaleY);
        g2.drawImage(pac, 0, 0, this);
        g2.setTransform(savedTransform);
        
        g2.translate(pac2TranslateX, pac2TranslateY);
        g2.rotate(pac2Rotation, 20, 10);
        g2.scale(pac2ScaleX, pac2ScaleY);
        g2.drawImage(pac2, 0, 0, this);
        g2.setTransform(savedTransform);
        
        g2.translate(dotTranslateX, dotTranslateY);
        g2.rotate(dotRotation, 20, 10);
        g2.scale(dotScaleX, dotScaleY);
        g2.drawImage(dot, 0, 0, this);
        g2.setTransform(savedTransform);
    }
    
    private void applyWindowToViewportTransformation(Graphics2D g2,
            double left, double right, double bottom, double top,
            boolean preserveAspect) {
        int width = getWidth();   // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }
    
}