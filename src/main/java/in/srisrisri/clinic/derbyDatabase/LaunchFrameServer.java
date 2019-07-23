
package in.srisrisri.clinic.derbyDatabase;

/*     */ import java.awt.Color;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.LayoutStyle;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import org.apache.derby.drda.NetworkServerControl;
/*     */ 
/*     */ public class LaunchFrameServer extends JFrame {
/*     */   NetworkServerControl n;
/*     */   PrintWriter pw;
/*     */   CustomStream customStream;
/*     */   
/*     */   public LaunchFrameServer() {
/*  31 */     initComponents();
/*  32 */     this.customStream = new CustomStream(this.jTextArea1);
/*  33 */     startServer();
/*  34 */     setTitle("SRI HMS server ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  47 */     this.jButton1 = new JButton();
/*  48 */     this.jButton2 = new JButton();
/*  49 */     this.jLabel1 = new JLabel();
/*  50 */     this.jScrollPane1 = new JScrollPane();
/*  51 */     this.jTextArea1 = new JTextArea();
/*  52 */     this.jLabel_ip = new JLabel();
/*  53 */     this.jLabel2 = new JLabel();
/*     */     
/*  55 */     setDefaultCloseOperation(3);
/*     */     
/*  57 */     this.jButton1.setText("start");
/*  58 */     this.jButton1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  60 */             LaunchFrameServer.this.jButton1ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/*  64 */     this.jButton2.setText("Stop");
/*  65 */     this.jButton2.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  67 */             LaunchFrameServer.this.jButton2ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/*  71 */     this.jLabel1.setText("Derby Server ");
/*     */     
/*  73 */     this.jTextArea1.setColumns(20);
/*  74 */     this.jTextArea1.setLineWrap(true);
/*  75 */     this.jTextArea1.setRows(5);
/*  76 */     this.jTextArea1.setWrapStyleWord(true);
/*  77 */     this.jScrollPane1.setViewportView(this.jTextArea1);
/*     */     
/*  79 */     this.jLabel_ip.setText("jLabel4");
/*     */     
/*  81 */     this.jLabel2.setFont(new Font("DejaVu Sans", 1, 18));
/*  82 */     this.jLabel2.setHorizontalAlignment(0);
/*  83 */     this.jLabel2.setText("SRI_HMS DATABASE SERVER");
/*     */     
/*  85 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  86 */     getContentPane().setLayout(layout);
/*  87 */     layout.setHorizontalGroup(layout
/*  88 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  89 */         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/*  90 */           .addGap(35, 35, 35)
/*  91 */           .addComponent(this.jButton1)
/*  92 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/*  93 */           .addComponent(this.jLabel1)
/*  94 */           .addGap(31, 31, 31)
/*  95 */           .addComponent(this.jButton2)
/*  96 */           .addGap(70, 70, 70))
/*  97 */         .addGroup(layout.createSequentialGroup()
/*  98 */           .addGap(27, 27, 27)
/*  99 */           .addComponent(this.jScrollPane1)
/* 100 */           .addContainerGap())
/* 101 */         .addGroup(layout.createSequentialGroup()
/* 102 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 103 */             .addGroup(layout.createSequentialGroup()
/* 104 */               .addGap(18, 18, 18)
/* 105 */               .addComponent(this.jLabel_ip, -2, 330, -2))
/* 106 */             .addGroup(layout.createSequentialGroup()
/* 107 */               .addContainerGap()
/* 108 */               .addComponent(this.jLabel2, -2, 376, -2)))
/* 109 */           .addContainerGap(-1, 32767)));
/*     */     
/* 111 */     layout.setVerticalGroup(layout
/* 112 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 113 */         .addGroup(layout.createSequentialGroup()
/* 114 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 115 */             .addGroup(layout.createSequentialGroup()
/* 116 */               .addGap(42, 42, 42)
/* 117 */               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 118 */                 .addComponent(this.jButton1)
/* 119 */                 .addComponent(this.jButton2)))
/* 120 */             .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 121 */               .addContainerGap()
/* 122 */               .addComponent(this.jLabel2)
/* 123 */               .addGap(24, 24, 24)
/* 124 */               .addComponent(this.jLabel1)))
/* 125 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 126 */           .addComponent(this.jScrollPane1, -2, 124, -2)
/* 127 */           .addGap(39, 39, 39)
/* 128 */           .addComponent(this.jLabel_ip, -2, 27, -2)
/* 129 */           .addContainerGap(34, 32767)));
/*     */ 
/*     */     
/* 132 */     pack();
/*     */   }
/*     */ 
/*     */   
/* 136 */   private void jButton1ActionPerformed(ActionEvent evt) { startServer(); }
/*     */ 
/*     */ 
/*     */   
/*     */   private void jButton2ActionPerformed(ActionEvent evt) {
/*     */     try {
/* 142 */       this.n.shutdown();
/* 143 */     } catch (Exception ex) {
/* 144 */       Logger.getLogger(LaunchFrameServer.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } 
/*     */   }
/*     */   
/* 148 */   static Properties properties = null; String ip_client; String ip_server; private JButton jButton1; private JButton jButton2;
/*     */   public static void readProperties() {
/* 150 */     properties = new Properties();
/*     */     try {
/* 152 */       properties.load(new FileInputStream("data/settings.properties"));
/* 153 */     } catch (Exception ex) {
/* 154 */       Logger.getLogger(LaunchFrameServer.class.getName()).log(Level.SEVERE, null, ex);
/* 155 */       System.out.println("pwd=" + (new File(" ")).getAbsolutePath());
/*     */     } 
/*     */   }
/*     */   private JLabel jLabel1; private JLabel jLabel2; private JLabel jLabel_ip; private JScrollPane jScrollPane1; private JTextArea jTextArea1;
/*     */   public void startServer() {
/* 160 */    
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 165 */       this.ip_server ="localhost";
/* 166 */       String port ="1527";
/*     */       
/* 168 */       String ip_and_port = this.ip_server + " : " + port;
/* 169 */       System.out.println("IP=" + ip_and_port);
/* 170 */       this.jLabel_ip.setText(ip_and_port + "");
/* 171 */       this.n = new NetworkServerControl(InetAddress.getByName(this.ip_server), Integer.parseInt(port));
/* 172 */       this.pw = new PrintWriter(this.customStream);
/*     */       
/* 174 */       this.n.start(this.pw);
/* 175 */       System.out.println("started server ...");
/* 176 */       this.jTextArea1.setBackground(Color.green);
/*     */     }
/* 178 */     catch (Exception ex) {
/*     */       
/* 180 */       System.out.println("Failed server ..." + ex.toString());
/* 181 */       this.jTextArea1.setBackground(Color.red);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/* 197 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
/* 198 */         if ("Nimbus".equals(info.getName())) {
/* 199 */           UIManager.setLookAndFeel(info.getClassName());
/*     */           break;
/*     */         } 
/*     */       } 
/* 203 */     } catch (ClassNotFoundException ex) {
/* 204 */       Logger.getLogger(LaunchFrameServer.class.getName()).log(Level.SEVERE, null, ex);
/* 205 */     } catch (InstantiationException ex) {
/* 206 */       Logger.getLogger(LaunchFrameServer.class.getName()).log(Level.SEVERE, null, ex);
/* 207 */     } catch (IllegalAccessException ex) {
/* 208 */       Logger.getLogger(LaunchFrameServer.class.getName()).log(Level.SEVERE, null, ex);
/* 209 */     } catch (UnsupportedLookAndFeelException ex) {
/* 210 */       Logger.getLogger(LaunchFrameServer.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     EventQueue.invokeLater(new Runnable()
/*     */         {
/* 224 */           public void run() { (new LaunchFrameServer()).setVisible(true); }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /home/akr2/Desktop/SRI_HMS_server.jar!/sri_hms_server/LaunchFrameServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */