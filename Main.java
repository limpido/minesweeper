package minesweeper;
import java.util.Scanner;

public class Main {
    final static int ROW = 9;
    final static int COL = 9;
    static MineSweeper mineSweeper;

    public static void main(String[] args) {
        int numOfMines = getNumOfMines();
        mineSweeper = new MineSweeper(ROW, COL, numOfMines);
        boolean failed = false;
        while (!failed && !mineSweeper.checkWin()) {
            mineSweeper.printField();
            String[] input = askPlayerMove();
            int[] coordinates = new int[2];
            for (int i=0; i<2; i++) {
                coordinates[i] = Integer.parseInt(input[i]);
            }
            switch (input[2]) {
                case "free":
                    if (mineSweeper.isMine(coordinates[1]-1, coordinates[0]-1)) {
                        failed = true;
                        break;
                    }
                    mineSweeper.exploreCell(coordinates[0], coordinates[1]);
                    break;
                case "mine":
                    mineSweeper.setMineMark(coordinates[1], coordinates[0]);
                    break;
                default:
                    break;
            }
        }
        if (failed) {
            mineSweeper.discloseMines();
            mineSweeper.printField();
            System.out.println("You stepped on a mine and failed!");
        } else {
            mineSweeper.printField();
            System.out.println("Congratulations! You found all mines!");
        }
    }

    public static String[] askPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Set/unset mines marks or claim a cell as free:");
        return scanner.nextLine().split(" ");
    }

    public static int getNumOfMines() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        return scanner.nextInt();
    }
}