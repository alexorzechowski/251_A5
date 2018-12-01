import java.io.*;
import java.util.*;

public class islands {
	
	private static int numProblems;
	private static int numRows;
	private static int numPixels;
	private static char[][] I;
	private static int numIslands;
	
	private static int[][] disc;
	
	
	public static void main(String[] args) {
		try {
			String file = "testIslands.txt";
			File f = new File(file);
			Scanner s = new Scanner(f);
			String[] ln = s.nextLine().split("\\s+");
			numProblems=Integer.parseInt(ln[0]);
			for(int i=0; i<numProblems; i++){
				ln = s.nextLine().split("\\s+");
				numRows=Integer.parseInt(ln[0]);
				numPixels=Integer.parseInt(ln[1]);
				I= new char[numRows][numPixels];
				disc = new int[numRows][numPixels];
				
				for(int row=0; row<numRows; row++){
					int chars_ret=0;
					int curCol=0;
					
					while(chars_ret<numPixels){
						ln = s.nextLine().split("\\s+");
						int index=0;
						for(int col=curCol; col<numPixels; col++){
							I[row][col]=ln[0].charAt(index);
							index++;		
						}
						curCol=ln[0].length();
						chars_ret+=ln[0].length();
					}
				}
				//subIslands= new ArrayList<SubIsland>();
				//Compute
				numIslands = findNumIslands(I);
				System.out.println(numIslands);
			
			}
			
			
		}
		catch(FileNotFoundException e){
        	System.out.println("File not found!");
            System.exit(1);
        }	
	}


	private static int findNumIslands(char[][] I) {
		int numIslands=0;
		for(int row=0; row<numRows; row++){
			for(int col=0; col<numPixels; col++){
				if(I[row][col]=='-'){
					int[] pixel=new int[2];
					pixel[0]=row;
					pixel[1]=col;
					if(disc[pixel[0]][pixel[1]]==0){
						disc[pixel[0]][pixel[1]]=1;
						discover(pixel);
					}
				}
			}
		}
		
		return numIslands;
	}
	public static void discover(int[] pixel){
		int[] nieghbor=hasWhiteNeighbor(pixel);
		while(nieghbor!=null){
			disc[nieghbor[0]][nieghbor[1]]=1;	//Mark Discovered
			discover(nieghbor);
		}
				//No More new white pixels to visit (Base Case)
		numIslands++;
		return;
		
	}
	
	public static int[] hasWhiteNeighbor(int[] pixel){
		int[] neighbor=null;
		if(pixel[1]==I[0].length-1){	//Right Column
			if(I[pixel[0]][I[0].length-1]=='-' && pixel[0]!=I.length-1){	//Look one row down except at last row
				neighbor[0]=pixel[0]+1;
				neighbor[1]=pixel[1];
			}
		}
		else if(pixel[0]==I.length-1){	//Bottom Row
			if(I[I.length-1][pixel[1]]=='-' && pixel[1]!=I[0].length-1){	//Look one to the write except at last col
				neighbor[0]=pixel[0];
				neighbor[1]=pixel[1]+1;
			}			
		}
		//Last row and col: chain is done
		
		//Regular Cases
		else if( I[pixel[0]][pixel[1]+1]=='-'){	//Pixels bordering horizontally
			neighbor=new int[2];
			neighbor[0]=pixel[0];
			neighbor[1]=pixel[1]+1;
		}	
		// disc[pixel[0]][pixel[1]+1]==0
		/*else if( I[pixel[0]][pixel[1]-1]=='-' &&  disc[pixel[0]][pixel[1]-1]==0){
			neighbor=new int[2];
			neighbor[0]=pixel[0];
			neighbor[1]=pixel[1]-1;
		} */
		else if( I[pixel[0]+1][pixel[1]]=='-' ){
			neighbor=new int[2];
			neighbor[0]=pixel[0]+1;
			neighbor[1]=pixel[1];
		} 
		//isc[pixel[0]+1][pixel[1]]==0
		/*else if( I[pixel[0]-1][pixel[1]]=='-' &&  disc[pixel[0]-1][pixel[1]]==0){
			neighbor=new int[2];
			neighbor[0]=pixel[0]-1;
			neighbor[1]=pixel[1];
		}	*/
				
			
		
		return neighbor;
	}
	
}
