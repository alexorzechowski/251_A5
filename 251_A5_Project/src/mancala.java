import java.io.*;

public class mancala {
	private static int numProblems;
	private static int[] answers;
	private static int[] board;
	private static int numOnes;
	public static void main(String[] args) {
		BufferedReader s=null;
		try {
			String file = "testMancala.txt";
			File f = new File(file);
			s = new BufferedReader(new FileReader(f));
			String[] ln = s.readLine().split("\\s+");
			numProblems=Integer.parseInt(ln[0]);
			answers=new int[numProblems];
			for(int i=0; i<numProblems; i++){
				ln = s.readLine().split("\\s+");
				board=new int[12];
				numOnes=0;
				for(int j=0; j<12; j++){
					board[j]=Integer.parseInt(ln[j]);
					if(board[j]==1)
						numOnes++;
				}
				int remaining = findMinRem(board);
				System.out.println(remaining);
				answers[i]=remaining;
			}
			 PrintWriter w = new PrintWriter("testMancala_solution.txt");
	            for (int i = 0; i < answers.length; i++){
	                if (i == answers.length - 1){
	                    w.print(answers[i]);
	                }
	                else{
	                    w.println(answers[i]);
	                }
	            }
	            w.close();	
		}
		catch(IOException e){
        	System.out.println("File not found!");
            System.exit(1);
        }	
		finally {
		    try {
		        s.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}


	private static int findMinRem(int[] board) {
		int w=numOnes;
		int y=numOnes;
		int numOnes=0;
		int[] newBoard=null;

		for(int i=0; i<11; i++){	//Traverse board left to right
			if(board[i]==1 && board[i+1]==1){	//Find adj 2 pebbles 
				if(i>=1 && board[i-1]==0){	//Empty slot to the left
					int[] newBoard1=board.clone();
					newBoard1[i-1]=1;
					newBoard1[i]=0;
					newBoard1[i+1]=0;
					w=findMinRem(newBoard1);						
				}
			
				if(i<=9 && board[i+2]==0){	//Empty slot to the right
					int[] newBoard2=board.clone();
					newBoard2[i]=0;
					newBoard2[i+1]=0;
					newBoard2[i+2]=1;
					y = findMinRem(newBoard2);
				}				
			}
		}
		for(int j=0; j<12; j++){	//Count num ones on board
			if(board[j]==1)
				numOnes++;
		}
		if(w<=y && w<=numOnes)
			return w;
		else if(y<=w && y<=numOnes)
			return y;
		else return numOnes; //Could not do any moves
		
	}	
}