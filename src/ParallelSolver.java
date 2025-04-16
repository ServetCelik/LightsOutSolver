import java.util.*;
import java.util.concurrent.*;

public class ParallelSolver {
    private final Board board;
    private final List<Piece> pieces;

    public ParallelSolver(Board board, List<Piece> pieces) {
        this.board = board;
        this.pieces = pieces;
    }

    public List<String> solve() {
        if (pieces.isEmpty()) return board.isAllZero() ? new ArrayList<>() : null;

        Piece firstPiece = pieces.get(0);
        List<Piece> remaining = pieces.subList(1, pieces.size());

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService<List<String>> service = new ExecutorCompletionService<>(executor);

        int maxRow = board.getRows() - firstPiece.getHeight();
        int maxCol = board.getCols() - firstPiece.getWidth();

        int taskCount = 0;

        for (int row = 0; row <= maxRow; row++) {
            for (int col = 0; col <= maxCol; col++) {
                Board copy = board.copy();
                copy.apply(firstPiece, row, col);
                int finalRow = row, finalCol = col;

                service.submit(() -> {
                    PuzzleSolver solver = new PuzzleSolver(copy, remaining);
                    List<String> rest = solver.solve();
                    if (rest != null) {
                        List<String> full = new ArrayList<>();
                        full.add(finalCol + "," + finalRow);
                        full.addAll(rest);
                        return full;
                    }
                    return null;
                });
                taskCount++;
            }
        }

        try {
            for (int i = 0; i < taskCount; i++) {
                Future<List<String>> future = service.take();
                List<String> result = future.get();
                if (result != null) {
                    executor.shutdownNow();
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow();
        }

        return null;
    }
}
