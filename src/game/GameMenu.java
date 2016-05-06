package game;

import game.gameset.*;

import javax.swing.*;
import java.awt.event.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/5/6.
 */
public class GameMenu extends JFrame implements ActionListener {
    private JMenuBar bar=new JMenuBar();
    private JMenu mGame=new JMenu("游戏");
    private JMenu mControl=new JMenu("控制");
    private JMenu mInfo=new JMenu("帮助");
    protected JMenuItem miNewGame=new JMenuItem("建立新游戏");
    protected JMenuItem miConnectGame=new JMenuItem("连接游戏");
    protected JMenuItem miExit=new JMenuItem("退出");
    protected JMenuItem miHardset=new JMenuItem("游戏难度设置");
    protected JMenuItem miControlKey=new JMenuItem("控制键设置");
    protected JMenuItem miAuthor=new JMenuItem("作者：Javai游戏设计组");
    protected JMenuItem miSourceInfo=new JMenuItem("版本：1.0");

    private Socket socket;
    private ServerSocket server=null;
    private int portNum=8081;
    private String address=null;

    private int gamehard=0005;

    private int up= KeyEvent.VK_UP;
    private int down= KeyEvent.VK_DOWN;
    private int left= KeyEvent.VK_LEFT;
    private int right= KeyEvent.VK_RIGHT;

    public GameMenu(String s){
        super(s);
        bar.add(mGame);
        bar.add(mControl);
        bar.add(mInfo);

        mGame.add(miNewGame);
        miNewGame.addActionListener(this);

        mGame.addSeparator();

        mGame.add(miConnectGame);
        miConnectGame.addActionListener(this);

        mGame.addSeparator();

        mGame.add(miExit);
        miExit.addActionListener(this);

        mControl.add(miControlKey);
        miControlKey.addActionListener(this);
        mControl.addSeparator();

        mControl.add(miHardset);
        miHardset.addActionListener(this);

        mInfo.add(miAuthor);
        miAuthor.addActionListener(this);
        mInfo.addSeparator();

        mInfo.add(miSourceInfo);
        miSourceInfo.addActionListener(this);

        setJMenuBar(bar);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==miNewGame){
            GameServerSocketSet newgameset=new GameServerSocketSet(this);
            portNum=newgameset.getPortNum();
            server=newgameset.getServerSocket();

        }else if (e.getSource()==miConnectGame)
        {
            GameClienSocketSet newgameset=new GameClienSocketSet(this);
            portNum=newgameset.getPortNum();
            address=newgameset.getAddr();
        }else if(e.getSource()==miExit){
            GameExitSet gameexit=new GameExitSet(this);
        }else if(e.getSource()==miControlKey){
            GameControlKeySet keycontrol=new GameControlKeySet(this);
            up=keycontrol.getUP();
            down=keycontrol.getDOWN();
            left=keycontrol.getLEFT();
            right=keycontrol.getRIGHT();
        }else if(e.getSource()==miHardset){
            GameHardSet gameHardSet=new GameHardSet(this);
            gamehard=gameHardSet.getgameHard();
        }else if(e.getSource()==miAuthor){

        }else if(e.getSource()==miSourceInfo){

        }

    }

    public int getGameHard(){
        return gamehard;
    }

    public int getPortNum(){
        return portNum;
    }

    public int getDown(){
        return down;
    }

    public int getLeft(){
        return left;
    }

    public int getRight(){
        return right;
    }

    public int getUp(){
        return up;
    }

    public JMenu getmControl(){
        return mControl;
    }

    public JMenu getmGame(){
        return mGame;
    }

    public JMenu getmInfo(){
        return mInfo;
    }

    public JMenuItem getMiAuthor(){
        return miAuthor;
    }

    public JMenuItem getMiConnectGame(){
        return miConnectGame;
    }

    public JMenuItem getMiControlKey(){
        return miControlKey;
    }

    public JMenuItem getMiExit(){
        return miExit;
    }

    public JMenuItem getMiHardset(){
        return miHardset;
    }

    public JMenuItem getMiNewGame(){
        return miNewGame;
    }

    public JMenuItem getMiSourceInfo(){
        return miSourceInfo;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public ServerSocket getServer(){
        return server;
    }

    public void setServer(ServerSocket server){
        this.server=server;
    }

}
