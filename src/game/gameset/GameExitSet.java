package game.gameset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Administrator on 2016/5/6.
 */
public class GameExitSet extends JDialog {
    private JButton ok=new JButton("��");
    private JButton no=new JButton("��");
    private JLabel label=new JLabel("                  �����Ҫ�˳���Ϸ��");
    private JPanel panel = new JPanel();
    public GameExitSet(JFrame menu){
        super(menu,"�˳��Ի���",true);
        getContentPane().setLayout(new BorderLayout());
        setSize(300,90);
        setResizable(false);
        Dimension scrSize=getToolkit().getDefaultToolkit().getScreenSize();
        setLocation((scrSize.width-getSize().width)/2,
                (scrSize.height-getSize().height)/2);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
        panel.add(ok);
        panel.add(no);
        panel.setLayout(new FlowLayout());
        getContentPane().add(panel,"South");
        getContentPane().add(label,"North");
        ok.addActionListener(new isok());
        no.addActionListener(new isno());
        setVisible(true);

    }

    private class isok implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    private class isno implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }
}
