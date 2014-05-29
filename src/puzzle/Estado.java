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


public class Estado {
    private int[][] estado;
    private String key;
    private int i;
    private int j;
    
    public Estado(int[][] e){
        key="";
        estado=new int[4][4];
        for(int i=0;i<4;++i)
            for(int j=0;j<4;++j){
                if(e[i][j]==0){
                    this.i=i;
                    this.j=j;
                }     
                estado[i][j]=e[i][j];
            }
        for(int i=0;i<4;++i)
            for(int j=0;j<4;++j)
                if(estado[i][j]<10)
                    key+=String.valueOf(estado[i][j]);
                else
                    switch(estado[i][j]){
                        case 10:
                            key+="a";
                        case 11:
                            key+="b";
                        case 12:
                            key+="c";
                        case 13:
                            key+="d";
                        case 14:
                            key+="e";
                        case 15:
                            key+="f";
                    }
    }
    
    public int[][] getEstado(){
        return estado;
    }
    
    public int fueraLugar(int[][] e){
        int n=0;
        for(int i=0;i<4;++i)
            for(int j=0;j<4;++j)
                if(estado[i][j]!=0 && estado[i][j]!=e[i][j])
                    ++n;
        return n;
    }
    
    public String getKey(){
        return key;
    }
    
    public void imprime(){
        System.out.print("-------------\n");
        for(int i=0;i<4;++i){
            System.out.print("|");
            for(int j=0;j<4;++j){
                if(estado[i][j]==0)
                    System.out.print("  |");
                else
                    if(estado[i][j]<10)
                        System.out.printf("%d |",estado[i][j]);
                    else
                        System.out.printf("%d|",estado[i][j]);
            }
            System.out.print("\n-------------");
            System.out.println();
        }
        
     System.out.println("\n");
    }
    
    @Override
    public int hashCode(){
        return key.hashCode();
    }
    
    @Override
    public boolean equals(Object obj){
        Estado e=(Estado)obj;
        return key.equals(e.getKey());
    }
    
    public int compareTo(Estado e){
        return key.compareTo(e.getKey());
    }
    
    public int[][] getCopyEstado(){
        int[][] c=new int[4][4];
        for(int i=0;i<4;++i)
            for(int j=0;j<4;++j)
                c[i][j]=estado[i][j];
        return c;
    }
    
    public int[][] getAbajo(){
        if(i==3)
            return null;
        else{
            int[][] c=new int[4][4];
            for(int i=0;i<4;++i)
                for(int j=0;j<4;++j)
                c[i][j]=estado[i][j];
            c[i][j]=c[i+1][j];
            c[i+1][j]=0;
            return c;
        }
    }
    
     public int[][] getArriba(){
        if(i==0)
            return null;
        else{
            int[][] c=new int[4][4];
            for(int i=0;i<4;++i)
                for(int j=0;j<4;++j)
                c[i][j]=estado[i][j];
            c[i][j]=c[i-1][j];
            c[i-1][j]=0;
            return c;
        }
     }
        
     public int[][] getIzquierda(){
        if(j==0)
            return null;
        else{
            int[][] c=new int[4][4];
            for(int i=0;i<4;++i)
                for(int j=0;j<4;++j)
                c[i][j]=estado[i][j];
            c[i][j]=c[i][j-1];
            c[i][j-1]=0;
            return c;
        }
    }
     
     public int[][] getDerecha(){
        if(j==3)
            return null;
        else{
            int[][] c=new int[4][4];
            for(int i=0;i<4;++i)
                for(int j=0;j<4;++j)
                c[i][j]=estado[i][j];
            c[i][j]=c[i][j+1];
            c[i][j+1]=0;
            return c;
        }
    }
}
