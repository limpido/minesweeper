package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class MineSweeper {
    private char[][] minefield;
    private char[][] field;
    private final int ROW;
    private final int COL;
    private final int mines;
    private int minesFound = 0;

    public MineSweeper(int row, int col, int mines) {
        this.mines = mines;
        this.ROW = row;
        this.COL = col;
        this.initMinefield();
        this.setMines();
        this.countMines();
        this.initField();
    }

    public void discloseMines() {
        for (int r=0; r<this.ROW; r++) {
            for (int c=0; c<this.COL; c++) {
                if (isMine(r, c)) {
                    this.field[r+2][c+2] = 'X';
                }
            }
        }
    }

    public void exploreCell(int x, int y) {
        int r = y-1;
        int c = x-1;
        this.dfs(r, c);
    }

    private void dfs(int r, int c) {
        if (r < 0 || r >= this.ROW || c < 0 || c >= this.COL || isMine(r, c) || this.field[r+2][c+2] != '.' && this.field[r+2][c+2] != '*') {
            return;
        }
        if (isNumber(r, c)) {
            this.field[r+2][c+2] = this.minefield[r][c];
            return;
        }

        this.field[r+2][c+2] = '/';
        this.dfs(r-1, c);
        this.dfs(r-1, c-1);
        this.dfs(r-1, c+1);
        this.dfs(r, c-1);
        this.dfs(r, c+1);
        this.dfs(r+1, c-1);
        this.dfs(r+1, c);
        this.dfs(r+1, c+1);
    }

    public void setMineMark(int row, int col) {
        if (this.field[row+1][col+1] == '*') { // unmark
            this.field[row+1][col+1] = '.';
            if (this.minefield[row-1][col-1] == 'X') {
                this.minesFound = Math.max(0, this.minesFound-1);
            }
        } else { // mark
            this.field[row+1][col+1] = '*';
            if (this.minefield[row-1][col-1] == 'X') {
                this.minesFound++;
            }
        }
    }

    public boolean isNumber(int row, int col) {
        return this.minefield[row][col] > '0' && this.minefield[row][col] < '9';
    }

    public boolean isMine(int row, int col) {
        return this.minefield[row][col] == 'X';
    }

    public boolean checkWin() {
        if (this.minesFound == this.mines) {
            return true;
        }
        for (int r=0; r<this.ROW; r++) {
            for (int c=0; c<this.COL; c++) {
                if (this.minefield[r][c] == '.' && this.field[r+2][c+2] == '/')
                    continue;
                else if (this.minefield[r][c] == 'X' && (this.field[r+2][c+2] == '.' || this.field[r+2][c+2] == '*'))
                    continue;
                else if (this.minefield[r][c] == this.field[r+2][c+2])
                    continue;
                else return false;
            }
        }
        return true;
    }

    private void initMinefield() {
        this.minefield = new char[this.ROW][this.COL];
        for (char[] chars : this.minefield) {
            Arrays.fill(chars, '.');
        }
    }

    private void initField() {
        int ROW = this.ROW+3;
        int COL = this.COL+3;
        this.field = new char[ROW][COL];
        for (int r=0; r<ROW; r++) {
            this.field[r][1] = '|';
            this.field[r][COL-1] = '|';
            if (r == 1 || r == ROW-1) {
                this.field[r][0] = '-';
                for (int c=2; c<COL-1; c++) {
                    this.field[r][c] = '-';
                }
            } else if (r == 0) {
                this.field[r][0] = ' ';
                for (int c=2; c<COL-1; c++) {
                    this.field[r][c] = (char) (c-1+'0');
                }
            } else {
                this.field[r][0] = (char) (r-1+'0');
                for (int c=2; c<COL-1; c++) {
                    this.field[r][c] = '.';
                }
            }
        }
    }

    private void setMines() {
        Random rand = new Random();
        int fieldSize = this.ROW * this.COL;
        int k = 0;
        while (k < this.mines) {
            int n = rand.nextInt(fieldSize);
            int r = n / this.ROW;
            int c = n - r*this.ROW;
            if (this.minefield[r][c] == '.') {
                this.minefield[r][c] = 'X';
                k += 1;
            }
        }
    }

    private void countMines() {
        for (int r=0; r<this.ROW; r++) {
            for (int c=0; c<this.COL; c++) {
                if (this.minefield[r][c] == 'X') {
                    continue;
                }
                char count = '0';
                count += this.countMineHelper(r-1, c-1);
                count += this.countMineHelper(r-1, c);
                count += this.countMineHelper(r-1, c+1);
                count += this.countMineHelper(r, c-1);
                count += this.countMineHelper(r, c+1);
                count += this.countMineHelper(r+1, c-1);
                count += this.countMineHelper(r+1, c);
                count += this.countMineHelper(r+1, c+1);
                if (count > '0') {
                    this.minefield[r][c] = count;
                }
            }
        }
    }

    private int countMineHelper(int row, int col) {
        if (row < 0 || row >= this.ROW || col < 0 || col >= this.COL) {
            return 0;
        }
        if (this.minefield[row][col] == 'X') {
            return 1;
        }
        return 0;
    }

    public void printMinefield() {
        for (char[] row : this.minefield) {
            System.out.println(new String(row));
        }
    }

    public void printField() {
        for (char[] row : this.field) {
            System.out.println(new String(row));
        }
    }
}
