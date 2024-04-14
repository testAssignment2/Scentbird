package com.example.demo.model;

public class Move {
    private int row; // Row number
    private int col; // Column number

    // Constructor
    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getters and setters
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
