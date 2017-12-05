/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.chess;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import src.pieces.Knight;
import java.lang.Object;
import java.util.Random;
/**
 *
 * @author ThanhTM
 * chưa check trường hợp không còn nước nào để đi
 */
public class main extends JFrame implements MouseListener{
    public static main mainBoard;
    
    // khoảng cách bàn cờ
    private static final int Height=700;
    private static final int Width=1110;
    
    //quân cờ
    private static Knight O,P,Q,K,L,M;
    
    //mảng lưu giá trị vị trí của các phe
    public ArrayList<Cell> black = new ArrayList<Cell>();
    public ArrayList<Cell> white = new ArrayList<Cell>();
    public Object []team = {white, black};
    
    // bàn cờ
    private JPanel board=new JPanel(new GridLayout(8,8));
    private Cell boardState[][];
    
    // tạo bàn cờ
    private JSplitPane split;
    private Container content;
    private JPanel controlPanel, temp;
//    mainBoard = new mainBoard();
    
    // xác định vị trí chọn chuột trước 
    private Cell previousCell;
    
    // xác định vị trí chọn chuột trước khi vào ô máy bay
    private Cell previousPlaneCell;
    
    // xác định người đang điều khiển: 0 là quân trắng, 1 là quân đen
    // do quân trắng đi trước nên được mặc định là 0
    private int forceColor = 0;
    // xác định nước trước ai đã điều khiển, mặc định là 1 
    private int previousForceColor = 1;
    
    // quân người chơi điều khiển
    private int humanColor;
    private int computerColor;
    
    // ô máy bay
    private ArrayList<Cell> planeCell = new ArrayList<Cell>();
    // Ô thành
    private ArrayList<Cell> castleCell = new ArrayList<Cell>();
    
    // xác định các vị trí quân cờ đã chọn có thể đi
    private ArrayList<Cell> moveableDesList = new ArrayList<Cell>();
    
    public static void main(String[] args){
        O = new Knight("O","White_Knight.png",0);
        P = new Knight("P","White_Knight.png",0);
        Q = new Knight("Q","White_Knight.png",0);
        K = new Knight("K","Black_Knight.png",1);
        L = new Knight("L","Black_Knight.png",1);
        M = new Knight("M","Black_Knight.png",1);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.magenta);
        panel.setLayout(new FlowLayout());
        
        mainBoard = new main();
        mainBoard.setVisible(true);
        
        
    }
    
    //contructor
    private main(){
        src.pieces.Piece piece;
        
        // khởi tạo khung chương trình
        content = getContentPane();
        setSize(Width,Height);
        setTitle("Chess");
	content.setBackground(Color.black);
        
        //Defining all the Cells
        Cell cell;
        boardState=new Cell[8][8];
	for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {	
                piece = null;    
                // xác định vị trí quân cờ
                if ( (i==0) && (j==0) )
                    piece = O;
                if ( (i==0) && (j==4) )
                    piece = P;
                if ( (i==0) && (j==7) )
                    piece = Q;
                if ( (i==7) && (j==0) )
                    piece = K;
                if ( (i==7) && (j==3) )
                    piece = L;
                if ( (i==7) && (j==7) )
                    piece = M;
		cell=new Cell(i,j,piece);
                if (piece != null)
                    if (piece.getColor() == 1) 
                        black.add(cell);
                    else
                        white.add(cell);
                
		cell.addMouseListener(this);
		board.add(cell);
		boardState[i][j]=cell;
                
                // xác định ô máy bay (vị trí 0 của quân trắng, 1 của quân đen)
                if ( (i==0) && (j==3) ) 
                    planeCell.add(cell);
                if ( (i==7) && (j==3) )
                    planeCell.add(cell);
                
                // xác định ô thành
                if ( (i==0) && (j==4) ) 
                    castleCell.add(cell);
                if ( (i==7) && (j==4) )
                    castleCell.add(cell);
            }
        }
        
        // tạo khung điều khiển bên phải
        controlPanel = new JPanel();
        
        // tạo khung chơi cờ bên trái
        temp = new JPanel();
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board, controlPanel);
        //chọn OK = 0, No = 1, CANCEL = 2 (cho về 0)
	humanColor = JOptionPane.showConfirmDialog(board, "Do you want to choose white?");
        if (humanColor == 2)
            humanColor = 0;
        computerColor = 1 - humanColor;
	content.add(split);
        
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // đi 1 nước
    public void play(Cell c){
        if (previousCell == null && previousPlaneCell == null){
            if (c.getPiece() != null){  
                if (c.getPiece().getColor() != forceColor)
                    return;
                c.selectPiece();
                previousCell = c;
                moveableDesList.clear();
                moveableDesList = c.getPiece().move(boardState, c.x, c.y);
                hightlightMoveableDes(moveableDesList);
            }
        }
        
        else if (previousPlaneCell != null){
            c.setPiece(previousCell.getPiece());
            previousCell.removePiece();

            removeHightlightMoveableDes(moveableDesList);
            moveableDesList.clear();
            if (previousCell != null)
                {
                    previousCell.deselectedPiece();
                    previousCell = null;
                }
            previousPlaneCell = null;
            checkTurn(c);
        }
        else
        {
            // trường hợp click 2 lần vào cùng ô sẽ hủy chọn
            if ( (previousCell.x == c.x) && (previousCell.y == c.y) )
            {
                c.deselectedPiece();
                removeHightlightMoveableDes(moveableDesList);
                moveableDesList.clear();
                previousCell = null;                
            }
            
            // trường hợp đi quân cờ
            else if ( (c.getPiece() == null) || (c.getPiece().getColor() != previousCell.getPiece().getColor() ) )
            {
                // kiểm tra vị trí được click có nằm ở trong các ô có thể di chuyển được hay không
                if (c.isMoveable())
                {
                    // ăn quân của đối phương thì sẽ xóa quân của đối phương đi
                    if (c.getPiece() != null){
                        if (c.getPiece().getColor() == 1)
                            black.remove(c);
                        else 
                            white.remove(c);
                        c.removePiece();
                    }
                    // di chuyển quân đã chọn vào vị trí
                    c.setPiece(previousCell.getPiece());
                    if (c.getPiece().getColor() == 1){
                        black.remove(previousCell);
                        black.add(c);
                    }
                    else
                    {
                        white.remove(previousCell);
                        white.add(c);
                    }
                        
                    previousCell.removePiece();

                    removeHightlightMoveableDes(moveableDesList);
                    moveableDesList.clear();
                    
                    if (previousCell != null)
                    {
                        previousCell.deselectedPiece();
                        previousCell = null;
                    }
                   
                    // trường hợp di chuyển đến ô máy bay
                    if (c == planeCell.get(1-forceColor)){
                        moveableDesList = c.getPiece().planeMove(boardState);
                        c.selectPiece();
                        previousPlaneCell = c;
                        previousCell = c;
                    }
                    else{
                        if (gameEnd() == 2)
                            //kiểm tra lượt đi của từng người
                            checkTurn(c);
                        else
                        {
                            String winner = gameEnd() == 0 ? "White" : "Black";
                            JOptionPane.showMessageDialog(board, "Checkmate!!!\n"+winner+" wins");
                        }
                            
                    }
                }
            }
            
            //trường hợp click vào quân khác cùng màu
            else if (c.getPiece().getColor() == previousCell.getPiece().getColor()){
                previousCell.deselectedPiece();
                removeHightlightMoveableDes(moveableDesList);
                moveableDesList.clear();
                c.selectPiece();
                previousCell = c;
                moveableDesList = c.getPiece().move(boardState, c.x, c.y);
                hightlightMoveableDes(moveableDesList);
            }
        }
    }
    
    // fix chỉ cho quân người chơi điều khiển đi
    @Override
    public void mouseClicked(MouseEvent me) {
        Cell c = (Cell)me.getSource();
        
        if (forceColor != humanColor)
            return;
   
        else
            play(c);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        // Do anything
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // Do anything
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        // Do anything
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // Do anything
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    // tô viền các vị trí có thể đi của quân cờ
    public void hightlightMoveableDes(ArrayList<Cell> moveableList){
        ListIterator<Cell> it = moveableList.listIterator();
    	while(it.hasNext())
    		it.next().setMoveableDes();
    }
    
    // xóa viền các vị trí có thể đi của quân cờ
    public void removeHightlightMoveableDes(ArrayList<Cell> moveableList)
    {
        ListIterator<Cell> it = moveableList.listIterator();
    	while(it.hasNext())
    		it.next().unsetMoveableDes();
    }
    
    // lượt đi của máy
    public void computer_move(){
        Random r = new Random();
//        int i = r.ints(0, )
//        Cell c = (team[computerColor]);
                     
    }
    
    // kiểm tra đến lượt của bên nào đi
    public void checkTurn(Cell c)
    {
        if (previousPlaneCell != null)
            return;
        // mới đi được 1 turn
        if (previousForceColor != forceColor)
        {
            previousForceColor = forceColor;
        }
        else
        {
            //check team đối phương còn nước đi hay không
            
            // đổi bên đi
            forceColor ^= 1;
        }
    }
    
    // kiểm tra game kết thúc hay không
    // trả về 0: trắng thắng, 1: đen thắng, 2: chưa kết thúc
    public int gameEnd()
    {
        if (boardState[0][4].getPiece() != null && boardState[0][4].getPiece().getColor() == 1 || white.isEmpty())
            return 1;
        if (boardState[7][4].getPiece() != null && boardState[7][4].getPiece().getColor() == 0 || black.isEmpty())
            return 0;
        return 2;
    }
}
