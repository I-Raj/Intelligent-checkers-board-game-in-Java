package checkers;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
private static final long serialVersionUID = 1L;
private Player R,B;
private Player Currentplayer,Nextplayer;
private Tile [][] T=new Tile[8][8];
private ArrayList <Piece> P=new ArrayList<Piece>();
private int selectedrow=-1,selectedcol=-1;
private int highlightedrow=-1,highlightedcol=-1;
private Boolean gameinprogress=false;


public static class Moves{
    int from_Row, from_Col;
    int to_Row, to_Col;

    Moves(int r1, int r2, int c1, int c2) {
        from_Row = r1;
        from_Col = c1;
        to_Row = r2;
        to_Col = c2;
    }
}

private Moves[] legalMovesforplayer ;
private Moves[] legalJumpsforplayer=null ;

public Board() {
	R=new Player("Red");
    B=new Player("Black");
    Boardsetup();
    Setcurrentplayer();
    setBounds(19,39,320,320);
	addMouseListener(this);
	
 }

public void Boardsetup(){
	    for(int i=0;i<8;i++){
		for(int j=0;j<8;j++){
			T[i][j]=new Tile(i,j,false);
		    T[i][j].setposition();
		    if((i%2)==(j%2)){
		     if((i>4)){         //RED on top 3 rows
		       Piece reds=new Piece(R,T[i][j],Color.RED,i,j);
		       P.add(reds);
		       R.pieceadded();
		      }
		      if((i<3)){       //BLACK on bottom 3 rows 
			   Piece blacks=new Piece(B,T[i][j],Color.BLACK,i,j);
	           P.add(blacks);
		       B.pieceadded();
		      }
		     }
		    }
		   }
        }

@Override
   public void paintComponent(Graphics g){
	    super.paintComponent(g);
         //Print Board
	    for(int i=0;i<8;i++) {
		 for(int j=0;j<8;j++){
		  if((i%2)==(j%2)){
		       g.setColor(Color.white);
	        }
	     else{
		       g.setColor(Color.gray);
	        }
		 g.fillRect((i*40), (j*40), 40, 40);
		   }
		}
	    //Print pieces
	    for(int i=0;i<8;i++){
	    	  for(int j=0;j<8;j++){
		      if(T[i][j].isoccupied()==true){
			  Piece p=T[i][j].getoccupant(P);
			  g.setColor(p.getcolor());
			  g.fillOval((p.getpiececol()*40+10), (p.getpiecerow()*40)+10, 20, 20);    //Print pieces
			  if(p.isking()) {         //If piece is king
			           g.setColor(Color.cyan);
			    		g.drawString("K", (p.getpiececol()*40)+16, (p.getpiecerow()*40)+24);
			    		//g.drawString("K", (p.getpiececol()*40)+17, (p.getpiecerow()*40)+25);
			    		
			         }
		        }
	    	}
		  }
	    g.setColor(Color.CYAN);
	    if(legalMovesforplayer!=null){
	    int len = legalMovesforplayer.length;
        for (int i = 0; i <len; i++){
            g.drawRect( (legalMovesforplayer[i].from_Col)*40, legalMovesforplayer[i].from_Row*40, 40, 40);
            g.drawRect( 1+legalMovesforplayer[i].from_Col * 40,1+ legalMovesforplayer[i].from_Row * 40, 40, 40);
            g.drawRect( 2+legalMovesforplayer[i].from_Col * 40,2+ legalMovesforplayer[i].from_Row * 40, 40, 40);
              }
	    }
        
	    if (selectedrow>=0) {
            g.setColor(Color.BLACK);
            g.drawRect(selectedcol* 40, selectedrow * 40, 40,40);
            g.drawRect(1 + selectedcol * 40, 1+ selectedrow * 40, 40,40);
            g.drawRect(2 + selectedcol * 40, 2+ selectedrow * 40, 40,40);
            
            g.setColor(Color.green);
            int len2 = legalMovesforplayer.length;
            for (int j = 0; j <len2; j++)
            {
                if (legalMovesforplayer[j].from_Col == selectedcol&& legalMovesforplayer[j].from_Row == selectedrow) {
                    g.drawRect( legalMovesforplayer[j].to_Col * 40,legalMovesforplayer[j].to_Row * 40, 40, 40);
                    g.drawRect( 1+legalMovesforplayer[j].to_Col * 40,1+legalMovesforplayer[j].to_Row * 40, 40, 40);
                    g.drawRect( 2+legalMovesforplayer[j].to_Col * 40,2+legalMovesforplayer[j].to_Row * 40, 40, 40);
                }
            }
          }
	    
	    if(highlightedrow>=0) {
	    	 g.setColor(Color.YELLOW);
	            g.drawRect( highlightedcol* 40, highlightedrow * 40, 40,40);
	            g.drawRect(1+ highlightedcol * 40, 1 + highlightedrow * 40, 40,40);
	            g.drawRect( 2+highlightedcol * 40, 2 + highlightedrow * 40, 40,40);
	    }
	   }
	    
  public Boolean checkgameover() {
  	if(R.getnumpieces()==0 || B.getnumpieces()==0){
  		return true;
  	}
  	return false;
  }


  public void startgame()
  {
	  gameinprogress=true;
	  Setcurrentplayer();
 	  legalMovesforplayer=Currentplayer.legalJumps(P,T,Nextplayer);
 	  if(legalMovesforplayer==null) {
 		 legalMovesforplayer=Currentplayer.legalMoves(P,T,Nextplayer);
 	  }
 	  
	  repaint();
  }
  
  public void getgamehint()
  {
	  int entry[]=new int[100];
	  int fromrow,torow,fromcol,tocol;
	  int highrow[]=new int[100];
	  int highcol[]=new int[100];
	  int max=0,index=0;
	  // hint for jumps
	  legalMovesforplayer=Currentplayer.legalJumps(P,T,Nextplayer);
      if(legalMovesforplayer!=null) {
    	for(int i=0;i<legalMovesforplayer.length;i++) {
    		  Color c;
				if(Currentplayer.getplayer()=="Red") {
					c=Color.RED;
				}
				else {
					c=Color.BLACK;
				}
				Piece p=new Piece(Currentplayer,c);
				fromrow=legalMovesforplayer[i].from_Row;
				fromcol=legalMovesforplayer[i].from_Col;
				torow=legalMovesforplayer[i].to_Row;
				tocol=legalMovesforplayer[i].to_Col;
				
				Piece piece=T[fromrow][fromcol].getoccupant(P);
				if(piece.isking()) {
					p.isking=true;
				}else {
					p.isking=false;
					}
			    p.settile(T[torow][tocol]);
			    p.Makeking(Currentplayer.getplayer());
			    settilefornextjump(fromrow,torow,fromcol,tocol);
				
                legalJumpsforplayer=Currentplayer.legalJumpsforhint(P,T,p,torow,tocol,Nextplayer);
				settileback(legalMovesforplayer[i].from_Row,legalMovesforplayer[i].to_Row,legalMovesforplayer[i].from_Col,legalMovesforplayer[i].to_Col);
				highrow[i]=torow;
				highcol[i]=tocol;
				while(legalJumpsforplayer!=null){
					entry[i]++;
					torow=legalJumpsforplayer[0].to_Row;
					tocol=legalJumpsforplayer[0].to_Col;
				    fromrow=legalJumpsforplayer[0].from_Row;
					fromcol=legalJumpsforplayer[0].from_Col;
					highrow[i]=torow;
					highcol[i]=tocol;
					p.settile(T[torow][tocol]);
					settilefornextjump(fromrow,torow,fromcol,tocol);
					p.Makeking(Currentplayer.getplayer());
					legalJumpsforplayer=Currentplayer.legalJumpsforhint(P,T,p,torow,tocol,Nextplayer);
					settileback(fromrow,torow,fromcol,tocol);
					}
			}
    	 for(int k=0;k<entry.length;k++){
			 if(max<entry[k]) {
				 max=entry[k];
				 index=k;
			 }
			 highlightedrow=highrow[index];
			 highlightedcol=highcol[index];
    	  }
      }
      
      //moves
      else if(legalMovesforplayer==null) {
 		  legalMovesforplayer=Currentplayer.legalMoves(P,T,Nextplayer);
 		  int length=legalMovesforplayer.length;
 		  if(Currentplayer.getplayer()=="Red") {
 		  highlightedrow=legalMovesforplayer[0].to_Row;
          highlightedcol=legalMovesforplayer[0].to_Col;
 		  }
 		  else
 		  {
 			 highlightedrow=legalMovesforplayer[length-1].to_Row;
 	         highlightedcol=legalMovesforplayer[length-1].to_Col; 
 		  }
        }
 	 selectedrow=-1;
 	 repaint();
      }
  
  
 public void Setcurrentplayer() {
	if(R.iscurrentplayer()){
		Currentplayer=R;
		Nextplayer=B;
	}
	else{
		Currentplayer=B;
	   Nextplayer=R;
	}
}

public void Declarewinner(){
	view.setmessage("gameMessage", "GAME OVER!!");
	if (R.getnumpieces()>B.getnumpieces()){
		view.setmessage("objMessage", "RED WON!!");
	}
	else if(R.getnumpieces()==B.getnumpieces()){
		view.setmessage("objMessage", "Its a draw Match!!");
	}
	else {
		view.setmessage("objMessage", "BLACK WON!!");
	}
}



@Override
public void mouseClicked(MouseEvent e) {
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	if(gameinprogress==true)
	{
	 int col = (e.getX()) / 40;
     int row = (e.getY()) / 40;
     if (col >= 0 && col < 8 && row >= 0 && row < 8){
    	tile_clicked(row,col);
     }
	}
     if(highlightedrow>=0)
	    {
	    	highlightedrow=-1;
	    	repaint();
	    }
 }
    
  
public void tile_clicked(int row, int col) {
    int len = legalMovesforplayer.length;
    for (int j = 0; j < len;j++) {
        if (legalMovesforplayer[j].from_Row == row&& legalMovesforplayer[j].from_Col == col) {
            selectedrow = row;
            selectedcol = col;
            view.setmessage("objMessage", Currentplayer.getplayer()+" : Choose the square to move piece to.");
            repaint();
            return;
        }}

    if (selectedrow < 0) {
    	view.setmessage("objMessage", Currentplayer.getplayer()+" : Choose the piece you want to move.");
        return;
            }
        
    int length = legalMovesforplayer.length;
        for (int i = 0; i < length;i++) {
        if (legalMovesforplayer[i].from_Row == selectedrow && legalMovesforplayer[i].from_Col == selectedcol
            && legalMovesforplayer[i].to_Row == row&& legalMovesforplayer[i].to_Col == col) {
        	  Piece p1=T[selectedrow][selectedcol].getoccupant(P);
      	      T[selectedrow][selectedcol].setisoccupied(false);
      	      if(p1!=null && Currentplayer.belongstoCurrentplayer(p1)){
      	    	removePiece(selectedrow,selectedcol,row,col);
      	    	doMove(T[row][col],p1);
      	    	canjumpAgain(selectedrow,row,col);
      	    	return ;
                }
      	      }
      
            }
          selectedrow=-1;
	      view.setmessage("objMessage", Currentplayer.getplayer()+" : Choose the piece you want to move.");
	      repaint();
    }
       
public boolean canjumpAgain(int selectedrow,int row,int col){
	  if((selectedrow-row)==2||(row-selectedrow==2)){
	    legalMovesforplayer=Currentplayer.getnextlegalJumps(P,T,row,col,Nextplayer);
	    if(legalMovesforplayer!=null) {
	    	
	    	if (Currentplayer.getplayer() == "Red") {
	    		
	    		view.setmessage("objMessage", " ");
	    	}
	        else {
	        
	        	view.setmessage("objMessage"," ");
	    	}
		tile_clicked(row,col);
		repaint();
		return true;
	  }
	    else{
	    	 moveDone();
	    	 return false;
	    }
	   }
	  else{
		  moveDone();
		  return false;
	  }
	 
  }
  
  public void moveDone(){
     
	    if(checkgameover())
	    {
	    	
		 	Declarewinner();
	        gameinprogress=false;
		 	selectedrow=-1;
		 	highlightedrow=-1;
		 	repaint();
	    }
	    else if(!checkgameover()) {
	    	Currentplayer.switchplayer(Nextplayer);
	   	    Setcurrentplayer();
	   	    legalMovesforplayer= Currentplayer.legalJumps(P,T,Nextplayer);
	   	    if(legalMovesforplayer==null) {
	   	    	legalMovesforplayer= Currentplayer.legalMoves(P,T,Nextplayer);
	   	    }
	   	    selectedrow=-1;
	   	    highlightedrow=-1;
	        if (Currentplayer.getplayer() == "Red") {
	    	     view.setmessage("objMessage", "RED: Make your move.");
	         }  
            else {
        	     view.setmessage("objMessage", "BLACK: Make your move.");
	    }
	   }
		repaint();
	}
  
  
   
 private void removePiece(int fromrow, int fromcol, int torow, int tocol) {
	if((fromrow-torow)==2||(torow-fromrow==2)){
    if(fromrow>torow) {
    	if(fromcol>tocol) {
    		Nextplayer.removePiece(T[fromrow-1][fromcol-1],P);
    	}
    	else {
    		Nextplayer.removePiece(T[fromrow-1][fromcol+1],P);
    	}
    }
    else if(fromrow<torow){
    	if(fromcol>tocol) {
    		Nextplayer.removePiece(T[fromrow+1][fromcol-1],P);
    	}
    	else{
    	    Nextplayer.removePiece(T[fromrow+1][fromcol+1],P);
    	}
       }
	  }
    }

public void doMove(Tile t,Piece p){
	Currentplayer.movepiece(t,p);
	p.Makeking(Currentplayer.getplayer());
	
}

@Override
public void mouseReleased(MouseEvent e) {
	
}

//Implemented for hint button
public void settilefornextjump(int fromrow,int torow, int fromcol, int tocol){
	  if(fromrow>torow) {
	    	if(fromcol>tocol) {
	    		T[fromrow-1][fromcol-1].setisoccupied(false);
	    	}
	    	else {
	    		T[fromrow-1][fromcol+1].setisoccupied(false);
	    	}
	    }
	    else if(fromrow<torow){
	    	if(fromcol>tocol) {
	    		T[fromrow+1][fromcol-1].setisoccupied(false);
	    	}
	    	else{
	    	    T[fromrow+1][fromcol+1].setisoccupied(false);
	    	}}
}

public void settileback(int fromrow,int torow,int fromcol,int tocol)
{
	  if(fromrow>torow) {
	    	if(fromcol>tocol) {
	    	T[fromrow-1][fromcol-1].setisoccupied(true);
	    	}
	    	else {
	        T[fromrow-1][fromcol+1].setisoccupied(true);
	    	}
	    }
	    else if(fromrow<torow){
	    	if(fromcol>tocol) {
	    	T[fromrow+1][fromcol-1].setisoccupied(true);
	    	}
	    	else{
	    	 T[fromrow+1][fromcol+1].setisoccupied(true);
	    	}
	       }
}

}















	








	










