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

        for (int row = 0; row <= maxRow; row++) {
            for (int col = 0; col <= maxCol; col++) {
                Board nextBoard = currentBoard.copy();
                nextBoard.apply(piece, row, col);
                solution.add(col + "," + row);

                if (solveRecursive(index + 1, nextBoard)) {
                    return true;
                }
                solution.remove(solution.size() - 1);
            }
        }

        return false;
    }
}
