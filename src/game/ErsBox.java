package game;



import java.awt.*;

/**
 * Created by Administrator on 2016/5/5.
 */
public class ErsBox implements Cloneable{
    private boolean isColor;
    private Dimension size=new Dimension();

    public ErsBox(boolean isColor){
        this.isColor=isColor;
    }

    public boolean isColorBox(){
        return isColor;
    }

    public void setColor(boolean isColor){
        this.isColor=isColor;
    }

    public Dimension getSize(){
        return size;
    }

    public void setSize(Dimension size){
        this.size=size;
    }
    @Override
    protected Object clone() {
        Object cloned=null;
        try {
            cloned = super.clone();
        }catch (CloneNotSupportedException e){
            e.toString();
        }
        return cloned;
    }
}
