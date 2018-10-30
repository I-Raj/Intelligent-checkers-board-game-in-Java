package checkers;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class view extends JFrame implements ActionListener {
private static final long serialVersionUID = 1L;
private Board check;
private JButton Hint,Start;
private static JLabel objMessage,gameMessage,winnerMessage;

public view(Board check) {
	// TODO Auto-generated constructor stub
	 this.check=check;
     setSize(800,430);
     setLayout(null);
     getContentPane().setBackground(Color.PINK);
     setVisible(true);
     setLocationRelativeTo(null);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     add(check);
     SetupLabels();
	 Setupbuttons();

}

public void SetupLabels()  {
   objMessage = new JLabel("", JLabel.CENTER);
   objMessage.setFont(new Font("Serif", Font.BOLD, 14));
   objMessage.setForeground(Color.BLUE);
	objMessage.setBounds(420, 250, 350, 100);
	add(objMessage);
	
	gameMessage = new JLabel("", JLabel.CENTER);
	gameMessage.setFont(new Font("Serif", Font.BOLD, 14));
	gameMessage.setForeground(Color.BLUE);
	gameMessage.setBounds(420, 200, 350, 100);
	add(gameMessage);
	
	winnerMessage = new JLabel("", JLabel.CENTER);
	winnerMessage.setFont(new Font("Serif", Font.BOLD, 14));
	winnerMessage.setForeground(Color.BLUE);
	winnerMessage.setBounds(420, 300, 350, 100);
	add(winnerMessage);
}


public void Setupbuttons(){
	Start=new JButton("START GAME");
	Start.setBounds(525,100,150,30);
	add(Start);
	
	Hint=new JButton("HINT");
	Hint.setBounds(525,150,150,30);
	add(Hint);
	
	Start.addActionListener(this);
	Hint.addActionListener(this);
	gameMessage.setText("Click Start to Begin Game....");
	}


@Override
public void actionPerformed(ActionEvent e) {
	Object src=e.getSource();
    if(src==Start){
    	check.startgame();
    	 gameMessage.setText("Game In Progress......");
   	     objMessage.setText("Red: Make your move");
    	
		}
    if(src==Hint) {
    	check.getgamehint();
     }
 }

public static void setmessage(String label,String msg){
	if(label=="winnerMessage") {
		winnerMessage.setText(msg);
	}
	if(label=="objMessage") {
		objMessage.setText(msg);
	}
	if(label=="gameMessage") {
		gameMessage.setText(msg);
		
	}
}
}


