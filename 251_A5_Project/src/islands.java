import java.io.*;
import java.util.*;

public class islands {
	
	private static int numProblems;
	private static int numRows;
	private static int numPixels;
	private static char[][] I;
	private static int numIslands;
	private static int[] answers;
	
	private static int[][] disc;
	private static int[][] finished;
	
	public static void main(String[] args) {
		BufferedReader s=null;
		try {
			String file = "testIslands.txt";
			File f = new File(file);
			s = new BufferedReader(new FileReader(f));
			String[] ln = s.readLine().split("\\s+");
			numProblems=Integer.parseInt(ln[0]);
			answers=new int[numProblems];
			for(int i=0; i<numProblems; i++){
				ln = s.readLine().split("\\s+");
				numIslands=0;
				numRows=Integer.parseInt(ln[0]);
				numPixels=Integer.parseInt(ln[1]);
				I= new char[numRows][numPixels];
				disc = new int[numRows][numPixels];
				finished=new int[numRows][numPixels];
				
				for(int row=0; row<numRows; row++){
					int chars_ret=0;
					int curCol=0;
					
					while(chars_ret<numPixels){
						ln = s.readLine().split("\\s+");
						int index=0;
						for(int col=curCol; col<numPixels; col++){
							I[row][col]=ln[0].charAt(index);
							index++;		
						}
						curCol=ln[0].length();
						chars_ret+=ln[0].length();
					}
				}
				//Compute
				numIslands = findNumIslands(I);
				answers[i]=numIslands;			
			}
			//Write Output
            PrintWriter w = new PrintWriter("testIslands_solution.txt");
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


	private static int findNumIslands(char[][] I) {		
		for(int row=0; row<numRows; row++){
			for(int col=0; col<numPixels; col++){
				if(I[row][col]=='-'){
					int[] pixel=new int[2];
					pixel[0]=row;
					pixel[1]=col;
					if(disc[pixel[0]][pixel[1]]==0){
						disc[pixel[0]][pixel[1]]=1;
						discover(pixel);
						numIslands++;
					}
				}
			}
		}
		
		return numIslands;
	}
	public static void discover(int[] pixel){
		//Get list of white pix touching, for each call discover
		disc[pixel[0]][pixel[1]]=1;	//Mark pixel disc
		ArrayList<int[]> adjWhitePix = getAdjWhitePix(I, pixel);
		
		while(adjWhitePix.size()>0){
			int[] neighbor = adjWhitePix.get(0);
			adjWhitePix.remove(0);					
			if(finished[neighbor[0]][neighbor[1]]==0){				
				discover(neighbor);
			}			 
		}
		finished[pixel[0]][pixel[1]]=1;	//Mark Finished
				//No More new white pixels to visit (Base Case)		
		return;
		
	}
	
	public static int[] hasWhiteNeighbor(int[] pixel){
		int[] neighbor=new int[2];
		int[] n=new int[2];	
		neighbor=null;
		n=null;
		//Corners
		if(pixel[0]==0 && pixel[1]==0){		//Top left corner
			if((n=lookRight(I, disc, pixel)) != null){
				neighbor=n;
			}
			else if((n=lookDown(I, disc, pixel)) != null){
				neighbor=n;
			}
		}
		else if(pixel[0]==0 && pixel[1]==I[0].length-1){	//Top right corner
			if((n=lookLeft(I, disc, pixel)) != null){
				neighbor=n;
			}
			else if((n=lookDown(I, disc, pixel)) != null){
				neighbor=n;
			}
		}
		else if(pixel[0]==I.length-1 && pixel[1]==0){	//Bottom left corner
			if((n=lookRight(I, disc, pixel)) != null){
				neighbor=n;
			}
			else if((n=lookUp(I, disc, pixel)) != null){
				neighbor=n;
			}
		}
		else if(pixel[0]==I.length-1 && pixel[1]==I[0].length-1){	//Bottom Right corner
			if((n=lookLeft(I, disc, pixel)) != null){
				neighbor=n;
			}
			else if((n=lookUp(I, disc, pixel)) != null){
				neighbor=n;
			}
		}
		//Edges
		else if(pixel[1]==0){	//Left Column
			if((n=lookRight(I, disc, pixel)) != null){
				neighbor=n;
			}
			else if((n=lookDown(I, disc, pixel)) != null){
				neighbor=n;
			}
			else if((n=lookUp(I, disc, pixel)) != null){
				neighbor=n;
			}	
		}
		else if(pixel[0]==0){	//Top Row
			if((n=lookRight(I, disc, pixel)) != null){
				neighbor=n;
			}
			else if((n=lookLeft(I, disc, pixel)) != null){
				neighbor=n;
			}
			else if((n=lookDown(I, disc, pixel)) != null){
				neighbor=n;
			}
			
		}
		else if(pixel[1]==I[0].length-1){	//Right Column
			if((n=lookDown(I, disc, pixel)) != null){
				neighbor=n;
			}
			else if((n=lookUp(I, disc, pixel)) != null){
				neighbor=n;
			}	
			else if((n=lookLeft(I, disc, pixel)) != null){
				neighbor=n;
			}
		}
		else if(pixel[0]==I.length-1){	//Bottom Row
			if((n=lookRight(I, disc, pixel)) != null){
				neighbor=n;
			}		
			else if((n=lookLeft(I, disc, pixel)) != null){
				neighbor=n;
			}	
			else if((n=lookUp(I, disc, pixel)) != null){
				neighbor=n;
			}	
		}
		
		//Regular Cases
		else if((n=lookLeft(I, disc, pixel)) != null){
			neighbor=n;
		}
		else if((n=lookRight(I, disc, pixel)) != null){
			neighbor=n;
		}
		else if((n=lookDown(I, disc, pixel)) != null){
			neighbor=n;
		}
		else if((n=lookUp(I, disc, pixel)) != null){
			neighbor=n;
		}		
		return neighbor;
	}
	
	public static int[] lookUp(char[][] I, int[][] disc, int[] pixel){
		int[] neighbor =null;
		if(I[pixel[0]-1][pixel[1]]=='-' && disc[pixel[0]-1][pixel[1]]==0){	//Look one up
			neighbor=new int[2];
			neighbor[0]=pixel[0]-1;
			neighbor[1]=pixel[1];
		}
		return neighbor;
	}
	public static int[] lookDown(char[][] I, int[][] disc, int[] pixel){
		int[] neighbor =null;
		if(I[pixel[0]+1][pixel[1]]=='-' && disc[pixel[0]+1][pixel[1]]==0){	//Look one down
			neighbor=new int[2];
			neighbor[0]=pixel[0]+1;
			neighbor[1]=pixel[1];
		}
		return neighbor;
	}
	public static int[] lookRight(char[][] I, int[][] disc, int[] pixel){
		int[] neighbor =null;
		if(I[pixel[0]][pixel[1]+1]=='-' && disc[pixel[0]][pixel[1]+1]==0){	//Look one to the right 
			neighbor=new int[2];
			neighbor[0]=pixel[0];
			neighbor[1]=pixel[1]+1;
		}	
		return neighbor;
	}
	public static int[] lookLeft(char[][] I, int[][] disc, int[] pixel){
		int[] neighbor =null;
		if(I[pixel[0]][pixel[1]-1]=='-' && disc[pixel[0]][pixel[1]-1]==0){	//Look one left
			neighbor=new int[2];
			neighbor[0]=pixel[0];
			neighbor[1]=pixel[1]-1;
		}	
		return neighbor;
	}
	
	public static ArrayList<int[]> getAdjWhitePix(char[][] I, int[] pixel){	//Get undiscovered only
		ArrayList<int[]> pixels = new ArrayList<int[]>(4);
		int neighbor[];
		while((neighbor=hasWhiteNeighbor(pixel)) !=null){
			disc[neighbor[0]][neighbor[1]]=1;
			pixels.add(neighbor);
		}
		return pixels;
	}
	
	
}
