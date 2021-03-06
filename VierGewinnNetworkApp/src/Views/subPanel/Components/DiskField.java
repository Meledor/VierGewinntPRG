package Views.subPanel.Components;

import java.awt.Color;
import java.awt.Graphics;

public class DiskField {
    private int posX;
    private int posY;
    private int diameter;
    private Color color;
    private boolean played;
    
    public DiskField(){
        this(0, 0, 10);
    }
    public DiskField(int diameter){
        this(0,0, diameter);
    }
    public DiskField(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    public DiskField(int posX, int posY, int diameter){
        this.posX = posX;
        this.posY = posY;
        this.diameter = diameter;
        this.played = false;
    }
    public void diskPlayed(){
        this.played = true;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public void diskPlayed(Color color){
        this.diskPlayed();
        this.setColor(color);
    }
    public boolean isPlayed(){
        return this.played;
    }
    
    public void draw(int x, int y, Graphics g, int size){/*
        g.setColor(Color.RED);
        g.fillOval(x, y, 20, 20);*/
        if(this.played){
            g.setColor(this.color);
        }else{
            g.setColor(Color.WHITE);
        }
        g.fillOval(x, y, this.diameter, this.diameter);
    }
}
