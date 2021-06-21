
public class Alien extends Sprite {


    public Alien(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {

        loadImage(Const.IMG_PATH.concat("spaceship (4).png"));
        getImageDimensions();
    }

    public void move() {

        if (y > Const.BOARD_HEIGHT) {
            y = Const.ALIENS_INITIAL_Y;
        }
        y++;
    }
}