import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.lang.Math;

import javax.imageio.ImageIO;

public class SmileyPanel extends Canvas implements MouseMotionListener, MouseListener, Runnable{
 
 private Color snobbyGreen = new Color(16, 137, 30);
 private Color snobbyRed = new Color(189, 35, 35);
 
 private Point mouse = new Point();
 private Point head = new Point(275, 175);
 private int headSize = 250;
 private int rage = 420;
 private int frame = 0;
 
 //centers of eyes
 private Point leftEye = new Point(head.x + 17*headSize/60, head.y + 29*headSize/80);
 private Point rightEye = new Point(head.x + 43*headSize/60, head.y + 29*headSize/80);
 private Point leftPupil = new Point(leftEye.x, leftEye.y);
 private Point rightPupil = new Point(rightEye.x, rightEye.y);
 private int pupilRadius = headSize/16;
 
 private final int WIDTH = 800;
 private final int HEIGHT = 600;
 
 public SmileyPanel()
    {
       setSize(WIDTH, HEIGHT);
       setBackground(Color.WHITE);
       setVisible(true);
       addMouseMotionListener(this);
       addMouseListener(this);
       new Thread(this).start();
    }
 
 public void paint(Graphics window)
    {
       smile(window);
    }
 
 public void smile(Graphics window) {
  window.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
  window.setColor(Color.BLACK);
  window.drawString("Pokes:" + rage, 8, HEIGHT - 30);
  if (rage < 5 || rage == 420) {
   snobby(window, head.x, head.y, headSize, snobbyGreen);
  }
  else {
   snobby(window, head.x, head.y, headSize, snobbyRed);
  }
  if (frame > 0) {
   chat(window, head.x, head.y);
  }
 }
 
 public void snobby(Graphics window, int x, int y, int size, Color c) {
  //darker shade
  Color darker = new Color(c.getRed() - 10, c.getGreen() - 10, c.getBlue() - 10);
  
  //draws a snobby dragon based on head position and size and color
  Graphics2D w = (Graphics2D)window;
  w.setStroke(new BasicStroke(1));
  
  //neck
  w.setColor(darker);
  w.fillPolygon(new Polygon(new int[]{x + size/10, x, x + size, x + size*9/10}, new int[]{y + size/2, HEIGHT, HEIGHT, y + size/2}, 4));
  if (c.equals(snobbyGreen)) {
   w.setColor(new Color(195, 202, 127)); //basic shape
   w.fillPolygon(new Polygon(new int[]{x + size/8, x + size/10, x + size*9/10, x + size*7/8}, new int[]{y + size/2, HEIGHT, HEIGHT, y + size/2}, 4));
   w.setColor(new Color(215, 222, 147)); //grooves
   w.fillArc(x + size*11/128, y + size*4/5 - size/20, size*33/40, size*3/5, 200, 140);
   w.fillArc(x + size*21/256, y + size*11/10 - size/20, size*67/80, size*13/20, 200, 140);
   w.setColor(new Color(195, 202, 127));
   w.fillArc(x + size*11/128, y + size*4/5, size*33/40, size/2, 200, 140);
   w.fillArc(x + size*21/256, y + size*11/10, size*67/80, size*11/20, 200, 140);
  }
  else {
   w.setColor(new Color(150, 30, 30)); //basic shape
   w.fillPolygon(new Polygon(new int[]{x + size/8, x + size/10, x + size*9/10, x + size*7/8}, new int[]{y + size/2, HEIGHT, HEIGHT, y + size/2}, 4));
   w.setColor(new Color(160, 40, 40)); //grooves
   w.fillArc(x + size*11/128, y + size*4/5 - size/20, size*33/40, size*3/5, 200, 140);
   w.fillArc(x + size*21/256, y + size*11/10 - size/20, size*67/80, size*13/20, 200, 140);
   w.setColor(new Color(150, 30, 30));
   w.fillArc(x + size*11/128, y + size*4/5, size*33/40, size/2, 200, 140);
   w.fillArc(x + size*21/256, y + size*11/10, size*67/80, size*11/20, 200, 140);
  }
  //grooves
  w.setColor(Color.BLACK);
  w.drawArc(x + size*11/128, y + size*4/5, size*33/40, size/2, 200, 140);
  w.drawArc(x + size*21/256, y + size*11/10, size*67/80, size*11/20, 200, 140);
  
  //top hat
  //top
  w.setColor(Color.DARK_GRAY);
  w.fillPolygon(new Polygon(new int[]{x + size/10, x, x + size, x + size*9/10}, new int[]{y, y - size/2, y - size/2, y}, 4));
  w.fillArc(x, y - size*19/32 + 1, size, size*3/16, 0, 180);
  //top ribbon
  w.setColor(Color.GRAY);
  w.fillPolygon(new Polygon(new int[]{x + size/10, x + size/13, x + size*13/14, x + size*9/10}, new int[]{y, y - size/8, y - size/8, y}, 4));
  w.fillArc(x + size/13, y - size/5, size*155/182, size*3/16, 0, 180);
  //base
  w.setColor(Color.BLACK);
  w.fillOval(x - size/6, y - size/20, size*4/3, size/4);
  
  //head
  w.setColor(darker);
  w.fillOval(x, y, size, size);
  w.setColor(c);
  w.fillOval(x, y + size/12, size, size);
  
  //eyes
  //whites
  w.setColor(Color.WHITE);
  w.fillOval(x + size/5, y + size/5, size/6, size/5);
  w.fillOval(x + size*19/30, y + size/5, size/6, size/5);
  //pupils will follow cursor
  w.setColor(Color.BLACK);
  w.fillOval(leftPupil.x - pupilRadius, leftPupil.y - pupilRadius*2, pupilRadius*2, pupilRadius*2);
  w.fillOval(rightPupil.x - pupilRadius, rightPupil.y - pupilRadius*2, pupilRadius*2, pupilRadius*2);
  
  //eyebrow
  if (rage >= 5) { //angry eyebrows
   w.setColor(Color.BLACK);
   w.setStroke(new BasicStroke(12));
   w.drawLine(x + size/5, y + size/6, x + size/5 + size/6, y + size/6 + size/10);
   w.drawLine(x + size/5 + size*13/30, y + size/6 + size/10, x + size/5 + size/6 + size*13/30, y + size/6);
  }
  
  //monocle
  w.setColor(new Color(230, 180, 13));
  w.setStroke(new BasicStroke(2));
  w.drawOval(x + size/6 + size/60 - size/20, y + size/5 - size/60 + size/10, size/20, size/20);
  w.setColor(new Color(255, 240, 13));
  w.setStroke(new BasicStroke(4));
  w.drawOval(x + size/6 + size/60, y + size/5 - size/60, size/5, size/5);
  
  //snout
  w.setStroke(new BasicStroke(1));
  w.setColor(new Color(c.getRed() + 25, c.getGreen() + 25, c.getBlue() + 25));
  w.fillOval(x - size/10, y + size/3, size*12/10, size*3/4);
  
  //nostrils
  w.setColor(Color.BLACK);
  w.fillArc(x + size/5, y + size/3 + size/6, size/8, size/20, 200, -200);
  w.fillArc(x + size*27/40, y + size/3 + size/6, size/8, size/20, 180, -200);
  
  if (rage < 5 || rage == 420) {
   //teeth
   w.setStroke(new BasicStroke(1));
   w.setColor(Color.WHITE);
   w.fillPolygon(new Polygon(new int[]{x + size/6 + size/10, x + size/6 + size/10 + size/20, x + size/6 + size/5}, new int[]{y + size/2 + size/11, y + size/2 + size/10 + size/20, y + size/2 + size/10}, 3));
   w.fillPolygon(new Polygon(new int[]{x + size*2/3 + size/6 - size/10, x + size*2/3 + size/6 - size/10 - size/20, x + size*2/3 + size/6 - size/5}, new int[]{y + size/2 + size/11, y + size/2 + size/10 + size/20, y + size/2 + size/10}, 3));
   
   //mouth
   w.setColor(Color.BLACK);
   w.setStroke(new BasicStroke(2));
   w.drawArc(x + size/6, y + size/2, size*2/3, size/10, 180, 180);
  }
  else {
   //teeth
   w.setStroke(new BasicStroke(1));
   w.setColor(Color.WHITE);
   w.fillPolygon(new Polygon(new int[]{x + size/6 + size/10, x + size/6 + size/10 + size/20, x + size/6 + size/5}, new int[]{y + size/2 + size*23/200, y + size/2 + size/10 + size/20 + size/40, y + size/2 + size/10}, 3));
   w.fillPolygon(new Polygon(new int[]{x + size*2/3 + size/6 - size/10, x + size*2/3 + size/6 - size/10 - size/20, x + size*2/3 + size/6 - size/5}, new int[]{y + size/2 + size*23/200, y + size/2 + size/10 + size/20 + size/40, y + size/2 + size/10}, 3));
   
   //mouth
   w.setColor(Color.BLACK);
   w.setStroke(new BasicStroke(2));
   w.drawArc(x + size/6, y + size*3/5, size*2/3, size/10, 180, -180);
  }
  
  //420
  if (rage == 420) {
   BufferedImage glasses = null;
   BufferedImage joint = null;
     try {
      glasses = ImageIO.read(new File("DealWithIt.png"));
      joint = ImageIO.read(new File("WeedJoint.png"));
     }
     catch (Exception e) {
         e.printStackTrace();
     }
     window.drawImage(glasses, x, y + size/20, size, size/2, null);
     window.drawImage(joint, x, y + size/2 + size/8 - size/30, size/2, size/4, null);
  }
 }
 
 public void chat(Graphics window, int x, int y) {
  //angrily says something, text appears based on top left corner of snobby dragon's head
  frame--;
  Graphics2D w = (Graphics2D)window;
  w.setStroke(new BasicStroke(1));
  w.setColor(Color.BLACK);
  w.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
  if (rage == 1) {
   w.drawString("Stop that!", WIDTH - x*3/4, y);
  }
  else if (rage == 2) {
   w.drawString("Stop it!", x/3, y);
  }
  else if (rage == 3) {
   w.drawString("Don't do that!", x/4, y*2);
  }
  else if (rage == 4) {
   w.drawString("Don't!", WIDTH - x*3/4, y*2);
  }
  else if (rage == 420) {
   w.setColor(snobbyGreen);
   w.drawString("Blaze it.", x/3, y);
  }
  else if (rage == 666) { //exits
   w.drawString("Goodbye.", x/3, y);
   try {
    Thread.sleep(1000);
   } catch (InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
   System.exit(0);
  }
  else {
   w.setColor(Color.BLACK);
   w.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
   int random = (int)(Math.random()*4);
   if (random == 0) {
    w.drawString("STOP!", x/3 + 2, y*3/2);
    w.drawString("STOP!", x/3 - 2, y*3/2);
    w.drawString("STOP!", x/3, y*3/2 + 2);
    w.drawString("STOP!", x/3, y*3/2 - 2);
    w.setColor(Color.RED);
    w.drawString("STOP!", x/3, y*3/2);
   }
   else if (random == 1) {
    w.drawString("STOP!", WIDTH - x*3/4 + 2, y);
    w.drawString("STOP!", WIDTH - x*3/4 - 2, y);
    w.drawString("STOP!", WIDTH - x*3/4, y + 2);
    w.drawString("STOP!", WIDTH - x*3/4, y - 2);
    w.setColor(Color.RED);
    w.drawString("STOP!", WIDTH - x*3/4, y);
   }
   else if (random == 2) {
    w.drawString("STOP!", x/4 + 2, y*2);
    w.drawString("STOP!", x/4 - 2, y*2);
    w.drawString("STOP!", x/4, y*2 + 2);
    w.drawString("STOP!", x/4, y*2 - 2);
    w.setColor(Color.RED);
    w.drawString("STOP!", x/4, y*2);
   }
   else if (random == 3) {
    w.drawString("STOP!", WIDTH - x*4/5 + 2, y*2);
    w.drawString("STOP!", WIDTH - x*4/5 - 2, y*2);
    w.drawString("STOP!", WIDTH - x*4/5, y*2 + 2);
    w.drawString("STOP!", WIDTH - x*4/5, y*2 - 2);
    w.setColor(Color.RED);
    w.drawString("STOP!", WIDTH - x*4/5, y*2);
   }
  }
 }

 @Override
 public void mouseDragged(MouseEvent e) {
  // TODO Auto-generated method stub
  //same as moving
  mouseMoved(e);
 }

 @Override
 public void mouseMoved(MouseEvent e) {
  // TODO Auto-generated method stub
  // sets mouse position
  mouse = e.getPoint();
  
  // calculates pupil locations
  //smallest distance from center (direct distance from center vs farthest possible distance from center: ellipse radius - pupil radius)
  double leftPupilDistanceFromCenter = Math.min(calculateDistance(mouse, leftEye), calculateRadius(headSize/12, headSize/10, leftEye) - pupilRadius);
  if (leftPupilDistanceFromCenter == calculateDistance(mouse, leftEye) || mouse.equals(leftEye)) {
   leftPupil.setLocation(mouse.getX(), mouse.getY());
  }
  else {
   if (mouse.x - leftEye.x >= 0) { //accounts for range of arc tan function
    leftPupil.setLocation(leftEye.getX() + Math.cos(calculateAngle(leftEye))*leftPupilDistanceFromCenter, leftEye.getY() + Math.sin(calculateAngle(leftEye))*leftPupilDistanceFromCenter);
   }
   else {
    leftPupil.setLocation(leftEye.getX() - Math.cos(calculateAngle(leftEye))*leftPupilDistanceFromCenter, leftEye.getY() - Math.sin(calculateAngle(leftEye))*leftPupilDistanceFromCenter);
   }
  }
  
  double rightPupilDistanceFromCenter = Math.min(calculateDistance(mouse, rightEye), calculateRadius(headSize/12, headSize/10, rightEye) - pupilRadius);
  if (rightPupilDistanceFromCenter == calculateDistance(mouse, rightEye) || mouse.equals(rightEye)) {
   rightPupil.setLocation(mouse.getX(), mouse.getY());
  }
  else {
   if (mouse.x - rightEye.x >= 0) { //accounts for range of arc tan function
    rightPupil.setLocation(rightEye.getX() + Math.cos(calculateAngle(rightEye))*rightPupilDistanceFromCenter, rightEye.getY() + Math.sin(calculateAngle(rightEye))*rightPupilDistanceFromCenter);
   }
   else {
    rightPupil.setLocation(rightEye.getX() - Math.cos(calculateAngle(rightEye))*rightPupilDistanceFromCenter, rightEye.getY() - Math.sin(calculateAngle(rightEye))*rightPupilDistanceFromCenter);
   }
  }
 }
 
 public double calculateDistance(Point one, Point two) {
  double distance = 0;
  distance = Math.sqrt(Math.pow(one.getX() - two.getX(), 2) + Math.pow(one.getY() - two.getY(), 2));
  return distance;
 }
 
 public double calculateAngle(Point eye) {
  double angle = Math.atan((mouse.getY() - eye.getY())/(mouse.getX() - eye.getX()));
  return angle;
 }
 
 public double calculateRadius(int a, int b, Point eye) { //semi major axis and semi minor axis, center of eye x and y
  double radius = 0;
  double angle = calculateAngle(eye);
  radius = (a*b)/(Math.sqrt(Math.pow(a*Math.sin(angle), 2) + Math.pow(b*Math.cos(angle), 2)));
  //System.out.println(angle + " " + radius);
  return radius;
 }

 @Override
 public void mouseClicked(MouseEvent e) {
  // TODO Auto-generated method stub
  /*to test calculation of radius
  double r = calculateRadius(headSize/12, headSize/10, leftEye) - pupilRadius;
  double a = calculateAngle(leftEye);
  System.out.println(mouse + "\t" + r + "\t" + a);*/
  //snobby rage
  rage++;
  if (rage == 420) {
	  frame = 20;
  }
  else {
	  frame = 10;
  }
 }

 @Override
 public void mousePressed(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }

 @Override
 public void mouseReleased(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }

 @Override
 public void mouseEntered(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }

 @Override
 public void mouseExited(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }
 
 @Override
 public void run() {
  // TODO Auto-generated method stub
  try {
      while (true) {
       Thread.currentThread();
       Thread.sleep(35);
       repaint();
      }
     }
     catch (Exception e) {
      e.printStackTrace();
     }
 }
}
