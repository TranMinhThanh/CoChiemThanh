/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.chess;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.pieces.*;
/**
 *
 * @author Admin
 */
public class Cell extends JPanel{
    //Quân cờ hiện tại trên ô cờ (null = không có quân nào trên ô cờ)
    private Piece piece;
    
    private JLabel content;
    //vị trí của ô cờ
    public int x,y;
    
    //trạng thái ô bàn cờ
    private boolean isSelected = false;
    private boolean isMoveable = false;
    
    public Cell(int x,int y,Piece p)
    {		
        this.x=x;
	this.y=y;	
	setLayout(new BorderLayout());
        //màu của ô cờ
	if((x+y)%2==0)
            setBackground(new Color(113,198,113));
	else
            setBackground(Color.white);
	if(p!=null)
            setPiece(p);
	}
    
    public void setPiece(Piece p)    //Function to inflate a cell with a piece
    {
        piece=p;
        ImageIcon img=new javax.swing.ImageIcon(this.getClass().getResource(p.getPath()));
        content=new JLabel(img);
        this.add(content);
    }
    
    public Piece getPiece()    //Function to access piece of a particular cell
    {
	return this.piece;
    }
    
    public void removePiece()
    {
        piece = null;
        this.remove(content);
    }
    
    public boolean isSelected()
    {
        return this.isSelected;
    }
        
    public boolean isMoveable()
    {
        return this.isMoveable;
    }
    
    public void selectPiece()
    {
        this.setBorder(BorderFactory.createLineBorder(Color.red,6));
	this.isSelected=true;
    }
    
    public void deselectedPiece()
    {
        this.setBorder(null);
        this.isSelected=false;    
    }
    
    public void setMoveableDes()
    {
        this.setBorder(BorderFactory.createLineBorder(Color.blue,6));
        this.isMoveable = true;
    }
    
    public void unsetMoveableDes()
    {
        this.setBorder(null);
        this.isMoveable = false;
    }
}
