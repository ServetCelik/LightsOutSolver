public class Board {
    private final int[][] cells;
    private final int depth;

    public Board(int[][] initialState, int depth) {
        this.depth = depth;
        this.cells = new int[initialState.length][initialState[0].length];
        for (int i = 0; i < initialState.length; i++) {
            System.arraycopy(initialState[i], 0, this.cells[i], 0, initialState[i].length);
        }
    }

    public Board copy() {
        return new Board(this.cells, this.depth);
    }

    public int getRows() {
        return cells.length;
    }

    public int getCols() {
        return cells[0].length;
    }

    public void apply(Piece piece, int row, int col) {
        for (int i = 0; i < piece.getHeight(); i++) {
            for (int j = 0; j < piece.getWidth(); j++) {
                if (piece.affects(i, j)) {
                    cells[row + i][col + j] = (cells[row + i][col + j] + 1) % depth;
                }
            }
        }
    }

    public boolean isAllZero() {
        for (int[] row : cells) {
            for (int val : row) {
                if (val != 0) return false;
            }
        }
        return true;
    }
}
