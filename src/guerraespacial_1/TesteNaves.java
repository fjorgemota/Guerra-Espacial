package guerraespacial_1;

import java.awt.Color;
import java.awt.Graphics;
import javaPlay.GameStateController;

public class TesteNaves implements GameStateController {

    NaveJogador1 naveSprite;
    NaveJogador2 naveSprite2;

    public void load() {
        this.naveSprite = new NaveJogador1();
        this.naveSprite2 = new NaveJogador2();
    }

    public void step(long timeElapsed) {
        this.naveSprite.step(timeElapsed);     
        this.naveSprite2.step(timeElapsed);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        this.naveSprite.draw(g);
        this.naveSprite2.draw(g);
    }


    public void unload() {}
    public void start() {}
    public void stop() {}
}
