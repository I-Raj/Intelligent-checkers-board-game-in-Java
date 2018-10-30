package checkers;
import javax.swing.SwingUtilities;
	public class Main {
		public static void main(String[] args) {
			Board check=new Board();
			 SwingUtilities.invokeLater(new Runnable() {
			      @Override
			      public void run() {
			    	  new view(check);  
			      }
			    });
		}
	}
		
	
	

