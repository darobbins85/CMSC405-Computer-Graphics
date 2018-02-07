/*
 * File: 
 * Author: David Robbins
 * Date: 2018.01.10
 * Purpose: 
 */

package cmsc405project1;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Image {

// Constants
    // X Size of Images
    
    // Define a Simple Images based on the Alphabet
    // Use static so can send them into the getImage() method remotely
    // Note these are 10x10 and can be modified to any desired
    // image size by adding or removing rows and columns
    
    
    private final static int IMGSIZEX = 30;
    private final static int IMGSIZEY = 27;
    public static int[][] axe = {
            {0,0,0,0,0,0,6,0,0,0,0,0,0,6,0,0,0,0,0,0,6,0,0,0,0,0,0},
            {0,0,0,0,0,6,6,0,0,0,0,0,6,6,6,0,0,0,0,0,6,6,0,0,0,0,0},
            {0,0,0,6,6,6,6,0,0,0,0,6,6,6,6,6,0,0,0,0,6,6,6,6,0,0,0},
            {0,0,6,6,6,6,6,0,0,0,6,6,6,6,6,6,6,0,0,0,6,6,6,6,6,0,0},
            {0,6,6,6,6,6,6,0,0,0,0,0,1,1,1,0,0,0,0,0,6,6,6,6,6,6,0},
            {6,6,6,6,6,6,6,0,0,0,0,0,1,1,1,0,0,0,0,0,6,6,6,6,6,6,6},
            {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
            {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},        
            {6,6,6,6,6,6,6,0,0,0,0,0,1,1,1,0,0,0,0,0,6,6,6,6,6,6,6},
            {0,6,6,6,6,6,6,0,0,0,0,0,1,1,1,0,0,0,0,0,6,6,6,6,6,6,0},
            {0,0,6,6,6,6,6,0,0,0,0,0,1,1,1,0,0,0,0,0,6,6,6,6,6,0,0},
            {0,0,0,6,6,6,6,0,0,0,0,0,1,1,1,0,0,0,0,0,6,6,6,6,0,0,0},
            {0,0,0,0,0,6,6,0,0,0,0,0,1,1,1,0,0,0,0,0,6,6,0,0,0,0,0},
            {0,0,0,0,0,0,6,0,0,0,0,0,1,1,1,0,0,0,0,0,6,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0}};
    
    public BufferedImage getImageAxe(int[][] data) {
        BufferedImage image = new BufferedImage(IMGSIZEX, IMGSIZEY,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < IMGSIZEX; x++) {
            for (int y = 0; y < IMGSIZEY; y++) {
                int pixelColor = data[x][y];
                // Set Colors based on Binary Image value
                if (pixelColor == 0) {
                    pixelColor = Color.WHITE.getRGB();
                } else if (pixelColor == 1) {
                    pixelColor = Color.BLACK.getRGB();
                } else if (pixelColor == 2) {
                    pixelColor = Color.RED.getRGB();
                } else if (pixelColor == 6) {
                    pixelColor = Color.GRAY.getRGB();
                }
                image.setRGB(x, y, pixelColor);
            } // End for y.
        } // End for x.
        return image;
    } // End getData method.
    
    private final static int TIMGSIZEX = 33;
    private final static int TIMGSIZEY = 17;
    public static int[][] letterT = {
        {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0},
        {0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,3,3,3,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,3,3,3,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,3,3,3,6,6,6,0,0,0,0},
        {0,0,0,0,6,6,6,3,3,3,6,6,6,0,0,0,0},
        {0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
        {0,0,1,1,1,1,0,1,1,1,0,1,1,1,1,0,0},
        {0,1,1,1,1,0,0,1,1,1,0,0,1,1,1,1,0},
        {0,1,1,1,0,0,2,2,2,2,2,0,0,1,1,1,0},
        {0,1,0,0,0,0,0,2,2,2,0,0,0,0,0,1,0},
        {1,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1}};

    public BufferedImage getImageT(int[][] data) {
        BufferedImage image = new BufferedImage(TIMGSIZEX, TIMGSIZEY,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < TIMGSIZEX; x++) {
            for (int y = 0; y < TIMGSIZEY; y++) {
                int pixelColor = data[x][y];
                // Set Colors based on Binary Image value
                if (pixelColor == 0) {
                    pixelColor = Color.WHITE.getRGB();
                } else if (pixelColor == 1) {
                    pixelColor = Color.BLUE.getRGB();
                } else if (pixelColor == 2) {
                    pixelColor = Color.RED.getRGB();
                } else if(pixelColor == 3){
                    pixelColor = Color.DARK_GRAY.getRGB();
                } else if (pixelColor == 6) {
                    pixelColor = Color.GRAY.getRGB();
                }
                image.setRGB(x, y, pixelColor);
            } // End for y.
        } // End for x.
        return image;
    } // End getData method.

    private final static int PACIMGSIZEX = 19;
    private final static int PACIMGSIZEY = 19;
    public static int[][] pacman = {
        {0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0},
        {0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0},
        {0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0},
        {0,0,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,0},
        {0,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,0,0},
        {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0},
        {2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0},
        {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0},
        {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0},
        {0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
        {0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0},
        {0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0},
        {0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0},};
            
    public BufferedImage getImagePac(int[][] data) {
        BufferedImage image = new BufferedImage(PACIMGSIZEX, PACIMGSIZEY,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < PACIMGSIZEX; x++) {
            for (int y = 0; y < PACIMGSIZEY; y++) {
                int pixelColor = data[x][y];
                // Set Colors based on Binary Image value
                if (pixelColor == 0) {
                    pixelColor = Color.WHITE.getRGB();
                } else if (pixelColor == 1) {
                    pixelColor = Color.BLACK.getRGB();
                } else if (pixelColor == 2) {
                    pixelColor = Color.YELLOW.getRGB();
                } 
                image.setRGB(x, y, pixelColor);
            } // End for y.
        } // End for x.
        return image;
    } // End getData method.

    private final static int PAC2IMGSIZEX = 19;
    private final static int PAC2IMGSIZEY = 19;
    public static int[][] pacman2 = {
        {0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0},
        {0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0},
        {0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0},
        {0,0,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,0,0},
        {0,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,0},
        {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
        {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
        {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
        {0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0},
        {0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0},
        {0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0},
        {0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0},};
            
    public BufferedImage getImagePac2(int[][] data) {
        BufferedImage image = new BufferedImage(PAC2IMGSIZEX, PAC2IMGSIZEY,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < PAC2IMGSIZEX; x++) {
            for (int y = 0; y < PAC2IMGSIZEY; y++) {
                int pixelColor = data[x][y];
                // Set Colors based on Binary Image value
                if (pixelColor == 0) {
                    pixelColor = Color.WHITE.getRGB();
                } else if (pixelColor == 1) {
                    pixelColor = Color.BLACK.getRGB();
                } else if (pixelColor == 2) {
                    pixelColor = Color.YELLOW.getRGB();
                } 
                image.setRGB(x, y, pixelColor);
            } // End for y.
        } // End for x.
        return image;
    } // End getData method.
    
    private final static int DOTIMGSIZEX = 2;
    private final static int DOTIMGSIZEY = 2;
    public static int[][] dot = {
        {1,1},
        {1,1}};
            
    public BufferedImage getImageDot(int[][] data) {
        BufferedImage image = new BufferedImage(DOTIMGSIZEX, DOTIMGSIZEY,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < DOTIMGSIZEX; x++) {
            for (int y = 0; y < DOTIMGSIZEY; y++) {
                int pixelColor = data[x][y];
                // Set Colors based on Binary Image value
                if (pixelColor == 0) {
                    pixelColor = Color.WHITE.getRGB();
                } else if (pixelColor == 1) {
                    pixelColor = Color.GREEN.getRGB();
                } 
                image.setRGB(x, y, pixelColor);
            } // End for y.
        } // End for x.
        return image;
    } // End getData method.
}