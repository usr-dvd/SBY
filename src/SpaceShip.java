import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite  {

    private int dx;
    private int dy;
    private ArrayList<Missile> missiles;
    int fireTrigger = Const.ZERO;
    private int missilesCounter = Const.ALIENS_QUANTITY;

    public SpaceShip(int x, int y) {
        super(x, y);
        initSpaceShip();
    }

    private void initSpaceShip() {

        missiles = new ArrayList<>();

        loadImage(Const.IMG_PATH.concat("battleship.png"));
        getImageDimensions();

    }

    public void move() {
        x += dx;
        y += dy;

        if (x < Const.ONE) {
            x = Const.ONE;
        }

        if (y < Const.ONE) {
            y = Const.ONE;
        }

        if (x < Const.ONE) {
            x = Const.ONE;
        }
        if (x > Const.SPACE_SHIP_X_BOUND) {
            x = Const.SPACE_SHIP_X_BOUND;
        }

        if (y > Const.SPACE_SHIP_Y_BOUND) {
            y = Const.SPACE_SHIP_Y_BOUND;
        }
    }

    public List<Missile> getMissiles() {
        return missiles;
    }



    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -Const.SPACE_SHIP_DX;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = Const.SPACE_SHIP_DX;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -Const.SPACE_SHIP_DY;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = Const.SPACE_SHIP_DY;
        }

        if (key == KeyEvent.VK_SPACE) {
            fire();
            fireTrigger = Const.ZERO;
        }
    }


    public void fire() {
        if (missilesCounter > Const.ZERO) {
            if (fireTrigger == Const.ONE) {
                missiles.add(new Missile(x, y + Const.LAUNCHER_PLACE));
                missilesCounter--;
            }
        }
    }


    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = Const.ZERO;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = Const.ZERO;
        }

        if (key == KeyEvent.VK_UP) {
            dy = Const.ZERO;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = Const.ZERO;
        }

        if (key == KeyEvent.VK_SPACE) {
            fireTrigger++;
        }
    }
    public int getMissilesCounter() {
        return missilesCounter;
    }

    public void setMissilesCounter(int missilesCounter) {
        this.missilesCounter = missilesCounter;
    }

}