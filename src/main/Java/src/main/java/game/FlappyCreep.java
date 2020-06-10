package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class FlappyCreep implements ActionListener, MouseListener, KeyListener {
    public static FlappyCreep flappyCreep;

    public int timerTick, yMove, score, musicPlay = 1, finalScore = 0, layout = 1;
    public boolean gameOver, started, begining = true;

    public Renderer renderer;
    public Rectangle player;
    public ArrayList<Rectangle> pipes;
    public Random rand;
    public Music music = new Music();
    public Visuals visual = new Visuals();

    JFrame jframe = new JFrame();
    Timer timer = new Timer(20, this);


    public FlappyCreep(){
        gameSettings();
        initialize();
        visual.layout(layout);
        timer.start();
        music.playMusic();
    }
    public void initialize(){
        player = new Rectangle(Constants.SIZE / 2 - 10, Constants.SIZE / 2 - 10, Constants.PLAYERSIZE, Constants.PLAYERSIZE);
        pipes = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            addPipe(true);
        }
    }

    public void gameSettings(){
        renderer = new Renderer();
        rand = new Random();
        jframe.add(renderer);
        jframe.setTitle("Flappy Creep");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(Constants.SIZE, Constants.SIZE);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setResizable(true);
        jframe.setVisible(true);

    }


    public void addPipe(boolean start){
        int space = 350;
        int width = 100;
        int height = 50 + rand.nextInt(350);

        if (start){
            pipes.add(new Rectangle(Constants.SIZE + width + pipes.size() * 300, Constants.MAPMAXHEIGHT - height, width, height));
            pipes.add(new Rectangle(Constants.SIZE + width + (pipes.size() - 1) * 300, 0, width, Constants.SIZE - height - space));
        }
        else{
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, Constants.MAPMAXHEIGHT - height, width, height));
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, Constants.SIZE - height - space));
        }
    }


    public void jump(){
        if (gameOver){
            player = new Rectangle(Constants.SIZE / 2 - 10, Constants.SIZE / 2 - 10, Constants.PLAYERSIZE, Constants.PLAYERSIZE);
            pipes.clear();
            yMove = 0;
            score = 0;

            for(int i = 0; i < 4; i++){
                addPipe(true);
            }
            gameOver = false;
        }

        if (!started){
            started = true;
        }
        else {
            if (yMove > 0){
                yMove = 0;
            }
            yMove -= 10;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        int speed = 10;

        timerTick++;

        if (started){
            for (Rectangle pipe : pipes) {
                pipe.x -= speed;
            }

            if (timerTick % 2 == 0 && yMove < 15){
                yMove += 2;
            }

            for (int i = 0; i < pipes.size(); i++){
                Rectangle pipe = pipes.get(i);

                if (pipe.x + pipe.width < 0){
                    pipes.remove(pipe);

                    if (pipe.y == 0){
                        addPipe(false);
                    }
                }
            }

            player.y += yMove;

            for (Rectangle pipe : pipes){
                if (pipe.y == 0 && player.x + player.width / 2 > pipe.x + pipe.width / 2 -7 && player.x + player.width / 2 < pipe.x + pipe.width / 2 + 7 && !gameOver){
                    score++;
                }

                if (pipe.intersects(player)){
                    gameOver = true;

                    if (player.x <= pipe.x){
                        player.x = pipe.x - player.width;
                    }
                    else if (pipe.y != 0){
                        player.y = pipe.y - player.height;
                    }
                    else if (player.y < pipe.height){
                        player.y = pipe.height;
                    }
                }
                else if (player.y > Constants.MAPMAXHEIGHT || player.y < 0){
                    gameOver = true;
                }
                else if (player.y + yMove >= Constants.MAPMAXHEIGHT){
                    player.y = Constants.MAPMAXHEIGHT - player.height;
                    gameOver = true;
                }
                finalScore = score;
            }
        }
        renderer.repaint();
    }

    public void refresh(Graphics g) {
        if (begining) {
            visual.homeScreen(g);
        }
        else {
            visual.drawGame(g, player);
            for (Rectangle pipe : pipes) {
                visual.drawPipe(g, pipe);
            }
            g.setColor(Color.white);

            if (!started) {
                visual.startBanner(g);
            }
            if (gameOver) {
                visual.gameOverScreen(g, finalScore);
            }
            if (!gameOver && started) {
                visual.scoreBar(g, score);
            }
        }
    }

    public static void main(String[] args){
        flappyCreep = new FlappyCreep();
    }

    @Override
    public void mouseClicked(MouseEvent e){
        Point coords = e.getPoint();
        if(gameOver && coords.x < 80 &&  coords.y < 90){
            begining = true;
            started = false;
            gameOver = false;
            initialize();
        }
        if(!begining) {
            jump();
        }
        else if(coords.x < 650 && coords.x > 150 && coords.y > 430 && coords.y < 490) {
            begining = false;
        }

        else if(coords.x < 390 && coords.x > 160 && coords.y > 530 && coords.y < 590) {
            layout ^= 1;
            visual.layout(layout);


        }
        else if(coords.x < 650 && coords.x > 420 && coords.y > 540 && coords.y < 590) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            jump();
        }
        if(e.getKeyCode() == KeyEvent.VK_L){
            layout ^= 1;
            visual.layout(layout);
        }
        if(e.getKeyCode() == KeyEvent.VK_M){
            if(musicPlay == 1){
                music.pauseMusic();
            }
            else{
                music.resumeMusic();
            }
            musicPlay ^= 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
    }
}