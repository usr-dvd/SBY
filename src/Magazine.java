public class Magazine extends Sprite {

    public Magazine(int x, int y) {
        super(x, y);
        initMagazine();
        visible = false;
    }
    private void initMagazine(){

        loadImage(Const.IMG_PATH.concat("missile (2).png"));
        getImageDimensions();
    }
    public void move() {

        if (y < Const.MAGAZINE_Y_PLACE) {
            y+=Const.TWO;
        }

    }
}
