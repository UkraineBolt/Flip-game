/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flip;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author alex
 */
public class Solver extends RecursiveAction{
    /*
    This game has a specific trait where the solution is symetric.
    This means that if a square is pressed twice, its equivalent to never pressing either of them.
    This means that the number of moves to shuffle the board is the exact solution.
    To save time that number 
    */
    private final boolean[][] Sstate;
    private final int min;
    private final ArrayList<Integer> answer;
    private volatile CopyOnWriteArrayList<Integer> solution;
    
    
    Solver(Flip x){
        this.Sstate = x.board;
        this.min=x.minAnswer.size();
        answer = new ArrayList<>();
        solution = new CopyOnWriteArrayList<>();
    }
    Solver(boolean[][]s,int m,ArrayList<Integer> a,CopyOnWriteArrayList<Integer> so){
        this.Sstate = s;
        this.min=m; 
        this.answer=a;
        this.solution=so;
    }
    
    @Override
    protected void compute() {
        if(goalState()&&solution.isEmpty()){
            solution.addAll(answer);
            System.out.println(solution.toString());
            return;
        }
        if(solution.isEmpty()&&answer.size()<min+1){
            System.out.println(Thread.currentThread().getId()+" preforming tasks: "+answer.toString());
            ArrayList<Solver> tasks = new ArrayList<>();
            tasks.addAll(createSubtasks());
            for(Solver sub : tasks){
                    sub.fork();
                
            }
        }
    }
    
    private ArrayList<Solver> createSubtasks(){
        Flip f = new Flip();
        ArrayList<Solver> subtasks = new ArrayList<>();
        for(int i=1;i<=f.getSize();i++){
            if(!answer.contains(i)){
                ArrayList<Integer> previous = new ArrayList<>();
                previous.addAll(answer);
                f.changeState(copyState());
                f.move(i);
                previous.add(i);
                Solver s = new Solver(f.board,min,previous,solution);
                subtasks.add(s);
            }
        }
        return subtasks;
    }
    
    private boolean[][] copyState(){
        Flip f = new Flip();
        boolean[][] newState = new boolean[f.getRowSize()][f.getRowSize()];
        for(int x=0;x<f.getRowSize();x++){
            for(int y=0;y<f.getRowSize();y++){
                newState[x][y]=Sstate[x][y];
            }
        }
        return newState;
    }
    
    private boolean goalState(){
        Flip f = new Flip();
        f.changeState(Sstate);
        return f.endGame();
    }
    
    public String getSolution(){
        return solution.toString();
    }
}
