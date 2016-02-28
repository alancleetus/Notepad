import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
//make a list of fonts to toggle through
public class NotePad
{
   private JFrame frame;
   private JPanel mainPanel;
   private JPanel menuPanel;
   private JTextArea textBox;
   private JPanel modePanel;
   private String fileName = "~New File~";
   public NotePad()
   {
      frame = new JFrame();
      //Frame Edit
      {
         frame.setLocation( 500, 200 );   //location on monitor
         frame.setTitle( fileName );             //title of frame
         frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); //what happens when the box is closed
         frame.setSize( new Dimension( 600, 600 ) );
         //frame.setResizable( false );
      }
      
      mainPanel = new JPanel(new BorderLayout());
      mainPanel.setBackground( new java.awt.Color( 122,183,179 ) );
      
      menuPanel = new JPanel(new GridLayout(5,1));
      menuPanel.setBackground( new java.awt.Color( 122,183,179 ) );
      menuPanel.setPreferredSize( new Dimension(112,500 ) );
      addMenuElements();
      
      textBox = new JTextArea();
      textBox.setPreferredSize( new Dimension(480,500 ) );
      textBox.setEditable( true );
      textBox.setLineWrap( true );
      
      
      modePanel = new JPanel(new GridLayout(5,1));
      modePanel.setBackground( new java.awt.Color( 122,183,179 ) );
      modePanel.setPreferredSize( new Dimension(112,500 ) );
      addModeElements();
      
      mainPanel.add( menuPanel, BorderLayout.WEST );
      mainPanel.add( textBox, BorderLayout.CENTER);
      mainPanel.add( modePanel, BorderLayout.EAST );
      frame.add( mainPanel, BorderLayout.CENTER );
      
      frame.setVisible( true );
   }
   
   private void addMenuElements()
   {
      Dimension buttonSize = new Dimension(100,100);
      Color c = new java.awt.Color(28,28,28);
      Font font = new Font("Century Gothic", Font.ITALIC, 15);
      
      //newButton
      
      JButton openNew = new JButton();
      openNew.setText("New");
      openNew.setSize(buttonSize);
      openNew.setForeground(Color.WHITE);
      openNew.setBackground(c);
      openNew.setFont(font);
      
      openNew.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                  textBox.setText("");
                  frame.setTitle( fileName);  
               }
            }
         );
      
      menuPanel.add(openNew);
      
      //openButton
      JButton open = new JButton();
      open.setText("Open");
      open.setSize(buttonSize);
      open.setForeground(Color.WHITE);
      open.setBackground(c);
      open.setFont(font);
      
      open.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                  boolean flag = true;
                  
                  Scanner fileIn = new Scanner(System.in);                  
                  while(flag)
                  {
                     String fileName = JOptionPane.showInputDialog(null, "Enter File Name:");
                     File f = new File(fileName);
                     try{
                        if(f.exists())
                        { 
                           flag=false;
                           fileIn = new Scanner(f);
                           frame.setTitle( fileName);
                        }
                     }
                     catch(Exception FileNotFound)
                     {
                        System.out.println("ERROR");
                     }
                  }
                  
                  String s= "";
                  while(fileIn.hasNextLine())
                  {  
                     s+=fileIn.nextLine();
                  }
                      
                  textBox.setText(s);
               }
            }
         );
      
      menuPanel.add(open);
      
   
      //saveButton
      JButton save = new JButton();
      save.setText("Save");
      save.setSize(buttonSize);
      save.setForeground(Color.WHITE);
      save.setBackground(c);
      save.setFont(font);
      
      save.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                  if(fileName == null) 
                  {
                  
                     fileName = JOptionPane.showInputDialog(null, "Enter File Name:");
                     File f = new File(fileName+".txt");
                     if(f.exists())
                     {
                        frame.setTitle( fileName);
                        int choice = JOptionPane.showConfirmDialog(null,
                           "This file already exsits.Are you sure you want to rewrite this file?");
                        
                        if(choice == JOptionPane.YES_OPTION)
                        {
                           writeToFile(f, textBox.getText());
                        }
                     }
                     else
                     {
                        writeToFile(f, textBox.getText());
                     }
                  }
                  else  
                  {
                     File f = new File(fileName+".txt");
                     
                     writeToFile(f, textBox.getText());
                  }
                    
               }
            }
         );
      
      menuPanel.add(save);
         
      //saveAsButton
      JButton saveAs = new JButton();
      saveAs.setText("Save As");
      saveAs.setSize(buttonSize);
      saveAs.setForeground(Color.WHITE);
      saveAs.setBackground(c);
      saveAs.setFont(font);
      
      saveAs.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                  fileName = JOptionPane.showInputDialog(null, "Enter File Name:");
                  File f = new File(fileName+".txt");
                  if(f.exists())
                  {
                     int choice = JOptionPane.showConfirmDialog(null,
                        "This file already exsits.Are you sure you want to rewrite this file?");
                        
                     if(choice == JOptionPane.YES_OPTION)
                     {
                        writeToFile(f, textBox.getText());
                     }
                  }
                  else
                  {
                     writeToFile(f, textBox.getText());
                  }
                  frame.setTitle( fileName+".txt");  
               }
            }
         );
      
      menuPanel.add(saveAs);
      
        
      //fontSizeButton
      JButton fontSize = new JButton();
      fontSize.setText("Font");
      fontSize.setSize(buttonSize);
      fontSize.setForeground(Color.WHITE);
      fontSize.setBackground(c);
      fontSize.setFont(font);
      
      fontSize.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                  String fontName = JOptionPane.showInputDialog(null, "Font Name:");
                  String textSize = JOptionPane.showInputDialog(null, "Test Size:");
                  
                  Font newFont= new Font("Century Gothic", Font.PLAIN, 12);
                  if(fontName.length()>0 && textSize.length()>0)
                  {
                     newFont = new Font(fontName, Font.PLAIN, Integer.parseInt(textSize));
                  }
                  else
                  { 
                     if(textSize.length()>0)//has textSize
                     {
                        newFont = new Font("Century Gothic", Font.PLAIN, Integer.parseInt(textSize));
                     }
                     else
                     {
                        newFont = new Font(fontName, Font.PLAIN, 12);
                     }
                  }
                  
                  textBox.setFont(newFont);
               }
            }
         );
      
      menuPanel.add(fontSize);
   }
   
   
   private void addModeElements()
   {
      Dimension buttonSize = new Dimension(100,100);
      Color c = new java.awt.Color(122,183,179);
      Font font = new Font("Century Gothic", Font.ITALIC, 15);
      
      JLabel label = new JLabel("Modes:");
      
      label.setFont(font);
      modePanel.add(label);
      
      //schoolButton
      
      JRadioButton schoolMode = new JRadioButton();
      schoolMode.setText("School");
      schoolMode.setForeground(Color.BLACK);
      schoolMode.setBackground(c);
      schoolMode.setFont(font);
      
      schoolMode.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                  textBox.setFont(new Font("Times New Roman", Font.PLAIN, 12));
               }
            }
         );
         modePanel.add(schoolMode);
   }

   private void writeToFile(File f, String output)
   {
      try
      {
         PrintStream outFile = new PrintStream(f);
         outFile.println(output);
      }
      catch(Exception e)
      {
         System.out.println("3r40r");
      }
   
   }         
      
   public static void main(String[] args)
   {
      NotePad n = new NotePad();
   }
         
}