package guerraespacial_3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javaPlay.GameEngine;
import javaPlay.GameStateController;
import javaPlay.Keyboard;
import javax.swing.JOptionPane;

public class TesteNaves implements GameStateController {

    NaveJogador1 naveJogador1;
    ArrayList<Tiro> tirosJogador;
    ArrayList<NaveInimiga> navesInimigas;
    ArrayList<Explosao> explosoes;
    int naveInimigaNivel = 5;
    public void load() {
        this.naveJogador1 = new NaveJogador1();
        this.tirosJogador = new ArrayList<Tiro>();
        this.navesInimigas = new ArrayList<NaveInimiga>();
        this.navesInimigas.add(new NaveInimiga(800, 600, this.naveInimigaNivel));
        this.explosoes = new ArrayList<Explosao>();
    }

    public void step(long timeElapsed) {
        if(this.naveJogador1.estaMorto()){
            JOptionPane.showMessageDialog(null, "Parabéns, você chegou até o nível "+(this.naveInimigaNivel-5)+"!","Parabéns",JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Você chegou ao nível "+(this.naveInimigaNivel-5));
            System.exit(0);
            return;
        }
        this.naveJogador1.step(timeElapsed);
        ArrayList<NaveInimiga> navesParaApagar = new ArrayList<NaveInimiga>();
        for(NaveInimiga nave: this.navesInimigas){
            nave.step(timeElapsed);
            nave.persegue(this.naveJogador1);
            if(nave.estaMorto()){
                this.explosoes.add(new ExplosaoForte(nave.getX(), nave.getY()));
                navesParaApagar.add(nave);
            }
            if(nave.temColisao(this.naveJogador1.getRetangulo())){
                int xExplosao = nave.getX();
                int yExplosao = nave.getY();
                this.explosoes.add( new ExplosaoFraca(xExplosao, yExplosao) );
                this.naveJogador1.perdeVida(this.naveInimigaNivel);
            }
        }
        for(NaveInimiga nave: navesParaApagar){
            //this.navesInimigas.add(new NaveInimiga(800, 600, this.naveInimigaNivel));
            //this.navesInimigas.add(new NaveInimiga(0, 600, this.naveInimigaNivel));
            this.navesInimigas.add(new NaveInimiga(800, 600, 5));
            this.navesInimigas.add(new NaveInimiga(0, 600, 5));
            //this.navesInimigas.add(new NaveInimiga(800, 0, 5));
            //this.navesInimigas.add(new NaveInimiga(0, 0, 5));
            this.naveInimigaNivel += 1;
            this.navesInimigas.remove(nave);
        }
        ArrayList<Tiro> tirosparaapagar = new ArrayList<Tiro>();
        for (Tiro tiro : this.tirosJogador) {
            tiro.step(timeElapsed);
            if(tiro.isDesativado()){
                tirosparaapagar.add(tiro);
            }
        }
        for(Tiro tiro: tirosparaapagar){
            this.explosoes.add(new ExplosaoFraca(tiro.getX(),tiro.getY()));
            this.tirosJogador.remove(tiro);
        }
        ArrayList<Explosao> explosoesparapagar = new ArrayList<Explosao>();
        for (Explosao explosao : this.explosoes) {
            explosao.step(timeElapsed);
            if(explosao.isDesativado()){
                explosoesparapagar.add(explosao);
            }
        }
        for(Explosao explosao: explosoesparapagar){
            this.explosoes.remove(explosao);
        }

        this.lancaTirosJogador();
        this.verificaColisaoNavesInimigas();
    }

    private void lancaTirosJogador() {
        Keyboard teclado = GameEngine.getInstance().getKeyboard();

        if(teclado.keyDown( Keys.ESPACO) ){
            if(this.naveJogador1.podeAtirar()){
                this.tirosJogador.add( this.naveJogador1.getTiro() );
            }
        }
        if(teclado.keyDown( KeyEvent.VK_ENTER) ){
            if(this.naveJogador1.podeImortal()){
                this.naveJogador1.imortalizar();
            }
        }
        for(NaveInimiga nave: this.navesInimigas){
            if(nave.podeAtirar()){
                this.tirosJogador.add(nave.getTiro());
            }
        }
   }

    private void verificaColisaoNavesInimigas() {
        for(NaveInimiga nave: this.navesInimigas){
            for(Tiro tiro : this.tirosJogador){
                if(tiro.temColisao(nave.getRetangulo())){
                    nave.perdeVida(tiro.perda_vida-(this.naveInimigaNivel-5>=tiro.perda_vida?1:this.naveInimigaNivel-5));
                    int xExplosao = nave.getX();
                    int yExplosao = nave.getY();
                    this.explosoes.add( new ExplosaoFraca(xExplosao, yExplosao) );
                }
            }
        }
        for(Tiro tiro : this.tirosJogador){
            if(tiro.temColisao(this.naveJogador1.getRetangulo())){
                this.naveJogador1.perdeVida(10);
                int xExplosao = this.naveJogador1.getX();
                int yExplosao = this.naveJogador1.getY();
                this.explosoes.add(new ExplosaoFraca(xExplosao, yExplosao));
            }
        }
    }



    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.WHITE);
        g.drawString("Nivel: "+(this.naveInimigaNivel-5), 50,50);
        this.naveJogador1.draw(g);
        for(NaveInimiga nave: this.navesInimigas){
            nave.draw(g);
        }

        for (Tiro tiro : this.tirosJogador) {
            tiro.draw(g);
        }

        for (Explosao explosao : this.explosoes) {
            explosao.draw(g);
        }
    }    

    public void unload() {}
    public void start() {}
    public void stop() {}
}
