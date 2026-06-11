import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 800;
        int heightWidth = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, heightWidth);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardWidth, heightWidth);
        frame.add(snakeGame); 
        frame.pack();
        snakeGame.requestFocus();
    }
}
