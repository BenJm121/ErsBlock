package game.gameset;

import game.GameMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2016/5/6.
 */
public class GameHardSet extends JDialog {
    private JLabel hard=new JLabel("       难度级别（1-10）：");
    private JTextField text=new JTextField("0005");
    private JButton ok=new JButton("完成");
    private int gamehard=0005;
    private JPanel panel1=new JPanel();
    private JPanel panel2=new JPanel();
    private GameMenu menu;

    public GameHardSet(final GameMenu menu){
        super(menu,"游戏难度设定",true);
        setSize(280,85);
        Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrSize.width-getSize().width)/2,
                (scrSize.height-getSize().height)/2);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center",panel1);
        getContentPane().add("South",panel2);
        setResizable(false);
        text.requestFocus();
        text.setText(new Integer(menu.getGameHard()).toString());
        gamehard=menu.getGameHard();

        panel1.setLayout(new GridLayout(1,2));
        panel1.add(hard);
        panel1.add(text);
        panel2.add(ok);
        ok.addActionListener(new isOk());
    }

    private class isOk implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            gamehard=Integer.parseInt(text.getText());
            show(false);
        }
    }
    public int getgameHard(){
        return gamehard;
    }
}


