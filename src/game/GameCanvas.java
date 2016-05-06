package game;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by Administrator on 2016/5/5.
 */
public class GameCanvas extends JPanel {
    private Color backColor = Color.black, frontColor = Color.orange;
    private int rows, cols, score = 0, scoreForLevelUpdata = 0;
    private ErsBox[][] boxes;
    private int boxWidth, boxHeight;
    private int[][] canvasData;
    public final static int CANVAS_ROWS = 22;
    public final static int CANVAS_COLS = 13;

    public GameCanvas(){
        this.rows=CANVAS_ROWS;
        this.cols=CANVAS_COLS;

        boxes=new ErsBox[rows][cols];
        canvasData=new int[rows][cols];
        for (int i=0;i<boxes.length;i++){
            for (int j=0;j<boxes[i].length;j++){
                boxes[i][j]=new ErsBox(false);
            }
        }

        setBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(
                148,145,140)));
    }

    public GameCanvas(int rows,int cols,Color backColor,Color frontColor){
        this();
        this.backColor=backColor;
        this.frontColor=frontColor;
    }

    public void setBackgroundColor(Color backColor){
        this.backColor=backColor;
    }

    public Color getBackgroundColor(){
        return backColor;
    }

    public void setBlockColor(Color frontColor){
        this.frontColor=frontColor;
    }

    public Color getBlockColor(){
        return frontColor;
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public int getScore(){
        return score;
    }

    public int getScoreForLevelUpdata(){
        return scoreForLevelUpdata;
    }

    public void resetScoreForLevelUpdata(){
        scoreForLevelUpdata-=ErsBlockGame.PER_LEVEL_SCORE;
    }

    public ErsBox getBox(int row,int col){
        if (row<0||row>boxes.length-1||col<0
                ||col>boxes[0].length-1)
            return null;
        return (boxes[row][col]);
    }

    public int[][]getCanvasData(){
        try{
            for (int i=0;i<boxes.length;i++){
                for (int j=0;j<boxes[i].length;j++){
                    if (boxes[i][j].isColorBox()){
                        canvasData[i][j]=1;
                    }else{
                        canvasData[i][j]=0;
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }

        return canvasData;
    }

    public int currentRow(){
        int currentRow=0;
        boolean sign=false;
        for (int i=GameCanvas.CANVAS_ROWS-1;i>=0;i--){
            for (int j=0;j<GameCanvas.CANVAS_COLS;j++){
                if (boxes[i][j].isColorBox()){
                    sign=false;
                    break;
                }
            }
            if (sign){
                currentRow=i+1;
                break;
            }
        }
        return currentRow;
    }

    public ErsBox setAutoBox(){
        ErsBox box=null;
        if (Math.random()*10>=5.0)
            box=new ErsBox(false);
        else
        {
            box= new ErsBox(true);
        }
        return box;
    }

    public boolean addNewRow(int num){
        int currentRow=currentRow();
        if (currentRow<=num)
            return false;
        else
        {
            for (int i=currentRow;i<GameCanvas.CANVAS_ROWS;i++){
                for (int j=0;j< GameCanvas.CANVAS_COLS;j++){
                    boxes[i-num][j]=(ErsBox)boxes[i][j].clone();
                }
            }
            for (int i=GameCanvas.CANVAS_ROWS-1;i> GameCanvas.CANVAS_ROWS-num-1;i--){
                for (int j=0;j<GameCanvas.CANVAS_COLS;j++){
                    boxes[i][j]=setAutoBox();
                }
            }
            repaint();
            return true;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(frontColor);

        for (int i=0;i<boxes.length;i++){
            for (int j=0;j<boxes[i].length;j++){
                if (boxes[i][j].isColorBox()){
                    g.setColor(frontColor);
                }else{
                    g.setColor(backColor);
                }
                g.fill3DRect(j*boxWidth,i*boxHeight,boxWidth,boxHeight,true);
            }
        }
    }

    public void fanning(){
        boxWidth=getSize().width/cols;
        boxHeight=getSize().height/rows;
    }

    public synchronized void removeLine(int row){
        for (int i=row;i>0;i--){
            for (int j=0;j<cols;j++){
                boxes[i][j]=(ErsBox)boxes[i-1][j].clone();
            }
        }
        score+=ErsBlockGame.PER_LINE_SCORE;
        scoreForLevelUpdata+=ErsBlockGame.PER_LINE_SCORE;
        repaint();
    }

    public void reset(){
        score=0;
        scoreForLevelUpdata=0;
        for (int i=0;i<boxes.length;i++){
            for (int j=0;j<boxes[i].length;j++){
                boxes[i][j].setColor(false);
            }
        }
        repaint();
    }


}
