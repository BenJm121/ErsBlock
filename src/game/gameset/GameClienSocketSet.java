package game.gameset;

import game.GameMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Administrator on 2016/5/6.
 */
public class GameClienSocketSet extends JDialog implements ActionListener{
    private JLabel port=new JLabel("        端口号：");
    private JTextField text=new JTextField("01234");
    private JLabel address=new JLabel("      主机地址:");
    private JTextField addressText=new JTextField("127.0.0.1");
    private JButton ok=new JButton("完成");
    private JPanel panel=new JPanel();
    private JPanel panel1=new JPanel();
    private int portNum=1234;
    private String addr=null;
    private GameMenu menu;

    public GameClienSocketSet(final GameMenu menu){
        super(menu,"connect",true);
        this.menu=menu;
        setSize(400,100);
        setResizable(false);
        Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrSize.width-getSize().width)/2,
                (scrSize.height-getSize().height)/2);
        getContentPane().setLayout(new BorderLayout());

        panel.setLayout(new GridLayout(1,4));
        panel.add(address);
        panel.add(addressText);
        panel.add(port);
        panel.add(text);
        text.setText(new Integer(menu.getPortNum()).toString());
        portNum=menu.getPortNum();
        addressText.setText(menu.getAddress());
        addr=menu.getAddress();

        panel1.setLayout(new FlowLayout());
        panel1.add(ok);

        getContentPane().add("North",panel);
        getContentPane().add("South",panel1);

        ok.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                show(false);
            }
        });
        show(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        menu.getMiNewGame().setEnabled(false);
        portNum=Integer.parseInt(text.getText());
        addr=addressText.getText();
        setVisible(false);
        System.out.println("建立端口成功！端口号是：");
        System.out.println(portNum);
        System.out.println(addr);
        setVisible(false);

    }

    public String getAddr(){
        return addr;
    }
    public void setAddr(String addr){
        this.addr=addr;
    }
    public int getPortNum(){
        return portNum;
    }
    public void setPortNum(int portNum){
        this.portNum=portNum;
    }
}
