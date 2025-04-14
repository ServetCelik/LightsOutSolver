public class Piece {
    private final char[][] shape;

    public Piece(char[][] shape) {
        this.shape = shape;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        return shape[0].length;
    }

    public boolean affects(int i, int j) {
        return shape[i][j] == 'X';
    }

    public char[][] getShape() {
        return shape;
    }

    public int countX() {
        int count = 0;
        for (char[] row : shape) {
            for (char c : row) {
                if (c == 'X') count++;
            }
        }
        return count;
    }
}