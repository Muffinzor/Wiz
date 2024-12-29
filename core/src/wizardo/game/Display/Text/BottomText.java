package wizardo.game.Display.Text;

public class BottomText extends FloatingText {

    public BottomText() {

    }

    public void update(float delta) {
        stateTime += delta;
        if(delta > 0) {
            y_increase += 0.3f;
        }
        if(stateTime >= 0.1f) {
            alpha -= .05f;
            stateTime = 0;
        }


        if (alpha <= 0) {
            text = "";
        }
    }
}
