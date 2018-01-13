/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package watermaze_ver_3_0;

import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Balaji
 */
class TraceData extends Object{
    double[] xData = null;
    double[] yData = null;
    int CurrPos = 0;
    int DataLength = 0;
    int ActLength =0;
    //boolean Y_Only = false;
   public TraceData( int length){
        if (length > 0){
            DataLength = length;
            xData = new double[DataLength];
            yData = new double[DataLength];
        }
    }
   public TraceData( double[] x, double[] y){
        if( x != null && y != null){
            xData = (double[])x.clone();
            yData = (double[])y.clone();
            DataLength = Math.min(xData.length,yData.length);
        }
    }
   public boolean addData(double x, double y){
        if (CurrPos >= DataLength){
            JOptionPane.showMessageDialog(null,"OOPS! I am full you can not add anymore to me");
            return false;
        }

        xData[CurrPos] = x;
        yData[CurrPos] = y;
        CurrPos++;
        ActLength =  CurrPos > ActLength ? CurrPos : ActLength;
        return true;
    }
   public boolean addXData(double x, int pos){
       if( pos >= xData.length)
           return false;
       xData[pos] = x;
       return true;
   }

   public boolean addData(double x){
       return addData(x, CurrPos);
   }
   public boolean addYData(double y, int pos){
       if(pos >= yData.length)
           return false;
       yData[pos] = y;
       return true;
   }
   public double getX(int pos){
       if(pos < DataLength)
           return xData[pos];
       return xData[DataLength];
   }
   public double getY(int pos){
      if(pos < DataLength) return yData[pos];
      return yData[DataLength];
   }
   public double[] getXY(int pos){
       double[] XY = new double[2];
       if (pos < DataLength){
            XY[1] = xData[pos];
            XY[2] = yData[pos];
       }
       else{
            XY[1] = xData[DataLength];
            XY[2] = yData[DataLength];
       }
       return XY;
   }
   public boolean setPosition(int pos){
       if(pos < DataLength){
           CurrPos = pos;
           return true;
       }
     return false;
   }
   public int getPosition(){
       return CurrPos;
   }
   public int getDataLength(){
       return DataLength;
   }
   public double[] getX(){
       return (double [])xData.clone();
   }
   public double[] getY(){
       return (double [])yData.clone();
   }
   public double[] getX(boolean trimmed){
       return Arrays.copyOf(xData, ActLength);
   }
   public double[] getY(boolean trimmed){
       return Arrays.copyOf(yData, ActLength);
   }
   public boolean setLength(int length){
       if (DataLength != 0)
           return false;
        if (length > 0){
            DataLength = length;
            xData = new double[DataLength];
            yData = new double[DataLength];
            CurrPos = ActLength = 0;
            return true;
        }
       return false;
   }
   public void OverrideLength(int length){
       if (length == 0){
           xData = null;
           yData = null;
           return;
       }
       DataLength = length;
       xData = new double[DataLength];
       yData = new double[DataLength];
       CurrPos = ActLength = 0;
       return;
   }
}

