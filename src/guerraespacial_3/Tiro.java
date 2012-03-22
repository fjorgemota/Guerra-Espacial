package guerraespacial_3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay.GameObject;

public class Tiro extends ObjetoComMovimento{

    boolean desativado;
    Direcao direcao;
    int velocidade;
    int perda_vida;
    int maxTempo = 10000;
    int atualTempo = 0;
    public Tiro(int x, int y, Direcao direcao){
        this.desativado = false;
        this.x = x;
        this.y = y;
        this.direcao = direcao;
        this.velocidade = 20;
        this.perda_vida = 10;
    }
    public boolean isDesativado(){
        return this.desativado;
    }
    public void step(long timeElapsed) {
        if(this.desativado){
            return;
        }
        this.atualTempo += timeElapsed;
        if(this.atualTempo>this.maxTempo){
            this.desativado = true;
        }
        switch(this.direcao){
            case DIREITA:
                this.moveDireita( this.velocidade );
                break;
            case ESQUERDA:
                this.moveEsquerda( this.velocidade );
                break;
            case CIMA:
                this.moveCima( this.velocidade );
                break;
            case BAIXO:
                this.moveBaixo( this.velocidade );
                break;
            case DIREITA_CIMA:
                this.moveDireitaCima( this.velocidade );
                break;
            case DIREITA_BAIXO:
                this.moveDireitaBaixo( this.velocidade );
                break;
            case ESQUERDA_CIMA:
                this.moveEsquerdaCima( this.velocidade );
                break;
            case ESQUERDA_BAIXO:
                this.moveEsquerdaBaixo( this.velocidade );
                break;
        }
        if(this.x>800){
            this.x = 0;
        }
        else if(this.y > 600){
            this.y = 0;
        }
        else if(this.x < 0){
            this.x = 800;   
        }
        else if(this.y < 0){
            this.y = 600;
        }
            
    }

    @Override
    public void draw(Graphics g) {
        if(this.desativado){
            return;
        }
        g.setColor(Color.YELLOW);
        g.drawLine(this.x, this.y, this.x+2, this.y+2);
    }

    public boolean temColisao(Rectangle retangulo){
        if(this.desativado){
            return false;
        }
        
        if(retangulo.contains(x, y)){
            this.desativado = true;
            return true;            
        } else {
            return false;
        }
    }

}
