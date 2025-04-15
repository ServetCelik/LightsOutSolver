# Lights Out Puzzle Solver 🔦🧩

This project solves a variant of the **Lights Out** puzzle, where the goal is to reduce a grid of integers to all zeros using a set of given pieces that affect certain cells.

## 🚀 Features

- Recursive backtracking algorithm with adaptive greedy placement
- Piece sorting for performance
- Solution output follows the **original piece order** (as given in input)
- Simple, extensible structure

## 📦 Input Format

The puzzle is initialized from a text file with the following format:

```
<depth>
<board_row1>,<board_row2>,...,<board_rowN>
<piece1_row1>,<piece1_row2>,... <piece2_row1>,<piece2_row2>,... ...
```

- Each number in the board is a digit (`0–(depth-1)`)
- Each piece is a shape using `X` (affects the board) and `.` (no effect)
- Pieces are separated by a space

### Example (input.txt)

```
2
100,101,011
..X,XXX,X.. X,X,X .X,XX XX.,.X.,.XX XX,X. XX .XX,XX.
```

## 🧠 How It Works

1. The board is parsed into a grid of integers
2. Each piece is parsed into a grid of characters
3. Pieces are **sorted** by number of `X`s for performance
4. The solver tries to apply each piece at every valid position
5. A greedy filtering strategy skips placements that don't meaningfully change the board
6. The final output shows where each piece is placed (in original input order)

## 📥 How to Use

### 🛠 Requirements
- Java 17+ recommended
- Run via IntelliJ or command line

### 👟 Running the Solver

```bash
java Main
```

You'll be prompted to enter the input filename (e.g., `01.txt`), which should be placed in the `/inputs` folder (or adjust the path accordingly).

## ✅ Output

The solution is printed as a list of coordinates (column,row) for each piece, matching the order of the input.

Example:
```
2,0
1,1
0,0
```

## 🧪 Performance Features

- Greedy adaptive placement using a threshold fallback per piece
- Efficient placement caching within recursive steps
- Strict output ordering for user clarity

## 📁 Project Structure

```
src/
├── Board.java
├── Piece.java
├── PuzzleSolver.java
├── Main.java
└── inputs/
    └── 01.txt
```

