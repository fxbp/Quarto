/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Quatro;

/**
 *
 * @author fbullich
 */
public class Board  implements Cloneable  {
    
    public final int DIM_X =4;
    public final int DIM_Y =4;
    
    private Piece[][] _board;
    
    
    public Board(){
        _board= new Piece[DIM_X][DIM_Y];
        
        for(int i = 0; i <DIM_X; i++){
            for (int j = 0; j <DIM_Y; j++) {
                _board[i][j]=new Piece();
            }
        } 
    }
}
