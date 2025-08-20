package name.panitz.game.CarrotGame;

import name.panitz.game.framework.*;
import name.panitz.game.framework.Button;
import name.panitz.game.framework.swing.SwingGame;
import name.panitz.game.framework.SoundObject;
import name.panitz.game.framework.TextObject;


import java.util.ArrayList;
import java.util.List;

public class CarrotGame<I,S> extends AbstractGame<I,S> {
    CarrotGame(){
      super(new ImageObject<>("hase.png"), 800, 600);

        for (int i = 0; i < 20; i++){
          karotten.add(new Karotten<>(new Vertex(Math.random()*(getWidth()-20), Math.random()*(getHeight()-30))));
      }
        for (int i = 0; i < 6; i++){
            bomben.add(new Bomben<>(new Vertex(Math.random()*(getWidth()-20), Math.random()*(getHeight()-30))));
        }
        hintergrund.add(new ImageObject<>("hintergrund.png"));



        getGOss().add(hintergrund);
        getGOss().add(karotten);
        getGOss().add(bomben);
        goss.add(treffer);
        goss.add(anzahlKarotten);
        getButtons().add(new Button("Pause", ()-> pause()));
        getButtons().add(new Button("Fortsetzen", ()-> start()));
        getButtons().add(new Button("Beenden", ()-> System.exit(0)));
    }

    List<GameObject<I>> hintergrund = new ArrayList<>();
    List<Karotten<I>> karotten = new ArrayList<>();
    List<Bomben<I>> bomben = new ArrayList<>();
    List<GameObject<I>> treffer = new ArrayList<>();
    List<GameObject<I>> anzahlKarotten = new ArrayList<>();




    int gegesseneKarotten = 0;
    int leben = 5;

    @Override
    public void keyPressedReaction(KeyCode keycode) {
        switch (keycode){
            case LEFT_ARROW: getPlayer().getVelocity().x -= 0.5;
            break;
            case UP_ARROW: getPlayer().getVelocity().y -= 0.5;
            break;
            case DOWN_ARROW: getPlayer().getVelocity().y += 0.5;
            break;
            case RIGHT_ARROW: getPlayer().getVelocity().x += 0.5;
            break;
            case VK_X: getPlayer().getVelocity().x = 0;
            getPlayer().getVelocity().y = 0;
            break;
            default:
                break;
        }
    }

    SoundObject<S> munch = new SoundObject<S>("munch.wav");
    SoundObject<S> explosion = new SoundObject<S>("explosion.wav");

    @Override
    public void doChecks() {
        for (var k : karotten) {
            if (k.getPos().x < 0 || k.getPos().x + k.getWidth() > getWidth()) {
                k.getVelocity().x *= -1;
            }
            if (k.getPos().y < 0 || k.getPos().y + k.getHeight() > getHeight()) {
                k.getVelocity().y *= -1;
            }
            if (k.touches(getPlayer())){
                k.getPos().moveTo(new Vertex(getWidth()+0.5,k.getPos().y));
                gegesseneKarotten ++;
                playSound(munch);

            }
            if (gegesseneKarotten >= 15){
                anzahlKarotten.add(new TextObject<>(new Vertex(180, 300), "Du hast gewonnen!","Arial",60));
            }
        }
        for (var b : bomben) {
            if (b.getPos().x < 0 || b.getPos().x + b.getWidth() > getWidth()) {
                b.getVelocity().x *= -1;
            }
            if (b.getPos().y < 0 || b.getPos().y + b.getHeight() > getHeight()) {
                b.getVelocity().y *= -1;
            }
            if (b.touches(getPlayer())){
                b.getPos().moveTo(new Vertex(getWidth()+0.5,b.getPos().y));
                playSound(explosion);
                leben-=1;
            }
        }
        if (leben == 0){
            treffer.add(new TextObject<>(new Vertex(180,300)
                    ,"Game Over","Arial",100));
        }
        if (getPlayer().getPos().x < 0 || getPlayer().getPos().x+ getPlayer().getWidth() > getWidth()){
            getPlayer().getVelocity().x *= -1;
        }
        if (getPlayer().getPos().y < 0 || getPlayer().getPos().y+ getPlayer().getHeight() > getHeight()){
            getPlayer().getVelocity().y *= -1;
        }
    }

    @Override
    public boolean isStopped() {return super.isStopped()||leben<=0||gegesseneKarotten==15;
    }


    @Override
    public void paintTo(GraphicsTool<I> g) {
        super.paintTo(g);
        g.drawString(675, 40, "Karotten: " + gegesseneKarotten);
        g.drawString(675, 70, "Leben: " + leben);
    }

    public static void main(String[] args) {
        SwingGame.startGame(new CarrotGame<>());
    }
}
