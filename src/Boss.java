

public class Boss extends Sprite {
    private int heath = Const.BOSS_H_P;
    private int yBaseCoordinate = Const.BOSS_Y_BASE;
    private boolean left;
    private boolean right;
    private int ramCounter = Const.ONE;
    boolean ram = false;
    boolean straightRam = false;


    public Boss(int x, int y) {
        super(x, y);
        visible = false;
        initBoss();
    }


    private void initBoss() {

        loadImage(Const.IMG_PATH.concat("spaceship (2).png"));
        getImageDimensions();
    }
    public void move(){

        if (visible) {
            if (y < Const.ZERO) {
                randomizeLeftRight();
            }
            if (y > Const.BOARD_HEIGHT) {
                randomizeLeftRight();
            }

            if (yBaseCoordinate > Const.BOARD_HEIGHT_CENTER) {
                yBaseCoordinate = Const.BOSS_Y_BASE;
            }

            if (y < yBaseCoordinate) {
                if (ramCounter==Const.ONE){
                y += Const.BOSS_Y_SPEED;
                }
                else {
                    y+=Const.BOSS_Y_SPEED*Const.TWO;
                }
            }

            else {

                if (!left && right)  {
                    x+=Const.BOSS_X_SPEED;
                    if (x > Const.BOSS_RIGHT_BOUND) {
                        right = false;
                        left = true;
                    }
                }
                if (left && !right)  {
                    x-=Const.BOSS_X_SPEED;
                    if (x <= Const.ZERO) {
                        right = true;
                        left = false;
                    }
                }
                if (isEven(ramCounter) && !divisibleByThree(ramCounter)) {
                    ram = true;
                }
                if (divisibleByThree(ramCounter) && !isEven(ramCounter)) {
                    straightRam = true;
                }

                if (straightRam) {
                    x = Const.BOSS_START_X;
                    y+=Const.BOSS_Y_RAM_SPEED;
                }

                if (ram) {
                    y+=Const.BOSS_Y_RAM_SPEED;
                }
                if (x < Const.BOSS_START_X && x <= Const.BOSS_LEFT_X_BOUND) { y+=Const.BOSS_Y_EXTRA_SPEED; }

                if (x > Const.BOSS_START_X && x >= Const.BOSS_RIGHT_X_BOUND) { y+=Const.BOSS_Y_EXTRA_SPEED; }

                if (y > Const.BOSS_BOOST_Y_BOUND) {
                    y+=Const.BOSS_Y_EXTRA_SPEED;
                }

                if (y >= Const.BOSS_Y_BOUND) {
                    ram = false;
                    straightRam = false;
                    y = Const.BOSS_START_Y;
                    x = Const.BOSS_START_X;
                    ramCounter++;
                    yBaseCoordinate+=Const.BOSS_BASE_DY;
                }
            }
        }
    }
    public void randomizeLeftRight(){
        long seed = Math.round(Math.random());

        switch ((int) seed) {
            case Const.ZERO:
                left = true;
                right = false;
                break;
            case Const.ONE:
                left = false;
                right = true;
        }
    }

    public boolean isEven(int num) {
        return num%Const.TWO==Const.ZERO;
    }

    public boolean divisibleByThree(int num) {
        return num%Const.THREE==Const.ZERO;
    }

    public int getHeath() {
        return heath;
    }

    public void setHeath(int heath) {
        this.heath = heath;
    }

    public int getyBaseCoordinate() {
        return yBaseCoordinate;
    }

    public void setyBaseCoordinate(int yBaseCoordinate) {
        this.yBaseCoordinate = yBaseCoordinate;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public int getRamCounter() {
        return ramCounter;
    }

    public void setRamCounter(int ramCounter) {
        this.ramCounter = ramCounter;
    }
}
