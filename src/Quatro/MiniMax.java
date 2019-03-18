
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
            best = Integer.MIN_VALUE;
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
            best = Integer.MAX_VALUE;
            for(Node n : actual.getChild()){
                int value = eval(n,actualDepth+1,depthToReach,firstDepth);
                if (value < best){
                    best = value;
                     if(actualDepth == firstDepth){
                         _bestNode = n;
                    }
                }
            }
            best = best*-1;
        }
        return best;
    }
    
    public void eval(Node actual, int actualDepth, int depthToReach){
        eval(actual,actualDepth,depthToReach,actualDepth);
        
    }
    
 
}
