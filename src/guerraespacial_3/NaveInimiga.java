package guerraespacial_3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay.GameEngine;
import javaPlay.GameObject;
import javaPlay.Keyboard;
import javaPlay.Sprite;

public class NaveInimiga extends ObjetoComMovimento {

    Sprite sprite;
    int velocidade;
    int vida;
    int controleTiros = 0;
    int controleSuperTiros = 0;
    int framesControleTiros = 30;
    int framesControleSuperTiros = 300;
    public NaveInimiga(int x, int y, int velocidade) {

        this.vida = 100;
        this.velocidade = velocidade;
        this.x = x;
        this.y = y;
        try {            
            this.sprite = new Sprite("resources/nave_branca_mini.png", 1, 32, 32);
        } catch (Exception ex) {
            System.out.println("Imagem não encontrada: " + ex.getMessage());
        }
    }

    public void step(long timeElapsed){
        this.controleTiros++;
        this.controleSuperTiros++;
    }
    public boolean podeAtirar(){
        return (this.controleTiros > this.framesControleTiros);
    }
    public void draw(Graphics g) {
        if(this.estaMorto()){
            return; //Não desenha nada;
        }
        g.setColor(Color.white);
        g.drawString(this.vida+"", this.x+5, this.y-15);
        
        this.sprite.draw(g, this.x, this.y);        
    }

    public void perdeVida(int numPontos){
        this.vida -= numPontos;
    }

    public boolean estaMorto(){
        return (this.vida <= 0);
    }

    public Rectangle getRetangulo(){
        return new Rectangle(this.x+4, this.y+4, 32, 32);
    }
    

    /**
     * Perseguir significa que o X e o Y desta nave deve sempre se aproximar
     * do x e do y do objeto perseguido.
     *
     * Para deixar o mais realista possível, faremos a perseguição também na diagonal.
     * Lembre que se o
     * x do perseguidor for Maior -> Perseguidor está à direita
     * x do perseguidor for Menor -> Perseguidor está à esquerda
     * y do perseguidor for Maior -> Perseguidor está abaixo
     * y do perseguidor for Menor -> Perseguidor está acima
     */
    public void persegue(GameObject objeto){
        this.controleTiros++;
        int xPerseguido = objeto.getX()/this.velocidade;
        int yPerseguido = objeto.getY()/this.velocidade;
        int xNave = this.x/this.velocidade;
        int yNave = this.y/this.velocidade;
        if(xNave < xPerseguido && yNave < yPerseguido){
            //NaveMini Está à esquerda e acima
            //Nave Perseguida está abaixo e à direita.
            this.moveDireitaBaixo(this.velocidade);
            this.sprite.setCurrAnimFrame(4);

        } else if(xNave < xPerseguido && yNave > yPerseguido){
            //NaveMini Está à esquerda e abaixo
            //Nave Perseguida está acima e à direita.
            this.moveDireitaCima(this.velocidade);
            this.sprite.setCurrAnimFrame(2);

        } else if(xNave > xPerseguido && yNave < yPerseguido){
            //NaveMini Está à direita e acima
            //Nave Perseguida está abaixo e à esquerda.
            this.moveEsquerdaBaixo(this.velocidade);
            this.sprite.setCurrAnimFrame(6);

        } else if(xNave > xPerseguido && yNave > yPerseguido){
            //NaveMini Está à direita e abaixo
            //Nave Perseguida está acima e à esquerda.
            this.moveEsquerdaCima(this.velocidade);
            this.sprite.setCurrAnimFrame(8);

        } else if(xNave < xPerseguido && yNave == yPerseguido){
            //Nave Perseguida está a direita.
            this.moveDireita(this.velocidade);
            this.sprite.setCurrAnimFrame(3);

        } else if(xNave > xPerseguido && yNave == yPerseguido){
            //Nave Perseguida está a esquerda.
            this.moveEsquerda(this.velocidade);
            this.sprite.setCurrAnimFrame(7);

        } else if(xNave == xPerseguido && yNave < yPerseguido){
            //Nave Perseguida está abaixo
            this.moveBaixo(this.velocidade);
            this.sprite.setCurrAnimFrame(5);

        } else if(xNave == xPerseguido && yNave > yPerseguido){
            //Nave Perseguida está acima
            this.moveCima(this.velocidade);
            this.sprite.setCurrAnimFrame(1);
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
    public boolean temColisao(Rectangle retangulo){
        if(this.estaMorto()){
            return false;
        }
        
        if(this.getRetangulo().intersects(retangulo)){
            this.perdeVida(10);
            return true;            
        } else {
            return false;
        }
    }
        public Tiro getTiro(){
        //O tiro pode sair de qualquer um dos oito lados.
        //E o x e y inicial do tiro podem sempre ser diferentes

        int xTiro = this.x;
        int yTiro = this.y;
        int tamanhoNave = 40;
        int metadeNave = tamanhoNave / 2;

        switch(this.direcao){
            case DIREITA:
                xTiro += tamanhoNave;
                yTiro += metadeNave;
                break;
            case ESQUERDA:
                yTiro += metadeNave;
                break;
            case CIMA:
                xTiro += metadeNave;
                break;
            case BAIXO:
                xTiro += metadeNave;
                yTiro += tamanhoNave;
                break;
            case DIREITA_CIMA:
                xTiro += tamanhoNave;
                break;
            case DIREITA_BAIXO:
                xTiro += tamanhoNave;
                yTiro += tamanhoNave;
                break;
            case ESQUERDA_CIMA:
                break;
            case ESQUERDA_BAIXO:
                yTiro += tamanhoNave;
                break;
        }
        if(this.controleSuperTiros>this.framesControleSuperTiros){
            this.controleSuperTiros = 0;
            return new SuperTiro(xTiro, yTiro, this.direcao);
        }
        this.controleTiros = 0;
        return new Tiro(xTiro, yTiro, this.direcao);
    }
}
