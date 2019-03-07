/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Quatro;


/**
 *
 * @author Francesc
 */
public class MiniMax {
    
    private Node _bestNode;
    
    public MiniMax(){
       _bestNode=null;
    }
    
    public Node getBestNode(){
        return _bestNode;
    }
    
    public int eval(Node actual, int actualDepth, int depthToReach){
        if (actualDepth == depthToReach) 
            return actual.heuristic();
        
        actual.generateChild();
        if (!actual.hasChild())
            return actual.heuristic();
        
        int best;
        if(actual.isMax()){
            best =-1;
            for(Node n : actual.getChild()){
                int value = eval(n,actualDepth+1,depthToReach);
                if (value > best){
                    _bestNode = n;
                    best = value;
                }
            }
        }
        else{
            best = 10;
            for(Node n : actual.getChild()){
                int value = eval(n,actualDepth+1,depthToReach);
                if (value < best){
                    _bestNode = n;
                    best = value;
                }
            }
        }
        return best;
        
    }
    
 
}
