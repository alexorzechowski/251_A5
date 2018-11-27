import java.io.*;
import java.util.*;

public class balloon {
	private static int numProblems;
	private static ArrayList<Integer> balloons;
	private static int[] problem_sizes;
	
	private static int numBalloons;
	private int numHit;
	private int numArrows;
	
	public static void main(String[] args) {
		
		try {
			String file = "testBalloons.txt";
			File f = new File(file);
			Scanner s = new Scanner(f);
            String[] ln = s.nextLine().split("\\s+"); /*first line is the source and destination*/
            numProblems=Integer.parseInt(ln[0]);
            int[] problem_sizes = new int[numProblems];
            ln = s.nextLine().split("\\s+");	//Get Problem sizes
            for(int i=0; i<numProblems; i++){
            	problem_sizes[i]=Integer.parseInt(ln[i]);
            }
            
            
            for(int i=0; i<numProblems; i++){	//For each problem
            	balloons=new ArrayList<Integer>();
            	int numBalloons_ret=0;
            	numBalloons=problem_sizes[i];
            	ln = s.nextLine().split("\\s+");
            	numBalloons_ret=ln.length;
            	for(int k=0; k<ln.length; k++)	//Fill balloons with the heights
            		balloons.add(Integer.parseInt(ln[k]));
            	
            	while(numBalloons_ret<numBalloons){	//Check if more to fill
            		ln = s.nextLine().split("\\s+");	//Look at next line
            		for(int j=0; j<ln.length; j++){
            			balloons.add(Integer.parseInt(ln[j]));
            			numBalloons_ret=numBalloons_ret+1;
            		}
            	}
            	//System.out.println(Arrays.toString(balloons.toArray()));
            	//System.out.println(balloons.size());
            	//Compute
            	int arrows = findNumArrows(balloons);
            	System.out.println(arrows);
            	
            	
            }
		}    
        catch(FileNotFoundException e){
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
