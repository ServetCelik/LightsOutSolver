import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input filename (e.g., 01.txt): ");
        String filename = "inputs/" + scanner.nextLine().trim();

        List<String> lines = Files.readAllLines(Paths.get(filename));
        int depth = Integer.parseInt(lines.get(0).trim());
        Board board = PuzzleInputParser.parseBoard(lines.get(1), depth);
        List<Piece> pieces = PuzzleInputParser.parsePieces(lines.get(2));

        ParallelSolver solver = new ParallelSolver(board, pieces);
        List<String> solution = solver.solve();

        if (solution != null) {
            System.out.println(String.join(" ", solution));
        } else {
            System.out.println("No solution found.");
        }
    }
}
