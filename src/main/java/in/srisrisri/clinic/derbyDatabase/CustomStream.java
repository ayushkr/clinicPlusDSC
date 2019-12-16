package in.srisrisri.clinic.derbyDatabase;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import javax.swing.JTextArea;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomStream
/*    */   extends OutputStream
/*    */ {
/*    */   JTextArea jTextArea;
/*    */   
/*    */   public CustomStream() {}
/*    */   
/* 23 */   public CustomStream(JTextArea jTextArea) { this.jTextArea = jTextArea; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 30 */     this.jTextArea.append(String.valueOf((char)b));
/* 31 */     this.jTextArea.setCaretPosition(this.jTextArea.getDocument().getLength());
/*    */   }
/*    */ }


/* Location:              /home/akr2/Desktop/SRI_HMS_server.jar!/sri_hms_server/CustomStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */