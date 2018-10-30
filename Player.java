package checkers;
	import java.awt.Color;
	import java.util.ArrayList;
    import checkers.Board.Moves;
	public class Player { 
	private String player;
	private int checkers;
	private boolean currentplayer; 
	private int currentrow,currentcol,nextrow,nextcol;

	public Player(String player) {
		checkers=0;
		this.player=player;
		if(player=="Red")
		{
			currentplayer=true;
		}
		else
			currentplayer=false;
		}


	//getters

	public int getnumpieces(){
	return checkers;
	}

	public String getplayer(){
		if(player=="Red")
			return "Red";
		else
			return "Black";
	}

	public boolean iscurrentplayer(){
		if(currentplayer==true)
			return true;
		else
			return false;
	}


	public void pieceadded(){
		checkers++;
	}


	public void removePiece(Tile t,ArrayList<Piece> p2){
		Piece p=t.getoccupantat(t, p2);
	    p2.remove(p);
	    t.setisoccupied(false);
	    checkers--;
	}


	public void switchplayer(Player next){
		
		if (this.currentplayer==true){
			currentplayer=false;
			next.currentplayer=true;
		}
		else if(currentplayer==false){
			currentplayer=true;
			next.currentplayer=true;
	   }
	}

	public void Initialisecurrentnextstate(Tile T[][],int posfrom,int posto)
	{
		//Returns row and col vales of positions
		  for(int i=0;i<8;i++) {
		    	for(int j=0;j<8;j++){
		       if(T[i][j].getposition()==posto) {
					 nextrow=i;	
					 nextcol=j;
				 }
		         if(T[i][j].getposition()==posfrom){
		        	 currentrow=i;
		        	 currentcol=j;
		         }
		        }
		      }
	}

	public boolean belongstoCurrentplayer(Piece p) {
		// Returns true if piece belongs to current player else returns false
		if(player=="Red" && p.getcolor()==Color.RED){
			return true;
		}
		else if(player=="Black" && p.getcolor()==Color.BLACK) {
			return true;
		}
		return false;
	}

	private boolean belongstoNextplayer(Piece p) {
		//Returns true if piece belongs to opponent player else returns false
		if(player=="Red" && p.getcolor()==Color.BLACK){
			return true;
		}
		else if(player=="Black" && p.getcolor()==Color.RED) {
			return true;
		}
		return false;
	}


	public void movepiece(Tile t,Piece p){
	p.settile(t);
	t.setisoccupied(true);
	}


	boolean isSingleJumpdone(int r1,int r2, int c1, int c2,Tile T[][], ArrayList<Piece> p2,Piece p,Player Nextplayer) {
		//Returns true if single jump is possible. Handled seperately for different types of pieces based on requirement.
		Piece opponent;
		if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
	        return false; 
		
		if(T[r2][c2].isoccupied()==true)
		    return false;
		
		if(!p.isking()){
			
		 if(r1 - r2 == 2 && p.getcolor()==Color.RED) {  //Red: only forward right and left jump
		     if((c1>c2) ) {
				 opponent=T[(r1-1)][(c1-1)].getoccupant(p2);
				 if((opponent!=null) && (belongstoNextplayer(opponent))) {
		 	         return true;
			      }
			   }
			  else if((c2>c1)){
			     opponent=T[(r1-1)][(c1+1)].getoccupant(p2);
				 if(opponent!=null && (belongstoNextplayer(opponent))) {
			          return true;
		         }
		       }
			  else{
			    	return false;
			    }
		    }
		    else if(r2-r1 ==2 && p.getcolor()==Color.BLACK) { //Black: only backward right and left jump {
		    	 if((c1>c2)) {
			    	opponent=T[(r1+1)][(c1-1)].getoccupant(p2);
			    	if(opponent!=null && (belongstoNextplayer(opponent))){
			 	     return true;
			    	}
				 }
			    else if(c2>c1) {
				     opponent=T[(r1+1)][(c1+1)].getoccupant(p2); 
				     if(opponent!=null && (belongstoNextplayer(opponent))){
				     return true;
				     }
			       }
			    else {
				    	 return false;
				     }
				}
		 }
		else if(p.isking()) {   // King: Jumps in all 4 directions possible
		 if (r1-r2==2||r1-r2==-2){
	      if(c1>c2){
				if((r1>r2) && (T[(r1-1)][(c1-1)].isoccupied())) {
					opponent=T[(r1-1)][(c1-1)].getoccupant(p2);    //check if the piece to jump over belongs to the opponent
					if(opponent!=null && (belongstoNextplayer(opponent))){
			            return true;
					}
					else
						return false;
				  }
			else if((r2>r1) && (T[(r1+1)][(c1-1)].isoccupied())){
					opponent=T[(r1+1)][(c1-1)].getoccupant(p2); 
					if(opponent!=null && (belongstoNextplayer(opponent))){
			 	     return true;
					}
					else
						return false;
					}
			}
			if(c2>c1){
				if((r1>r2) && (T[(r1-1)][(c1+1)].isoccupied())){
					opponent=T[(r1-1)][(c1+1)].getoccupant(p2); 
					if(opponent!=null && (belongstoNextplayer(opponent))){
			 	       return true;
					}
					else
					return false;
				}
				else if((r2>r1) &&(T[(r1+1)][(c1+1)].isoccupied())){

					opponent=T[(r1+1)][(c1+1)].getoccupant(p2); 
					if(opponent!=null && (belongstoNextplayer(opponent))){
			 	     return true;
					}
					else
						return false;
				
				}
			}
			}
		}
		 return false;
		}


   public boolean isValidmove(int r1,int r2,int c1, int c2,Piece movep) 
	{
		if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8){
	        return false;
		}
	    
	if(movep.isking()==false && (c2-c1==1||c1-c2==1)){
			  
			  if(player=="Red"){
		           if(( movep.getcolor()==Color.red ) && (r1-r2==1)){
		        	   return true;
	                }
	            }
	            else if(player=="Black") {
		            if(movep.getcolor()==Color.BLACK && (r2-r1==1)){
		            	 return true;
		              }
		         }
	             else{
		        return false;
		          }
		     }
		else if(movep.isking()==true && (c2-c1==1||c1-c2==1) && ((r1-r2==1)||(r2-r1==1)) ){
	    	if((player=="Red") && (movep.getcolor()==Color.red)){
	           return true;
	        }
	        
	       else if((player=="Black") && (movep.getcolor()==Color.BLACK)){
	    	   return true;
		   }
		   else{
			    return false;
	        }
		   }
	return false;
	}


		

		public Moves[] legalMoves(ArrayList<Piece> P,Tile T[][],Player Nextplayer)
	    {
		ArrayList<Moves> moves = new ArrayList<Moves>();
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
			 Piece piece=T[i][j].getoccupant(P);
			 if(T[i][j].isoccupied()==true && belongstoCurrentplayer(piece) &&(piece!=null)) {
				         int row=piece.getpiecerow();
					     int col=piece.getpiececol();
			             if(isValidmove(row,row-1,col,col-1,piece)==true && T[row-1][col-1].isoccupied()==false){
			            	 moves.add(new Moves(row, row-1,col,col-1));
				          }
	                     if(isValidmove(row,row-1,col,col+1,piece)==true && T[row-1][col+1].isoccupied()==false){
	                    	 moves.add(new Moves(row, row-1,col,col+1));
	                        }
	                     if(isValidmove(row,row+1,col,col-1,piece)==true && T[row+1][col-1].isoccupied()==false) {
	                         moves.add(new Moves(row, row+1,col,col-1));
	                        }
	                     if(isValidmove(row,row+1,col,col+1,piece)==true && T[row+1][col+1].isoccupied()==false){
	                         moves.add(new Moves(row, row+1,col,col+1));
	                        }
			 }
			}
		}
		 if (moves.size() == 0)
             return null;
         else {
             Moves[] moveArray = new Moves[moves.size()];
             for (int k = 0; k < moves.size(); k++)
                 moveArray[k] = moves.get(k);
             return moveArray;
              }
	    }
	     
		
		public Moves[] legalJumps(ArrayList<Piece> P,Tile T[][],Player Nextplayer) {             
	         ArrayList<Moves> moves = new ArrayList<Moves>();
	 		 for(int i=0;i<8;i++){
	 			for(int j=0;j<8;j++){
	 			  Piece piece=T[i][j].getoccupant(P);
	 			    if(T[i][j].isoccupied()==true && belongstoCurrentplayer(piece) &&(piece!=null)) {
	 				         int row=piece.getpiecerow();
	 					     int col=piece.getpiececol();  
	                  
	                     if(isSingleJumpdone(row,row-2,col,col-2,T,P,piece,Nextplayer)==true){
			            	 moves.add(new Moves(row,row-2,col,col-2));
				            }
	                     if(isSingleJumpdone(row,row-2,col,col+2,T,P,piece,Nextplayer)==true){
	                 	     moves.add(new Moves(row, row-2,col,col+2));
	                        }
	                     if(isSingleJumpdone(row,row+2,col,col-2,T,P,piece,Nextplayer)==true){
	                 	     moves.add(new Moves(row, row+2,col,col-2));
	                       }
	                     if(isSingleJumpdone(row,row+2,col,col+2,T,P,piece,Nextplayer)==true){
	                 	    moves.add(new Moves(row, row+2,col,col+2));
	                       }
			              }
		                 }
	                	}

	                  if (moves.size() == 0)
	                      return null;
	                  else {
	                      Moves[] moveArray = new Moves[moves.size()];
	                      for (int k = 0; k < moves.size(); k++)
	                          moveArray[k] = moves.get(k);
	                      return moveArray;
	                       }
		}   

       public Moves[] getnextlegalJumps(ArrayList<Piece> P,Tile T[][],int row,int col,Player Nextplayer)
	    {
		ArrayList<Moves> jumps = new ArrayList<Moves>();
		Piece piece=T[row][col].getoccupant(P);
		if(piece!=null) {
	                     if(isSingleJumpdone(row,row-2,col,col-2,T,P,piece,Nextplayer)==true){
			                  jumps.add(new Moves(row,row-2,col,col-2));
				           }
	                     if(isSingleJumpdone(row,row-2,col,col+2,T,P,piece,Nextplayer)==true){
	                    	 jumps.add(new Moves(row, row-2,col,col+2));
	                       }
	                    if(isSingleJumpdone(row,row+2,col,col-2,T,P,piece,Nextplayer)==true){
	                    	  jumps.add(new Moves(row, row+2,col,col-2));
	                       }
	                     if(isSingleJumpdone(row,row+2,col,col+2,T,P,piece,Nextplayer)==true) {
	                         jumps.add(new Moves(row, row+2,col,col+2));
	                       }
					     }

	                  if (jumps.size() == 0)
	                      return null;
	                  else {
	                      Moves[] jumpArray = new Moves[jumps.size()];
	                      for (int k = 0; k < jumps.size(); k++)
	                          jumpArray[k] = jumps.get(k);
	                      return jumpArray;
	                       }
	    }
	                     

	 public Moves[] legalJumpsforhint(ArrayList<Piece> P,Tile T[][],Piece piece,int row,int col,Player Nextplayer)
	    {
	    	ArrayList<Moves> jumps1 = new ArrayList<Moves>();
	    	if(isSingleJumpdone(row,row-2,col,col-2,T,P,piece,Nextplayer)==true){
	            jumps1.add(new Moves(row,row-2,col,col-2));
	         }
	       if(isSingleJumpdone(row,row-2,col,col+2,T,P,piece,Nextplayer)==true){
	      	 jumps1.add(new Moves(row, row-2,col,col+2));
	         }
	      if(isSingleJumpdone(row,row+2,col,col-2,T,P,piece,Nextplayer)==true){
	           jumps1.add(new Moves(row, row+2,col,col-2));
	         }
	       if(isSingleJumpdone(row,row+2,col,col+2,T,P,piece,Nextplayer)==true) {
	           jumps1.add(new Moves(row, row+2,col,col+2));
	         }
	       
	       if (jumps1.size() == 0)
	           return null;
	       else {
	           Moves[] jumpArray = new Moves[jumps1.size()];
	           for (int k = 0; k < jumps1.size(); k++)
	               jumpArray[k] = jumps1.get(k);
	           return jumpArray;
	             }
	    }
	    }


