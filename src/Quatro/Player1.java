package Quatro;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;


/**
 * @author Usuari
 */
public class Player1 {
    private Tauler meutaulell;
    
    private List<Piece> _availables;
    
    private Piece _givenPiece;
    
    private Node _root;
    
    Player1(Tauler entrada){
        meutaulell = entrada;
        _availables = new ArrayList();
        initializePieces();
        _givenPiece = null;        
        _root= null;
       
    }
    public int[] tirada(int colorin, int formain, int foratin, int tamanyin){
     //colorin - Color de la peça a colocar -> 	0 = Blanc 	1 = Negre
     //formain - Forma de la peça a colocar -> 	0 = Rodona 	1 = Quadrat
     //foratin - Forat de la peça a colocar -> 	0 = No  	1 = Si
     //tamanyin - Forat de la peça a colocar -> 0 = Petit 	1 = Gran
        
        updateState();
        int x,y,color,forma,forat,tamany;
        color=-1;
        forma=-1;
        forat=-1;
        tamany=-1;
        boolean trobat=true;
        
        while( trobat){
            //mentres la trobem al taulell genero peçes
            //La peça que posarem
            color= (int) java.lang.Math.round( java.lang.Math.random() );
            forma=(int) java.lang.Math.round( java.lang.Math.random() );
            forat= (int) java.lang.Math.round( java.lang.Math.random() );
            tamany= (int) java.lang.Math.round( java.lang.Math.random() );

            trobat = color==colorin && forma==formain && forat==foratin && tamany==tamanyin;
            
            int valor= color*1000+forma*100+forat*10+tamany;
            //busco la peça
            for(int i=0;i<meutaulell.getX();i++){
                for(int j=0;j<meutaulell.getY();j++){
                    if (meutaulell.getpos(i,j) == valor){
                        trobat=true; 
                    }
                }
            }
        }
        
 
        //busco una posicio buida on posar la peça
        for(int i=0;i<meutaulell.getX();i++){
            for(int j=0;j<meutaulell.getY();j++){
                if (meutaulell.getpos(i,j) == -1){
                    return new int[]{i,j,color, forma, forat, tamany}; 
                }
            }
        }
        
        //Un retorn per defecte
        return new int[]{0,0,0,0,0,0};
        //format del retorn vector de 6 int {posX[0a3], posY[0a3], color[0o1] forma[0o1], forat[0o1], tamany[0o1]}
        //posX i posY es la posicio on es coloca la peça d'entrada
        //color forma forat i tamany descriuen la peça que colocara el contrari
        //color -  	0 = Blanc 	1 = Negre
        //forma -  	0 = Rodona 	1 = Quadrat
        //forat - 	0 = No  	1 = Si
        //tamany -      0 = Petit 	1 = Gran
    }
    
    
    private void initializePieces(){
       
        _availables.add(new Piece(0,0,0,1));
        _availables.add(new Piece(0,0,1,0));
        _availables.add(new Piece(0,0,1,1));
        _availables.add(new Piece(0,1,0,0));
        _availables.add(new Piece(0,1,0,1));
        _availables.add(new Piece(0,1,1,0));
        _availables.add(new Piece(0,1,1,1));
        _availables.add(new Piece(1,0,0,1));
        _availables.add(new Piece(1,0,1,0));
        _availables.add(new Piece(1,0,1,1));
        _availables.add(new Piece(1,1,0,0));
        _availables.add(new Piece(1,1,0,1));
        _availables.add(new Piece(1,1,1,0));
        _availables.add(new Piece(1,1,1,1));
    }
    
    private void updateState(){
        //si la peça que li he donat es null he de trobar la primera que ha posat ell, si no buscare on ha posat
        int x=0, y=0;
        while(x < meutaulell.getX() && y < meutaulell.getY() && meutaulell.getpos(x,y)==-1 && 
                (_givenPiece == null || meutaulell.getpos(x, y)==_givenPiece.getValue())){
            
            if (y+1 >= meutaulell.getY())
                x++;
            y=(y+1)%(int)meutaulell.getY();
        }
        
        if (x < meutaulell.getX() && y < meutaulell.getY()){
            if (_givenPiece == null){
                String binary = String.format("%04d", new BigInteger(Integer.toBinaryString(meutaulell.getpos(x,y))));
                _givenPiece = new Piece(Character.getNumericValue(binary.charAt(0)),
                                        Character.getNumericValue(binary.charAt(1)),
                                        Character.getNumericValue(binary.charAt(2)),
                                        Character.getNumericValue(binary.charAt(3)));
               
            }
            _availables.remove(_givenPiece);
        }
    }
}
