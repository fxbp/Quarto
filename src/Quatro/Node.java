/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Quatro;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fbullich
 */
public class Node {
    
    private int _posX;
    private int _posY;
    private boolean _player; //true = player 1, false = player2
    private boolean _isLeaf;
    private boolean _isQuarto;
    private Piece _piece;
    private List<Node> _child;  
    private Board _board;
    
    public Node(Board board){
        this(-1,-1,true,null, board);
    }
    
    public Node(int x, int y, boolean player, Piece p, Board board){
        _piece=p;
        _posX=x;
        _posY=y;
        _player=player;
        _child= new ArrayList();
        _isLeaf = true;
        _board=board;
        _isQuarto=false;
        updateState();
    }
    
    public void addChild(Node n){
        _child.add(n);
        _isLeaf=false;
    }
    
    
    private void updateState(){
        if (_piece != null){
            _board.setPiece(_piece,_posX,_posY);
            _isQuarto = _board.isQuarto(_posX, _posY);
            _isLeaf = _isQuarto || _isLeaf;
        }
    }

   
    
}
