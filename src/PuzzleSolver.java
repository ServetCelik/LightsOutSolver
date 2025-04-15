import java.util.*;

public class PuzzleSolver {
    private final Board board;
    private final List<PieceWithIndex> pieces;
    private final List<String> solution;
    private long stepCounter = 0;
    private final long printEvery = 10000000;

    public PuzzleSolver(Board board, List<Piece> inputPieces) {
        this.board = board;
        this.pieces = new ArrayList<>();

        for (int i = 0; i < inputPieces.size(); i++) {
            this.pieces.add(new PieceWithIndex(i, inputPieces.get(i)));
        }

        this.pieces.sort((a, b) -> b.piece.countX() - a.piece.countX());
        this.solution = new ArrayList<>(Collections.nCopies(inputPieces.size(), ""));
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

        PieceWithIndex pw = pieces.get(index);
        Piece piece = pw.piece;
        int originalIndex = pw.index;

        int maxRow = currentBoard.getRows() - piece.getHeight();
        int maxCol = currentBoard.getCols() - piece.getWidth();
        int pieceMaxImpact = piece.countX();

        // Precompute all valid placements with affected score
        List<Placement> placements = new ArrayList<>();
        for (int row = 0; row <= maxRow; row++) {
            for (int col = 0; col <= maxCol; col++) {
                int affected = countNonZeroAffected(currentBoard, piece, row, col);
                placements.add(new Placement(row, col, affected));
            }
        }

        // Try placements, starting from most greedy threshold
        for (int threshold = pieceMaxImpact; threshold >= 0; threshold--) {
            for (Placement p : placements) {
                if (p.affected != threshold) continue;

                Board nextBoard = currentBoard.copy();
                nextBoard.apply(piece, p.row, p.col);
                solution.set(originalIndex, p.col + "," + p.row);

                if (solveRecursive(index + 1, nextBoard)) {
                    return true;
                }

                solution.set(originalIndex, "");
            }

            if (!solution.get(originalIndex).isEmpty()) return true;
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
}