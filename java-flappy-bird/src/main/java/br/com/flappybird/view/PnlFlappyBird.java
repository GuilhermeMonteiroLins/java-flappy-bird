package br.com.flappybird.view;

import br.com.flappybird.model.Bird;
import br.com.flappybird.model.Pipe;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author Guilherme Monteiro
 */
public class PnlFlappyBird extends javax.swing.JPanel implements KeyListener {

    private int width;
    private int height;

    private Image backgroungImg;
    private Image bottomPipeImg;
    private Image topPipeImg;

    private Bird bird;
    private Pipe pipeTop;
    private Pipe pipeBottom;
    private ArrayList<Pipe> pipes;
    private Random random;

    private int velocityX = -4;
    private int velocityY = 0;
    private int gravity = 1;

    private Timer gameLoop;
    private Timer placePipesTimer;

    private boolean gameOver;
    private double score;
    private int scoreCheckpoint = 2;

    public PnlFlappyBird() {
        width = 360;
        height = 640;

        // Set Size Panel
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(this);

        // Init Images
        backgroungImg = new ImageIcon(getClass().getResource("/flappyBirdbg.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();

        //Bird Configs
        bird = new Bird(width / 8, height / 2, 34, 24, new ImageIcon(getClass().getResource("/flappybird.png")).getImage());

        // Pipe Configs
        pipes = new ArrayList<>();
        random = new Random();

        //Place Pipes Timer
        placePipesTimer = new Timer(1500, (ActionEvent e) -> {
            placePipes();
        });
        placePipesTimer.start();

        //Timer loop
        gameLoop = new Timer(1000 / 60, ((e) -> {
            move();
            repaint();
            if (gameOver) {
                placePipesTimer.stop();
                gameLoop.stop();
            }
        }));
        gameLoop.start();

        // Game Over
        gameOver = false;

        //Score
        score = 0;
    }

    public void move() {
        velocityY += gravity;
        bird.setY(bird.getY() + velocityY);
        bird.setY(Math.max(bird.getY(), 0));
        
        // Aumenta a velocidade a cada 5 pontos atingidos
        if((int) score % scoreCheckpoint == 0 && score != 0){
            velocityX--;
            scoreCheckpoint += 2;
        }
        
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setX(pipe.getX() + velocityX);

            if (!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getWidth()) {
                pipe.setPassed(true);
                score += 0.5;
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.getY() > height) {
            gameOver = true;
        }
    }

    public void placePipes() {
        int randomPipeY = (int) (0 - 512 / 4 - Math.random() * (512 / 2));

        int openingSpace = height / 4;

        pipeTop = new Pipe(width, 0, 64, 512, false, topPipeImg);
        pipeTop.setY(randomPipeY);
        pipes.add(pipeTop);

        pipeBottom = new Pipe(width, 0, 64, 512, false, bottomPipeImg);
        pipeBottom.setY(pipeTop.getY() + 512 + openingSpace);
        pipes.add(pipeBottom);
    }

    public boolean collision(Bird bird, Pipe pipe) {
        return bird.getX() < pipe.getX() + pipe.getWidth()
                && bird.getX() + bird.getWidth() > pipe.getX()
                && bird.getY() < pipe.getY() + pipe.getHeight()
                && bird.getY() + bird.getHeight() > pipe.getY();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Background Image
        g.drawImage(backgroungImg, 0, 0, width, height, null);

        //Bird
        g.drawImage(bird.getImg(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);

        //Pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImg(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        //Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("GAME OVER : " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;

            if (gameOver) {
                bird.setY(height / 2);
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
                velocityX = -4;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
