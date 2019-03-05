
package Quatro;

/**
 *
 * @author Francesc
 */
public class Piece {
    
    private int _color;
    private int _size;
    private int _hole;
    private int _shape;
    
    public Piece(int color, int shape, int hole, int size){
        _color = color;
        _size = size;
        _hole = hole;
        _shape = shape;
    }
    
    
    public int getValue(){
       
        return _color * 1000 + _shape*100 + _hole *10 + _size;
    }
}
