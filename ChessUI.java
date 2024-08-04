// Author: William Curtis
import javax.swing.*;
import java.awt.*;

public class ChessUI {
    public static GamePiece[][] newBoard() {
        /* Creates a new 8-by-8 board with the game pieces placed in their normal starting possitions 
         * 
         * Example of the board layout (lowercase is black, uppercase is white, and the index values of each tile can be found on the left and top sides)
         * i  0 1 2 3 4 5 6 7
         * 0  r h b q k b h r
         * 1  p p p p p p p p
         * 2  . . . . . . . .
         * 3  . . . . . . . .
         * 4  . . . . . . . .
         * 5  . . . . . . . .
         * 6  P P P P P P P P
         * 7  R H B Q K B H R
         */
        // Create the blank board
        GamePiece[][] board = new GamePiece[Globals.ROWS][Globals.COLS];
        // Add Pawns
        for (int col = 0; col < board.length; col++) {
            board[1][col] = new Pawn("Black");
            board[6][col] = new Pawn("White");
        }
        // Add Rooks
        board[0][0] = new Rook("Black");
        board[0][7] = new Rook("Black");
        board[7][0] = new Rook("White");
        board[7][7] = new Rook("White");
        // Add Kights
        board[0][1] = new Knight("Black");
        board[0][6] = new Knight("Black");
        board[7][1] = new Knight("White");
        board[7][6] = new Knight("White");
        // Add Bishops
        board[0][2] = new Bishop("Black");
        board[0][5] = new Bishop("Black");
        board[7][2] = new Bishop("White");
        board[7][5] = new Bishop("White");
        // Add Queens
        board[0][3] = new Queen("Black");
        board[7][3] = new Queen("White");
        // Add Kings
        board[0][4] = new King("Black");
        board[7][4] = new King("White");
        return board;
    }

    public static void printBoard(GamePiece[][] board, String playerTurn) {
        /* Prints the state of the board after every turn
         * This will not be visible from the GUI but is useful for debugging or creating a record of each turn in a separate file
         */
        String type;
        System.out.println(playerTurn + "'s Turn");
        System.out.println("i  0 1 2 3 4 5 6 7");
        for (int row = 0; row < board.length; row ++) {
            System.out.print(row + "  ");
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == null) { // Places '.' when there is no piece at the location
                    System.out.print(". ");
                }
                else if (board[row][col].getColour().equals("White")) {
                    type = board[row][col].getType();
                    if (type.equals("Pawn")) {
                        System.out.print("P ");
                    }
                    else if (type.equals("Rook")) {
                        System.out.print("R ");
                    }
                    else if (type.equals("Bishop")) {
                        System.out.print("B ");
                    }
                    else if (type.equals("Knight")) {
                        System.out.print("H ");
                    }
                    else if (type.equals("Queen")) {
                        System.out.print("Q ");
                    }
                    else if (type.equals("King")) {
                        System.out.print("K ");
                    }
                }
                else if (board[row][col].getColour().equals("Black")) {
                    type = board[row][col].getType();
                    if (type.equals("Pawn")) {
                        System.out.print("p ");
                    }
                    else if (type.equals("Rook")) {
                        System.out.print("r ");
                    }
                    else if (type.equals("Bishop")) {
                        System.out.print("b ");
                    }
                    else if (type.equals("Knight")) {
                        System.out.print("h ");
                    }
                    else if (type.equals("Queen")) {
                        System.out.print("q ");
                    }
                    else if (type.equals("King")) {
                        System.out.print("k ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void updateStatus(String message) {
        /* Updates the status bar under the chess board GUI */
        Globals.status.setText("Game Status: " + message);
    }

    public static void updateBoard(GamePiece[][] board) {
        /* Syncs the boards and updates the icon for every tile */
        for (int row = 0; row < Globals.ROWS; row++) {
            for (int col = 0; col < Globals.COLS; col++) {
                Globals.grid[row][col].setPiece(board[row][col]);
                Globals.grid[row][col].updateIcon();
            }
        }
    }

    public static boolean validMove(GamePiece[][] board, int[] from, int[] to, String playerTurn) {
        /* Checks if the given move is valid */
        // Generic restrictions
        if (from[0] < 0 || from[0] >= Globals.ROWS || from[1] < 0 || from[1] >= Globals.COLS) { // Checks if from location is out of bounds
            return false;
        }
        if (to[0] < 0 || to[0] >= Globals.ROWS || to[1] < 0 || to[1] >= Globals.COLS) { // Checks if to location is out of bounds
            return false;
        }
        GamePiece Piece = board[from[0]][from[1]];
        if (board[from[0]][from[1]] == null) { // Checks if a blank tile is trying to be moved
            return false;
        }
        if (!Piece.getColour().equals(playerTurn)) { // Can't move opponent's pieces
            return false;
        }
        if (board[to[0]][to[1]] != null && board[to[0]][to[1]].getColour().equals(Piece.getColour())) { // Can't land on own pieces
            return false;
        }
        if (from[0] == to[0] && from[1] == to[1]) { // Can't stay still
            return false;
        }
        // Pawn
        if (Piece.getType().equals("Pawn")) {
            if (from[1] - to[1] == 0) { // If same Column
                if (board[to[0]][to[1]] != null) { // Can't Capture
                    return false;
                }
                if (Piece.getColour().equals("White")) {
                    if (from[0] - to[0] == 1) { // Single move
                        return true;
                    }
                    else if (from[0] - to[0] == 2 && ((Pawn) Piece).isFirstMove() && board[from[0] - 1][from[1]] == null) { // Double move
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else if (Piece.getColour().equals("Black")) {
                    if (to[0] - from[0] == 1) { // Single move
                        return true;
                    }
                    else if (to[0] - from[0] == 2 && ((Pawn) Piece).isFirstMove() && board[from[0] + 1][from[1]] == null) { // Double move
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            else if (Math.abs(from[1] - to[1]) == 1) { // If adjacent Column
                if (board[to[0]][to[1]] == null) { // Must land on an opposing piece unless it's capturing via En Passant
                    // Makes sure there is a valid piece to En Passant
                    if (from[1] - to[1] == 1 && board[from[0]][from[1] - 1] != null && board[from[0]][from[1] - 1].getType().equals("Pawn") && ((Pawn) board[from[0]][from[1] - 1]).isEnPassantable()) {
                        // Makes sure the piece is moving in the right direction
                        if ((from[0] - to[0] < 0 && Piece.getColour().equals("Black")) || (from[0] - to[0] > 0 && Piece.getColour().equals("White"))) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                    // Makes sure there is a valid piece to En Passant
                    else if (board[from[0]][from[1] + 1] != null && board[from[0]][from[1] + 1].getType().equals("Pawn") && ((Pawn) board[from[0]][from[1] + 1]).isEnPassantable()) {
                        // Makes sure the piece is moving in the right direction
                        if ((from[0] - to[0] < 0 && Piece.getColour().equals("Black")) || (from[0] - to[0] > 0 && Piece.getColour().equals("White"))) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                    else {
                        return false;
                    }
                }
                // Normal capturing
                if (Piece.getColour().equals("White")) {
                    if (Math.abs(from[1] - to[1]) == 1 && from[0] > to[0]) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else if (Piece.getColour().equals("Black")) {
                    if (Math.abs(from[1] - to[1]) == 1 && from[0] < to[0]) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            else {
                return false;
            }
        }
        // Rook
        else if (Piece.getType().equals("Rook")) {
            if (from[0] != to[0] && from[1] != to[1]) { // Checks if it is moving into a different row and column
                return false;
            }
            // Checks if there is a piece in its way (column)
            else if (from[0] == to[0]) {
                if (from[1] > to[1]) {
                    for (int i = to[1] + 1; i < from[1]; i++) {
                        if (board[from[0]][i] != null) {
                            return false;
                        }
                    }
                }
                else {
                    for (int i = from[1] + 1; i < to[1]; i++) {
                        if (board[from[0]][i] != null) {
                            return false;
                        }
                    }
                }
            }
            // Checks if there is a piece in its way (row)
            else if (from[1] == to[1]) {
                if (from[0] > to[0]) {
                    for (int i = to[0] + 1; i < from[0]; i++) {
                        if (board[i][from[1]] != null) {
                            return false;
                        }
                    }
                }
                else {
                    for (int i = from[0] + 1; i < to[0]; i++) {
                        if (board[i][from[1]] != null) {
                            return false;
                        }
                    }
                }
            }
        }
        // Bishop
        else if (Piece.getType().equals("Bishop")) {
            if (Math.abs(from[0] - to[0]) == Math.abs(from[1] - to[1])) { // Must move in diagonals
                if (from[0] > to[0] && from[1] > to[1]) { // Checks if there is a piece in its way (Up-Left)
                    for (int i = 1; i < Math.abs(from[0] - to[0]); i++) {
                        if (board[from[0] - i][from[1] - i] != null) {
                            return false;
                        }
                    }
                }
                else if (from[0] < to[0] && from[1] < to[1]) { // Checks if there is a piece in its way (Down-Right)
                    for (int i = 1; i < Math.abs(from[0] - to[0]); i++) {
                        if (board[from[0] + i][from[1] + i] != null) {
                            return false;
                        }
                    }
                }
                else if (from[0] > to[0]) {
                    for (int i = 1; i < Math.abs(from[0] - to[0]); i++) { // Checks if there is a piece in its way (Up-Right)
                        if (board[from[0] - i][from[1] + i] != null) {
                            return false;
                        }
                    }
                }
                else if (from[1] > to[1]) {
                    for (int i = 1; i < Math.abs(from[0] - to[0]); i++) { // Checks if there is a piece in its way (Down-Left)
                        if (board[from[0] + i][from[1] - i] != null) {
                            return false;
                        }
                    }
                }
                return true;
            }
            else {
                return false;
            }
        }
        // Knight
        else if (Piece.getType().equals("Knight")) {
            if (Math.abs(from[0] - to[0]) == 1) { // One tile up
                if (Math.abs(from[1] - to[1]) == 2) { // Two tiles sideways
                    return true;
                }
                return false;
            }
            else if (Math.abs(from[0] - to[0]) == 2) { // Two tiles up
                if (Math.abs(from[1] - to[1]) == 1) { // One tile sideways
                    return true;
                }
                return false;
            }
            else {
                return false;
            }
        }
        // Queen
        else if (Piece.getType().equals("Queen")) {
            if (from[0] == to[0]) { // Checks if there is a piece in its way (column)
                if (from[1] > to[1]) {
                    for (int i = to[1] + 1; i < from[1]; i++) {
                        if (board[from[0]][i] != null) {
                            return false;
                        }
                    }
                }
                else {
                    for (int i = from[1] + 1; i < to[1]; i++) {
                        if (board[from[0]][i] != null) {
                            return false;
                        }
                    }
                }
            }
            else if (from[1] == to[1]) { // Checks if there is a piece in its way (row)
                if (from[0] > to[0]) {
                    for (int i = to[0] + 1; i < from[0]; i++) {
                        if (board[i][from[1]] != null) {
                            return false;
                        }
                    }
                }
                else {
                    for (int i = from[0] + 1; i < to[0]; i++) {
                        if (board[i][from[1]] != null) {
                            return false;
                        }
                    }
                }
            }
            else if (Math.abs(from[0] - to[0]) == Math.abs(from[1] - to[1])) { // Diagonals
                if (from[0] > to[0] && from[1] > to[1]) { // Checks if there is a piece in its way (Up-Left)
                    for (int i = 1; i < Math.abs(from[0] - to[0]); i++) {
                        if (board[from[0] - i][from[1] - i] != null) {
                            return false;
                        }
                    }
                }
                else if (from[0] < to[0] && from[1] < to[1]) { // Checks if there is a piece in its way (Down-Right)
                    for (int i = 1; i < Math.abs(from[0] - to[0]); i++) {
                        if (board[from[0] + i][from[1] + i] != null) {
                            return false;
                        }
                    }
                }
                else if (from[0] > to[0]) {
                    for (int i = 1; i < Math.abs(from[0] - to[0]); i++) { // Checks if there is a piece in its way (Up-Right)
                        if (board[from[0] - i][from[1] + i] != null) {
                            return false;
                        }
                    }
                }
                else if (from[1] > to[1]) {
                    for (int i = 1; i < Math.abs(from[0] - to[0]); i++) { // Checks if there is a piece in its way (Down-Left)
                        if (board[from[0] + i][from[1] - i] != null) {
                            return false;
                        }
                    }
                }
                return true;
            }
            else {
                return false;
            }
        }
        // King
        else if (Piece.getType().equals("King")) {
            if (((King) board[from[0]][from[1]]).isFirstMove() && Math.abs(from[1] - to[1]) == 2 && from[0] == to[0]) { // Castling
                if (from[1] - to[1] == 2 && board[from[0]][0] != null && board[from[0]][0].getType().equals("Rook") && ((Rook) board[from[0]][0]).isFirstMove()) {
                    if (board[from[0]][1] != null || board[from[0]][2] != null || board[from[0]][3] != null) {
                        return false;
                    }
                }
                else if (board[from[0]][7] != null && board[from[0]][7].getType().equals("Rook") && ((Rook) board[from[0]][7]).isFirstMove()) {
                    if (board[from[0]][6] != null || board[from[0]][5] != null) {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
            else if (Math.abs(from[0] - to[0]) > 1 || Math.abs(from[1] - to[1]) > 1) { // Makes sure king doesn't move more than 1 tile in any direction
                return false;
            }
            // Makes sure the king doesn't end in check
            GamePiece[][] tempboard = deepCopy(board);
            movePiece(tempboard, from, to);
            if (isLocationThreatened(tempboard, to, playerTurn)) {
                return false;
            }
        }
        return true;
    }

    public static GamePiece[][] deepCopy(GamePiece[][] original) {
        /* Creates an independent copy of the game board */
        if (original == null) {
            return null;
        }

        final GamePiece[][] result = new GamePiece[Globals.ROWS][Globals.COLS];
        for (int row = 0; row < Globals.ROWS; row++) {
            for (int col = 0; col < Globals.COLS; col++) {
                if (original[row][col] == null);
                else if (original[row][col].getType().equals("Pawn")) {
                    result[row][col] = new Pawn(original[row][col].getColour());
                }
                else if (original[row][col].getType().equals("Rook")) {
                    result[row][col] = new Rook(original[row][col].getColour());
                }
                else if (original[row][col].getType().equals("Knight")) {
                    result[row][col] = new Knight(original[row][col].getColour());
                }
                else if (original[row][col].getType().equals("Bishop")) {
                    result[row][col] = new Bishop(original[row][col].getColour());
                }
                else if (original[row][col].getType().equals("Queen")) {
                    result[row][col] = new Queen(original[row][col].getColour());
                }
                else if (original[row][col].getType().equals("King")) {
                    result[row][col] = new King(original[row][col].getColour());
                }
            }
        }
        return result;
    }

    public static void movePiece(GamePiece[][] board, int[] from, int[] to) {
        if (board[from[0]][from[1]] != null) { // Can't move nothing
            // set hasMoved parameter for applicable pieces
            if (board[from[0]][from[1]].getType().equals("Pawn")) {
                ((Pawn) board[from[0]][from[1]]).hasMoved();
            }
            else if (board[from[0]][from[1]].getType().equals("Rook")) {
                ((Rook) board[from[0]][from[1]]).hasMoved();
            }
            else if (board[from[0]][from[1]].getType().equals("King")) {
                ((King) board[from[0]][from[1]]).hasMoved();
            }
        }
        // moves the pieces
        board[to[0]][to[1]] = board[from[0]][from[1]];
        board[from[0]][from[1]] = null;
    }

    
    public static void isGameOver(GamePiece[][] board, String playerTurn) {
        /* Checks if a player has won the game, sets the Globals variable of gameOver to true */
        GamePiece[][] tempboard;
        int[] PieceLocation = new int[2];
        int[] kingLocation = new int[2];
        int[] targetLocation = new int[2];
        String player;
        boolean gameOver = true; // Assumes true until disproven
        if (playerTurn.equals("White")) {
            player = "Black";
        }
        else {
            player = "White";
        }
        // Checks to see if there is a move the player can make that doesn't result in their king being checked
        for (int row = 0; row < Globals.ROWS && gameOver; row++) {
            for (int col = 0; col < Globals.COLS && gameOver; col++) {
                if (board[row][col] != null && board[row][col].getColour().equals(player)) {
                    PieceLocation[0] = row;
                    PieceLocation[1] = col;
                    for (int r = 0; r < Globals.ROWS && gameOver; r++) {
                        for (int c = 0; c < Globals.COLS && gameOver; c++) {
                            targetLocation[0] = r;
                            targetLocation[1] = c;
                            tempboard = deepCopy(board);
                            if (validMove(tempboard, PieceLocation, targetLocation, player)) {
                                movePiece(tempboard, PieceLocation, targetLocation);
                                for (int tempRow = 0; tempRow < Globals.ROWS; tempRow++) {
                                    for (int tempCol = 0; tempCol < Globals.COLS; tempCol++) {
                                        if (tempboard[tempRow][tempCol] != null && tempboard[tempRow][tempCol].getType().equals("King") && tempboard[tempRow][tempCol].getColour().equals(player)) {
                                            kingLocation[0] = tempRow;
                                            kingLocation[1] = tempCol;
                                        }
                                    }
                                }
                                if (!isLocationThreatened(tempboard, kingLocation, player)) {
                                    gameOver = false;
                                }
                            }
                        }
                    }
                }
                
            }
        }
        Globals.gameOver = gameOver;
    }

    public static boolean isLocationThreatened(GamePiece[][] board, int[] location, String colour) {
        /* Checks to see if a given tile on a board is threatened by an opponent's piece */
        // Pawn
        if (colour.equals("White")) {
            if (location[0] != 0) {
                if (location[1] != 0 && board[location[0] - 1][location[1] - 1] != null && board[location[0] - 1][location[1] - 1].getType().equals("Pawn") && !board[location[0] - 1][location[1] - 1].getColour().equals(colour)) {
                    return true;
                }
                if (location[1] != 7 && location[0] != 7 && board[location[0] - 1][location[1] + 1] != null && board[location[0] - 1][location[1] + 1].getType().equals("Pawn") && !board[location[0] - 1][location[1] + 1].getColour().equals(colour)) {
                    return true;
                }
            }
        }
        else {
            if (location[0] != 7) {
                if (location[1] != 0 && board[location[0] + 1][location[1] - 1] != null && board[location[0] + 1][location[1] - 1].getType().equals("Pawn") && !board[location[0] + 1][location[1] - 1].getColour().equals(colour)) {
                    return true;
                }
                if (location[1] != 7 && location[0] != 7  && board[location[0] + 1][location[1] + 1] != null && board[location[0] + 1][location[1] + 1].getType().equals("Pawn") && !board[location[0] + 1][location[1] + 1].getColour().equals(colour)) {
                    return true;
                }
            }
        }

        // Rook
        for (int i = location[0] + 1; i < Globals.ROWS; i++) {
            if (board[i][location[1]] != null && board[i][location[1]].getType().equals("Rook") && !board[i][location[1]].getColour().equals(colour)) {
                return true;
            }
            else if (board[i][location[1]] != null) {
                break;
            }
        }
        for (int i = location[0] - 1; i >= 0; i--) {
            if (board[i][location[1]] != null && board[i][location[1]].getType().equals("Rook") && !board[i][location[1]].getColour().equals(colour)) {
                return true;
            }
            else if (board[i][location[1]] != null) {
                break;
            }
        }
        for (int i = location[1] + 1; i < Globals.COLS; i++) {
            if (board[location[0]][i] != null && board[location[0]][i].getType().equals("Rook") && !board[location[0]][i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0]][i] != null) {
                break;
            }
        }
        for (int i = location[1] - 1; i >= 0; i--) {
            if (board[location[0]][i] != null && board[location[0]][i].getType().equals("Rook") && !board[location[0]][i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0]][i] != null) {
                break;
            }
        }

        // Knight
        if (location[0] != 7 && location[1] <= 5 && board[location[0] + 1][location[1] + 2] != null && board[location[0] + 1][location[1] + 2].getType().equals("Knight") && !board[location[0] + 1][location[1] + 2].getColour().equals(colour)) {
            return true;
        }
        if (location[0] != 7 && location[1] >= 2 && board[location[0] + 1][location[1] - 2] != null && board[location[0] + 1][location[1] - 2].getType().equals("Knight") && !board[location[0] + 1][location[1] - 2].getColour().equals(colour)) {
            return true;
        }
        if (location[0] != 0 && location[1] <= 5 && board[location[0] - 1][location[1] + 2] != null && board[location[0] - 1][location[1] + 2].getType().equals("Knight") && !board[location[0] - 1][location[1] + 2].getColour().equals(colour)) {
            return true;
        }
        if (location[0] != 0 && location[1] >= 2 && board[location[0] - 1][location[1] - 2] != null && board[location[0] - 1][location[1] - 2].getType().equals("Knight") && !board[location[0] - 1][location[1] - 2].getColour().equals(colour)) {
            return true;
        }
        
        if (location[0] <= 5 && location[1] != 7 && board[location[0] + 2][location[1] + 1] != null && board[location[0] + 2][location[1] + 1].getType().equals("Knight") && !board[location[0] + 2][location[1] + 1].getColour().equals(colour)) {
            return true;
        }
        if (location[0] <= 5 && location[1] != 0 && board[location[0] + 2][location[1] - 1] != null && board[location[0] + 2][location[1] - 1].getType().equals("Knight") && !board[location[0] + 2][location[1] - 1].getColour().equals(colour)) {
            return true;
        }
        if (location[0] >= 2 && location[1] != 7 && board[location[0] - 2][location[1] + 1] != null && board[location[0] - 2][location[1] + 1].getType().equals("Knight") && !board[location[0] - 2][location[1] + 1].getColour().equals(colour)) {
            return true;
        }
        if (location[0] >= 2 && location[1] != 0 && board[location[0] - 2][location[1] - 1] != null && board[location[0] - 2][location[1] - 1].getType().equals("Knight") && !board[location[0] - 2][location[1] - 1].getColour().equals(colour)) {
            return true;
        }

        // Bishop
        for (int i = 1; i < board.length; i++) {
            if (location[0] + i > 7 || location[1] + i > 7) {
                break;
            }
            if (board[location[0] + i][location[1] + i] != null && board[location[0] + i][location[1] + i].getType().equals("Bishop") && !board[location[0] + i][location[1] + i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0] + i][location[1] + i] != null) {
                break;
            }
        }
        for (int i = 1; i < board.length; i++) {
            if (location[0] - i < 0 || location[1] + i > 7) {
                break;
            }
            if (board[location[0] - i][location[1] + i] != null && board[location[0] - i][location[1] + i].getType().equals("Bishop") && !board[location[0] - i][location[1] + i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0] - i][location[1] + i] != null) {
                break;
            }
        }
        for (int i = 1; i < board.length; i++) {
            if (location[0] + i > 7 || location[1] - i < 0) {
                break;
            }
            if (board[location[0] + i][location[1] - i] != null && board[location[0] + i][location[1] - i].getType().equals("Bishop") && !board[location[0] + i][location[1] - i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0] + i][location[1] - i] != null) {
                break;
            }
        }
        for (int i = 1; i < board.length; i++) {
            if (location[0] - i < 0 || location[1] - i < 0) {
                break;
            }
            if (board[location[0] - i][location[1] - i] != null && board[location[0] - i][location[1] - i].getType().equals("Bishop") && !board[location[0] - i][location[1] - i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0] - i][location[1] - i] != null) {
                break;
            }
        }

        // Queen
        for (int i = location[0] + 1; i < Globals.ROWS && i >= 0; i++) {
            if (board[i][location[1]] != null && board[i][location[1]].getType().equals("Queen") && !board[i][location[1]].getColour().equals(colour)) {
                return true;
            }
            else if (board[i][location[1]] != null) {
                break;
            }
        }
        for (int i = location[0] - 1; i < Globals.ROWS && i >= 0; i--) {
            if (board[i][location[1]] != null && board[i][location[1]].getType().equals("Queen") && !board[i][location[1]].getColour().equals(colour)) {
                return true;
            }
            else if (board[i][location[1]] != null) {
                break;
            }
        }
        for (int i = location[1] + 1; i < Globals.COLS && i >= 0; i++) {
            if (board[location[0]][i] != null && board[location[0]][i].getType().equals("Queen") && !board[location[0]][i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0]][i] != null) {
                break;
            }
        }
        for (int i = location[1] - 1; i < Globals.COLS && i >= 0; i--) {
            if (board[location[0]][i] != null && board[location[0]][i].getType().equals("Queen") && !board[location[0]][i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0]][i] != null) {
                break;
            }
        }
        for (int i = 1; i < Globals.ROWS; i++) {
            if (location[0] + i > 7 || location[1] + i > 7) {
                break;
            }
            if (board[location[0] + i][location[1] + i] != null && board[location[0] + i][location[1] + i].getType().equals("Queen") && !board[location[0] + i][location[1] + i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0] + i][location[1] + i] != null) {
                break;
            }
        }
        for (int i = 1; i < Globals.ROWS; i++) {
            if (location[0] - i < 0 || location[1] + i > 7) {
                break;
            }
            if (board[location[0] - i][location[1] + i] != null && board[location[0] - i][location[1] + i].getType().equals("Queen") && !board[location[0] - i][location[1] + i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0] - i][location[1] + i] != null) {
                break;
            }
        }
        for (int i = 1; i < Globals.ROWS; i++) {
            if (location[0] + i > 7 || location[1] - i < 0) {
                break;
            }
            if (board[location[0] + i][location[1] - i] != null && board[location[0] + i][location[1] - i].getType().equals("Queen") && !board[location[0] + i][location[1] - i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0] + i][location[1] - i] != null) {
                break;
            }
        }
        for (int i = 1; i < Globals.ROWS; i++) {
            if (location[0] - i < 0 || location[1] - i < 0) {
                break;
            }
            if (board[location[0] - i][location[1] - i] != null && board[location[0] - i][location[1] - i].getType().equals("Queen") && !board[location[0] - i][location[1] - i].getColour().equals(colour)) {
                return true;
            }
            else if (board[location[0] - i][location[1] - i] != null) {
                break;
            }
        }

        // King
        for (int row = location[0] - 1; row <= location[0] + 1; row++) {
            for (int col = location[1] - 1; col <= location[1] + 1; col++) {
                if (row >= 0 && row <= 7 && col >= 0 && col <= 7) {
                    if (board[row][col] != null && board[row][col].getType().equals("King") && !board[row][col].getColour().equals(colour)) {
                        return true;
                    }
                }
            }
        }

        // Location not threatened
        return false;
    }
    
    public static void main(String[] args) {
        GamePiece[][] board = newBoard();
        GamePiece[][] tempboard;
        String playerTurn = "White";
        int[] from = new int[2];
        int[] to = new int[2];
        int[] kingLocation;
        boolean invalidInput = false;

        // Creates the GUI for the chess board
        JFrame mainWindow = new JFrame();
        mainWindow.setTitle("Chess");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        mainWindow.setSize(800, 800);
        mainWindow.setLayout(null);


        
        JPanel chessPanel = new JPanel();
        chessPanel.setLayout(new GridLayout(Globals.ROWS, Globals.COLS));
        Color colour;
        for (int row = 0; row < Globals.ROWS; row++) { // Colours the tiles in an alterating fashion
            for (int col = 0; col < Globals.grid[row].length; col++) {
                if ((row + col) % 2 == 0) {
                    colour = Color.WHITE;
                }
                else {
                    colour = Color.DARK_GRAY;
                }
                Globals.grid[row][col] = new GridPanel(colour, row, col, null);
                chessPanel.add(Globals.grid[row][col]);
            }
        }

        GridBagLayout gridBag = new GridBagLayout();
        mainWindow.getContentPane().setLayout(gridBag);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = Globals.COLS;
        mainWindow.getContentPane().add(chessPanel, gridBagConstraints);

        mainWindow.getContentPane().add(chessPanel);
        mainWindow.getContentPane().add(Globals.status, gridBagConstraints);
        mainWindow.pack();
        mainWindow.setVisible(true);

        // Pawn promotion window
            JFrame pawnWindow = new JFrame();
            pawnWindow.setTitle("Pawn Promotion");
            pawnWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            pawnWindow.setResizable(false);
            pawnWindow.setSize(400, 100);
            pawnWindow.setLayout(null);

            JPanel choicePanel = new JPanel();
            choicePanel.setLayout(new GridLayout(Globals.CHOICE_ROWS, Globals.CHOICE_COLS));
            for (int col = 0; col < Globals.choiceGrid[0].length; col++) {
                if (col % 2 == 0) {
                    colour = Color.WHITE;
                }
                else {
                    colour = Color.DARK_GRAY;
                }
                Globals.choiceGrid[0][col] = new GridPanel(colour, -1, col, null);
                choicePanel.add(Globals.choiceGrid[0][col]);
            }

            GridBagLayout choiceGridBag = new GridBagLayout();
            pawnWindow.getContentPane().setLayout(choiceGridBag);

            GridBagConstraints choiceGridBagConstraints = new GridBagConstraints();
            choiceGridBagConstraints.gridx = 0;
            choiceGridBagConstraints.gridy = Globals.CHOICE_COLS;
            pawnWindow.getContentPane().add(choicePanel, choiceGridBagConstraints);

            pawnWindow.getContentPane().add(choicePanel);
            pawnWindow.pack();
            pawnWindow.setVisible(false);
        //

        updateBoard(board); // Makes the pieces visible
        
        do {
            printBoard(board, playerTurn);
            System.out.println();
            do { // Keeps looping until given a valid move
                try {
                    Globals.locationEntered = false;
                    while (!Globals.locationEntered) { // Wait until location entered
                        System.out.print("");
                    }
                    from[0] = Globals.location[0];
                    from[1] = Globals.location[1];
                    Globals.grid[from[0]][from[1]].selectTile();

                    Globals.locationEntered = false;
                    while (!Globals.locationEntered) { // Wait until location entered
                        System.out.print("");
                    }
                    to[0] = Globals.location[0];
                    to[1] = Globals.location[1];
                    Globals.locationEntered = false;
                    Globals.grid[from[0]][from[1]].unselectTile();
                    invalidInput = false;
                }
                catch (NumberFormatException e) {
                    System.out.println("| Error |");
                    invalidInput = true;
                }
                catch (Exception e) {
                    System.out.println("||| Unexpected Error |||");
                }

                if (!invalidInput) {
                    tempboard = deepCopy(board);
                    movePiece(tempboard, from, to);
                    kingLocation = new int[2];
                    for (int row = 0; row < Globals.ROWS; row++) {
                        for (int col = 0; col < Globals.COLS; col++) {
                            if (tempboard[row][col] != null && tempboard[row][col].getType().equals("King") && tempboard[row][col].getColour().equals(playerTurn)) {
                                kingLocation[0] = row;
                                kingLocation[1] = col;
                            }
                        }
                    }
                    if (isLocationThreatened(tempboard, kingLocation, playerTurn)) {
                        invalidInput = true;
                    }
                }
                
                System.out.println();
            } while (!(!invalidInput && validMove(board, from, to, playerTurn)));

            // Castling
            if (board[from[0]][from[1]] != null && board[from[0]][from[1]].getType().equals("King")) {
                if (Math.abs(from[1] - to[1]) > 1) {
                    int[] altFrom = new int[2];
                    int[] altTo = new int[2];
                    altFrom[0] = from[0];
                    altTo[0] = to[0];
                    if (from[1] - to[1] == 2) {
                        altFrom[1] = 0;
                        altTo[1] = 3;
                    }
                    else {
                        altFrom[1] = 7;
                        altTo[1] = 5;
                    }
                    movePiece(board, altFrom, altTo);
                }
            }

            // En Passant
            if (board[from[0]][from[1]].getType().equals("Pawn")) {
                if (Math.abs(from[0] - to[0]) == 2) {
                    ((Pawn) board[from[0]][from[1]]).setEnPassantable(true);
                }
                else if (Math.abs(from[0] - to[0]) == 1 && Math.abs(from[1] - to[1]) == 1 && board[to[0]][to[1]] == null) {
                    if (from[1] - to[1] == 1) {
                        if (board[from[0]][from[1] - 1] != null && board[from[0]][from[1] - 1].getType().equals("Pawn") && ((Pawn) board[from[0]][from[1] - 1]).isEnPassantable()) {
                            board[from[0]][from[1] - 1] = null;
                        }
                    }
                    else {
                        if (board[from[0]][from[1] + 1] != null && board[from[0]][from[1] + 1].getType().equals("Pawn") && ((Pawn) board[from[0]][from[1] + 1]).isEnPassantable()) {
                            board[from[0]][from[1] + 1] = null;
                        }
                    }
                }
            }

            // Moving the piece
            movePiece(board, from, to);
            updateBoard(board);

            // Only a Pawn moved last turn could be En Passantable
            for (int row = 0; row < Globals.ROWS; row++) {
                for (int col = 0; col < Globals.COLS; col++) {
                    if (board[row][col] != null && board[row][col].getType().equals("Pawn") && (row != to[0] || col != to[1])) {
                        ((Pawn) board[row][col]).setEnPassantable(false);
                    }
                }
            }

            // Pawn Promotion
            if (board[to[0]][to[1]] != null && board[to[0]][to[1]].getType().equals("Pawn")) {
                if ((board[to[0]][to[1]].getColour().equals("White") && to[0] == 0) || (board[to[0]][to[1]].getColour().equals("Black") && to[0] == 7)) {
                    int choicePiece = 3;
                    Globals.choiceGrid[0][0].setPiece(new Rook(playerTurn));
                    Globals.choiceGrid[0][0].updateIcon();
                    Globals.choiceGrid[0][1].setPiece(new Knight(playerTurn));
                    Globals.choiceGrid[0][1].updateIcon();
                    Globals.choiceGrid[0][2].setPiece(new Bishop(playerTurn));
                    Globals.choiceGrid[0][2].updateIcon();
                    Globals.choiceGrid[0][3].setPiece(new Queen(playerTurn));
                    Globals.choiceGrid[0][3].updateIcon();
                    pawnWindow.setVisible(true);
                    do {
                        invalidInput = false;
                        Globals.locationEntered = false;
                        while (!Globals.locationEntered) {
                            System.out.print("");
                        }
                        choicePiece = Globals.location[1];
    
                        if (Globals.location[0] != -1) {
                            invalidInput = true;
                        }
                        else if (choicePiece == 0) {
                            board[to[0]][to[1]] = new Rook(playerTurn);
                        }
                        else if (choicePiece == 1) {
                            board[to[0]][to[1]] = new Knight(playerTurn);
                        }
                        else if (choicePiece == 2) {
                            board[to[0]][to[1]] = new Bishop(playerTurn);
                        }
                        else if (choicePiece == 3) {
                            board[to[0]][to[1]] = new Queen(playerTurn);
                        }
                        else {
                            invalidInput = true;
                        }    
                    } while (invalidInput);
                    
                    updateBoard(board);
                    pawnWindow.setVisible(false);
                }
            }

            isGameOver(board, playerTurn); // Checks if someone won

            // Switches whose turn it is unless if someone won
            if (!Globals.gameOver && playerTurn.equals("White")) {
                playerTurn = "Black";
                updateStatus("Black's Turn");
            }
            else if (!Globals.gameOver && playerTurn.equals("Black")) {
                playerTurn = "White";
                updateStatus("White's Turn");
            }
        } while (!Globals.gameOver);

        updateStatus("Game Over -- " + playerTurn + " Wins");
    }
}