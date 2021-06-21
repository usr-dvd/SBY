import javax.swing.*;
import java.awt.BorderLayout;

public class Frame extends JFrame {


    public Frame() {

        initUI();
    }

    private void initUI() {
        JTextArea description = new JTextArea(
                "Press Left arrow to move left\n" +
                "Press Right arrow to move right\n" +
                "Press Up arrow to move up\n" +
                "Press Down arrow to move down\n" +
                "Press Space to fire\n" +"\n"+
                "Mission: destroy all alien ships"
        );

        description.setBounds(Const.TXT_AREA_X, Const.TXT_AREA_Y, Const.TXT_AREA_WIDTH, Const.TXT_AREA_HEIGHT);
        description.setVisible(true);
        ImageIcon img = new ImageIcon(Const.IMG_PATH.concat("planet.png"));
        setIconImage(img.getImage());
        setTitle("    Space Battleship Yamamoto");
        JButton btnStart = new JButton(" Start Game ");
        btnStart.setBounds(Const.BTN_ST_X, Const.BTN_ST_Y, Const.BTN_ST_WIDTH, Const.BTN_ST_HEIGHT);
        setLayout(null);
        btnStart.setVisible(true);
        add(btnStart);
        add(description);
        btnStart.addActionListener((e -> {
            this.setLayout(new BorderLayout(Const.ZERO, Const.ZERO));
            add(new Board());
            btnStart.setVisible(false);
            description.setVisible(false);
        }));

        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}