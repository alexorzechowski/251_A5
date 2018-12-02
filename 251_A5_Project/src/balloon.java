import java.io.*;
import java.util.*;

public class balloon {
	private static int numProblems;
	private static ArrayList<Integer> balloons;
	private static int[] problem_sizes;
	private static int[] answers;
	
	private static int numBalloons;
	private int numHit;
	private int numArrows;
	
	public static void main(String[] args) {
		BufferedReader s=null;
		try {
			String file = "testBalloons.txt";
			File f = new File(file);
			s = new BufferedReader(new FileReader(f));
            String[] ln = s.readLine().split("\\s+"); /*first line is the source and destination*/
            numProblems=Integer.parseInt(ln[0]);
            answers = new int[numProblems];
            int[] problem_sizes = new int[numProblems];
            ln = s.readLine().split("\\s+");	//Get Problem sizes
            for(int i=0; i<numProblems; i++){
            	problem_sizes[i]=Integer.parseInt(ln[i]);
            }
            
            
            for(int i=0; i<numProblems; i++){	//For each problem
            	balloons=new ArrayList<Integer>();
            	int numBalloons_ret=0;
            	numBalloons=problem_sizes[i];
            	ln = s.readLine().split("\\s+");
            	numBalloons_ret=ln.length;
            	for(int k=0; k<ln.length; k++)	//Fill balloons with the heights
            		balloons.add(Integer.parseInt(ln[k]));
            	
            	while(numBalloons_ret<numBalloons){	//Check if more to fill
            		ln = s.readLine().split("\\s+");	//Look at next line
            		for(int j=0; j<ln.length; j++){
            			balloons.add(Integer.parseInt(ln[j]));
            			numBalloons_ret=numBalloons_ret+1;
            		}
            	}           	
            	//Compute
            	int arrows = findNumArrows(balloons);
            	answers[i]=arrows;
         	
            }
            //Write Output
            PrintWriter w = new PrintWriter("testBalloons_solution.txt");              // To generate a solution file.
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
	}
	public static int findNumArrows(ArrayList<Integer> balloons){
		int numArrowsNeeded=0;
		int maxHeight = Collections.max(balloons);
		int arrowHeight=maxHeight;
		while(balloons.size()>0){	//Draw new Arrow
			maxHeight = Collections.max(balloons);
			int index=balloons.indexOf(maxHeight);
			arrowHeight=maxHeight;
			balloons.remove(new Integer(arrowHeight));
			numArrowsNeeded++;
			arrowHeight--;
			while(balloons.size()>0 && arrowHeight>=Collections.min(balloons)){	//Check for multiple hits
				int newIndex=findFirstIndexOfStartingAt(balloons,arrowHeight, index);
				if (newIndex==-1)	//Check if balloon at new height, if not launch new arrow
					break;
				balloons.remove(newIndex);
				arrowHeight--;
				index=newIndex;	//Update index
			}
		}
		return numArrowsNeeded;		
	}
	
	public static int findFirstIndexOfStartingAt(ArrayList<Integer> list, int element, int start){
		for(int i=start; i<list.size(); i++){
			if(list.get(i)==element)
				return i;
		}
		return -1;
	}

}
