import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private SpaceShip spaceship;
    private List<Alien> aliens;
    private boolean inGame, collided=false;
    private final int[][] pos = new int[Const.ALIENS_QUANTITY][Const.ALIENS_COORDINATES_Q];
    Boss boss = new Boss(Const.BOSS_START_X, Const.BOSS_START_Y);
    Magazine magazine = new Magazine(Const.MAGAZINE_START_X, Const.MAGAZINE_START_Y);
    ImageIcon bg = new ImageIcon(Const.IMG_PATH.concat("galaxy-space.jpg"));





    public Board() {
        initPositions(pos);
        initBoard();
    }
    private void initPositions(int[][] pos) {
        int x = Const.ALIENS_POS_X;
        int y = Const.ALIENS_POS_Y;
        for (int i = 0; i < pos.length; i++) {
            x += Const.ALIENS_POS_DX+Const.TWO;
            if (x >= Const.ALIENS_POS_RIGHT_BOUND) {
                x = Const.ALIENS_POS_X;
                y -= -Const.ALIENS_POS_Y;
            }
            pos[i][0]=x;
            pos[i][1]=y;
        }
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        if (!inGame) {
            setBackground(Color.BLACK);
        }
        inGame = true;

        setPreferredSize(new Dimension(Const.BOARD_WIDTH, Const.BOARD_HEIGHT));

        spaceship = new SpaceShip(Const.SPACE_SHIP_START_X, Const.SPACE_SHIP_START_Y);

        initAliens();

        timer = new Timer(Const.DELAY, this);
        timer.start();
    }

    public void initAliens() {

        aliens = new ArrayList<>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {
        Image image = bg.getImage();
        g.drawImage(image, Const.ZERO, Const.ZERO, Const.BOARD_WIDTH, Const.BOARD_HEIGHT, null);

        if (spaceship.isVisible()) {
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                    this);
        }

        List<Missile> ms = spaceship.getMissiles();

        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(),
                        missile.getY(), this);
            }
        }

        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        if (aliens.size() > Const.ZERO) {
            g.drawString("Aliens left: " + aliens.size(), Const.COUNTING_STR_X, Const.COUNTING_STR_Y);
        }
        else {
            g.drawString("Boss HP: " + boss.getHeath(), Const.COUNTING_STR_X, Const.COUNTING_STR_Y);
        }
        if (boss.getRamCounter()==Const.ONE && boss.isVisible() && boss.getY() < Const.BOSS_MSG_Y_BOUND) {
            g.drawString(" **WARNING** Boss incoming! ", Const.BOSS_MSG_X, Const.BOSS_MSG_Y);
        }

        if (boss.isVisible()) {
                g.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);

        }
        g.drawString("Missiles left: " + spaceship.getMissilesCounter(), Const.MISSILE_MSG_X, Const.COUNTING_STR_Y);

        if (magazine.isVisible()) {
            g.drawImage(magazine.getImage(), magazine.getX(), magazine.getY(), this);
        }
    }

    private void drawGameOver(Graphics g) {
        String msg;
        if (collided) {
            msg = "Game Over. Mission failed.";
        }
        else {
            msg = "Mission complete! Level Passed.";
        }
        Font small = new Font("Helvetica", Font.BOLD, Const.FINAL_MSG_FONT_SIZE);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (Const.BOARD_WIDTH - fm.stringWidth(msg)) / Const.TWO,
                Const.BOARD_HEIGHT / Const.TWO);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateShip();
        updateMissiles();
        updateAliens();

        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!inGame) {
            timer.stop();
        }
    }

    private void updateShip() {

        if (spaceship.isVisible()) {

            spaceship.move();
        }
    }

    private void updateMissiles() {

        List<Missile> missiles = spaceship.getMissiles();

        if (spaceship.getMissilesCounter() == Const.ZERO){
            magazine.setVisible(true);
            magazine.move();
        }

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {
                missile.move();
            } else {
                missiles.remove(i);
            }
        }

    }

    private void updateAliens() {

        if (aliens.isEmpty()) {
             boss.setVisible(true);
             boss.move();
             if (boss.getHeath() <= Const.ZERO){
             inGame = false;
             }
            return;
        }

        for (int i = 0; i < aliens.size(); i++) {

            Alien a = aliens.get(i);

            if (a.isVisible()) {
                a.move();
            } else {
                aliens.remove(i);
            }
        }
    }

    public void checkCollisions() {

        Rectangle spShipRect = spaceship.getBounds();
        Rectangle bossRect = boss.getBounds();
        Rectangle magRect = magazine.getBounds();

        if (spShipRect.intersects(magRect)) {
            magazine.setVisible(false);
            magazine.setY(Const.MAGAZINE_START_Y);
            spaceship.setMissilesCounter(Const.ALIENS_QUANTITY);
        }

        for (Alien alien : aliens) {

            Rectangle alienRect = alien.getBounds();

            if (spShipRect.intersects(alienRect)) {
                inGame = false;
                collided = true;
            }
        }
        if (spShipRect.intersects(bossRect)) {
            inGame = false;
            collided = true;
        }

        List<Missile> ms = spaceship.getMissiles();

        for (Missile m : ms) {

            Rectangle misRect = m.getBounds();

            if (misRect.intersects(bossRect) && boss.getY() > Const.ZERO) {
                boss.setHeath(boss.getHeath()-Const.ONE);
                m.setVisible(false);
            }

            for (Alien alien : aliens) {

                Rectangle alienRectMis = alien.getBounds();

                if (misRect.intersects(alienRectMis) && alien.getY() > Const.ZERO) {

                    m.setVisible(false);
                    alien.setVisible(false);
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }
        

    }
}