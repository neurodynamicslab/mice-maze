                                                                                                                                                                                                                                                                                                                                                                                                                                      package watermaze_ver_3_0; 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Dimension;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.process.ImageStatistics;
import java.awt.FileDialog;
import java.awt.LayoutManager;
import java.io.FileWriter;
/**
 *
 * @author Balaji
 */
public class WaterMazeMainFrame extends javax.swing.JFrame {

    private float[] DataX;        // float array to store x- co - ordinates of the mice position 
    private float[] DataY;        // float array to store y- co-ordinate of the mice position

    /**
     * Creates new form WaterMazeMainFramewh
     */
    public WaterMazeMainFrame() {           // the default constructor for the "main" class. 
        this.dataFiles = null;              // This is array of datafiles to be processed by the program. Put in place with 
                                            // idea future expansion to batch processing of multiple files. We initialise at the 
                                            //start of the program to null. So that we assign the files after getting inputs from the
                                            //user.
        initComponents();
    }

    /**
     * This function reads the co-ordinates from the files saved in *.WMDF format (watermaze data file format)
     * These files are saved when acquiring water maze files from Coulborn Systems. The format for decoding is deciphered from
     * matlab files that is present as a supplementary information in Paul's maximum entropy measure for watermaze paper. 
     * https://www.ncbi.nlm.nih.gov/pmc/articles/PMC2802531/
     * @return
     */
    public int readWMDFData(){
      
        File tmpData = null;
        if(this.currDataFile != null){ 
             tmpData = currDataFile; 
             this.outDirectory = currDataFile.getParentFile();
             JOptionPane.showMessageDialog(this, "Using "+ currDataFile.getAbsolutePath() + " as data directory or file");
        }else{                  // too redundant ? (it is required as a safety net for the usage of the  function readWMDFData)
                                // best practice would be to use the arugments to pass the data file and thereby make the function universal. 
                                // In such cases we need this checks.
                                // replace with simple return -1 statement ?
            JFileChooser fc = new JFileChooser();
            int status = fc.showOpenDialog(null);
            if(status == JFileChooser.CANCEL_OPTION)
                return -1;
            currDataFile = fc.getSelectedFile();
            this.outDirectory = currDataFile.getParentFile();
        }
        FileInputStream Stream = null;
        DataInputStream Data;
        int status = 0;
        float [] dataX, dataY;
        
            if(tmpData != null && tmpData.exists()){              
                try {
                   Stream = new FileInputStream(tmpData);
                } catch (FileNotFoundException ex) {
                   // Logger.getLogger(WaterMazeAnalysisView.class.getName()).log(Level.SEVERE, null, ex);
                }
                Data = new DataInputStream(Stream);
                int probeNo =  1 ;//new Integer( Data_Table.getModel().getValueAt(count, 1).toString()).intValue();
                long fileSz = tmpData.length();

                if(fileSz >= Integer.MAX_VALUE){
                    JOptionPane.showMessageDialog(null,"File size of " + fileSz + " bytes exceeds the limits of this program quiting");
                    return -1;
                }
                //JOptionPane.showMessageDialog(null,"File size of " + fileSz + " bytes will be read");
                int DataSz = (int) fileSz;
                ByteBuffer DataBuff = ByteBuffer.allocate(DataSz);
                try {
                    Data.read(DataBuff.array());
                } catch (EOFException ex) {
                   status = -1;
                    //Logger.getLogger(WaterMazeAnalysisView.class.getName()).log(Level.SEVERE, null, ex);
                }catch (IOException e){
                    status = -1;
                }
                int trailStart = 4;
                int trailSize = 0;
                int n_tpts = 0;
                
                for(int probe = 0 ; probe < probeNo ; probe ++){
                        DataBuff.position(trailStart);
                        trailSize = DataBuff.getInt();
                        DataBuff.position( trailStart+28);
                        n_tpts = DataBuff.getInt();
                       // JOptionPane.showMessageDialog(null, ("The file has " + n_tpts + " time points and trail Size is"));
                        trailStart += trailSize + 4;
                }

                DataBuff.position(DataBuff.position()+(n_tpts*Float.SIZE/8)); //skip the timestamps of the frame
                dataX = new float[n_tpts];
                float Scale = 1;
                if(dataX != null)
                  for(int index = 0; index < n_tpts && status >= 0; index ++)
                             dataX[index] = Scale * DataBuff.getFloat();
                dataY = new float[n_tpts];
                if(dataY != null && status > 0);
                   for(int index = 0; index < n_tpts && status >= 0; index ++)
                             dataY[index] = Scale * DataBuff.getFloat();
                if(dataX == null || dataY == null){
                    JOptionPane.showMessageDialog(null, "Problem reading data buffer");
                    return -1;
                }
                 this.DataX = dataX.clone();
                 this.DataY = dataY.clone();
    }
            return 0;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RawImagePanel = new watermaze_ver_3_0.ImagePanel();
        Secondary_ImagePanel = new watermaze_ver_3_0.ImagePanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        analyseMenu = new javax.swing.JMenu();
        GenerateHMap = new javax.swing.JMenuItem();
        ExpXY = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WaterMaze Analysis 2.0");
        setBounds(new java.awt.Rectangle(0, 0, 480, 480));
        getContentPane().setLayout(new java.awt.FlowLayout());

        javax.swing.GroupLayout RawImagePanelLayout = new javax.swing.GroupLayout(RawImagePanel);
        RawImagePanel.setLayout(RawImagePanelLayout);
        RawImagePanelLayout.setHorizontalGroup(
            RawImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        RawImagePanelLayout.setVerticalGroup(
            RawImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        getContentPane().add(RawImagePanel);

        javax.swing.GroupLayout Secondary_ImagePanelLayout = new javax.swing.GroupLayout(Secondary_ImagePanel);
        Secondary_ImagePanel.setLayout(Secondary_ImagePanelLayout);
        Secondary_ImagePanelLayout.setHorizontalGroup(
            Secondary_ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        Secondary_ImagePanelLayout.setVerticalGroup(
            Secondary_ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        getContentPane().add(Secondary_ImagePanel);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Import Data File(s)");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save Project");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save Project As ...");
        saveAsMenuItem.setEnabled(false);
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        analyseMenu.setMnemonic('e');
        analyseMenu.setText("Analyse");
        analyseMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analyseMenuActionPerformed(evt);
            }
        });

        GenerateHMap.setMnemonic('t');
        GenerateHMap.setText("Generate HMap");
        GenerateHMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateHMapActionPerformed(evt);
            }
        });
        analyseMenu.add(GenerateHMap);

        ExpXY.setText("Export XY Co-Ordinates");
        ExpXY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExpXYActionPerformed(evt);
            }
        });
        analyseMenu.add(ExpXY);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        copyMenuItem.setEnabled(false);
        analyseMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        pasteMenuItem.setEnabled(false);
        analyseMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        deleteMenuItem.setEnabled(false);
        analyseMenu.add(deleteMenuItem);

        menuBar.add(analyseMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void GenerateHMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateHMapActionPerformed
        // TODO add your handling code here:
       /*  File tmpData = null;
        ByteBuffer bytedata = null;
        DataInputStream Data = null;
        FileInputStream Stream = null;
        byte b;
        float dataX[] = null,dataY[] = null;
        //dataX = new byte[1];
        int status = 0, nBytes = 0;
        int trailSize = 0;
        int trailStart = 4;
        int n_Skippped = 0;*/
        this.outDirectory = null;
        
        if(this.dataFiles == null){
            JFileChooser fc = new JFileChooser();
            fc.setDialogType(JFileChooser.OPEN_DIALOG);
            //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.showDialog(null, "Choose the datafile");

            currDataFile = fc.getSelectedFile();
        }
        else{
            currDataFile = null; //we have to run through the loop and get the files one after another
        }
        if (currDataFile == null)
            return;
        //statusPanel.setVisible(true);
        //progressBar.setVisible(true);
        //progressBar.setMinimum(0);
        //progressBar.setMaximum(nFiles-1);

       generateMap();
          //  progressBar.setValue(count);
        //}

    }//GEN-LAST:event_GenerateHMapActionPerformed

    private void analyseMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analyseMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_analyseMenuActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        // TODO add your handling code here:
       
        
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void ExpXYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExpXYActionPerformed
        // TODO add your handling code here:
        int status = 0;
        if(dataFiles == null || dataFiles.length == 0){
            JFileChooser fc = new JFileChooser();
            status = fc.showOpenDialog(this);
            if(status == JFileChooser.CANCEL_OPTION){
                JOptionPane.showMessageDialog(this,"You need to choose a datafile to export the co-ordinates");
                return;
            }
            this.currDataFile = fc.getSelectedFile();
            if(!currDataFile.exists()){
                JOptionPane.showMessageDialog(this, "The file "+ currDataFile.getName()+"does not exist");
                return;
            }
            
        }
        
        status = readWMDFData();
        
        if (status!= 0  ){
            JOptionPane.showMessageDialog(this,"Error reading the data file");
            return;
        }
        
        File out;
        String basename;
        FileWriter  outStream = null;
        if(currDataFile != null){
          basename =  File.separator +"XY_of_" +  this.currDataFile.getName().substring(0, currDataFile.getName().lastIndexOf("."));
          this.outDirectory = currDataFile.getParentFile();
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: Current data file is invalid");
            return;
        }
         if( this.outDirectory != null && this.outDirectory.exists()){
             if (this.outDirectory.isDirectory())
                 out = new File (this.outDirectory +basename);
                 
             else{
                 out = new File (this.outDirectory.getParent()+basename);
             }
             try {
                     outStream = new FileWriter(out);
                 } catch (IOException ex) {
                     Logger.getLogger(WaterMazeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
         }
         int datalength = DataX.length;
         int count = 0;
         String toWrite = "";
         
         while(count < datalength){
         
             toWrite += DataX[count] + "\t" + DataY[count] + "\n";
             count++;
             
         }
         
        try {
            outStream.write(toWrite);
        } catch (IOException ex) {
            Logger.getLogger(WaterMazeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            outStream.close();
        } catch (IOException ex) {
            Logger.getLogger(WaterMazeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }//GEN-LAST:event_ExpXYActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WaterMazeMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WaterMazeMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WaterMazeMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WaterMazeMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WaterMazeMainFrame().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ExpXY;
    private javax.swing.JMenuItem GenerateHMap;
    private watermaze_ver_3_0.ImagePanel RawImagePanel;
    private watermaze_ver_3_0.ImagePanel Secondary_ImagePanel;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu analyseMenu;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables

private File outDirectory; // placeholder for storing where the result files will be stored. 
// placeholder for storing where the result files will be stored. 
private int n_Files; // number of data files that one need to analyse
private File [] dataFiles; 
private File currDataFile; // // stores the current data file that is being used for analysis


    private int [][] createimage(float[] dataX, float[] dataY, int xMax , int yMax) {
        int [][] Image = null;
        int xlen = dataX.length;
        int ylen = dataY.length;
        int xidx, yidx = 0;

        if(xlen != ylen)
            return null;
        Image = new int [xMax][yMax];

        for (int count = 0 ; count < xlen ; count++){
            xidx = (dataX[count] > 0 && dataX[count] < 1 ) ? 1 : (int)dataX[count];
            yidx = (dataY[count] > 0 && dataY[count] < 1) ? 1 : (int)dataY[count];
            if(xidx < xMax && yidx < yMax && xidx > 0 && yidx > 0)
                Image[xidx][yidx] += 10;
        }
            

        return Image.clone();
    }

    private int[] createImgdata(float[] dataX, float[] dataY, int xMax, int yMax) {
        int []Image = null;
        int xlen = dataX.length;
        int ylen = dataY.length;
        int xidx, yidx = 0;
        if (dataX == null || dataY == null)
            return null;
        if(xlen != ylen)
            return null;
        Image = new int [xMax * yMax];
        for (int count = 0 ; count < xlen ; count++){
            xidx = (dataX[count] > 0 && dataX[count] < 1 ) ? 1 : (int)dataX[count];
            yidx = (dataY[count] > 0 && dataY[count] < 1) ? 1 : (int)dataY[count];
            if(xidx < xMax && yidx < yMax && xidx > 0 && yidx > 0)
               // Image[((yidx-1)*xMax)+ xidx] += 10;
                 Image[((yidx)*xMax)+ xidx] += 10;
        }
        return Image.clone();

    }
/*private float[] createImgdata(float[] dataX, float[] dataY, int xMax, int yMax) {
        float []Image = null;
        int xlen = dataX.length;
        int ylen = dataY.length;
        int xidx, yidx = 0;
        float curInt = 0, maxInt = 0,minInt = 0;

        if (dataX == null || dataY == null)
            return null;
        if(xlen != ylen)
            return null;
        Image = new float [xMax * yMax];
        for (int count = 0 ; count < xlen ; count++){
            xidx = (dataX[count] > 0 && dataX[count] < 1 ) ? 1 : (int)dataX[count];
            yidx = (dataY[count] > 0 && dataY[count] < 1) ? 1 : (int)dataY[count];
            if(xidx < xMax && yidx < yMax && xidx > 0 && yidx > 0)
                curInt = Image[((yidx-1)*xMax)+ xidx] ++;
            maxInt = (curInt > maxInt) ? curInt : maxInt ;
            minInt = ( curInt < minInt ) ? curInt : minInt ;
        }
        if(false/*autoscale){
            for(int count = 0 ; count < Image.length ; count ++ ){
                Image[count] = Image[count] * 100 /maxInt ;
            }
        }
        return Image.clone();

    }*/

    private int generateMap() {
        int status = 0;
        File tmpData;
        FileInputStream Stream = null;
        DataInputStream Data;
        float [] dataX,dataY;
        // for(int count = 0 ; count < nFiles ; count++){
         //   tmpData = new File((String)fileList.getModel().getElementAt(count));
         tmpData = new File (this.currDataFile.getAbsolutePath());
         this.outDirectory = this.currDataFile.getParentFile();
            if(tmpData.exists()){              
                try {
                   Stream = new FileInputStream(tmpData);
                } catch (FileNotFoundException ex) {
                   // Logger.getLogger(WaterMazeAnalysisView.class.getName()).log(Level.SEVERE, null, ex);
                }
                 Data = new DataInputStream(Stream);
                int probeNo =  1 ;//new Integer( Data_Table.getModel().getValueAt(count, 1).toString()).intValue();
                long fileSz = tmpData.length();

                if(fileSz >= Integer.MAX_VALUE){
                    JOptionPane.showMessageDialog(null,"File size of " + fileSz + " bytes exceeds the limits of this program quiting");
                    return -1;
                }
                //JOptionPane.showMessageDialog(null,"File size of " + fileSz + " bytes will be read");
                int DataSz = (int) fileSz;
                ByteBuffer DataBuff = ByteBuffer.allocate(DataSz);
                try {
                    Data.read(DataBuff.array());
                } catch (EOFException ex) {
                   status = -1;
                    //Logger.getLogger(WaterMazeAnalysisView.class.getName()).log(Level.SEVERE, null, ex);
                }catch (IOException e){
                    status = -1;
                }
               int trailStart = 4;
                int trailSize = 0;
                int n_tpts = 0;
                
                for(int probe = 0 ; probe < probeNo ; probe ++){
                        DataBuff.position(trailStart);
                        trailSize = DataBuff.getInt();
                        DataBuff.position( trailStart+28);
                        n_tpts = DataBuff.getInt();
                       // JOptionPane.showMessageDialog(null, ("The file has " + n_tpts + " time points and trail Size is"));
                        trailStart += trailSize + 4;
                }

                DataBuff.position(DataBuff.position()+(n_tpts*Float.SIZE/8)); //skip the timestamps of the frame
                dataX = new float[n_tpts];
                float Scale = 1;
                if(dataX != null)
                  for(int index = 0; index < n_tpts && status >= 0; index ++)
                             dataX[index] = Scale * DataBuff.getFloat();
                dataY = new float[n_tpts];
                if(dataY != null && status > 0);
                   for(int index = 0; index < n_tpts && status >= 0; index ++)
                             dataY[index] = Scale * DataBuff.getFloat();
                if(dataX == null || dataY == null){
                    JOptionPane.showMessageDialog(null, "Problem reading data buffer");
                    return -1;
                }
                 this.DataX = dataX.clone();
                 this.DataY = dataY.clone();
                 
                int [][] Image = createimage(this.DataX,this.DataY,240,240);
                int [] ImageData =createImgdata(this.DataX,this.DataY,240,240);
                BufferedImage realImg = new BufferedImage(240,240,BufferedImage.TYPE_BYTE_GRAY);
                realImg.getRaster().setPixels(0,0,240, 240, ImageData);
                //realImg.getRaster().setPixels(0,0,240, 240, ImageData);
                
                
                
                
                    ImagePanel IPanel = new ImagePanel();
                    JFrame ImageFrame = new JFrame();
                    //ImageFrame.setVisible(true);
                    Insets border = ImageFrame.getInsets();
                    Dimension d = new Dimension(240+border.left+border.right,240+border.top+border.bottom);
                    /*ImageFrame.setPreferredSize(d);
                    
                    
                    IPanel.setSize( 240,240);
                    ImageFrame.getContentPane().add(IPanel);
                    
                    ImageFrame.pack();
                    IPanel.setImgBuff(realImg);
                    IPanel.repaint();*/
                                                 
                //Scaling operation 

                    AffineTransform upScale = new AffineTransform(), downScale = new AffineTransform(); 
                    downScale.setToScale(0.5, 0.5);
                    upScale.setToScale(2, 2);
                    AffineTransformOp downScaling = new AffineTransformOp(downScale,AffineTransformOp.TYPE_BILINEAR);
                    AffineTransformOp upScaling = new AffineTransformOp(upScale,AffineTransformOp.TYPE_BILINEAR);

                    BufferedImage tmpImg = downScaling.filter(realImg, null);
                    BufferedImage FiltImg;
                    FiltImg = upScaling.filter(tmpImg, null);
                    FiltImg.getRaster().getDataBuffer();
                    
                    d = new Dimension(480+border.left+border.right, 240+border.top+border.bottom);
                    JFrame filtFrame = new JFrame("Filtered Image");
                    filtFrame.setPreferredSize(d);

                    //filtFrame.setVisible(true); //need brightness and contrast adjustment
                   /* ImagePanel FPanel = new ImagePanel();
                    FPanel.setSize(240,240);
                    filtFrame.getContentPane().add(FPanel);
                    filtFrame.pack();
                    FPanel.setImgBuff(FiltImg);
                    FPanel.repaint();*/

                   
                 //show the resulting images
                 
                
                 
                 ImagePlus imp_raw = new ImagePlus ("Result",realImg);              //raw tracks
                 ImagePlus imp_filt = new ImagePlus ("Filtered_Result",FiltImg);    //after bilinear operation
                 
                 
                 ImageStatistics stat = new ImageStatistics();
                 
                 stat = ImageStatistics.getStatistics(imp_filt.getProcessor(),ImageStatistics.MIN_MAX,null);
                 double filt_min = stat.min;
                 double filt_max = stat.max;
                 stat = ImageStatistics.getStatistics(imp_raw.getProcessor(),ImageStatistics.MIN_MAX,null);
                 double raw_min = stat.min;
                 double raw_max = stat.max;
                 
                 imp_filt.setDisplayRange(filt_min, filt_max);
                 imp_raw.setDisplayRange(raw_min,raw_max);
                 //ImagePanel FPanel = new ImagePanel();
                 
                 
                 //FPanel.add(new ImageCanvas(imp_filt));
                 this.RawImagePanel.add(new ImageCanvas(imp_raw));
                 
                 
                 this.add(new ImageCanvas(imp_filt));
                 this.pack();
                 
                 //this.add(new ImageCanvas(imp_raw));
                 //this.pack();
                 
//                 this.getContentPane().add(imp_raw.getCanvas());
 //                this.getContentPane().add(imp_filt.getWindow());
                 
                 //imp_filt.show();
                 //imp_raw.show();

                //Write to file
                
                File outputDirectory;
                outputDirectory = (outDirectory.isDirectory())? outDirectory:outDirectory.getParentFile();
               /* JFileChooser fc2 = new JFileChooser();
                fc2.setDialogType(JFileChooser.OPEN_DIALOG);
                fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc2.showDialog(null, "Choose Output Directory");
                outputDirectory = fc2.getSelectedFile();*/
                
                
                
                
                
                String baseName = outputDirectory + File.separator +"Map_of_" + probeNo + tmpData.getName().substring(0, tmpData.getName().lastIndexOf("."));
                File outFile = new File( baseName + "_filtered.jpg");
                File pngFile  = new File (baseName + ".png");
                
                try {
                    ImageIO.write(realImg,"png",pngFile);
                    ImageIO.write(FiltImg, "jpg", outFile);

                    //realImg = ImageIO.read(pngFile);
                } catch (IOException ex) {
            //        Logger.getLogger(WaterMazeAnalysisView.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                
               
              /*  try {
                    if (!outFile.createNewFile())
                        JOptionPane.showMessageDialog(null,"Error creating the file");
                    FileOutputStream FS = new FileOutputStream(outFile);
                    for(int yCount = 0 ; yCount < Image.length ; yCount ++)
                        for(int xCount = 0 ; xCount < Image[yCount].length ; xCount ++)
                            FS.write(IScale*Image[xCount][yCount]); // Compare density maps
                    FS.close();
                } catch(IOException ex){
                    JOptionPane.showMessageDialog(null, "Unable to write data to file skipping");
                }*/
                
            }
    return 0;
    }
}
