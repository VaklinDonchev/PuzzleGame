import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.File;

public class Puzzle_Game implements ActionListener{
	
	TimerPanel timer = new TimerPanel();
	JFrame frame = new JFrame();
	JPanel game_panel = new JPanel();
	
	File dir = new File("D:\\STUDY\\Programming\\JAVA\\eclipse-java-2021-09-R-win32-x86_64\\workspace\\PuzzleGame\\src\\images\\");
	 
	boolean checker= true;
	JButton[] array_of_buttons = new JButton[9];
	Icon[] array_of_icons = new Icon[8];
	
	static final int puzzle_panel_widht = 400;
	static final int puzzle_panel_height = 300;
	
	Point[] array_of_points = new Point[9];
	
	static int chek = 0;//new change made static if anything wrong remove static 
	static int in   = 0;
	static int moves= 0;
	
		Puzzle_Game(){
		create_Points() ;
		create_Buttons();
		
		//*************GamePanel*****************//
		game_panel.setPreferredSize(new Dimension(puzzle_panel_widht,puzzle_panel_height));
		game_panel.setBounds(0,55,puzzle_panel_widht,puzzle_panel_height);
		game_panel.setBackground(Color.GRAY);
		game_panel.setLayout(null);

		//game_panel.setLayout(new GridLayout(3,3));
		
		//*************END_OF_GamePanel*****************//
		
		//*************FRAME*****************//
		
		frame.add(game_panel);
		frame.add(timer);
		frame.setTitle("Briliant Puzzle by Vaklin");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLayout(null);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setSize(400,400);
		
		frame.setVisible(true);
		//*************END_OF_FRAME*****************//
		cheking_buttons();
		chek_if_won();
		find_empty_button();
		chek_solvable() ;
		//won();
	}

	public void create_Points() {
		
		for(int x =0 ;x<=266;x=x+133)
		{
			for(int y =0 ;y<=200;y=y+100)
			{ 
				array_of_points[in]=(new Point(x,y));
				
				in++;
			}
		}
	}
	public JButton[] create_Buttons() {
		int num=0;
		 File[] directoryListing = dir.listFiles(); 
		
		  for (File child : directoryListing) {
		    	if(num<8) {
			  	array_of_buttons[num]= new JButton(String.valueOf(num+1),new ImageIcon(((new ImageIcon(String.valueOf(child))).getImage()).getScaledInstance(133, 100, java.awt.Image.SCALE_SMOOTH)));
			  	array_of_buttons[num].setDisabledIcon(new ImageIcon(((new ImageIcon(String.valueOf(child))).getImage()).getScaledInstance(133, 100, java.awt.Image.SCALE_SMOOTH)));
				array_of_buttons[num].setHorizontalTextPosition(JButton.CENTER);
				array_of_buttons[num].setVerticalTextPosition(JButton.CENTER);
				array_of_buttons[num].setForeground(Color.red);
				array_of_buttons[num].addActionListener(this);
				num++;
		    }
		    	else {
		    		array_of_buttons[8]= new JButton("0");
		    		game_panel.add(array_of_buttons[8]);
		    	}
		    	
		  }
		   shuffle_parts();
		   return array_of_buttons;
	}
	
	public void shuffle_parts() { 
		
		
		  
			List<Point> intList = Arrays.asList(array_of_points);
		
			Collections.shuffle(intList);
			
			intList.toArray(array_of_points);
			
			for(int i =0;i<9;i++) {
				int x =(int) array_of_points[i].getX();
				int y =(int) array_of_points[i].getY();
				 array_of_buttons[i].setBounds(x,y,133,100);
				game_panel.add(array_of_buttons[i]);	
		}
			int chek=chek_solvable();
			if((chek%2)==0) {
				
				System.out.println(chek+" they are even  do nothing!");
			}	
			else {
				System.out.println(chek +" they are not even  so i called shuffle again:)");
				shuffle_parts();
			}
			
		}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i = 0; i<8; i++) {
			if(e.getSource()== array_of_buttons[i]&&array_of_buttons[i].getText()!="0") {
				
				
				swap_locations(array_of_buttons[i],i);
				
			}
		}
			
			if(chek==0) {
				chek=1;
			timer.startTimer();
			}
		
	}
	
	public int chek_solvable() 
	{
		ArrayList<Integer> str = new ArrayList<Integer>();
		int inversion=0;
		
	  for(int y=0;y<=200;y=y+100)
		{	
			for(int x=0;x<=266;x=x+133)
			{
				for (int i=0;i<9;i++) {	
					if(array_of_buttons[i].getLocation().x==x &&array_of_buttons[i].getLocation().y==y)
					{
						
						
						str.add(Integer.valueOf(array_of_buttons[i].getText()));
					}
				}
			}
			
		}
	  
	  for (int i=0;i<8;i++) {
		  
		  for(int ii=i;ii<8;ii++) {
			  if(str.get(i)> str.get(ii+1)&&str.get(ii+1)!=0) {
				  inversion++;
				 
			  }
		  }
	  }
	  
	
	  return inversion;
	
	}
	
	public void swap_locations(JButton clicked_button,int temp_index) {
		checker=true;
		moves++;
		timer.winLabel.setText("Moves: "+moves);
		 int i=0;
		 do {
			
			 if(array_of_buttons[i].getText()=="0") {
				 
				 int button_X_Pos = clicked_button.getLocation().x;
				 int button_Y_Pos = clicked_button.getLocation().y;
				 int button_X_Pos2 = array_of_buttons[i].getLocation().x;
				 int button_Y_Pos2 = array_of_buttons[i].getLocation().y;
				clicked_button.setBounds(button_X_Pos2,button_Y_Pos2,133,100);
				array_of_buttons[i].setBounds(button_X_Pos,button_Y_Pos,133,100);
			
				checker=false;
				break;
				}
			 i++;
			
		 }while(checker);

		
		cheking_buttons();
		chek_if_won();
		
		}
	
		
 	public void cheking_buttons() {
 		Point zero_point=find_empty_button();
 		
		for(int i = 0; i<9; i++) {
			array_of_buttons[i].setEnabled(false);
			
			//enables for the right ones
			if(zero_point.x+133==array_of_buttons[i].getLocation().x&&zero_point.y==array_of_buttons[i].getLocation().y) {
				array_of_buttons[i].setEnabled(true);
			}
			//enables for the left ones
		else if(zero_point.x-133==array_of_buttons[i].getLocation().x&&zero_point.y==array_of_buttons[i].getLocation().y) {
				array_of_buttons[i].setEnabled(true);
			}
			//enables for the below ones
		else if(zero_point.x==array_of_buttons[i].getLocation().x&&zero_point.y-100==array_of_buttons[i].getLocation().y) {
			array_of_buttons[i].setEnabled(true);
		}
			//enables for the upper ones
		else if(zero_point.x==array_of_buttons[i].getLocation().x&&zero_point.y+100==array_of_buttons[i].getLocation().y) {
			array_of_buttons[i].setEnabled(true);
		}
				
			}}
		
 	public Point find_empty_button()
	{
		
 		int tempx;
		int tempy;
		
		for(int i = 0; i<9; i++) {
			
			if(array_of_buttons[i].getText() =="0") {
				
				tempx=array_of_buttons[i].getLocation().x;
				tempy=array_of_buttons[i].getLocation().y;
				Point temp_point = new Point(tempx,tempy);
				
				return temp_point;
			}
			}
		return (new Point(0,0));
	}
	
	public void chek_if_won() {
		
		 int matches= 0;
		 Point zero_point=find_empty_button();
	
		if(zero_point.x==266&&zero_point.y==200) {
			for(int y=0;y<=200;y=y+100) {
				for(int x=0;x<=266;x=x+133) {
					for(int i=0;i<9;i++) {
					if(array_of_buttons[i].getLocation().x==x &&array_of_buttons[i].getLocation().y==y) {
						
						if(Integer.valueOf(array_of_buttons[i].getText())==(matches+1)) {
							matches++;
							if(matches==8)
							{   
								
								won();
								
							}
						}
					}
				  }
				}
				
			}
		}}
	
	public void won() {
		timer.timer_started=false;
		timer.winLabel.setForeground(Color.green);
		timer.timeLabel.setForeground(Color.green);
		
		timer.stopTimer();
		array_of_buttons[8].setIcon(new ImageIcon(((new ImageIcon(String.valueOf("D:\\STUDY\\Programming\\JAVA\\eclipse-java-2021-09-R-win32-x86_64\\workspace\\PuzzleGame\\src\\image_part_009.jpg"))).getImage()).getScaledInstance(133, 100, java.awt.Image.SCALE_SMOOTH)));
		array_of_buttons[8].setDisabledIcon(new ImageIcon(((new ImageIcon(String.valueOf("D:\\STUDY\\Programming\\JAVA\\eclipse-java-2021-09-R-win32-x86_64\\workspace\\PuzzleGame\\src\\image_part_009.jpg"))).getImage()).getScaledInstance(133, 100, java.awt.Image.SCALE_SMOOTH)));
		array_of_buttons[3].setText("You");
		array_of_buttons[3].setFont(new Font("Arial", Font.PLAIN, 50));
		
		array_of_buttons[3].setEnabled(true);
		array_of_buttons[3].setForeground(Color.green);
		array_of_buttons[4].setText("Win");
		array_of_buttons[4].setFont(new Font("Arial", Font.PLAIN, 50));
		array_of_buttons[4].setEnabled(true);
		array_of_buttons[4].setForeground(Color.green);
		array_of_buttons[5].setText("Bro");
		array_of_buttons[5].setFont(new Font("Arial", Font.PLAIN, 50));
		
		array_of_buttons[5].setEnabled(true);
		array_of_buttons[5].setForeground(Color.green);
		
	}
	
}
