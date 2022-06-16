package com.example.sudoku;

import java.util.ArrayList;
import java.util.Random;

public class Solve {

    int [][] board;
    ArrayList<ArrayList<Object>> emptyBox;
    Random rand = new Random();

    private int selectedRow;
    private int selectedCol;
    private int numOfEmptyCells = 26;


    Solve(){
        selectedCol = -1;
        selectedRow = -1;


        board = new int[9][9];
        randBoard();

        emptyBox = new ArrayList<>();
    }

    private void randBoard(){
        board[0][0] = rand.nextInt(9);
        for(int i = 0; i < numOfEmptyCells;){
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            board[row][col] = rand.nextInt(9);
            while(!check(row,col)){
                int tmp = rand.nextInt(9);
                board[row][col] = tmp;
            }
            ++i;
        }
    }


    public int getSelectedCol() {
        return selectedCol;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedCol(int selectedCol) {
        this.selectedCol = selectedCol;
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public int[][] getBoard(){
        return this.board;
    }

    public ArrayList<ArrayList<Object>> getEmptyBoxIndex(){
        return this.emptyBox;
    }


    public void getEmptyIndexes(){
        for(int rows = 0 ; rows < 9;++rows){
            for(int col = 0; col < 9; ++col){
                if(this.board[rows][col] == 0){
                    this.emptyBox.add(new ArrayList<>());
                    this.emptyBox.get( this.emptyBox.size()-1).add(rows);
                    this.emptyBox.get( this.emptyBox.size()-1).add(col);
                }
            }
        }
    }

    private boolean check(int row, int col){

        if(this.board[row][col] > 0){
            for(int i =0; i < 9 ; ++i){
                if (this.board[i][col] == this.board[row][col] && row != i) {
                    return false;
                }
                if (this.board[row][i] == this.board[row][col] && col != i) {
                    return false;
                }
            }

            int boxRow = row/3;
            int boxCol = col/3;

            for (int r = boxRow*3;r<boxRow*3+3; ++r){
                for(int c = boxCol*3; c <boxCol*3+3; ++c){
                    if(this.board[r][c] == this.board[row][col] && row != r && col !=c){
                         return false;
                    }
                }
            }

        }
        return true;
    }

    public boolean slv(Board display){
        int row = -1;
        int col = -1;


        for(int r = 0; r < 9 ; ++r){
            for(int c = 0 ; c < 9; ++c){
                if(this.board[r][c]==0){
                    row = r;
                    col = c;
                    break;
                }
            }
        }


        if(row == -1 || col == -1){
             return true;
        }

        for(int i =1 ; i <10;++i){
            this.board[row][col] = i;
             display.invalidate();

             if(check(row,col)){
                 if(slv(display)){
                     return true;
                 }
             }

             this.board[row][col] = 0;
        }
        return false;
    }

    public void setNumPosition(int num){
            if (selectedRow != -1 && selectedCol != -1) {
                if (this.board[this.selectedCol - 1][this.selectedRow - 1] == num) {
                    this.board[this.selectedCol - 1][this.selectedRow - 1] = 0;
                } else {
                    this.board[this.selectedCol - 1][this.selectedRow - 1] = num;
                }

            }
    }

    public void resetBoard(){
        for(int rows = 0 ; rows < 9; ++rows){
            for(int col = 0 ; col < 9; ++col){
                board[rows][col] = 0;
            }
        }


        this.emptyBox = new ArrayList<>();

        randBoard();
    }
}
