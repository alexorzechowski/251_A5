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
			String file = args[0];
			File f = new File(file);
			Scanner s = new Scanner(new File(file));
            String[] ln = s.nextLine().split("\\s+"); /*first line is the source and destination*/
            numProblems=Integer.parseInt(ln[0]);
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
            	
            	while(numBalloons_ret<numBalloons){	//Get an array list of balloon heights
            		ln = s.nextLine().split("\\s+");	//Look at next line
            		for(int j=0; j<ln.length; j++)
            			balloons.add(Integer.parseInt(ln[j]));
            	}
            	
            	
            	
            }
		}    
        catch(Exception e){
        	
        }
	}

}
