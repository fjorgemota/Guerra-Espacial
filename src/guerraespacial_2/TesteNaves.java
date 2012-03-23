package guerraespacial_2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javaPlay.GameEngine;
import javaPlay.GameStateController;
import javaPlay.Keyboard;

public class TesteNaves implements GameStateController {

    NaveJogador1 naveJogador1;
    NaveJogador2 naveJogador2;
    ArrayList<Tiro> tirosJogador;
    ArrayList<NaveJogador1> naves;
    private int naveIntervalo=300;
    private int naveContador;
    public void load() {
        this.naveJogador1 = new NaveJogador1();
        this.naveJogador2 = new NaveJogador2();
        this.naves = new ArrayList<NaveJogador1>();
        this.tirosJogador = new ArrayList<Tiro>();
    }

    public void step(long timeElapsed) {
        ++this.naveContador;
        if(naveContador>naveIntervalo){
            this.naves.add(new NaveJogador1());
        }
        this.naveJogador1.step(timeElapsed);
        this.naveJogador2.step(timeElapsed);
        for (Tiro tiro : this.tirosJogador) {
            tiro.step(timeElapsed);
            if(tiro.temColisao(this.naveJogador1.getRetangulo())){
                this.naveJogador1.morre();
            }
            if(tiro.temColisao(this.naveJogador2.getRetangulo())){
                this.naveJogador2.morre();
            }
        }

        this.lancaTirosJogador();
    }

    private void lancaTirosJogador() {
        Keyboard teclado = GameEngine.getInstance().getKeyboard();

        if(teclado.keyDown( Keys.ESPACO) ){
            if(this.naveJogador1.podeAtirar()){
                this.tirosJogador.add( this.naveJogador1.getTiro() );
            }
        }
        if(teclado.keyDown(Keys.Q)){
            if(this.naveJogador2.podeAtirar()){
                this.tirosJogador.add(this.naveJogador2.getTiro());
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        this.naveJogador1.draw(g);
        this.naveJogador2.draw(g);
        for (Tiro tiro : this.tirosJogador) {
            tiro.draw(g);
        }
    }    

    public void unload() {}
    public void start() {}
    public void stop() {}
}
