import java.util.*;

public class PuzzleInputParser {
    public static Board parseBoard(String line, int depth) {
        String[] rows = line.split(",");
        int[][] board = new int[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                String digit = String.valueOf(rows[i].charAt(j));
                board[i][j] = Integer.parseInt(digit);
            }
        }
        return new Board(board, depth);
    }

    public static List<Piece> parsePieces(String line) {
        List<Piece> pieces = new ArrayList<>();
        for (String pieceStr : line.split(" ")) {
            String[] rows = pieceStr.split(",");
            char[][] shape = new char[rows.length][rows[0].length()];
            for (int i = 0; i < rows.length; i++) {
                shape[i] = rows[i].toCharArray();
            }
            pieces.add(new Piece(shape));
        }
        return pieces;
    }
}