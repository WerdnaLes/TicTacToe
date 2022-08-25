package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class TicTacToeBoard extends View {

    private final float ROUND_CORNERS = 0.2f;
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;
    private final Paint paint = new Paint();
    private int cellSize = getWidth() / 3;
    private final GameLogic game;
    // ширина і висота в'ю
    private RectF board;
    private final Cell[] cells = new Cell[9];

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredHeight(), getMeasuredWidth());
        cellSize = dimension / 3;
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        board = new RectF(0.0f, 0.0f, (float) w, (float) h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if (game.isGameWon()) {
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(event.getY() / cellSize);
            int col = (int) Math.ceil(event.getX() / cellSize);
            for (int i = 0; i < 9; i++) {
                Cell cell = cells[i];
                if (cell.row == row - 1 && cell.col == col - 1) {
                    if (game.updateGameBoard(i))
                        invalidate();
                }
            }
            invalidate();
            return true;
        }
        return false;
    }

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCell();

        game = new GameLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard, 0, 0);

        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);
        } finally {
            a.recycle();
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(16);
    }

    public enum TicTacToeModel {
        SPACE,
        X,
        O
    }

    public void setUpGame(Button playAgain, Button home, TextView playerTurn) {
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerTurn);
    }

    public void reset() {
        game.resetGame();
    }

    // виклик в конструкторі
    private void initCell() {
        int c = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[c] = new Cell(i, j);
                c++;
            }
        }
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);

        for (int c = 1; c < 3; c++) {
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getWidth(), paint);
        }
        for (int r = 1; r < 3; r++) {
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);
        }
    }

    private void drawMarkers(Canvas canvas) {
        for (int i = 0; i < 9; i++) {
            if (game.getGameBoard()[i] != TicTacToeModel.SPACE) {
                if (game.getGameBoard()[i] == TicTacToeModel.X) {
                    drawX(canvas, cells[i].getRow(), cells[i].getCol());
                }
                if (game.getGameBoard()[i] == TicTacToeModel.O) {
                    drawO(canvas, cells[i].getRow(), cells[i].getCol());
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(XColor);
        canvas.drawLine(((col + 1) * cellSize - cellSize * ROUND_CORNERS),
                (row * cellSize + cellSize * ROUND_CORNERS),
                (col * cellSize + cellSize * ROUND_CORNERS),
                ((row + 1) * cellSize - cellSize * ROUND_CORNERS),
                paint);

        canvas.drawLine((col * cellSize + cellSize * ROUND_CORNERS),
                (row * cellSize + cellSize * ROUND_CORNERS),
                ((col + 1) * cellSize - cellSize * ROUND_CORNERS),
                ((row + 1) * cellSize - cellSize * ROUND_CORNERS),
                paint);
    }

    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(OColor);
        canvas.drawOval((col * cellSize + cellSize * ROUND_CORNERS),
                (row * cellSize + cellSize * ROUND_CORNERS),
                ((col * cellSize + cellSize) - cellSize * ROUND_CORNERS),
                ((row * cellSize + cellSize) - cellSize * ROUND_CORNERS),
                paint);
    }

    private void drawHorizontalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col, row * cellSize + (float) cellSize / 2,
                cellSize * 3, row * cellSize + (float) cellSize / 2,
                paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col * cellSize + (float) cellSize / 2, row,
                col * cellSize + (float) cellSize / 2, cellSize * 3,
                paint);
    }

    private void drawDiagonalLinePos(Canvas canvas) {
        canvas.drawLine(0, cellSize * 3, cellSize * 3, 0,
                paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas) {
        canvas.drawLine(0, 0, cellSize * 3, cellSize * 3,
                paint);
    }

    private void drawWinningLine(Canvas canvas) {
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2]) {
            case 1:
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawVerticalLine(canvas, row, col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
        }
    }

    private static class Cell {
        private final int row;
        private final int col;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }
}
