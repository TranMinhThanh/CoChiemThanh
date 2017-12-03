package src.pieces;

import java.util.ArrayList;

import src.chess.Cell;

/**
 * This is the Knight Class inherited from the Piece abstract class
 *  
 *
 */
public class Knight extends Piece{
	
	//Constructor
    public Knight(String i,String p,int c)
    {
	setId(i);
	setPath(p);
    	setColor(c);
    }
	
    //Move Function overridden
    //There are at max 8 possible moves for a knight at any point of time.
    //Knight moves only 2(1/2) steps
    public ArrayList<Cell> move(Cell state[][],int x,int y)
    {
	moveableDesList.clear();
        //số phần tử của vị trí có thể đi
        int n = 0;
        int []posx = new int[8];
        int []posy = new int[8];
//	int posx[]={x+1,x+1,x+2,x+2,x-1,x-1,x-2,x-2};
//	int posy[]={y-2,y+2,y-1,y+1,y-2,y+2,y-1,y+1};
        
        // xóa vị trí bị chặn
        if (x-1>=0 && state[x-1][y].getPiece() == null){
            posx[n] = x-2;
            posy[n] = y-1;
            posx[n+1] = x-2;
            posy[n+1] = y+1;
            n += 2;
        }
        
        if (y-1>=0 && state[x][y-1].getPiece() == null){
            posx[n] = x-1;
            posy[n] = y-2;
            posx[n+1] = x+1;
            posy[n+1] = y-2;
            n += 2;
        }
        
        if (x+1<=7 && state[x+1][y].getPiece() == null){
            posx[n] = x+2;
            posy[n] = y-1;
            posx[n+1] = x+2;
            posy[n+1] = y+1;
            n += 2;
        }
        
        if (y+1<=7 && state[x][y+1].getPiece() == null){
            posx[n] = x-1;
            posy[n] = y+2;
            posx[n+1] = x+1;
            posy[n+1] = y+2;
            n += 2;
        }

        
	for(int i=0;i<n;i++)
            if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8))
		if((state[posx[i]][posy[i]].getPiece()==null||state[posx[i]][posy[i]].getPiece().getColor()!=this.getColor()))
		{
                    moveableDesList.add(state[posx[i]][posy[i]]);
		}
	return moveableDesList;
	}
}