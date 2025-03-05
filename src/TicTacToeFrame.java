import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame {
    private JFrame frame;
    private TicTacToeButton[][] buttons = new TicTacToeButton[3][3];
    private String[][] board = new String[3][3];
    private String currentPlayer = "X";

    private class TicTacToeButton extends JButton {
        private int row;
        private int col;

        public TicTacToeButton(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }

    public TicTacToeFrame() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));

        ButtonClickListener listener = new ButtonClickListener();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
                buttons[i][j] = new TicTacToeButton(i, j);
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].addActionListener(listener);
                gridPanel.add(buttons[i][j]);
            }
        }

        JPanel bottomPanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 20));
        quitButton.addActionListener(e -> System.exit(0));
        bottomPanel.add(quitButton);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton clickedButton = (TicTacToeButton) e.getSource();
            int row = clickedButton.getRow();
            int col = clickedButton.getCol();

            if (board[row][col].equals(" ")) {
                board[row][col] = currentPlayer;
                clickedButton.setText(currentPlayer);

                if (checkWinner()) {
                    handleGameEnd(currentPlayer + " wins!");
                } else if (isBoardFull()) {
                    handleGameEnd("It's a tie!");
                } else {
                    switchPlayer();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid move. Please choose an empty square.");
            }
        }
    }

    private void handleGameEnd(String message) {
        int response = JOptionPane.showConfirmDialog(
                frame,
                message + "\nDo you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (board[i][0].equals(currentPlayer) &&
                    board[i][1].equals(currentPlayer) &&
                    board[i][2].equals(currentPlayer)) return true;

            // Check columns
            if (board[0][i].equals(currentPlayer) &&
                    board[1][i].equals(currentPlayer) &&
                    board[2][i].equals(currentPlayer)) return true;
        }

        // Check diagonals
        if (board[0][0].equals(currentPlayer) &&
                board[1][1].equals(currentPlayer) &&
                board[2][2].equals(currentPlayer)) return true;

        if (board[0][2].equals(currentPlayer) &&
                board[1][1].equals(currentPlayer) &&
                board[2][0].equals(currentPlayer)) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
                buttons[i][j].setText(" ");
            }
        }
        currentPlayer = "X";
    }
}