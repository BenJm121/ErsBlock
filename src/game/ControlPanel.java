package game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ControlPanel extends JPanel{
    private JTextField tfLevel=new JTextField(""
    +ErsBlockGame.DEFAULT_LEVEL),
    tfScore=new JTextField("0");
    private JButton btPlay=new JButton("开始"),btPause=new JButton("");

    private JPanel plTip=new JPanel(new BorderLayout());

    private TipPanel plTipBlock=new TipPanel();
    private JPanel useInfo=new JPanel(new BorderLayout());
    private ShowPanel infoBlock=new ShowPanel();

    private JPanel plInfo=new JPanel(new GridLayout(4,1));

    private Timer timer;
    private ErsBlockGame game;

    private Border border=new EtchedBorder(EtchedBorder.RAISED,Color.white,
            new Color(148,145,140));

    public ControlPanel(final ErsBlockGame game){
        setLayout(new GridLayout(3,1,0,4));

        plTip.add(new JLabel(" 下一个方块"),BorderLayout.NORTH);
        plTip.add(plTipBlock);
        plTip.setBorder(border);

        useInfo.add(new JLabel("     对方玩家   "),BorderLayout.NORTH);
        useInfo.add(infoBlock);

        plInfo.add(btPlay);
        plInfo.add(btPause);
        plInfo.add(new JLabel("       得分     "));
        plInfo.add(tfScore);
        plInfo.setBorder(border);

        tfScore.setEditable(false);

        add(plTip);
        add(plInfo);
        add(useInfo);

        addKeyListener(new ControlKeyListener());

        btPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getmGame().setEnabled(false);
                game.getmControl().setEnabled(false);
                game.getmInfo().setEnabled(false);
                game.playGame();
            }
        });

        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfScore.setText(""+game.getScore());
                int scoreForLevelUpdate=game.getScoreForLevelUpdate();
                if (scoreForLevelUpdate>=ErsBlockGame.PER_LEVEL_SCORE
                        &&scoreForLevelUpdate>0)
                    game.levelUpdate();
            }
        });
        timer.start();
    }
    public void setTipStyle(int style){
        plTipBlock.setStyle(style);
    }

    public void setUseInfo(int[][]date){
        infoBlock.setInfo(date);
    }

    public int getLevel(){
        int level=0;
        try{
            level=Integer.parseInt(tfLevel.getText());
        }catch(NumberFormatException e){
        }
        return level;
    }

    public void setLevel(int level){
        if (level>0&&level<11)
            tfLevel.setText(""+level);
    }

    public void setPlayButtonEnable(boolean enable){
        btPlay.setEnabled(enable);
    }

    public void reset(){
        tfScore.setText("0");
        plTipBlock.setStyle(0);
        infoBlock.resetInfo(0);
    }

    public void fanning(){
        plTipBlock.fanning();
    }



    private class TipPanel extends JPanel{
        private Color backColor=Color.darkGray,frontColor=Color.lightGray;
        private ErsBox[][]boxes=new ErsBox[ErsBlock.BOXES_ROWS][ErsBlock.BOXES_CLOS];
        private int style,boxWidth,boxHeight;
        private boolean isTiled=false;

        public TipPanel(){
            for (int i=0;i<boxes.length;i++){
                for (int j=0;j<boxes[i].length;j++){
                    boxes[i][j]=new ErsBox(false);
                }
            }
        }

        public TipPanel(Color backColor,Color frontColor){
            this();
            this.backColor=backColor;
            this.frontColor=frontColor;
        }

        public void setStyle(int style){
            this.style=style;
            repaint();
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            if (!isTiled)
                fanning();
            int key=0x8000;
            for (int i=0;i<boxes.length;i++){
                for (int j=0;j<boxes[i].length;j++){
                    Color color=(((key&style)!=0)?frontColor:backColor);
                    g.setColor(color);
                    g.fill3DRect(j*boxWidth,i*boxHeight,boxWidth,boxHeight,true);
                    key>>=1;
                }
            }

        }

        public void fanning(){
            boxWidth=getSize().width/ErsBlock.BOXES_CLOS;
            boxHeight=getSize().height/ErsBlock.BOXES_ROWS;
            isTiled=true;
        }
    }


    private class ShowPanel extends JPanel{
        private Color backColor=Color.darkGray,frontColor=Color.lightGray;

        private ErsBox[][]boxes=new ErsBox[GameCanvas.CANVAS_ROWS][GameCanvas.CANVAS_COLS];
        private int boxWidth,boxHeight;

        private boolean isTiled=false;

        private int[][]date=new int[GameCanvas.CANVAS_ROWS][GameCanvas.CANVAS_COLS];

        public ShowPanel(){
            for (int i=0;i<boxes.length;i++){
                for (int j=0;j<boxes[i].length;j++){
                    boxes[i][j]=new ErsBox(false);
                }
            }
        }

        public ShowPanel(Color backColor,Color frontColor){
            this();
            this.backColor=backColor;
            this.frontColor=frontColor;
        }

        public void setInfo(int[][]date){
            this.date=date;
            repaint();
        }

        public void resetInfo(int style){
            for (int i=0;i<boxes.length;i++){
                for (int j=0;j<boxes[i].length;j++){
                    date[i][j]=style;
                }
            }
            repaint();
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            if (!isTiled)
                fanning();
            for (int i=0;i<boxes.length;i++){
                for (int j=0;j<boxes[i].length;j++){
                    Color  color=((date[i][j]==1)?frontColor:backColor);
                    g.setColor(color);
                    g.fill3DRect(j*boxWidth,i*boxHeight,boxWidth,
                            boxHeight,true);
                }
            }

        }

        public void fanning(){
            boxWidth=getSize().width/GameCanvas.CANVAS_COLS;
            boxHeight=getSize().height/ GameCanvas.CANVAS_ROWS;
            isTiled=true;
        }


    }

    private class ControlKeyListener extends KeyAdapter{


        @Override
        public void keyPressed(KeyEvent ke) {
            if (!game.isPlaying())
                return;
            ErsBlock block=game.getCurBlock();
            if (ke.getKeyCode()==game.getUp()){
                block.turnNext();
                return;
            }
            if (ke.getKeyCode()==game.getDown()){
                block.moveDown();
                return;
            }
            if (ke.getKeyCode()==game.getLeft()){
                block.moveLeft();
                return;
            }
            if (ke.getKeyCode()==game.getRight()){
                block.moveRight();
                return;
            }
        }
    }
}
