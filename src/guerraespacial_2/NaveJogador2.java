/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guerraespacial_2;

import guerraespacial_1.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
    int controleTiro = 0;
    int controleSuperTiro = 0;
    int framesControleTiro = 5;
    int framesControleSuperTiros = 30;
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
        ++this.controleTiro;
        ++this.controleSuperTiro;
        Keyboard teclado = GameEngine.getInstance().getKeyboard();
        Mouse mouse = GameEngine.getInstance().getMouse();
        int velocidade = 12;
        int mouseY = mouse.getMousePos().y/velocidade;
        int mouseX = mouse.getMousePos().x/velocidade;
        int naveX = this.getX()/velocidade;
        int naveY = this.getY()/velocidade;
        if(teclado.keyDown(Keys.A) && teclado.keyDown(Keys.W) || mouse.isLeftButtonPressed() && mouseX < naveX && mouseY < naveY){
            this.sprite.setCurrAnimFrame(8);
            this.moveEsquerdaCima(velocidade);
        }
        else if(teclado.keyDown(Keys.D) && teclado.keyDown(Keys.W) || mouse.isLeftButtonPressed() && mouseX > naveX && mouseY < naveY){
            this.sprite.setCurrAnimFrame(2);
            this.moveDireitaCima(velocidade);
        }
        else if(teclado.keyDown(Keys.A) && teclado.keyDown(Keys.S) || mouse.isLeftButtonPressed() && mouseX < naveX && mouseY > naveY){
            this.sprite.setCurrAnimFrame(6);
            this.moveEsquerdaBaixo(velocidade);
        }
        else if(teclado.keyDown(Keys.D) && teclado.keyDown(Keys.S)  || mouse.isLeftButtonPressed() && mouseX > naveX && mouseY > naveY){
            this.sprite.setCurrAnimFrame(4);
            this.moveDireitaBaixo(velocidade);
        }
        else if(teclado.keyDown(Keys.W) || mouse.isLeftButtonPressed() && mouseY < naveY){
            this.sprite.setCurrAnimFrame(1);
            this.moveCima(velocidade);
        }
        else if(teclado.keyDown(Keys.S) || mouse.isLeftButtonPressed() && mouseY > naveY){
            this.sprite.setCurrAnimFrame(5);
            this.moveBaixo(velocidade);
        }
        else if(teclado.keyDown(Keys.A) || mouse.isLeftButtonPressed() && mouseX < naveX){
            this.sprite.setCurrAnimFrame(7);
            this.moveEsquerda(velocidade);
        }
        else if(teclado.keyDown(Keys.D) || mouse.isLeftButtonPressed() && mouseX > naveX){
            this.sprite.setCurrAnimFrame(3);
            this.moveDireita(velocidade);
        }
        
    }
    public void morre(){
        this.vida -= 1;
    }
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawString(this.vida+"", this.x+5, this.y-15);
        this.sprite.draw(g, this.x, this.y);
    }
    public boolean podeAtirar(){
        return (this.controleTiro > this.framesControleTiro);
    }
    public Tiro getTiro(){
        int tamanhoNave = 64;
        int metadeNave = tamanhoNave/2;
        int xTiro = this.getX();
        int yTiro = this.getY();
        switch(this.direcao){
            case BAIXO:
                yTiro += tamanhoNave;
                xTiro += metadeNave;
                break;
            case CIMA:
                xTiro += metadeNave;
                break;
            case DIREITA:
                yTiro += metadeNave;
                xTiro += tamanhoNave;
                break;
            case ESQUERDA:
                yTiro += metadeNave;   
                break;
            case DIREITA_BAIXO:
                xTiro += tamanhoNave;
                yTiro += tamanhoNave;
                break;
            case DIREITA_CIMA:
                xTiro += tamanhoNave;
                break;
            case ESQUERDA_BAIXO:
                yTiro += tamanhoNave;
                break;
            case ESQUERDA_CIMA:
                break;
                   
        }
        if(this.controleSuperTiro>this.framesControleSuperTiros){
            this.controleSuperTiro = 0;
            return new SuperTiro(xTiro, yTiro, this.direcao);
        }
        else{
            this.controleTiro = 0;
            return new Tiro(xTiro, yTiro, this.direcao);
        }
    }
    public Rectangle getRetangulo(){
        return new Rectangle(this.x+4, this.y+4, 56, 56);
    }
}
