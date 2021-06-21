public class Missile extends Sprite {


    public Missile(int x, int y) {
        super(x, y);

        initMissile();
    }

    private void initMissile() {

        loadImage(Const.IMG_PATH.concat("missile.png"));
        getImageDimensions();
    }

    public void move() {

        y -= Const.MISSILE_SPEED;

        if (y < Const.ZERO) {
            visible = false;
        }
    }

}