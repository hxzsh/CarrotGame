package name.panitz.game.CarrotGame;

import name.panitz.game.framework.ImageObject;
import name.panitz.game.framework.Vertex;

public class Bomben<I> extends ImageObject<I> {
    public Bomben(Vertex pos) {
        super("bombe.png", pos, new Vertex(Math.random() - 0.8, Math.random() - 0.8));
    }
}





