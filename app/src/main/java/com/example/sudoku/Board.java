package com.example.sudoku;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Board extends View {


    private final int boardColor;
    private final int cellHighlight;
    private final int cellFill;

    private final int letterColor;
    private final int letterColorSolved;


    private final Paint boardColorPaint = new Paint();
    private final Paint cellHighlightPaint= new Paint();
    private final Paint cellFillPaint= new Paint();

    private final Paint letterColorPaint = new Paint();
    private final Rect letterColorBound = new Rect();

    private int cellSize;

    private final Solve solve = new Solve();

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Board,0,0);

        try{

            boardColor = arr.getInteger(R.styleable.Board_boardColor, 0);
            cellFill = arr.getInteger(R.styleable.Board_cellFill, 0);
            cellHighlight = arr.getInteger(R.styleable.Board_cellHighlight, 0);
            letterColor = arr.getInteger(R.styleable.Board_letterColor,0);
            letterColorSolved = arr.getInteger(R.styleable.Board_letterColorSolved,0);

        }finally {
            arr.recycle();
        }

    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width,height);

        int dimension = Math.min(getMeasuredWidth(),getMeasuredHeight());
        cellSize = dimension/9;

        setMeasuredDimension(dimension,dimension);

    }

    @Override
    protected void onDraw(Canvas canvas){

        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(15);
        boardColorPaint.setColor(boardColor);

        boardColorPaint.setAntiAlias(true);


        cellFillPaint.setStyle(Paint.Style.FILL);
        cellFillPaint.setColor(cellFill);

        cellFillPaint.setAntiAlias(true);

        cellHighlightPaint.setStyle(Paint.Style.FILL);
        cellHighlightPaint.setColor(cellHighlight);

        cellHighlightPaint.setAntiAlias(true);


        letterColorPaint.setStyle(Paint.Style.FILL);
        letterColorPaint.setColor(letterColor);
        letterColorPaint.setAntiAlias(true);

        colorCell(canvas,solve.getSelectedRow(),solve.getSelectedCol());
        canvas.drawRect(0,0,this.getWidth(),this.getHeight(),boardColorPaint);
        drawBoard(canvas);
        drawNum(canvas);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean isValid;

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN) {
            solve.setSelectedCol((int) Math.ceil(y/cellSize));
            solve.setSelectedRow((int) Math.ceil(x/cellSize));
            isValid = true;
        }else{
            isValid = false;
        }

        return isValid;
    }


    public Solve getSolved(){
        return this.solve;
    }

    private void colorCell(Canvas canvas, int col, int row){
         if(solve.getSelectedRow() != -1 && solve.getSelectedCol()!= -1){
             canvas.drawRect((col-1) * cellSize,0,col*cellSize,cellSize*9,cellHighlightPaint);
             canvas.drawRect(0,(row-1)* cellSize,cellSize*9,row*cellSize,cellHighlightPaint);
             canvas.drawRect((col-1) * cellSize,(row-1)* cellSize,col*cellSize,row*cellSize,cellHighlightPaint);
         }

         invalidate();
    }


    private void drawThickLines(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(boardColor);
    }

    private void drawThinLines(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(3);
        boardColorPaint.setColor(boardColor);
    }

    private void drawBoard(Canvas canvas){
        for(int col = 0 ; col < 10 ; ++col){
            if(col%3 == 0){
                drawThickLines();
            }else{
                drawThinLines();
            }

            canvas.drawLine(cellSize * col, 0 , cellSize * col,getWidth(),boardColorPaint);
        }

        for(int rows = 0 ; rows< 10 ; ++rows){
            if(rows %3 == 0){
                drawThickLines();
            }else{
                drawThinLines();
            }
            canvas.drawLine(0, cellSize * rows, getWidth(),cellSize*rows,boardColorPaint);
        }

    }

    private void drawNum(Canvas canvas) {

        letterColorPaint.setTextSize(cellSize);

        for (int rows = 0; rows < 9; ++rows) {
            for (int col = 0; col < 9; ++col) {
                if (solve.getBoard()[rows][col] != 0) {
                    String text = Integer.toString(solve.getBoard()[rows][col]);

                    float wight, height;
                    letterColorPaint.getTextBounds(text, 0, text.length(), letterColorBound);
                    wight = letterColorPaint.measureText(text);
                    height = letterColorBound.height();

                    canvas.drawText(text, (col * cellSize) + ((cellSize - wight) / 2), (rows * cellSize + cellSize) - ((cellSize - height) / 2), letterColorPaint);
                }
            }
        }

        letterColorPaint.setColor(letterColorSolved);

        for(ArrayList<Object> letter : solve.getEmptyBoxIndex()){
            int rows = (int)letter.get(0);
            int col = (int)letter.get(1);

            String text = Integer.toString(solve.getBoard()[rows][col]);

            float wight, height;
            letterColorPaint.getTextBounds(text, 0, text.length(), letterColorBound);
            wight = letterColorPaint.measureText(text);
            height = letterColorBound.height();

            canvas.drawText(text, (col * cellSize) + ((cellSize - wight) / 2), (rows * cellSize + cellSize) - ((cellSize - height) / 2), letterColorPaint);
        }
    }
}
