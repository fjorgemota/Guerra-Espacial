/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guerraespacial_1;

import java.awt.Graphics;
import javaPlay.GameEngine;
import javaPlay.Keyboard;
import javaPlay.Mouse;
import javaPlay.Sprite;

/**
 *
 * @author fernando_mota
 */
public class NaveJogador2 extends ObjetoComMovimento{
    Sprite sprite;
    int vida;
    
    public NaveJogador2(){
        try{
            this.sprite = new Sprite("resources/nave_verde.png",4,64,64);
        }
        catch(Exception ex){
            System.out.println("Erro ao carregar a imagem: "+ex.getLocalizedMessage());
        }
        this.vida = 100;
        this.x = 200;
        this.y = 272;
    }
    public void step(long timeElapsed) {
        Keyboard teclado = GameEngine.getInstance().getKeyboard();
        Mouse mouse = GameEngine.getInstance().getMouse();
        int mouseY = mouse.getMousePos().y/12;
        int mouseX = mouse.getMousePos().x/12;
        int naveX = this.getX()/12;
        int naveY = this.getY()/12;
        if(teclado.keyDown(Keys.A) && teclado.keyDown(Keys.W) || mouse.isLeftButtonPressed() && mouseX < naveX && mouseY < naveY){
            this.sprite.setCurrAnimFrame(8);
            this.moveEsquerdaCima(12);
        }
        else if(teclado.keyDown(Keys.D) && teclado.keyDown(Keys.W) || mouse.isLeftButtonPressed() && mouseX > naveX && mouseY < naveY){
            this.sprite.setCurrAnimFrame(2);
            this.moveDireitaCima(12);
        }
        else if(teclado.keyDown(Keys.A) && teclado.keyDown(Keys.S) || mouse.isLeftButtonPressed() && mouseX < naveX && mouseY > naveY){
            this.sprite.setCurrAnimFrame(6);
            this.moveEsquerdaBaixo(12);
        }
        else if(teclado.keyDown(Keys.D) && teclado.keyDown(Keys.S)  || mouse.isLeftButtonPressed() && mouseX > naveX && mouseY > naveY){
            this.sprite.setCurrAnimFrame(4);
            this.moveDireitaBaixo(12);
        }
        else if(teclado.keyDown(Keys.W) || mouse.isLeftButtonPressed() && mouseY < naveY){
            this.sprite.setCurrAnimFrame(1);
            this.moveCima(12);
        }
        else if(teclado.keyDown(Keys.S) || mouse.isLeftButtonPressed() && mouseY > naveY){
            this.sprite.setCurrAnimFrame(5);
            this.moveBaixo(12);
        }
        else if(teclado.keyDown(Keys.A) || mouse.isLeftButtonPressed() && mouseX < naveX){
            this.sprite.setCurrAnimFrame(7);
            this.moveEsquerda(12);
        }
        else if(teclado.keyDown(Keys.D) || mouse.isLeftButtonPressed() && mouseX > naveX){
            this.sprite.setCurrAnimFrame(3);
            this.moveDireita(12);
        }
    }

    public void draw(Graphics g) {
        this.sprite.draw(g, this.x, this.y);
    }
    
}
