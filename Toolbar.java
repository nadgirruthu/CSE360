package project1;

import java.io.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.*;

import javax.swing.*;

public class Toolbar extends JPanel implements ActionListener{
	//JFileChooser chooser = new JFileChooser();
	private JButton select;
	private JButton format;
	private JButton save;
	private final JLabel saved;
	private String line;
	private String linetemp;
	private String temp;
	private String formatted;
	JFileChooser chooser = new JFileChooser();
	private String counter ="";
	private FormPanel pannel;
	private FormPanel lp;
	private FormPanel blankLines;
	private FormPanel aveLineLength;
	private FormPanel wordsPerLine;
    int count;
    int lines;
	//BufferedReader output = null;
	BufferedReader input = null;
	PrintWriter output = null;


	
	File file = null;
	
	private StringListener textListener; 
	
	public Toolbar(){
		
		setBorder(BorderFactory.createEtchedBorder());
		
		select = new JButton("Select file");
		format = new JButton("Format");
		save = new JButton ("Save file");
		
		saved = new JLabel ("File saved in the program's folder");
		saved.setVisible(false);
		
		select.addActionListener(this);
		format.addActionListener(this);
		save.addActionListener(this);
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(select);
		add(format);
		add(save);
		add(saved);
		
		
	}
	
	public void setStringListener(StringListener listener){
		
		this.textListener = listener;
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		JButton clicked = (JButton)e.getSource();
		if (clicked == select){
			
			if(textListener != null){
				
				saved.setVisible(false);
				
				// open a file
				int fileVal = chooser.showOpenDialog(this);
				
				if(fileVal == JFileChooser.APPROVE_OPTION)  {   
					file = chooser.getSelectedFile();    
				}

			
				// display the contant on the file 
				
				textListener.clearText();
				try {
					input = new BufferedReader(new FileReader(file));
				} catch (FileNotFoundException e1) {

					e1.printStackTrace();
				}
				
				textListener.clearText();
				
				try {
					line = input.readLine();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				while(line != null){
				textListener.textEmitted1(line + "\n");
				//System.out.println(line + "\n");
				temp = line;
				  try {
					line = input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				
				//final JFileChooser fc = new JFileChooser();
				
				//fc.showOpenDialog(this);
				
				//textListener.textEmitted("input file\n");
				
			}
		}
		if (clicked == format){
			
			if(textListener != null){
				
				textListener.clearOutput();
				
				//System.out.println(line);
				if ( temp != null ){
					try {
						output = new PrintWriter(new FileWriter("result.txt"));
						
						
						
						//output = new BufferedReader(new FileReader("result.txt"));
					} catch (FileNotFoundException e2) {
						
						e2.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// counting how many words in the file
					try(Scanner sc = new Scanner(new FileInputStream(file))){    
					    count=0;
					    
					    while(sc.hasNext()){
					    	
					        sc.next();
					        
					        count++;
					    }
					   
					//System.out.println("Number of words: " + count);
					    // Display number of words in the app
					pannel.updateWordProcessedField (String.valueOf(count));
					
					FileReader fr = new FileReader(file);
	    		    LineNumberReader linenumber = new LineNumberReader(fr);


	    	            while (linenumber.readLine() != null){
	    	        	lines++;
	    	            }

	    	            //System.out.println("Total number of lines : " + lines);
	    	            
	    	            lp.updateLinesProcessedField(String.valueOf(lines));
	    	            
	    	            linenumber.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						input = new BufferedReader(new FileReader(file));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						line = input.readLine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					String tempText = line;
					System.out.println(tempText);

					////////
					
					
					
					
				///////// count how many blank line 
					
					//final BufferedReader br = new BufferedReader(new FileReader(file));
					//String line;
					int empty = 0;
					try {
						while ((line = input.readLine()) != null) {
						  if (line.trim().isEmpty()) {
						    empty++;
						  }
						}
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
    	            blankLines.updateBlankLineField(String.valueOf(empty));

					//System.out.println(empty);
					
					//System.out.println(tempText);

    	            formatted = "";
    	            linetemp = textListener.returnText();
    	            linetemp = linetemp.replace("\n", " ").replace("\r", " ");
    	            int length = linetemp.length();
    	            int i = 0, j = 80, k = 0, l = 0, lastSpace = 0;
    	            
    	            while (i < length)
    	           	{
    	            	for (i = k; i < j && i < length; i++)
    	            	{
    	            		if (linetemp.charAt(i) == ' ')
    	            			lastSpace = i;
    	            	}
    	            	
    	            	for (k = l; k < lastSpace; k++)
    	            		formatted = formatted +  linetemp.charAt(k);
    	            	
    	            	formatted = formatted + "\n";
    	            	l = k;
    	            	j = lastSpace + 80;
    	            }
    	            
    	            // average line length
    	            float avegLineLength = 0, totalLines = 0;
    	            
    	            for (int m = 0; m < length; m++)
    	            {
    	            	if (formatted.charAt(m) == '\n')
    	            		totalLines++;
    	            }
    	            
    	            avegLineLength = (float)length / totalLines;
					aveLineLength.updateAveLineLengthField(String.valueOf(avegLineLength));
					
					// average words per line
					float avegWordsPerLine, words = 0;
					for (int n = 0; n < length; n++)
    	            {
    	            	if (formatted.charAt(n) == ' ')
    	            		words++;
    	            }
					
					words++;
    	            
    	            avegWordsPerLine = words / (float) totalLines;
					wordsPerLine.updateWordsPerLine(String.valueOf(avegWordsPerLine));
					//////// print the text formatted after deleting the blank lines.
					try {
						input = new BufferedReader(new FileReader(file));
					} catch (FileNotFoundException e1) {

						e1.printStackTrace();
					}
					
					try {
						line = input.readLine();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
				
						      output.println(formatted);
						      
						      System.out.println(formatted);

						      textListener.textEmitted(formatted+"\n");
					

					  try {
							line = input.readLine();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						}
				/////////////////////////////////////////////////////
					
				 

					
					
					
					
					
				}
				
				
				
			}
			
		
		if (clicked == save){
			if(textListener != null){
				
			saved.setVisible(true);
			String temp = "";
			
			try
			{
				BufferedWriter writer = new BufferedWriter(new FileWriter("formatted-file.txt"));
				for (int i = 0; i < formatted.length(); i++)
				{
					if (formatted.charAt(i) == '\n')
					{
						writer.write(temp);
						writer.newLine();
						temp = "";
					}
					else
						temp = temp + formatted.charAt(i);
					
				}
				writer.close();
			} catch (IOException e4) {}
			
			}
		}
		
	}

	public void setWord(String counter) {
	    this.counter = String.valueOf(count);
	}
	
	public void setPanel (FormPanel panel) {
		pannel = panel;
	}
	public void setLine (FormPanel lp){
		
		this.lp = lp;
		
	}
	public void setBlankline (FormPanel blankLines){
		
		this.blankLines = blankLines;
		
	}
	
	public void setAveLineLength (FormPanel aveLineLength){
		
		this.aveLineLength = aveLineLength;
		
	}
	public void setWordsPerLine (FormPanel wordsPerLine)
	{
		this.wordsPerLine = wordsPerLine;
	}
}
