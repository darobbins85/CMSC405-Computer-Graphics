/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;

/**
 *
 * @author darob
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.gl2.GLUT;  // for drawing GLUT objects (such as the teapot)

/**
 * A template for a basic JOGL application with support for animation, and for
 * keyboard and mouse event handling, and for a menu.  To enable the support, 
 * uncomment the appropriate lines in main(), in the constructor, and in the
 * init() method.  See all the lines that are marked with "TODO".
 * 
 * See the JOGL documentation at http://jogamp.org/jogl/www/
 * Note that this program is based on JOGL 2.3, which has some differences
 * from earlier versions; in particular, some of the package names have changed.
 */
public class Project2 extends JPanel implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {
        
    private JMenuBar menubar;
    private GLJPanel display;
    private Camera camera;
    private Timer animationTimer;
    private int frameNumber = 0;  // The current frame number for an animation.    
    private GLUT glut = new GLUT();  // TODO: For drawing GLUT objects, otherwise, not needed.
    private double rotateX=0, rotateY=90, rotateZ=45;
    private float translateL, translateR, translateU, translateD;
    private float scaleU=1, scaleD=1;
    
    
    public static void main(String[] args) {
        JFrame window = new JFrame("Project 2");
        Project2 panel = new Project2();
        window.setContentPane(panel);
        window.setJMenuBar(panel.createMenuBar());
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
    

    public Project2() {
        GLCapabilities caps = new GLCapabilities(null);
        display = new GLJPanel(caps);
        display.setPreferredSize( new Dimension(800,800) );
        display.addGLEventListener(this);
        display.addKeyListener(this);
        display.addMouseListener(this);
        display.requestFocusInWindow();
        setLayout(new BorderLayout());
        add(display,BorderLayout.CENTER);
        camera = new Camera();
        camera.lookAt(90,90,0, 0,0,0, 0,1,0);
        camera.setScale(15);
        camera.installTrackball(display);
        
        
        //TODO:  Uncomment the following line to start the animation
        //startAnimation();

    }
    
    // draw a 2d square or a cube - cube requires square
    private void square(GL2 gl2) {
        gl2.glBegin(GL2.GL_TRIANGLE_FAN);
        gl2.glVertex3d(-0.5, -0.5, 0.5);
        gl2.glVertex3d(0.5, -0.5, 0.5);
        gl2.glVertex3d(0.5, 0.5, 0.5);
        gl2.glVertex3d(-0.5, 0.5, 0.5);
        gl2.glEnd();
    }        
    private void cube(GL2 gl2) {                         
        gl2.glPushMatrix();
        square(gl2);
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 0, 1, 0);
        square(gl2);
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(-90, 1, 0, 0);
        square(gl2);
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(180, 0, 1, 0);
        square(gl2);
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(-90, 0, 1, 0);
        square(gl2);
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 1, 0, 0);
        square(gl2);
        gl2.glPopMatrix();
        
        gl2.glPopMatrix();
    }
    
    // draw a 2d ring or circle. Or draw a sphere which requires a circle
    
    private void circle(GL2 gl, double radius, int slices, int rings, boolean makeTexCoords) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be greater than zero.");
        ring(gl,0,radius,slices,rings,makeTexCoords);
    }    
    private void uvSphere(GL2 gl, double radius, int slices, int stacks, boolean makeTexCoords) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive.");
        if (slices < 3)
            throw new IllegalArgumentException("Number of slices must be at least 3.");
        if (stacks < 2)
            throw new IllegalArgumentException("Number of stacks must be at least 2.");
        for (int j = 0; j < stacks; j++) {
            double latitude1 = (Math.PI/stacks) * j - Math.PI/2;
            double latitude2 = (Math.PI/stacks) * (j+1) - Math.PI/2;
            double sinLat1 = Math.sin(latitude1);
            double cosLat1 = Math.cos(latitude1);
            double sinLat2 = Math.sin(latitude2);
            double cosLat2 = Math.cos(latitude2);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (int i = 0; i <= slices; i++) {
                double longitude = (2*Math.PI/slices) * i;
                double sinLong = Math.sin(longitude);
                double cosLong = Math.cos(longitude);
                double x1 = cosLong * cosLat1;
                double y1 = sinLong * cosLat1;
                double z1 = sinLat1;
                double x2 = cosLong * cosLat2;
                double y2 = sinLong * cosLat2;
                double z2 = sinLat2;
                gl.glNormal3d(x2,y2,z2);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0/slices * i, 1.0/stacks * (j+1));
                gl.glVertex3d(radius*x2,radius*y2,radius*z2);
                gl.glNormal3d(x1,y1,z1);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0/slices * i, 1.0/stacks * j);
                gl.glVertex3d(radius*x1,radius*y1,radius*z1);
            }
            gl.glEnd();
        }
    } // end uvSphere
    private void uvSphere(GL2 gl) {
        uvSphere(gl,0.5,32,16,true);
    }
    
    // draw a cone
    private void uvCone(GL2 gl, double radius, double height, int slices, int stacks, int rings, boolean makeTexCoords) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive.");
        if (height <= 0)
            throw new IllegalArgumentException("Height must be positive.");
        if (slices < 3)
            throw new IllegalArgumentException("Number of slices must be at least 3.");
        if (stacks < 2)
            throw new IllegalArgumentException("Number of stacks must be at least 2.");
        for (int j = 0; j < stacks; j++) {
            double z1 = (height/stacks) * j;
            double z2 = (height/stacks) * (j+1);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (int i = 0; i <= slices; i++) {
                double longitude = (2*Math.PI/slices) * i;
                double sinLong = Math.sin(longitude);
                double cosLong = Math.cos(longitude);
                double x = cosLong;
                double y = sinLong;
                double nz = radius/height;
                double normLength = Math.sqrt(x*x+y*y+nz*nz);
                gl.glNormal3d(x/normLength,y/normLength,nz/normLength);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0/slices * i, 1.0/stacks * (j+1));
                gl.glVertex3d((height-z2)/height*radius*x,(height-z2)/height*radius*y,z2);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0/slices * i, 1.0/stacks * j);
                gl.glVertex3d((height-z1)/height*radius*x,(height-z1)/height*radius*y,z1);
            }
            gl.glEnd();
        }
        if (rings > 0) {
            gl.glNormal3d(0,0,-1);
            for (int j = 0; j < rings; j++) {
                double d1 = (1.0/rings) * j;
                double d2 = (1.0/rings) * (j+1);
                gl.glBegin(GL2.GL_QUAD_STRIP);
                for (int i = 0; i <= slices; i++) {
                    double angle = (2*Math.PI/slices) * i;
                    double sin = Math.sin(angle);
                    double cos = Math.cos(angle);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5*(1+cos*d2),0.5*(1+sin*d2));
                    gl.glVertex3d(radius*cos*d2,radius*sin*d2,0);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5*(1+cos*d1),0.5*(1+sin*d1));
                    gl.glVertex3d(radius*cos*d1,radius*sin*d1,0);
                }
                gl.glEnd();
            }
        }
    } // end uvCone
    private void uvCone(GL2 gl) {
        uvCone(gl,0.5,1,32,10,5,true);
    }
    
    // draw a cylinder
    private void uvCylinder(GL2 gl, double radius, double height, int slices, int stacks, int rings, boolean makeTexCoords) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive.");
        if (height <= 0)
            throw new IllegalArgumentException("Height must be positive.");
        if (slices < 3)
            throw new IllegalArgumentException("Number of slices must be at least 3.");
        if (stacks < 2)
            throw new IllegalArgumentException("Number of stacks must be at least 2.");
        for (int j = 0; j < stacks; j++) {
            double z1 = (height/stacks) * j;
            double z2 = (height/stacks) * (j+1);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (int i = 0; i <= slices; i++) {
                double longitude = (2*Math.PI/slices) * i;
                double sinLong = Math.sin(longitude);
                double cosLong = Math.cos(longitude);
                double x = cosLong;
                double y = sinLong;
                gl.glNormal3d(x,y,0);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0/slices * i, 1.0/stacks * (j+1));
                gl.glVertex3d(radius*x,radius*y,z2);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0/slices * i, 1.0/stacks * j);
                gl.glVertex3d(radius*x,radius*y,z1);
            }
            gl.glEnd();
        }
        if (rings > 0) { // draw top and bottom
            gl.glNormal3d(0,0,1);
            for (int j = 0; j < rings; j++) {
                double d1 = (1.0/rings) * j;
                double d2 = (1.0/rings) * (j+1);
                gl.glBegin(GL2.GL_QUAD_STRIP);
                for (int i = 0; i <= slices; i++) {
                    double angle = (2*Math.PI/slices) * i;
                    double sin = Math.sin(angle);
                    double cos = Math.cos(angle);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5*(1+cos*d1),0.5*(1+sin*d1));
                    gl.glVertex3d(radius*cos*d1,radius*sin*d1,height);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5*(1+cos*d2),0.5*(1+sin*d2));
                    gl.glVertex3d(radius*cos*d2,radius*sin*d2,height);
                }
                gl.glEnd();
            }
            gl.glNormal3d(0,0,-1);
            for (int j = 0; j < rings; j++) {
                double d1 = (1.0/rings) * j;
                double d2 = (1.0/rings) * (j+1);
                gl.glBegin(GL2.GL_QUAD_STRIP);
                for (int i = 0; i <= slices; i++) {
                    double angle = (2*Math.PI/slices) * i;
                    double sin = Math.sin(angle);
                    double cos = Math.cos(angle);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5*(1+cos*d2),0.5*(1+sin*d2));
                    gl.glVertex3d(radius*cos*d2,radius*sin*d2,0);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5*(1+cos*d1),0.5*(1+sin*d1));
                    gl.glVertex3d(radius*cos*d1,radius*sin*d1,0);
                }
                gl.glEnd();
            }
        }
    } // end uvCylinder
    private void uvCylinder(GL2 gl) {
        uvCylinder(gl,0.5,1,32,10,5,true);
    }
    
    // draw a 2d ring or a torus (donut)
    private void ring(GL2 gl, double innerRadius, double outerRadius, int slices, int rings, boolean makeTexCoords) {
        if (innerRadius < 0)
            throw new IllegalArgumentException("innerRadius must be greater than or equal to zero.");
        if (outerRadius <= innerRadius)
            throw new IllegalArgumentException("outerRadius must be greater than innerRadius.");
        if (slices < 3)
            throw new IllegalArgumentException("Number of slices must be 3 or more.");
        if (rings < 1)
            throw new IllegalArgumentException("Number of rings must be 1 or more.");
          gl.glNormal3d(0,0,1);
          double dr = (outerRadius - innerRadius) / rings;
          for (int j = 0; j < rings; j++) {
             double d1 = innerRadius + dr * j;
             double d2 = innerRadius + dr * (j+1);
             gl.glBegin(GL2.GL_QUAD_STRIP);
             for (int i = 0; i <= slices; i++) {
                double angle = (2*Math.PI/slices) * i;
                double sin = Math.sin(angle);
                double cos = Math.cos(angle);
                if (makeTexCoords)
                   gl.glTexCoord2d(0.5*(1+cos*d1/outerRadius),0.5*(1+sin*d1/outerRadius));
                gl.glVertex3d(cos*d1,sin*d1,0);
                if (makeTexCoords)
                   gl.glTexCoord2d(0.5*(1+cos*d2/outerRadius),0.5*(1+sin*d2/outerRadius));
                gl.glVertex3d(cos*d2,sin*d2,0);
             }
             gl.glEnd();
          }
    }
    private void uvTorus(GL2 gl, double outerRadius, double innerRadius, int slices, int rings, boolean makeTexCoords) {
        if (outerRadius == innerRadius)
            throw new IllegalArgumentException("Outer and inner radii can't be the same.");
        if (outerRadius < innerRadius) {
            double temp = outerRadius;
            outerRadius = innerRadius;
            innerRadius = temp;
        }
        if (innerRadius < 0)
            throw new IllegalArgumentException("Radius can't be negative.");
        if (slices < 3)
            throw new IllegalArgumentException("Number of slices must be 3 or more.");
        if (rings < 3)
            throw new IllegalArgumentException("Number of rings must be 3 or more.");
        double centerRadius = (innerRadius + outerRadius) / 2;
        double tubeRadius = outerRadius - centerRadius;
        for (int i = 0; i < slices; i++) {
            double s1 = 1.0/slices * i;
            double s2 = 1.0/slices * (i+1);
            double centerCos1 = Math.cos(2*Math.PI*s1);
            double centerSin1 = Math.sin(2*Math.PI*s1);
            double centerCos2 = Math.cos(2*Math.PI*s2);
            double centerSin2 = Math.sin(2*Math.PI*s2);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (int j = 0; j <= rings; j++) {
                double t = 1.0/rings * j;
                double cos = Math.cos(2*Math.PI*t - Math.PI);
                double sin = Math.sin(2*Math.PI*t - Math.PI);
                double x1 = centerCos1*(centerRadius + tubeRadius*cos);
                double y1 = centerSin1*(centerRadius + tubeRadius*cos);
                double z1 = sin*tubeRadius;
                gl.glNormal3d(centerCos1*cos,centerSin1*cos,sin);
                if (makeTexCoords)
                    gl.glTexCoord2d(s1,t);
                gl.glVertex3d(x1,y1,z1);
                double x2 = centerCos2*(centerRadius + tubeRadius*cos);
                double y2 = centerSin2*(centerRadius + tubeRadius*cos);
                double z2 = sin*tubeRadius;
                gl.glNormal3d(centerCos2*cos,centerSin2*cos,sin);
                if (makeTexCoords)
                    gl.glTexCoord2d(s2,t);
                gl.glVertex3d(x2,y2,z2);
            }
            gl.glEnd();
        }
    } // end uvTorus
    private void uvTorus(GL2 gl) {
        uvTorus(gl,0.5,1.0/6,48,72,true);
    }
    
    // ---------------  Methods of the GLEventListener interface -----------
    
    /**
     * This method is called when the OpenGL display needs to be redrawn.
     */
    public void display(GLAutoDrawable drawable) {
        // called when the panel needs to be drawn
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT ); // TODO? Omit depth buffer for 2D.
        
        // draw a red cube and place it on the right
        GL2 glCube = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        camera.apply(gl);
        glCube.glColor3d(1, 0, 0);
        glCube.glRotated(rotateZ,0,0,1);
        glCube.glRotated(rotateY,0,1,0);
        glCube.glRotated(rotateX,1,0,0);
        glCube.glTranslatef(0, translateU, 0);
        glCube.glTranslatef(0, -translateD, 0);
        glCube.glTranslatef(-translateL, 0, 0);
        glCube.glTranslatef((translateR + 10), 0, 0);
        glCube.glScalef(scaleU, scaleU, scaleU);
        glCube.glScalef(scaleD, scaleD, scaleD);
        cube(glCube);
        
        
        // draw a blue sphere and place it in the upper right
        GL2 glSphere = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        camera.apply(gl);
        glSphere.glColor3d(0, 1, 0);
        glSphere.glRotated(rotateZ,0,0,1);
        glSphere.glRotated(rotateY,0,1,0);
        glSphere.glRotated(rotateX,1,0,0);
        glSphere.glTranslatef(0, translateU + 7, 0);
        glSphere.glTranslatef(0, -translateD, 0);
        glSphere.glTranslatef(-translateL, 0, 0);
        glSphere.glTranslatef(translateR + 5, 0, 0);
        glSphere.glScalef(scaleU, scaleU, scaleU);
        glSphere.glScalef(scaleD, scaleD, scaleD);
        uvSphere(glSphere);
        // draw a ring around the sphere in the upper right
        GL2 glRing = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        camera.apply(gl);
        glRing.glColor3d(1, 1, 0);
        glRing.glRotated(rotateZ,0,0,1);
        glRing.glRotated(rotateY,0,1,0);
        glRing.glRotated(rotateX,1,0,0);
        glRing.glTranslatef(0, translateU + 7, 0);
        glRing.glTranslatef(0, -translateD, 0);
        glRing.glTranslatef(-translateL, 0, 0);
        glRing.glTranslatef(translateR + 5, 0, 0);
        glRing.glScalef(scaleU+.5f, scaleU+.5f, scaleU-.5f);
        glRing.glScalef(scaleD+.5f, scaleD+.5f, scaleD-.5f);
        uvTorus(glRing);
        
        
        // draw a cream sphere and place it in the upper left
        GL2 glCream = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        camera.apply(gl);
        glCream.glColor3d(1, 1, .8);
        glCream.glRotated(rotateZ,0,0,1);
        glCream.glRotated(rotateY,0,1,0);
        glCream.glRotated(rotateX,1,0,0);
        glCream.glTranslatef(0, translateU + 7, 0);
        glCream.glTranslatef(0, -translateD, 0);
        glCream.glTranslatef(-translateL-5, 0, 0);
        glCream.glTranslatef(translateR, 0, 0);
        glCream.glScalef(scaleU, scaleU, scaleU);
        glCream.glScalef(scaleD, scaleD, scaleD);
        uvSphere(glCream);
        // draw a brown cone in the top left
        GL2 glCone = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        camera.apply(gl);
        glCone.glColor3d(.5459f,.279f,.1f);
        glCone.glRotated(rotateZ,0,0,1);
        glCone.glRotated(rotateY,0,1,0);
        glCone.glRotated(rotateX,1,0,0);
        glCone.glTranslatef(0, translateU + 7, 0);
        glCone.glTranslatef(0, -translateD, 0);
        glCone.glTranslatef(-translateL-5, 0, 0);
        glCone.glTranslatef(translateR, 0, 0);
        glCone.glScalef(scaleU, scaleU, scaleU);
        glCone.glScalef(scaleD, scaleD, scaleD);
        uvCone(glCone);
        
        // draw a blue cylinder to the left
        GL2 glCylinder = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        camera.apply(gl);
        glCylinder.glColor3d(0,0,1);
        glCylinder.glRotated(rotateZ,0,0,1);
        glCylinder.glRotated(rotateY,0,1,0);
        glCylinder.glRotated(rotateX,1,0,0);
        glCylinder.glTranslatef(0, translateU, 0);
        glCylinder.glTranslatef(0, -translateD, 0);
        glCylinder.glTranslatef(-translateL-10, 0, 0);
        glCylinder.glTranslatef(translateR, 0, 0);
        glCylinder.glScalef(scaleU, scaleU, scaleU);
        glCylinder.glScalef(scaleD, scaleD, scaleD);
        uvCylinder(glCylinder);
        
        // draw a pink torus to the bottom left
        GL2 glTorus = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        camera.apply(gl);
        glTorus.glColor3d(.5f,0,.5f);
        glTorus.glRotated(rotateZ,0,0,1);
        glTorus.glRotated(rotateY,0,1,0);
        glTorus.glRotated(rotateX,1,0,0);
        glTorus.glTranslatef(0, translateU, 0);
        glTorus.glTranslatef(0, -translateD-7, 0);
        glTorus.glTranslatef(-translateL-5, 0, 0);
        glTorus.glTranslatef(translateR, 0, 0);
        glTorus.glScalef(scaleU, scaleU, scaleU);
        glTorus.glScalef(scaleD, scaleD, scaleD);
        uvTorus(glTorus);
        
        // draw a cream tea pot and place it at the vertex
        GL2 glTeaPot = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        camera.apply(gl);
        glTeaPot.glColor3d(.7, .9 , .9);
        glTeaPot.glRotated(rotateZ,0,0,1);
        glTeaPot.glRotated(rotateY,0,1,0);
        glTeaPot.glRotated(rotateX,1,0,0);
        glTeaPot.glTranslatef(0, translateU, 0);
        glTeaPot.glTranslatef(0, -translateD, 0);
        glTeaPot.glTranslatef(-translateL, 0, 0);
        glTeaPot.glTranslatef(translateR, 0, 0);
        glTeaPot.glScalef(scaleU, scaleU, scaleU);
        glTeaPot.glScalef(scaleD, scaleD, scaleD);
        glTeaPot.glPushMatrix();
        glut.glutSolidTeapot(1);
        glTeaPot.glPopMatrix();
        
        
        GL2 glDeca = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        camera.apply(gl);
        glDeca.glColor3d(1, .5 , 0);
        glDeca.glRotated(rotateZ,0,0,1);
        glDeca.glRotated(rotateY,0,1,0);
        glDeca.glRotated(rotateX,1,0,0);
        glDeca.glTranslatef(0, translateU, 0);
        glDeca.glTranslatef(0, -translateD-7, 0);
        glDeca.glTranslatef(-translateL, 0, 0);
        glDeca.glTranslatef(translateR+5, 0, 0);
        glDeca.glScalef(scaleU, scaleU, scaleU);
        glDeca.glScalef(scaleD, scaleD, scaleD);
        glDeca.glPushMatrix();
        glDeca.glPopMatrix();
        glut.glutWireIcosahedron();
        glDeca.glPopMatrix();
    }
    
    /**
     * This is called when the GLJPanel is first created.  It can be used to initialize
     * the OpenGL drawing context.
     */
    public void init(GLAutoDrawable drawable) {
        // called when the panel is created
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glOrtho(-1, 1 ,-1, 1, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glClearColor(.7F,.7F,.7F,0);
        gl.glEnable(GL2.GL_DEPTH_TEST);  // TODO: Required for 3D drawing, not usually for 2D.
        
        // TODO: Uncomment the following 4 lines to do some typical initialization for 
        // lighting and materials.

         //gl.glEnable(GL2.GL_LIGHTING);        // Enable lighting.
         //gl.glEnable(GL2.GL_LIGHT0);          // Turn on a light.  By default, shines from direction of viewer.
         //gl.glEnable(GL2.GL_NORMALIZE);       // OpenGL will make all normal vectors into unit normals
         //gl.glEnable(GL2.GL_COLOR_MATERIAL);  // Material ambient and diffuse colors can be set by glColor*
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

    public void dispose(GLAutoDrawable drawable) {}

    public void transformer(GL2 gl, double r, double g, double b){
        gl.glLoadIdentity();
        camera.apply(gl);
        gl.glColor3d(r, g, b);
        gl.glRotated(rotateZ,0,0,1);
        gl.glRotated(rotateY,0,1,0);
        gl.glRotated(rotateX,1,0,0);
        gl.glTranslatef(0, translateU, 0);
        gl.glTranslatef(0, -translateD, 0);
        gl.glTranslatef(-translateL, 0, 0);
        gl.glTranslatef(translateR, 0, 0);
        gl.glScalef(scaleU, scaleU, scaleU);
        gl.glScalef(scaleD, scaleD, scaleD);
    }
    // ------------ Support for a menu -----------------------
    
    public JMenuBar createMenuBar() {
        // create menubar and menuhandler
        menubar = new JMenuBar();
        MenuHandler menuHandler = new MenuHandler(); // An object to respond to menu commands.
        
        // create menus
        JMenu menu = new JMenu("Menu"); // Create a menu and add it to the menu bar   
        JMenu cube = new JMenu("Cube");
        JMenu sphere = new JMenu("Sphere");
        JMenu cone = new JMenu("Cone");
        
        // create menu items
        JMenuItem clear = new JMenuItem("Clear");
        JMenuItem quit = new JMenuItem("Quit");
        
        JMenuItem redCube = new JMenuItem("Red");
        JMenuItem greenCube = new JMenuItem("Green");
        JMenuItem blueCube = new JMenuItem("Blue");
        JMenuItem redSphere = new JMenuItem("Red");
        JMenuItem greenSphere = new JMenuItem("Green");
        JMenuItem blueSphere = new JMenuItem("Blue");
        JMenuItem redCone = new JMenuItem("Red");
        JMenuItem greenCone = new JMenuItem("Green");
        JMenuItem blueCone = new JMenuItem("Blue");
        
        
        // add action listeners
        redCube.addActionListener(menuHandler);
        greenCube.addActionListener(menuHandler);
        blueCube.addActionListener(menuHandler);
        redSphere.addActionListener(menuHandler);           
        greenSphere.addActionListener(menuHandler);
        blueSphere.addActionListener(menuHandler);
        redCone.addActionListener(menuHandler);           
        greenCone.addActionListener(menuHandler);
        blueCone.addActionListener(menuHandler);
        clear.addActionListener(menuHandler);
        quit.addActionListener(menuHandler);
        
        // add menus
        menubar.add(menu);
        menubar.add(cube);
        menubar.add(sphere);
        menubar.add(cone);
        
        
        // add menu items
        menu.add(clear);
        menu.add(quit);
        cube.add(redCube);
        cube.add(greenCube);
        cube.add(blueCube);
        sphere.add(redSphere);
        sphere.add(greenSphere);
        sphere.add(blueSphere);
        cone.add(redCone);
        cone.add(greenCone);
        cone.add(blueCone);
       
        return menubar;
    }
    
    /**
     * A class to define the ActionListener object that will respond to menu commands.
     */
    private class MenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String command = evt.getActionCommand();
            // The text of the command.
            
            if(command.equals("Clear")){
                
            }            
            if (command.equals("Quit")) {
                System.exit(0);
            }
            if(command.equals("Cube")){
 
            }
            if(command.equals("Sphere")){
                
            }
            if(command.equals("Cone")){
                
            }
            if(command.equals("Red")){
                
            }
            if(command.equals("Green")){
                
            }
            if(command.equals("Blue")){
                System.out.println("You chose blue");
            }
        }
    }

    
    // ------------ Support for keyboard handling  ------------

    /**
     * Called when the user presses any key on the keyboard, including
     * special keys like the arrow keys, the function keys, and the shift key.
     * Note that the value of key will be one of the constants from
     * the KeyEvent class that identify keys such as KeyEvent.VK_LEFT,
     * KeyEvent.VK_RIGHT, KeyEvent.VK_UP, and KeyEvent.VK_DOWN for the arrow
     * keys, KeyEvent.VK_SHIFT for the shift key, and KeyEvent.VK_F1 for a
     * function key.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();  // Tells which key was pressed.
        // TODO:  Add code to respond to key presses.
        if ( key == KeyEvent.VK_S ){
            rotateY -= 15;
        } else if ( key == KeyEvent.VK_W ){
            rotateY += 15;
        } else if ( key == KeyEvent.VK_Q){
            rotateX += 15;
        } else if ( key == KeyEvent.VK_A ){
            rotateX -= 15;
        } else if ( key == KeyEvent.VK_E ){
            rotateZ += 15;
        } else if ( key == KeyEvent.VK_D ){
            rotateZ -= 15;
        } else if ( key == KeyEvent.VK_HOME ){
            rotateX = 0;
            rotateY = 90;
            rotateZ = 45;
        } else if ( key == KeyEvent.VK_UP) {
            translateU += .1f; 
        } else if ( key == KeyEvent.VK_DOWN){
            translateD += .1f;
        } else if ( key == KeyEvent.VK_LEFT) {
            translateL += .1f; 
        } else if ( key == KeyEvent.VK_RIGHT){
            translateR += .1f;
        } else if ( key == KeyEvent.VK_PAGE_UP){
            scaleU += .1;
        } else if ( key == KeyEvent.VK_PAGE_DOWN){
            scaleD -= .1;
        }
        
        display.repaint();  // Causes the display() function to be called.
    }
    
    public void keyTyped(KeyEvent e) { 
        char ch = e.getKeyChar();  // Which character was typed.
        // TODO:  Add code to respond to the character being typed.
        display.repaint();  // Causes the display() function to be called.
    }
    public void keyReleased(KeyEvent e) { 
    }

    // --------------------------- animation support ---------------------------
    
    /* You can call startAnimation() to run an animation.  A frame will be drawn every
     * 30 milliseconds (can be changed in the call to glutTimerFunc.  The global frameNumber
     * variable will be incremented for each frame.  Call pauseAnimation() to stop animating.
     */
    
    private boolean animating;  // True if animation is running.  Do not set directly.
                                // This is set by startAnimation() and pauseAnimation().
    
    private void updateFrame() {
        frameNumber++;
        // TODO:  add any other updating required for the next frame.
    }   
    public void startAnimation() {
        if ( ! animating ) {
            if (animationTimer == null) {
                animationTimer = new Timer(30, this);
            }
            animationTimer.start();
            animating = true;
        }
    }   
    public void pauseAnimation() {
        if (animating) {
            animationTimer.stop();
            animating = false;
        }
    }
    public void actionPerformed(ActionEvent evt) {
        updateFrame();
        display.repaint();
    }

    
    
    // ---------------------- support for mouse events ----------------------
    
    private boolean dragging;  // is a drag operation in progress?
    private int startX, startY;  // starting location of mouse during drag
    private int prevX, prevY;    // previous location of mouse during drag
    
    public void mousePressed(MouseEvent evt) {
        if (dragging) {
            System.out.println("dragging mouse");
            return;  // don't start a new drag while one is already in progress
        }
        int x = evt.getX();  // mouse location in pixel coordinates.
        int y = evt.getY();
        // TODO: respond to mouse click at (x,y)
        dragging = true;  // might not always be correct!
        prevX = startX = x;
        prevY = startY = y;
        display.repaint();    //  only needed if display should change
    }
    public void mouseReleased(MouseEvent evt) {
        if (! dragging) {
            return;
        }
        dragging = false;
        // TODO:  finish drag (generally nothing to do here)
    }
    public void mouseDragged(MouseEvent evt) {
        if (! dragging) {
            return;
        }
        int x = evt.getX();  // mouse location in pixel coordinates.
        int y = evt.getY();
        // TODO:  respond to mouse drag to new point (x,y)
        prevX = x;
        prevY = y;
        display.repaint();
    }
    public void mouseMoved(MouseEvent evt) { }    // Other methods required for MouseListener, MouseMotionListener.
    public void mouseClicked(MouseEvent evt) { }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    
    
    


}

