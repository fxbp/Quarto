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
    private boolean _leaf;
    private Piece _piece;
    private List<Node> _child;  
    
    
    public Node(int x, int y, boolean player, Piece p){
        _piece=p;
        _posX=x;
        _posY=y;
        _player=player;
        _child= new ArrayList();
        _leaf = true;
        
    }
    
    public void addChild(Node n){
        _child.add(n);
        _leaf=false;
    }
    
}
