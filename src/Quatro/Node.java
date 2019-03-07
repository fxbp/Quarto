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
    private boolean _max; 
    private Piece _piece;
    private List<Node> _child;  
    private Board _board;
    
    public Node(Board board){
        this(-1,-1,true,null, board);
    }
    
    public Node(int x, int y, boolean max, Piece p, Board board){
        _piece=p;
        _posX=x;
        _posY=y;
        _max=max;
        _child= new ArrayList();
        _board=board;
        updateState();
    }
    
    public boolean isMax(){
        return _max;
    }
    
    public boolean hasChild(){
        return _child.size()>0;
    }
    
    public List<Node> getChild(){
        return _child;
    }
    
    
    public void generateChild(){
        
    }
    
    public int heuristic(){
        boolean isQuarto = _board.isQuarto(_posX, _posY);
        if (isQuarto && _max)
            return 1;
        else if(isQuarto)
            return -1;
        else 
            return 0;
    }
    
    public void addChild(Node n){
        _child.add(n);
    }
    
    
    private void updateState(){
        if (_piece != null){
            _board.setPiece(_piece,_posX,_posY);
            
        }
    }

   
    
}
