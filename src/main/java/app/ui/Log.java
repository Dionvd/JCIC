package app.ui;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author dion
 */
public class Log extends JTextArea {
    
    public static Log self;
    
    public Log()
    {
        self = this;
        DefaultCaret caret = (DefaultCaret)this.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
    
    public static void write(String string) { 
        self.append("\n"+string);
        self.setCaretPosition(self.getCaretPosition());
    }
    
        
}
