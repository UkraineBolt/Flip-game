/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author alex
 */
public class Flip {
    boolean[][] board;
    private HashMap<Integer,Integer[]> key;
    ArrayList<Integer> minAnswer = new ArrayList<>();
    public final Random r;
    private static final int SIZE = 5;
    
    Flip(){
        r = new Random();
        board=new boolean[SIZE][SIZE];
        key = new HashMap<>();
        
        int l = 1;
        for(int x=0;x<SIZE;x++){
            for(int y=0;y<SIZE;y++){
                int[] list = new int[2];
                list[0]=x;list[1]=y;
                key.put(l, new Integer[]{x,y});
                l++;
            }
        }
    }
    Flip(boolean[][] state){
        board=state;
        r = new Random();
        int l = 1;
        for(int x=0;x<SIZE;x++){
            for(int y=0;y<SIZE;y++){
                int[] list = new int[2];
                list[0]=x;list[1]=y;
                key.put(l, new Integer[]{x,y});
                l++;
            }
        }
    }
    
    public void changeState(boolean[][] state){
        board = state;
    } 
    
    public void move(int i){
        Integer[] loci = key.get(i);
        int x = loci[0];
        int y = loci[1];
        
        try{
            board[x][y] = !board[x][y];
        }catch(Exception e){
        
        }
        
        try{
            board[x+1][y] = !board[x+1][y];
        }catch(Exception e){
        
        }
        
        try{
            board[x-1][y] = !board[x-1][y];
        }catch(Exception e){
        
        }
        
        try{
            board[x][y+1] = !board[x][y+1];
        }catch(Exception e){
        
        }
        
        try{
            board[x][y-1] = !board[x][y-1];
        }catch(Exception e){
        
        }
        
    }
    
    public void clearBoard(){
        board = new boolean[SIZE][SIZE];
    }
    
    
    
    public void newBoard(){
        minAnswer.clear();
        clearBoard();
        int numOfChanges = r.nextInt(10)+3;
        
        for(int i=0;i<numOfChanges;i++){
            int change = r.nextInt(SIZE*SIZE)+1;
            if(!minAnswer.contains(change)&&change<SIZE*SIZE){
                minAnswer.add(change);
                move(change);
            }
        }
    }
    
    public boolean endGame(){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getSize(){
        return SIZE*SIZE;
    }
    
    public int getRowSize(){
        return SIZE;
    }
    
    /*
    private static void print(Flip f){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                System.out.print(f.board[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println(f.minAnswer.size());
        System.out.println(f.minAnswer.toString());
    }
    */
    
    
    
}
