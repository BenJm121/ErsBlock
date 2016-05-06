package game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/5/5.
 */
public class ErsBlockGame extends GameMenu {
    public final static int PER_LINE_SCORE=100;
    public final static int PER_LEVEL_SCORE=PER_LINE_SCORE*20;
    public final static int MAX_LEVEL=10;
    public final static int DEFAULT_LEVEL=5;

    private GameCanvas canvas;

    private ErsBlock block;
    private ControlPanel ctrlPanel;
    private boolean playing=false;
    public static int selfFail=0;
    public static int otherFail=0;

    

    public ErsBlockGame(String title) {
        super(title);
        setSize(315,392);
        Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrSize.width-getSize().width)/2,
                (scrSize.height-getSize().height)/2);
        Container container=getContentPane();
        container.setLayout(new BorderLayout(6,0));
        canvas=new GameCanvas();
        ctrlPanel=new ControlPanel(this);
        ctrlPanel.setPlayButtonEnable(false);
        container.add(canvas,BorderLayout.CENTER);
        container.add(ctrlPanel,BorderLayout.EAST);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
        canvas.fanning();
        setResizable(false);
    }

    public void reset(){
        ctrlPanel.reset();
        canvas.reset();
    }

    public void gameReset(){
        ctrlPanel.setPlayButtonEnable(false);
        getmGame().setEnabled(true);
        getmControl().setEnabled(true);
        getmInfo().setEnabled(true);
        getMiNewGame().setEnabled(true);
        getMiConnectGame().setEnabled(true);
    }

    public boolean isPlaying(){
        return playing;
    }

    public ErsBlock getCurBlock(){
        return block;
    }

    public GameCanvas getCanvas(){
        return canvas;
    }

    public void playGame(){
        play();
        ctrlPanel.setPlayButtonEnable(false);
        ctrlPanel.requestFocus();
    }

    public int getLevel(){
        return ctrlPanel.getLevel();
    }

    public void setLevel(int level){
        if (level<11&&level>0){
            ctrlPanel.setLevel(level);
        }
    }

    public int getScore() {
        if (canvas!=null)
            return canvas.getScore();
        return 0;
    }

    public int getScoreForLevelUpdate(){
        if (canvas!=null)
            return canvas.getScoreForLevelUpdata();
        return 0;
    }

    public boolean levelUpdate(){
        int curLevel=getLevel();
        if (curLevel<MAX_LEVEL){
            setLevel(curLevel+1);
            canvas.resetScoreForLevelUpdata();
            return true;
        }
        return false;
    }

    private void play(){
        reset();
        playing=true;
        Thread thread=new Thread(new Game());
        thread.start();
    }

    private void reportGameWin(){
        JOptionPane.showMessageDialog(this,"你赢了");
    }

    private void reportGameLose(){
        JOptionPane.showMessageDialog(this,"你输了");
    }

    private JDialog serverSuc(){
        JDialog dialog=new JDialog(this,"提示信息",false);
        JLabel label=new JLabel("              端口有效,等待其他玩家中..............");
        dialog.setSize(300,90);
        dialog.setResizable(false);
        dialog.getContentPane().add(label);
        Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((scrSize.width-getSize().width)/2,
                (scrSize.height-getSize().height)/2);
        return dialog;
    }

    private JDialog socketSuc(){
        JDialog dialog=new JDialog(this,"提示信息",false);
        JLabel label=new JLabel("与玩家连接建立成功，10s后开始游戏！");
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.setSize(300,90);
        dialog.setResizable(false);
        dialog.getContentPane().add(label);
        Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((scrSize.width-getSize().width)/2,
        (scrSize.height-getSize().height)/2);
        return dialog;
    }

    private JDialog showException(Exception e){
        JDialog dialog=new JDialog(this,"提示信息",false);
        JLabel label=new JLabel(e.toString());
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.setSize(300,90);
        dialog.setResizable(false);
        dialog.getContentPane().add(label);
        Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((scrSize.width-getSize().width)/2,
        (scrSize.height-getSize().height)/2);
        return dialog;
    }

    private class Game implements Runnable {
        public void run() {
            int col = canvas.getCols() - 8;
            int style = ErsBlock.STYLES[(int) (Math.random() * 7)][(int) (Math.random() * 4)];
            ServerSocket server = getServer();
            JDialog serverSuc = serverSuc();
            JDialog socketSuc = socketSuc();
            Socket socketOut = null;
            Socket socketIn = null;
            ObjectInputStream in = null;
            ObjectOutputStream out = null;

            selfFail = 0;
            otherFail = 0;
            int numLose = 0;
            boolean serverSign = false;
            boolean clienSign = false;
            int[][] failData;
            if (getAddress() != null) {
                serverSign = false;
                clienSign = true;
                try {
                    socketIn = new Socket(getAddress(), getPortNum());
                    socketOut = new Socket(getAddress(), getPortNum());

                    in = new ObjectInputStream(socketIn.getInputStream());
                    out = new ObjectOutputStream(socketOut.getOutputStream());
                } catch (Exception e) {
                    System.out.print(e.toString());
                }
            } else {
                if (server != null) {
                    serverSign = true;
                    clienSign = false;
                    try {
                        serverSuc.setVisible(true);
                        socketOut = server.accept();
                        socketIn = server.accept();
                        Thread.sleep(1000 * 2);

                        serverSuc.setVisible(false);

                        socketSuc.setVisible(true);
                        Thread.sleep(1000 * 2);
                        socketSuc.setVisible(false);
                        out = new ObjectOutputStream(socketOut.getOutputStream());
                        in = new ObjectInputStream(socketIn.getInputStream());
                    } catch (Exception e) {
                        showException(e).setVisible(true);
                    }
                }
            }
            while (playing) {
                if (otherFail == 1) {
                    try {

                    } catch (Exception e) {
                        System.out.println("对方玩家失败");
                        System.out.println(e.toString());
                    }
                    reportGameWin();
                    try {
                        if (in != null)
                            in.close();
                        if (out != null)
                            out.close();
                        if (serverSign) {
                            if (server != null) {
                                server.close();
                                getServer().close();
                            }
                        }
                        if (socketIn != null)
                            socketIn.close();
                        if (socketOut != null)
                            socketOut.close();
                        setAddress(null);
                        setServer(null);
                    } catch (Exception e) {

                    }
                    gameReset();
                    reset();
                    return;
                }
                if (selfFail == 1) {
                    failData = new int[GameCanvas.CANVAS_ROWS][GameCanvas.CANVAS_COLS];
                    try {
                        out.writeObject(new Data(failData, selfFail, 0));
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    reportGameLose();

                    try {
                        if (in != null)
                            in.close();
                        if (out != null)
                            out.close();
                        if (serverSign) {
                            if (server != null) {
                                server.close();
                                getServer().close();
                            }
                        }
                        if (socketIn != null)
                            socketIn.close();
                        if (socketOut != null)
                            socketOut.close();
                        setAddress(null);
                        setServer(null);
                    } catch (Exception e) {

                    }
                    gameReset();
                    reset();
                    return;
                }
                if (block != null) {
                    if (block.isAlive())
                        try {
                            Thread.currentThread().sleep(100);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                    continue;
                }


                numLose = checkFullline();

                if (isGameOver()) {
                    selfFail = 1;
                }

                block = new ErsBlock(style, -1, col, getLevel(), canvas,
                        ctrlPanel, in, out, numLose);
                block.start();

                style = ErsBlock.STYLES[(int) (Math.random() * 7)][(int) (Math.random() * 4)];
                ctrlPanel.setTipStyle(style);
            }

        }


        public int checkFullline() {
            int num = 0;
            for (int i = 0; i < canvas.getRows(); i++) {
                int row = -1;
                boolean fullLineColorBox = true;
                for (int j = 0; j < canvas.getCols(); j++) {
                    if (!canvas.getBox(i, j).isColorBox()) {
                        fullLineColorBox = false;
                        break;
                    }
                }
                if (fullLineColorBox) {
                    row = i--;
                    canvas.removeLine(row);
                    num++;
                }
            }
            return num;
        }

        private boolean isGameOver(){
            for (int i=0;i<canvas.getCols();i++){
                ErsBox box=canvas.getBox(0,i);
                if (box.isColorBox())
                    return true;
            }
            return false;
        }
    }

    public static void main(String[] args){
        new ErsBlockGame("俄罗斯方块游戏");
    }

    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);
        if (e.getSource()==miNewGame||e.getSource()==miConnectGame){
            ctrlPanel.setPlayButtonEnable(true);
        }
    }
}
