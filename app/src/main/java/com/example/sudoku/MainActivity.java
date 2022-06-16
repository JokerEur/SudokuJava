package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Board gameBoard;
    private Solve gameBoardSolved;

   private Button solveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.Board);
        gameBoardSolved = gameBoard.getSolved();

        solveBtn = findViewById(R.id.solveBtn);
    }

    public void BtnOnePressed(View view){
        gameBoardSolved.setNumPosition(1);
        gameBoard.invalidate();
    }


    public void BtnTwoPressed(View view){
        gameBoardSolved.setNumPosition(2);
        gameBoard.invalidate();
    }

    public void BtnThreePressed(View view){
        gameBoardSolved.setNumPosition(3);
        gameBoard.invalidate();
    }

    public void BtnFourPressed(View view){
        gameBoardSolved.setNumPosition(4);
        gameBoard.invalidate();
    }

    public void BtnFivePressed(View view){
        gameBoardSolved.setNumPosition(5);
        gameBoard.invalidate();
    }

    public void BtnSixPressed(View view){
        gameBoardSolved.setNumPosition(6);
        gameBoard.invalidate();
    }

    public void BtnSevenPressed(View view){
        gameBoardSolved.setNumPosition(7);
        gameBoard.invalidate();
    }

    public void BtnEightPressed(View view){
        gameBoardSolved.setNumPosition(8);
        gameBoard.invalidate();
    }

    public void BtnNinePressed(View view){
        gameBoardSolved.setNumPosition(9);
        gameBoard.invalidate();
    }

    public void solve(View view){
        if(solveBtn.getText().toString().equals(getString(R.string.Solve))){
            solveBtn.setText(getString(R.string.Celear));

            gameBoardSolved.getEmptyIndexes();

            SolveBoardThread solveBoardThread = new SolveBoardThread();

            new Thread(solveBoardThread).start();

            gameBoard.invalidate();

        }else{
            solveBtn.setText(getString(R.string.Solve));

            gameBoardSolved.resetBoard();
            gameBoard.invalidate();
        }
    }


    class SolveBoardThread implements Runnable{
        @Override
        public void run(){
            gameBoardSolved.slv(gameBoard);
        }
    }

}