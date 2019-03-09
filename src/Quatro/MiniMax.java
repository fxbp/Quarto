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
    
    private int eval(Node actual, int actualDepth, int depthToReach, int firstDepth){
         if (actualDepth == depthToReach) 
            return actual.heuristic();
        
        actual.generateChild();
        if (!actual.hasChild())
            return actual.heuristic();
        
        int best;
        if(actual.isMax()){
            best =-2;
            for(Node n : actual.getChild()){
                int value = eval(n,actualDepth+1,depthToReach,firstDepth);
                if (value > best){                   
                    best = value;
                    if(actualDepth == firstDepth){
                         _bestNode = n;
                    }
                }
            }
        }
        else{
            best = 10;
            for(Node n : actual.getChild()){
                int value = eval(n,actualDepth+1,depthToReach,firstDepth);
                if (value < best){
                    best = value;
                     if(actualDepth == firstDepth){
                         _bestNode = n;
                    }
                }
            }
        }
        return best;
    }
    
    public void eval(Node actual, int actualDepth, int depthToReach){
        eval(actual,actualDepth,depthToReach,actualDepth);
        
    }
    
 
}
