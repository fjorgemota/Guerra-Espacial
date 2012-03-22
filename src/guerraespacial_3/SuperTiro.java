/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guerraespacial_3;

import guerraespacial_2.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author fernando_mota
 */
public class SuperTiro extends Tiro{
    public SuperTiro(int x, int y, Direcao direcao){
        super(x,y,direcao);
        this.velocidade = 100;
        this.perda_vida = 20;
    }
    public void draw(Graphics g) {
        if(this.desativado){
            return;
        }
        g.setColor(Color.RED);
        g.drawLine(this.x, this.y, this.x+2, this.y+2);
    }
}
