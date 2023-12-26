import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class first extends JFrame {

    private JTextField[][] grid;

    public first() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        setLayout(new GridLayout(10, 10));
        JPanel sudokuPanel = new JPanel(new GridLayout(10, 10));
        grid = new JTextField[9][9];

        // Initialize the grid with empty text fields
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new JTextField();
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                add(grid[i][j]);
            }
        }

        mainPanel.add(sudokuPanel, BorderLayout.CENTER);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });

        JButton setValuesButton = new JButton("Set Values");
        setValuesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGridValues();
            }
        });

        add(solveButton);
        add(setValuesButton);
        mainPanel.add(solveButton, BorderLayout.NORTH);
        mainPanel.add(setValuesButton, BorderLayout.NORTH);

        setVisible(true);
    }

    private void setGridValues() {
        setGridValue(0, 0, "5");
        setGridValue(0, 1, "3");
        setGridValue(0, 4, "7");

        setGridValue(1, 0, "6");
        setGridValue(1, 3, "1");
        setGridValue(1, 4, "9");
        setGridValue(1, 5, "5");

        setGridValue(2, 1, "9");
        setGridValue(2, 2, "8");
        setGridValue(2, 7, "6");
        

        setGridValue(3, 0, "8");
        setGridValue(3, 4, "6");
        setGridValue(3, 8, "3");

        setGridValue(4, 0, "4");
        setGridValue(4, 3, "8");
        setGridValue(4, 5, "3");
        setGridValue(4, 8, "1");

        setGridValue(5, 0, "7");
        setGridValue(5, 4, "2");
        setGridValue(5, 8, "6");

        setGridValue(6, 1, "6");
        setGridValue(6, 6, "2");
        setGridValue(6, 7, "8");

        setGridValue(7, 3, "4");
        setGridValue(7, 4, "1");
        setGridValue(7, 5, "9");
        setGridValue(7, 8, "5");

        setGridValue(8, 4, "8");
        setGridValue(8, 7, "7");
        setGridValue(8, 8, "9");
    }


    private void setGridValue(int row, int col, String value) {
        grid[row][col].setText(value);
    }

    private void solveSudoku() {
        if (first(0, 0)) {
            System.out.println("Sudoku solved!");
        } else {
            System.out.println("No solution exists.");
        }
    }
    private boolean first(int row, int col) {
        if (row == 9) {
            // We have reached the end of the grid
            return true;
        }

        // Calculate the next row and col
        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col + 1) % 9;

        JTextField currentCell = grid[row][col];
        if (!currentCell.getText().isEmpty()) {
            // If the cell is pre-filled, move on to the next one
            return first(nextRow, nextCol);
        }

        for (int num = 1; num <= 9; num++) {
            if (isSafe(row, col, num)) {
                currentCell.setText(String.valueOf(num));
                updateUI();
                if (first(nextRow, nextCol)) {
                    return true;
                }
                // Backtrack if the current choice doesn't lead to a solution
                currentCell.setText("");
                updateUI();
            }
        }

        // No valid number found, backtrack
        return false;
    }

    private boolean isSafe(int row, int col, int num) {
        // Check if 'num' is not present in the current row, current column, and current 3x3 grid
        return !isInRow(row, num) && !isInCol(col, num) && !isInBox(row - row % 3, col - col % 3, num);
    }

    private boolean isInRow(int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (grid[row][col].getText().equals(String.valueOf(num))) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (grid[row][col].getText().equals(String.valueOf(num))) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (grid[startRow + row][startCol + col].getText().equals(String.valueOf(num))) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateUI() {
        // Repaint the UI
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new first();
            }
        });
    }
}
