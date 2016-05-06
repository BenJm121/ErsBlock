package game.gameset;

import game.GameMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;

/**
 * Created by Administrator on 2016/5/6.
 */
public class GameServerSocketSet extends JDialog implements ActionListener {
    private JLabel port=new JLabel("            端口号：");
    private JTextField text=new JTextField("01234");
    private JButton ok=new JButton("完成");
    private JTextArea tf=new JTextArea("请输入端口号（注意：端口号请选择大于1024的数字");
    private JPanel panel=new JPanel();
    private int portNum=8081;
    private ServerSocket server=null;
    private Socket socket=null;

    private GameMenu menu;
    public GameServerSocketSet(final GameMenu menu){
        super(menu,"set the PORT_NUM of socket",true);
        this.menu=menu;
        getContentPane().setLayout(new BorderLayout());
        setSize(350,90);
        setResizable(false);
        Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrSize.width-getSize().width)/2,
                (scrSize.height-getSize().height)/2);
        getContentPane().add("North",tf);
        getContentPane().add("South",panel);
        tf.setEditable(false);
        panel.setLayout(new GridLayout(1,3,10,10));
        panel.add(port);
        panel.add(text);
        text.setText(new Integer(menu.getPortNum()).toString());
        portNum=menu.getPortNum();
        ok.requestFocus();
        panel.add(ok);
        ok.addActionListener(this);
        ok.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode()== KeyEvent.VK_ENTER){
                    portNum=Integer.parseInt(text.getText());
                    show(false);
                    System.out.println("asdaksdjadlkjaskdja");
                }
            }
        });
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
        portNum=Integer.parseInt(text.getText());
        setVisible(false);
        menu.getMiConnectGame().setEnabled(false);
        try{
            server=new ServerSocket(portNum);
            System.out.println("建立端口成功！端口号是：");
            System.out.println(portNum);
            setVisible(false);
        }catch(IOException ex){
            JOptionPane gameexit=new JOptionPane(ex.toString()+"\nYou need to reset port_num.",
                    JOptionPane.WARNING_MESSAGE);
            JDialog dialog=gameexit.createDialog(this,"");
            dialog.setVisible(true);
        }
    }
    public int getPortNum(){
        return portNum;
    }
    public ServerSocket getServerSocket(){
        return server;
    }
}
