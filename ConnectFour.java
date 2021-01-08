import java.util.Scanner;

/**
 * CS312 Assignment 10.
 *
 * On my honor, Julia, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 *  email address:juliajoseph65@gmail.com
 *  UTEID:jaj4598
 *  TA name: Alec
 *  Number of slip days used on this assignment:1
 *
 * Program that allows two people to play Connect Four.
 */


public class ConnectFour {
    final static int ROWS = 6;
    final static int COLUMNS = 7;
    final static char PLAYER1LETTER = 'r';
    final static char PLAYER2LETTER = 'b';

    public static void main(String[] args) {
        char playerLetter;
        String playerName, prompt;
        int count = 0, answer = 0;
        Scanner key = new Scanner(System.in);

        intro();
        char[][] board = initializeBoard();
        String player1Name = getName(key, 1);
        String player2Name = getName(key, 2);
        printBoard(board, false);

        boolean loop = true;
        while (loop) {
            if (count % 2 == 0) {
                playerLetter = PLAYER1LETTER;
                playerName = player1Name;
            } else {
                playerLetter = PLAYER2LETTER;
                playerName = player2Name;
            }
            prompt = playerName + ", enter the column to drop your checker: ";
            switchTurns(playerName, playerLetter, prompt);
            int rowIndex = -1;
            while (rowIndex == -1) {
                answer = getCheckerColumn(prompt, key);
                rowIndex = updateBoard(board, answer, playerLetter, prompt);
            }

            boolean isWon = checkIfWon(board,playerLetter, answer, rowIndex);
            boolean isFull = checkFullBoard(board);

            if(!isWon && isFull) {
                System.out.println("The game is a draw");
                System.out.println();
                printBoard(board, true);
                loop = false;
            } else if (!isWon){
                printBoard(board, false);
                count++;
            } else {
                System.out.println();
                System.out.println(playerName + " wins!!");
                System.out.println();
                printBoard(board, true);
                loop = false;
            }
        }
        key.close();
    }

    public static boolean checkIfWon(char[][] board, char playerLetter, int answer,
                                     int rowIndex)
    {
        boolean isWon = checkVertical(board, playerLetter, answer, rowIndex);
        if (!isWon)
        {
            isWon = checkHorizontal(board, playerLetter, rowIndex);
        }
        if (!isWon)
        {
            isWon = checkSouthEast(board, playerLetter);
        }
        if (!isWon)
        {
            isWon = checkSouthWest(board, playerLetter);
        }
        return isWon;
    }

    // show the intro
    public static void intro() {
        System.out.println("This program allows two people to play the");
        System.out.println("game of Connect four. Each player takes turns");
        System.out.println("dropping a checker in one of the open columns");
        System.out.println("on the board. The columns are numbered 1 to 7.");
        System.out.println("The first player to get four checkers in a row");
        System.out.println("horizontally, vertically, or diagonally wins");
        System.out.println("the game. If no player gets fours in a row and");
        System.out.println("and all spots are taken the game is a draw.");
        System.out.println("Player one's checkers will appear as r's and");
        System.out.println("player two's checkers will appear as b's.");
        System.out.println("Open spaces on the board will appear as .'s.\n");
    }

    // This retrieves the players' names
    public static String getName(Scanner key, int playerNum) {
        System.out.print("Player " + playerNum + " enter your name: ");
        String name = key.next();
        System.out.println();
        return name;
    }

    // This prints the board
    public static void printBoard(char[][] board, boolean finalText) {

          if (!finalText) {
              System.out.println();
              System.out.println("Current Board");
        } else {
            System.out.println("Final Board");
        }
        for (int i = 1; i <= ROWS + 1; i++) {
            System.out.print(i + " ");
        }
        System.out.println(" column numbers");
        for (int l = 0; l < ROWS; l++) {
            for (int n = 0; n < COLUMNS; n++) {
                System.out.print(board[l][n] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //This checks whether all the slots are occupied
    public static boolean checkFullBoard(char[][] board){
        int counter = 0;
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j ++){
                if (board[i][j] == '.'){
                    counter ++;
                }
            }

        }
        return counter == 0;
    }

    // This initializes the board
    public static char[][] initializeBoard() {
        char[][] board = new char[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = '.';
            }
        }
        return board;
    }

    // This switches the player's turns
    public static void switchTurns(String playerName, char letter,
                                   String prompt) {
        System.out.println(playerName + " it is your turn.");
        System.out.println("Your pieces are the " + letter + "'s.");
        System.out.print(prompt);
    }

    // Prompt the user for an int. The String prompt will
    // be printed out. I expect key is connected to System.in.
    public static int getInt(Scanner key, String prompt) {
        while (!key.hasNextInt()) {
            String notAnInt = key.nextLine();
            System.out.println();
            System.out.println(notAnInt + " is not an integer.");
            System.out.print(prompt);
        }
        int result = key.nextInt();
        key.nextLine();
        return result;
    }

    // This checks whether the input is a valid column
    public static int getCheckerColumn(String prompt, Scanner key) {
        boolean foundAnswer = false;
        int answer = -1;

        while (!foundAnswer) {
            answer = getInt(key, prompt);
            if (answer > COLUMNS || answer <= 0) {
                System.out.println();
                System.out.println(answer + " is not a valid column.");
                System.out.print(prompt);
            } else {
                foundAnswer = true;
            }
        }
        return answer;
    }

    // This updates the board and places the user's input into the board
    public static int updateBoard(char[][] board, int answer, char playerLetter,
                                  String prompt) {
        int counter = ROWS - 1;
        boolean isSlotOccupied = true;

        while (counter >= 0 && isSlotOccupied) {
            if (board[counter][answer - 1] == '.')
                isSlotOccupied = false;
            counter--;
        }

        if (!isSlotOccupied) {
            counter++;
            board[counter][answer - 1] = playerLetter;
        }

        if (counter == -1) {
            System.out.println();
            System.out.println(answer + " is not a legal column. That column is full");
            System.out.print(prompt);
        }

        return counter;
    }

    // This checks whether there's four in a row horizontally
    public static boolean checkHorizontal(char[][] board, char playerLetter, int rowIndex) {
        int counter = 0;
        int j = 0;
        while (j < COLUMNS) {

            if (board[rowIndex][j] == playerLetter) {
                counter += 1;
            } else {
                counter = 0;
            }
            j++;
            if (counter >= 4) {
                return true;
            }
        }
        return false;
    }

    // This checks whether there's four in a row in column
   public static boolean checkVertical(char[][] board, char playerLetter, int answer,
                                       int rowIndex){
        int i = rowIndex;
        int j = answer - 1;
        int counter = 0;
        while(i < ROWS){
            if (board[i][j] == playerLetter) {
                counter += 1;
            } else {
                counter = 0;
            }
            i++;
            if (counter >= 4) {
                return true;
            }
        }
       return false;
   }

    // This checks whether there's four in a row diagonally down east
    public static boolean checkSouthEast(char[][] board, char playerLetter) {
        for(int row = 0; row <= 2; row++){
            for (int column = 0; column <= 3; column++) {
                if (board[row][column] == playerLetter &&
                board[row + 1][column + 1] == playerLetter &&
                board[row + 2][column + 2] == playerLetter &&
                board[row + 3][column + 3] == playerLetter) {
                    return true;
                }
            }
        }
    return false;
    }

    // This checks whether there's four in a row diagonally down west
    public static boolean checkSouthWest(char[][] board, char playerLetter){
        for(int row = 0; row <= 2; row++){
            for (int column = 3; column < COLUMNS; column ++) {
                if (board[row][column] == playerLetter &&
                        board[row + 1][column - 1] == playerLetter &&
                        board[row + 2][column - 2] == playerLetter &&
                        board[row + 3][column - 3] == playerLetter) {
                    return true;
                }
            }
        }
        return false;
    }
}
