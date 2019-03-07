/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Quatro;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fbullich
 */
public class Board  implements Cloneable  {
    
    public final static int DIM_X =4;
    public final static int DIM_Y =4;
    
    private int[][] _board;
    private ArrayList<Integer> _availablePositions;
    private ArrayList<Integer> _availablePieces;
    
    
    public Board(ArrayList<Integer> positions, ArrayList<Integer> pieces){
        _board= new int[DIM_X][DIM_Y];
        
        for(int i = 0; i <DIM_X; i++){
            for (int j = 0; j <DIM_Y; j++) {
                _board[i][j]=-1;
            }
        } 
        _availablePositions = positions;
        _availablePieces = pieces;
    }
    
    @Override
    public Object clone(){
        Board b= null;
        try {
            b = (Board)super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        b._board = _board.clone();
        b._availablePieces = (ArrayList<Integer>)(_availablePieces).clone();
        b._availablePositions = (ArrayList<Integer>)(_availablePositions).clone();
        return b;
    }
    
    public List<Integer> getRemainingPieces(){
        return (ArrayList<Integer>)(_availablePieces).clone();
    }
    
    public List<Integer> getRemainigPositions(){
        return (ArrayList<Integer>)(_availablePositions).clone();
    }
    
    public void setPiece(Piece p, int x, int y){
        if(p != null && x >=0 && y >=0 && x < DIM_X && y < DIM_Y){
            _board[x][y]=p.getNumericValue();
            int position = x*4+y;
            _availablePositions.remove(_availablePositions.indexOf(position));
            _availablePieces.remove(_availablePieces.indexOf(p.getNumericValue()));
        }
    }
    
    public boolean isQuarto(int x, int y){
        return checkRow(x) || checkColumn(y) || checkDiagonal(x,y);
    }
    
    private boolean checkRow(int row){
        boolean completedRow=true;
        int i =0 ;
        while(i<DIM_X && completedRow){
            completedRow = completedRow && _board[row][i] ==-1;
            i++;
        }
        if(!completedRow)
            return false;
        else{
            return Piece.compare(_board[row][0],_board[row][1],_board[row][2],_board[row][3]);
        }
    }
    
    private boolean checkColumn(int column){
        boolean completedRow=true;
        int i =0 ;
        while(i<DIM_Y && completedRow){
            completedRow = completedRow && _board[i][column] ==-1;
            i++;
        }
        if(!completedRow)
            return false;
        else{
            return Piece.compare(_board[0][column],_board[1][column],_board[2][column],_board[3][column]);
        }
    }
    
    private boolean checkDiagonal(int x, int y){
        if (x == y || (3-x)==y ){
            boolean completedDiagonal = true;
            int i =0 ;
            while(i<DIM_Y && completedDiagonal){
                completedDiagonal = completedDiagonal && _board[i][i] ==-1;
                i++;
            } 
            boolean completedInDiagonal = true;
            i =0 ;
            while(i<DIM_Y && completedInDiagonal){
                completedInDiagonal = completedInDiagonal && _board[i][3-i] ==-1;
                i++;
            } 
            if(!completedInDiagonal && !completedDiagonal){
                return false;
            }
            else
            {
                return (completedDiagonal && Piece.compare(_board[0][0],_board[1][1],_board[2][2],_board[3][3])) ||
                        (completedInDiagonal && Piece.compare(_board[0][3],_board[1][2],_board[2][1],_board[3][0]));
            }
        }
        else 
            return false;
    }
}
