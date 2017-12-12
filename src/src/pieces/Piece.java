package src.pieces;

import java.util.ArrayList;

import src.chess.Cell;


/**
 * This is the Piece Class. It is an abstract class from which all the actual pieces are inherited.
 * It defines all the function common to all the pieces
 * The move() function an abstract function that has to be overridden in all the inherited class
 * It implements Cloneable interface as a copy of the piece is required very often
 */
public abstract class Piece implements Cloneable{

	//Member Variables
	private int color;
	private String id=null;
	private String path;
	protected ArrayList<Cell> moveableDesList = new ArrayList<Cell>();  //Protected (access from child classes)
	public abstract ArrayList<Cell> move(Cell pos[][],int x,int y);  //Abstract Function. Must be overridden   
        
        public ArrayList<Cell> planeMove(Cell pos[][])
        {
            moveableDesList.clear();
            for (int i=0; i<8; i++)
                for (int j=0; j<8; j++)
                {
                    if ( (i==0 && j==3) || (i==0 && j ==4) || (i==7 && j==3) || (i==7 && j==4) );
                    else
                    {
                        if (pos[i][j].getPiece() == null)
                            moveableDesList.add(pos[i][j]);
                    }
                }
            return moveableDesList;
        }
        
	//Id Setter
	public void setId(String id)
	{
		this.id=id;
	}
	
	//Path Setter
	public void setPath(String path)
	{
		this.path=path;
	}
	
	//Color Setter
	public void setColor(int c)
	{
		this.color=c;
	}
	
	//Path getter
	public String getPath()
	{
		return path;
	}
	
	//Id getter
	public String getId()
	{
		return id;
	}
	
	//Color Getter
	public int getColor()
	{
		return this.color;
	}
	
	//Function to return the a "shallow" copy of the object. The copy has exact same variable value but different reference
	public Piece getCopy() throws CloneNotSupportedException
	{
		return (Piece) this.clone();
	}
}