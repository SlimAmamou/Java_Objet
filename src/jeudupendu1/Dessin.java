/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudupendu1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Slim
 */
public class Dessin {

    private final GraphicsContext gc;
    private final int loosepoints;
    
    public Dessin(GraphicsContext gc, int loosepoints){
        this.gc = gc;
        this.loosepoints = loosepoints;
    }
    
    public void dessiner(){
        switch(loosepoints)
        {
            case 0 :{
                gc.setLineWidth(10);
                gc.setStroke(Color.ORANGE);
                gc.strokeLine(280, 50, 280, 330);
                gc.strokeLine(145, 50, 280, 50);
                gc.strokeLine(145, 50, 145,135);
                gc.strokeLine(200, 52, 278, 150);
                gc.strokeLine(250, 350, 310, 350);
                gc.strokeLine(250, 348, 280, 330);
                gc.strokeLine(280, 330, 310, 348);
                break;
            }
            
            case 1 : {
                gc.setLineWidth(10);
                gc.setStroke(Color.BLACK);
                gc.strokeOval(128, 90, 33, 45);
                break;
            }
            
            case 2 : {
                gc.setLineWidth(10);
                gc.setStroke(Color.BLACK);
                gc.strokeOval(118, 135, 58, 90);
                break;
            }
            
            case 3 : {
                gc.setLineWidth(10);
                gc.setStroke(Color.BLACK);
                gc.strokeLine(95, 194, 120, 160);
                break;
            }
            
            case 4 : {
                gc.setLineWidth(10);
                gc.setStroke(Color.BLACK);
                gc.strokeLine(175, 160, 200, 194);
                break;
            }
            
            case 5: {
                gc.setLineWidth(10);
                gc.setStroke(Color.BLACK);
                gc.strokeLine(158, 225, 158, 270);
                break;
            }
            
            case 6 : {
                gc.setLineWidth(10);
                gc.setStroke(Color.BLACK);
                gc.strokeLine(138, 225, 138, 270);
                break;
            }
        }
    }
}
