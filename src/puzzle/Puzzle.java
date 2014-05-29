/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package puzzle;

/**
 *
 * @author miguel
 */

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
public class Puzzle {

    /**
     * @param args the command line arguments
     */
    

//inicializa las variables estaticas, esto se ejecuta antes que el constructor


    public static void main(String[] args) {
      new Puzzle().jugar();
    }
    
    public void jugar(){
       int rondas;
       int posicionInicial[][]=new int[4][4];
       int posicionFinal[][]=new int[4][4];
       Estado estadoInicial,estadoFinal;
       Stack<Estado> pila=new Stack<Estado>();
       Hashtable<String,Integer> existencia=new Hashtable<String,Integer>();
       ManejadorArchivos  archivo=new ManejadorArchivos("archivo.txt");
       archivo.abrirArchivoParaLectura();
       String[] datos=archivo.leerArchivo().split("\n");
       rondas=Integer.parseInt(datos[0]);//obtiene el numero de rondas del juego
       
       int n=1;
       while(rondas>0){
           //obtiene la matriz de posicion origen
           for(int i=0;i<4;++i){
               datos=archivo.leerArchivo().split("\n");
               datos=datos[0].split(" ");
               for(int j=0;j<4;++j)
                   posicionInicial[i][j]=Integer.parseInt(datos[j]);
           }          
           estadoInicial=new Estado(posicionInicial);
           //obtiene la matriz de poscion Final
           for(int i=0;i<4;++i){
               datos=archivo.leerArchivo().split("\n");
               datos=datos[0].split(" ");
               for(int j=0;j<4;++j)
                   posicionFinal[i][j]=Integer.parseInt(datos[j]);
           }
          if(I(posicionInicial)==I(posicionFinal) || accesibilidad(posicionInicial,posicionFinal)!=null){
                estadoFinal=new Estado(posicionFinal);
                pila.push(estadoInicial);
                existencia.put(estadoInicial.getKey(),0);
                System.out.println("JUEGO:"+(n++));
                try{
                solucion(pila,estadoFinal,existencia);
                }
                catch(StackOverflowError e){
                    System.out.print("La jugada:"+(n++)+" No tiene solucion....\n\n");
                }
           }
           else
               System.out.print("La jugada:"+(n++)+" No tiene solucion....\n\n");
         --rondas;
       }
       archivo.cerrarArchivoDeLectura();
       
    }
    
public boolean solucion(Stack<Estado> p,Estado solucion,Hashtable<String,Integer> exist){
    Estado e=p.pop();
    int r=p.size();
   // System.out.println("Saca de la pila:");
    //e.imprime();
    if(solucion.fueraLugar(e.getCopyEstado())==0){
        e.imprime();
        return true;
    }
    else{
        while(r>=0){
            --r;
            int[][] copy;
            ArrayList<Estado> lista=new ArrayList<>();
            if((copy=e.getAbajo())!=null)
                lista.add(new Estado(copy));
            if((copy=e.getArriba())!=null)
                lista.add(new Estado(copy));
            if((copy=e.getDerecha())!=null)
                lista.add(new Estado(copy));
            if((copy=e.getIzquierda())!=null)
                lista.add(new Estado(copy));
            for(int i=0;i<lista.size();++i){
                if(exist.containsKey(lista.get(i).getKey())){
                    lista.remove(i);
                    i=0;
                }
            }
           
            if(lista.isEmpty() && !p.isEmpty()){
               e=p.pop();
            }
            if(!lista.isEmpty()){
                     Estado aux;
                     //odena segun el numero fuera de lugar
                    for(int i=0;i<lista.size()-1;++i)
                        for(int j=0;j<lista.size()-1;++j)
                            if(lista.get(j).fueraLugar(solucion.getCopyEstado())<lista.get(j+1).fueraLugar(solucion.getCopyEstado())){
                                aux=lista.get(j);
                                lista.set(j,lista.get(j+1));
                                lista.set(j+1,aux);  
                            }
                    //ordena lexicograficamente
                    for(int i=0;i<lista.size()-1;++i)
                        for(int j=0;j<lista.size()-1;++j)
                            if(lista.get(j).fueraLugar(solucion.getCopyEstado())==lista.get(j+1).fueraLugar(solucion.getCopyEstado()))
                                if(lista.get(j).getKey().compareTo(lista.get(j+1).getKey())<0){
                                    aux=lista.get(j);
                                    lista.set(j,lista.get(j+1));
                                    lista.set(j+1,aux);  
                                }
                   
                    Stack<Estado> pila=new Stack<Estado>();
                    for(int i=0;i<lista.size();++i){
                        pila.push(lista.get(i));
                        exist.put(lista.get(i).getKey(), 0);
                    }
                    lista.clear();
                    if(solucion(pila,solucion,exist)){
                        e.imprime();
                        return true;
                    }                    
            
                }
        }
        return false;
    }
        
}

public int I(int[][] m){
    int i;
    a:
    for( i=0;i<4;++i)
        for(int j=0;j<4;++j)
            if(m[i][j]==0)
                break a;
    return inversiones(m)+((i+1)%2);
    
}

public int inversiones(int[][] m){
    int inv=0;
    for( int i=0;i<4;++i)
        for(int j=0;j<4;++j)
            if(m[i][j]!=0){
                for(int k=i;k<4;++k)
                    for(int z=(j+1);z<4;++z)
                        if(m[i][j]>m[k][z])
                            ++inv;
            }
    return inv%2;
}

public int[][] accesibilidad(int[][] posicionInicial,int [][] posicionFinal){
   
    int filaI=0,filaF=0,columF=0,columI=0;
    int[][] auxI,auxF;
    auxI=new int[4][4];
    auxF=new int[4][4];
    for(int i=0;i<4;++i)
        for(int j=0;j<4;++j){
            auxI[i][j]=posicionInicial[i][j];
            auxF[i][j]=posicionFinal[i][j];
            if(auxI[i][j]==0){
                filaI=i;
                columI=j;
            }
            if(auxF[i][j]==0){
                filaF=i;
                columF=j;
            }
        }
    for(int i=filaI;i>0;i--){
        auxI[i][columI]=auxI[i-1][columI];
        auxI[i-1][columI]=0;        
    }
    for(int i=0;i<3;++i){
        auxI[i][columI]=auxI[i+1][columI];
        auxI[i+1][columI]=0;
        if(I(auxI)==I(auxF))
            return auxI;
    }
    return null;  
    
}


}
    

