package chatting.application;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class Client implements ActionListener{
    
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1 = new JFrame();
    
    static Box vertical = Box.createVerticalBox();
    
    
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    
    Boolean typing;
    
    Client(){
	f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(51, 204, 255));
        p1.setBounds(0, 0, 450, 200);
        f1.add(p1);
        
       ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/back.png"));
       Image i2 = i1.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
       ImageIcon i3 = new ImageIcon(i2);
       JLabel l1 = new JLabel(i3);
       l1.setBounds(5, 5, 60, 60);
       p1.add(l1);
       
       l1.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent ae){
               System.exit(0);
           }
       });
       
       ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/modi.png"));
       Image i5 = i4.getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT);
       ImageIcon i6 = new ImageIcon(i5);
       JLabel l2 = new JLabel(i6);
       l2.setBounds(160, 60, 140, 140);
       p1.add(l2);
       
       ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/bor.png"));
       Image i8 = i7.getImage().getScaledInstance(370, 60, Image.SCALE_DEFAULT);
       ImageIcon i9 = new ImageIcon(i8);
       JLabel l5 = new JLabel(i9);
       l5.setBounds(70, 5, 370, 60);
       p1.add(l5);
       
       JLabel l3 = new JLabel("Narendra Modi");
       l3.setFont(new Font("SERIF", Font.BOLD, 22));
       l3.setForeground(Color.WHITE);
       l3.setBounds(10, 170, 200, 25);
       p1.add(l3);   
       
       
       JLabel l4 = new JLabel("Active Now");
       l4.setFont(new Font("SERIF", Font.PLAIN, 16));
       l4.setForeground(Color.WHITE);
       l4.setBounds(360, 170, 200, 18);
       p1.add(l4);   
       
        Timer t = new Timer(1, new ActionListener(){
           public void actionPerformed(ActionEvent ae){
               if(!typing){
                   l4.setText("Active Now");
               }
           }
       });
       
       t.setInitialDelay(2000);

       a1 = new JPanel();
       a1.setBounds(5, 200, 440, 450);
       a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    
       
       JScrollPane sp = new JScrollPane(a1);
       sp.setBounds(5, 200, 440, 450);
       sp.setBorder(BorderFactory.createEmptyBorder());
             
       ScrollBarUI ui = new BasicScrollBarUI(){
           protected JButton createDecreseButton(int orientation){
               JButton button = super.createDecreaseButton(orientation);
               button.setBackground(new Color(7,94,84));
               button.setForeground(Color.WHITE);
               this.thumbColor = new Color(7,94,84);
               return button;
           }
            protected JButton createIncreseButton(int orientation){
               JButton button = super.createDecreaseButton(orientation);
               button.setBackground(new Color(7,94,84));
               button.setForeground(Color.WHITE);
               this.thumbColor = new Color(7,94,84);
               return button;
           }
       };
       
       sp.getVerticalScrollBar().setUI(ui);
       f1.add(sp);
       
       t1 = new JTextField();
       t1.setBounds(5, 655, 310, 40);
       t1.setFont(new Font("SERIF", Font.PLAIN, 16));
       f1.add(t1);
       
       t1.addKeyListener(new KeyAdapter(){
           public void keyPressed(KeyEvent ke){
               l4.setText("typing...");
               
               t.stop();
               
               typing = true;
           }
           
           public void keyReleased(KeyEvent ke){
               typing = false;
               
               if(!t.isRunning()){
                   t.start();
               }
           }
       });
       
       b1 = new JButton("Send");
       b1.setBounds(320, 655, 123, 40);
       b1.setBackground(new Color(255, 204, 51));
       b1.setForeground(Color.WHITE);
       b1.setFont(new Font("SERIF", Font.PLAIN, 16));
       b1.addActionListener(this);
       f1.add(b1);
        
       f1.getContentPane().setBackground(new Color(51, 204, 255));
       f1.setLayout(null);
       f1.setSize(450, 700);
       f1.setLocation(1100, 200); 
       f1.setUndecorated(true);
       f1.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        
        try{
            String out = t1.getText();
            
            JPanel p2 = formatLabel(out);
            
            a1.setLayout(new BorderLayout());
            
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            
            a1.add(vertical, BorderLayout.PAGE_START);
            
            //a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(255,255,0));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l1);
        p3.add(l2);
        return p3;
    }
    
    public static void main(String[] args){
        new Client().f1.setVisible(true);
        
        try{
            
            s = new Socket("127.0.0.1", 6001);
            din  = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            String msginput = "";
            
	    while(true){
                a1.setLayout(new BorderLayout());
	        msginput = din.readUTF();
            	JPanel p2 = formatLabel(msginput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);
                
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f1.validate();
            }
            
        }catch(Exception e){}
    }    
}
