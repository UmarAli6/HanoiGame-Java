import java.util.Scanner;

public class TextUI {

    private final Hanoi model;
    private final Scanner scan;

    public TextUI(int noOfDisks) {
        model = new Hanoi(noOfDisks);
        scan = new Scanner(System.in);
    }

    public void run() {
        int choice1;
        int choice2;
        
        String cont = "y";
        while ("y".equals(cont.toLowerCase())) {
            int[][] board = model.getCopyOfBoard();
            printBoard(board);
            System.out.println("Number of moves: " + model.getNoOfMoves());
            
            do {
                System.out.print("From rod: ");
                choice1 = scan.nextInt();
                if (choice1 == 0 || choice1 == 4) {
                    cont = "n";
                } else {
                    System.out.print("To rod: ");
                    choice2 = scan.nextInt();
                    scan.nextLine();
                    move(choice1, choice2);
                    printBoard(model.getCopyOfBoard());
                    System.out.println("Number of moves: " + 
                                       model.getNoOfMoves());
                }
            } while(model.isSolved() == false && "y".equals(cont.toLowerCase()));
            
            if (model.isSolved() == true) {
                System.out.print("Congratulations! You completed the game in "
                                 + model.getNoOfMoves() + " moves. Do you "
                                 + "want to continue (y/n)?: ");
                cont = scan.nextLine();
                if (cont.toLowerCase().charAt(0) == 'y') {
                    int disks;
                    System.out.print("How many disks do you want to play "
                                     + "with?: ");
                    disks = scan.nextInt();
                    model.reset(disks);
                }
            } else if (model.isSolved() == false && cont.toLowerCase().charAt(0)
                       == 'n') {
                if (choice1 == 4) {
                    int disks;
                    System.out.print("How many disks do you want to play "
                                     + "with?: ");
                    disks = scan.nextInt();
                    model.reset(disks);
                    cont = "y";
                }
            }
        }   
        
        System.out.println("Bye");
        scan.close();
    }
    
    public void move(int choice1, int choice2) {
        Rod from, to;
       
            if (choice1 == 1 && choice2 == 2) {
                from = Rod.LEFT;
                to = Rod.MIDDLE;
            } else if (choice1 == 1 && choice2 == 3) {
                from = Rod.LEFT;
                to = Rod.RIGHT;
            } else if (choice1 == 2 && choice2 == 1) {
                from = Rod.MIDDLE;
                to = Rod.LEFT;
            } else if (choice1 == 2 && choice2 == 3) {
                from = Rod.MIDDLE;
                to = Rod.RIGHT;
            } else if (choice1 == 3 && choice2 == 1) {
                from = Rod.RIGHT;
                to = Rod.LEFT;
            } else if (choice1 == 3 && choice2 == 2) {
                from = Rod.RIGHT;
                to = Rod.MIDDLE; 
            } else {
                System.out.println("Wrong input. Try again.");
                from = Rod.RIGHT;
                to = Rod.RIGHT;
            }
        
        if (model.isLegalMove(from, to) == false) {
            System.out.println("Illegal move. Try again.\n");
        } else {
            model.makeMove(from, to);
        }
        
    }

    public void printBoard(int[][] board) {
        int height = board[0].length; // the number of positions on a rod
        int nRods = board.length; // number of rods, i.e. width
        for (int pos = height - 1; pos >= 0; pos--) {
            String rowStr = "";
            for (int rod = 0; rod < nRods; rod++) {
                if (board[rod][pos] != 0) {
                    rowStr += " " + board[rod][pos];
                } else {
                    rowStr += "  ";
                }
            }
            System.out.println(rowStr);
        }
        String bottomStr = "";
        for (int rod = 0; rod < nRods; rod++) {
            bottomStr += " =";
        }
        System.out.println(bottomStr);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Welcome to Tower of Hanoi!\nHow many disks do you "
                          + "want to play with?: ");
        int noOfDisks = scan.nextInt();
        System.out.println("LEFT = 1, MIDDLE = 2, RIGHT = 3, RESET = 4, "
                            + "QUIT = 0");
        TextUI ui = new TextUI(noOfDisks);
        ui.run();
    }

}