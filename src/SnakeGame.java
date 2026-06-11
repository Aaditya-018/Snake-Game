import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener,KeyListener  {
    private class Tile{
        int x ;
        int y ;
        Tile(int x , int y){
            this.x = x;
            this.y = y;
        } 
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 32;

    //Snake
    Tile SnakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //gameLogic
    Timer gameLoop;
    int VelocityX;
    int VelocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth,int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);

        addKeyListener(this);
        setFocusable(true);

        SnakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile (10,10);
        random = new Random();
        placeFood();

        VelocityX = 0;
        VelocityY = 0;

        gameLoop = new Timer(100,this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw (Graphics g){
        //Grid

        // for(int i = 0;i<boardWidth/tileSize;i++){
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        // }

        //Food
        g.setColor(Color.red);
        // g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize,true);

        //Snake Head
        g.setColor(Color.orange); 
        // g.fillRect(SnakeHead.x*tileSize, SnakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(SnakeHead.x*tileSize, SnakeHead.y*tileSize, tileSize, tileSize,true);
        

        //Snake Body
        for(int i = 0;i<snakeBody.size();i++){
        Tile snakePart = snakeBody.get(i);
        g.setColor(Color.green);
        // g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize,true);
        }

        //Score
        g.setFont(new Font("Arial",Font.PLAIN,25));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: "+ String.valueOf( snakeBody.size()),tileSize-18,tileSize);
        }
        else{
            g.drawString("Score: "+ String.valueOf(snakeBody.size()), tileSize-18, tileSize);
        }
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }
    public boolean collision(Tile tile1 ,Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        //eat food
        if(collision(SnakeHead,food))
    {   snakeBody.add(new Tile(food.x,food.y));
    placeFood();
    }   
    //Snake Body(How body grow and his movement)
    for(int i = snakeBody.size()-1;i >= 0;i--){
        Tile snakePart = snakeBody.get(i);
        if(i == 0){
            snakePart.x = SnakeHead.x;
            snakePart.y = SnakeHead.y;
        }
        else{
            Tile prevSnakePart = snakeBody.get(i-1);
            snakePart.x = prevSnakePart.x;
            snakePart.y = prevSnakePart.y;
        }
    }

     //snake Head
        SnakeHead.x += VelocityX;
        SnakeHead.y += VelocityY;

    //gameOver Conditions
    for(int i = 0;i<snakeBody.size();i++){
        Tile snakePart = snakeBody.get(i);
        //collide with the Snake head
        if(collision(SnakeHead, snakePart))
        gameOver = true;
    }
    //Wall hitting Condition
    if(SnakeHead.x * tileSize < 0 ||  SnakeHead.x * tileSize > boardWidth ||
     SnakeHead.y * tileSize < 0 || SnakeHead.y * tileSize > boardHeight){
        gameOver = true;
    }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_UP && VelocityY != 1){
        VelocityX = 0;
        VelocityY = -1;
      }
      else if(e.getKeyCode() == KeyEvent.VK_DOWN && VelocityY != -1){
        VelocityX = 0;
        VelocityY = 1;
      }
      else if(e.getKeyCode() == KeyEvent.VK_LEFT && VelocityX != 1){
        VelocityX = -1;
        VelocityY = 0;
      }
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT && VelocityX != -1){
        VelocityX = 1;
        VelocityY = 0;
      }
    }

    // Define but not needed
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
