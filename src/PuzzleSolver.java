import java.util.*;

public class PuzzleSolver {
    private final Board board;
    private final List<Piece> pieces;
    private final List<String> solution;
    private long stepCounter = 0;
    private final long printEvery = 10000000;

    public PuzzleSolver(Board board, List<Piece> pieces) {
        this.board = board;
        this.pieces = pieces;
        this.solution = new ArrayList<>();
        this.pieces.sort((a, b) -> countX(b) - countX(a));

    }

    public List<String> solve() {
        return solveRecursive(0, board.copy()) ? solution : null;
    }

    private boolean solveRecursive(int index, Board currentBoard) {
        stepCounter++;
        if (stepCounter % printEvery == 0) {
            System.out.println("Steps tried: " + stepCounter);
        }

        if (index == pieces.size()) {
            return currentBoard.isAllZero();
        }

        Piece piece = pieces.get(index);
        int maxRow = currentBoard.getRows() - piece.getHeight();
        int maxCol = currentBoard.getCols() - piece.getWidth();

        int pieceMaxImpact = countX(piece);
        for (int threshold = pieceMaxImpact; threshold >= 0; threshold--) {

            for (int row = 0; row <= maxRow; row++) {
                for (int col = 0; col <= maxCol; col++) {
                    int affected = countNonZeroAffected(currentBoard, piece, row, col);
                    if (affected != threshold) continue;

                    Board nextBoard = currentBoard.copy();
                    nextBoard.apply(piece, row, col);
                    solution.add(col + "," + row);

                    if (solveRecursive(index + 1, nextBoard)) {
                        return true;
                    }
                    solution.remove(solution.size() - 1);
                }
            }

            if (solution.size() > index) return true;
        }

        return false;
    }

    private int countNonZeroAffected(Board board, Piece piece, int startRow, int startCol) {
        int count = 0;
        for (int i = 0; i < piece.getHeight(); i++) {
            for (int j = 0; j < piece.getWidth(); j++) {
                if (piece.affects(i, j)) {
                    int r = startRow + i;
                    int c = startCol + j;
                    if (board.getValue(r, c) != 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private int countX(Piece piece) {
        int count = 0;
        for (char[] row : piece.getShape()) {
            for (char c : row) {
                if (c == 'X') count++;
            }
        }
        return count;
    }
}