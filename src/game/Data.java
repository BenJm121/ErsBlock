package game;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/5.
 */
public class Data implements Serializable {
    private int[][]a;
    private int fail;
    private int numLose=0;
    public Data(int[][]b,int fail,int numLose){
        a=new int[b.length][b[0].length];
        for (int i=0;i<b.length;i++){
            for (int j=0;j<b[i].length;j++){
                a[i][j]=b[i][j];
            }
        }
        this.fail=fail;
        this.numLose=numLose;
    }

    public int[][] getA(){
        return a;
    }

    public void setA(int[][]a){
        this.a=a;
    }

    public int getFail(){
        return fail;
    }

    public void setFail(int fail){
        this.fail=fail;
    }

    public int getNumLose(){
        return numLose;
    }

    public void setNumLose(int numLose){
        this.numLose=numLose;
    }
}
