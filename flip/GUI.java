/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flip;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author alex
 */
public class GUI extends JFrame implements ActionListener{
    JPanel master = new JPanel();
    JPanel p=new JPanel();
    JPanel bot = new JPanel();
    JPanel answerKey = new JPanel();
    Flip f = new Flip();
    JLabel min;
    JTextField output;
    JLabel turn;
    int turns = 0;
    Thread out;
    
    GUI(){
        this.setSize(1000, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        answerKey.setLayout(new BoxLayout(answerKey,BoxLayout.X_AXIS));
        p.setLayout(new GridLayout(5,5));
        bot.setLayout(new BoxLayout(bot,BoxLayout.X_AXIS));
        master.setLayout(new BoxLayout(master,BoxLayout.PAGE_AXIS));
        answerKey.setSize(600,100);
        
        for(int j=0;j<25;j++){
            JButton b = new JButton(Integer.toString(j+1));
            b.setActionCommand(Integer.toString(j+1));
            
            b.addActionListener(e -> {
                try{
                int i = Integer.parseInt(e.getActionCommand());
                System.out.println(i);
                f.move(i);
                this.turns++;
                this.actionPerformed(null);
                }catch(Exception p){
                    System.out.println(e.getActionCommand());
                }
            });//end of action
            
            b.setBackground(Color.white);
            p.add(b);
            
        }
        //p.setSize(600,600);
        
        
        master.add(p);
        
        
        JButton ng = new JButton("New Game");
        ng.addActionListener(e->{
            this.cancelPreviousTasks();
            f.newBoard();
            turns=0;
            this.actionPerformed(null);
            this.min.setText("Max moves: "+f.minAnswer.size());
        });
        
        JButton solve = new JButton("Solve");
        solve.addActionListener(e->{
            Solver s = new Solver(f);
            this.cancelPreviousTasks();
            out = new Thread(new outputReader(s,output));
            out.start();
        });
        min = new JLabel("Max moves: ");
        JButton clear = new JButton("Clear");
        clear.addActionListener(e->{
            this.cancelPreviousTasks();
            turns=0;
            f.clearBoard();
            min.setText("Max moves: ");
            this.actionPerformed(null);
        });
        
        turn = new JLabel("Turns: "+turns);
        
        bot.add(clear);
        bot.add(Box.createHorizontalStrut(10));
        bot.add(ng);
        bot.add(Box.createHorizontalStrut(10));
        bot.add(solve);
        bot.add(Box.createHorizontalStrut(10));
        bot.add(min);
        bot.add(Box.createHorizontalStrut(10));
        bot.add(turn);
        
        output = new JTextField("");
        output.setEditable(false);
        output.setSize(500, 100);
        answerKey.add(output);
        
        
        master.add(bot);
        master.add(answerKey);
        this.add(master);
        this.setVisible(true);
    }
    
    public void cancelPreviousTasks(){
        if(out!=null&&out.isAlive()){
                out.interrupt();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int dex=0;
        for(int x=0;x<f.board.length;x++){
            for(int y=0;y<f.board[x].length;y++){
                if(f.board[x][y]){
                    p.getComponent(dex).setBackground(Color.black);
                }else{
                    p.getComponent(dex).setBackground(Color.white);
                }
                dex++;
            }
        }
        p.updateUI();
        
        turn.setText("Turns: "+turns);
    }
    
    private class outputReader implements Runnable{
        Solver x;
        JTextField out;
        
        outputReader(Solver so,JTextField label){
            x=so; out=label;
        }
        
        @Override
        public void run() {
            x.compute();
            int count =-1;
            //out.setText("loading answer");
            String text1 = "finding answer";
            String text2 = "finding answer.";
            String text3 = "finding answer..";
            String text4 = "finding answer...";
            while(x.getSolution().equals("[]")){
                count++;
                switch (count) {
                    case 0:
                        out.setText(text1);
                        break;
                    case 1:
                        out.setText(text2);
                        break;
                    case 2:
                        out.setText(text3);
                        break;
                    default:
                        out.setText(text4);
                        count=-1;
                        break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    return;
                }
            }
            out.setText(x.getSolution());
            
        }
        
    }

}
