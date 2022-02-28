import java.util.Arrays;

/**
 * This class represents functionality for the game Tower of Hanoi. Refer to
 * https://en.wikipedia.org/wiki/Tower_of_Hanoi for rules.
 *
 * @author Umar Ali
 */
public class Hanoi {

    private int[][] board;
    private int noOfDisks;
    private int noOfMoves;

    /**
     * Create a new Hanoi game, with 4 disks.
     */
    public Hanoi() {
        noOfDisks = 4;
        board = new int[Rod.values().length][noOfDisks];
        reset();
    }

    /**
     * Create a new Hanoi game, with the indicated number of disks.
     * @param noOfDisks
     */
    public Hanoi(int noOfDisks) {
        reset(noOfDisks);
    }

    /**
     * Reset for a new round of the game, with the same number of disks as
     * before. Place all disks on the left rod, in ascending order, clear the
     * other rods and reset the number of moves. If the number of disks are
     * changed reallocate the board array.
     */
    public void reset() {
        noOfMoves = 0;
        
        for (int pos = 0; pos < noOfDisks; pos++) {
            board[0][pos] = noOfDisks - pos;
            board[1][pos] = 0;
            board[2][pos] = 0;
        }
    }

    /**
     * Reset for a new round of the game, with a different number of disks.This
     * method allocates a new board array before executing reset().
     *
     * @param noOfDisks the number of disks in the new round
     */
    public void reset(int noOfDisks) {
        this.noOfDisks = noOfDisks;
        board = new int[Rod.values().length][this.noOfDisks];
        reset();
    }

    /**
     * @return the number of moves since the last reset.
     */
    public int getNoOfMoves() {
        return noOfMoves;
    }

    /**
     * @return the number of disks
     */
    public int getNoOfDisks() {
        return noOfDisks;
    }

    /**
     * Return whether it is legal to move a disk between the indicated rods.
     * The fromRod must not be empty. The disk to move must be smaller than the
     * top disk on the toRod, or the toRod empty.
     *
     * @param fromRod the rod to move the top disk from
     * @param toRod the rode to place the disk on
     * @return true if the move is legal, otherwise false
     */
    public boolean isLegalMove(Rod fromRod, Rod toRod) {
        if (getTopIndex(fromRod) != -1) {
            if (getTopSize(fromRod) < getTopSize(toRod) || getTopIndex(toRod) == -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * If the move is legal, move the top disk from the fromRod to the toRod.
     *
     * @param fromRod the rod to move the top disk from
     * @param toRod the rod to place the disk on
     */
    public void makeMove(Rod fromRod, Rod toRod) {
        if (isLegalMove(fromRod, toRod) == true) {
            int from = fromRod.getIndex();
            int to = toRod.getIndex();

            int diskValue = getTopSize(fromRod);
            
            board[from][getTopIndex(fromRod)] = 0;
            board[to][getTopIndex(toRod) + 1] = diskValue;

            noOfMoves++;
        }
    }

    /**
     * Return whether the game is solved, i.e. all disks are at the right rod in
     * ascending order.
     *
     * @return true if the game is solved
     */
    public boolean isSolved() {
        return (getTopIndex(Rod.RIGHT) == (noOfDisks - 1) &&
                getTopSize(Rod.RIGHT) == 1);
    }

    /**
     * Return a <em>copy</em> of the internal board, a two-dimensional int
     * array, dimensions [no of rods][number of disks]. The value of a single
     * field represents the size of the disk; zero represents an empty position.
     *
     * @return a copy of the board
     */
    public int[][] getCopyOfBoard() {
        int[][] temp = new int[Rod.values().length][noOfDisks];
        
        for (int i = 0; i < temp.length; ++i) {
            temp[i] = new int[board[i].length];
            for (int x = 0; x < temp[i].length; ++x) {
                temp[i][x] = board[i][x];
            }
        }
        return temp;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (Rod rod : Rod.values()) {
            builder
                    .append(Arrays.toString(board[rod.getIndex()]))
                    .append(", ");
        }
        return builder.append(noOfMoves).toString();
    }

    // private helper methods
    // 0 represents empty rod
    public int getTopSize(Rod rod) {
        int tempSize, tempIndex = getTopIndex(rod);
        if (tempIndex == -1){
            return 0;
        } else {
            tempSize = board[rod.getIndex()][tempIndex];
            return tempSize;
        }
    }
    
    // -1 represents empty rod
    private int getTopIndex(Rod rod) {
        int temp, i = 0;
        boolean check;
        do {
            temp = board[rod.getIndex()][i];
            if (temp == 0) {
                check = false;
            } else {
                check = true;
                i++;
            }
        }while (check == true && i < noOfDisks);
        
        if (i <= noOfDisks) {
            return i-1;
        } else
            return -1;
    }
}
