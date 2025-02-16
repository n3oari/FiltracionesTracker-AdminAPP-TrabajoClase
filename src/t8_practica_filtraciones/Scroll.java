
package t8_practica_filtraciones;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Scroll {
    
    
    protected JTextArea areaT;
    protected String messageArea;
    protected JScrollPane scrollJ;
    
  
    public Scroll(){
        
        areaT = new JTextArea(40,10);
        areaT.setEditable(false);
        areaT.setWrapStyleWord(true);
        areaT.setLineWrap(true);
        
        scrollJ = new JScrollPane(areaT);
        scrollJ.setPreferredSize(new Dimension(200,200));
                
        
        messageArea = "";
        areaT.setText(messageArea);
    }
    
    public void setMessage(String message){
        messageArea = message;
        areaT.setText(messageArea);
    }
    
    public JScrollPane getJScrollPane(){
        return scrollJ;
    }
}


