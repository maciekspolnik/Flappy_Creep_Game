package game;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener, KeyListener{
    public static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 800, MAPHEIGHTMAX = 675, MAPHEIGHTMIN = 0;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle> pipes;
    public int timerTick, yMove, score, finalScore = 0;
    public boolean gameOver, started, begining = true;
    public Random rand;
    public Font minecraftTitleFont, minecraftNormalFont, MinecraftScoreFont;
    public Image gameBackgroud, gamePlayer, gameGround, gamePipe, layer, skeleton, lava, creeper, background;

    JFrame jframe = new JFrame();
    Timer timer = new Timer(20, this);

    public FlappyBird(){
        loadFiles();
        gameSettings();
        initialize();

        timer.start();
    }
    public void initialize(){
        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        pipes = new ArrayList<Rectangle>();

        for(int i = 0; i<4;i++){
            addPipe(true);
        }
    }

    public void gameSettings(){
        renderer = new Renderer();
        rand = new Random();
        jframe.add(renderer);
        jframe.setTitle("Flappy Bird");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setVisible(true);
    }
    public void layout(int num){
    }

    public void loadFiles(){
        try{
            minecraftTitleFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftTitle.ttf")).deriveFont(75f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftTitle.ttf")));
        }
        catch(IOException | FontFormatException e){
        }

        try{
            minecraftNormalFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")).deriveFont(35f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")));
        }
        catch(IOException | FontFormatException e){
        }

        try{
            MinecraftScoreFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")));
        }
        catch(IOException | FontFormatException e){
        }
        layer = new ImageIcon(".\\src\\main\\java\\game\\img\\layer.png").getImage();
        background = new ImageIcon(".\\src\\main\\java\\game\\img\\background.jpg").getImage();
        creeper = new ImageIcon(".\\src\\main\\java\\game\\img\\creeper.png").getImage();
        lava = new ImageIcon(".\\src\\main\\java\\game\\img\\lava.gif").getImage();
        skeleton = new ImageIcon(".\\src\\main\\java\\game\\img\\wither-skeleton.png").getImage();
    }

    public void addPipe(boolean start){
        int space = 300;
        int width = 100;
        int height = 50 + rand.nextInt(300);

        if (start){
            pipes.add(new Rectangle(WIDTH + width + pipes.size() * 300, MAPHEIGHTMAX - height, width, height));
            pipes.add(new Rectangle(WIDTH + width + (pipes.size() - 1) * 300, 0, width, HEIGHT - height - space));
        }
        else{
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, MAPHEIGHTMAX - height, width, height));
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    public void drawPipe(Graphics g, Rectangle pipe){
        g.setColor(Color.black.darker());
        g.drawImage(lava, pipe.x, pipe.y, pipe.width, pipe.height, Color.BLACK,null);
    }

    public void jump(){
        if (gameOver){
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            pipes.clear();
            yMove = 0;
            score = 0;

            for(int i = 0; i<4; i++){
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

            bird.y += yMove;

            for (Rectangle pipe : pipes){
                if (pipe.y == 0 && bird.x + bird.width / 2 > pipe.x + pipe.width / 2 - 10 && bird.x + bird.width / 2 < pipe.x + pipe.width / 2 + 10 && !gameOver){
                    score++;
                }

                if (pipe.intersects(bird)){
                    gameOver = true;


                    if (bird.x <= pipe.x){
                        bird.x = pipe.x - bird.width;

                    }
                    else if (pipe.y != 0){
                        bird.y = pipe.y - bird.height;
                    }
                    else if (bird.y < pipe.height){
                        bird.y = pipe.height;
                    }
                }
                else if (bird.y > MAPHEIGHTMAX || bird.y < 0){
                    gameOver = true;
                    //System.out.println("Przecięcie");
                }
                else if (bird.y + yMove >= MAPHEIGHTMAX){
                    bird.y = MAPHEIGHTMAX - bird.height;
                    gameOver = true;

                }
                finalScore = score;
            }
        }
        renderer.repaint();
    }

    public void refresh(Graphics g){
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (begining) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("Welcome to our Flappy Bird!", 75, HEIGHT / 2 - 150);

            g.setColor(Color.red.darker());
            g.fillRect(300,350,200,100);
            g.setColor(Color.green.darker());
            g.fillRect(300,500,200,100);
        }
        else{
            g.drawImage(background, 0, 0,800,MAPHEIGHTMAX, null);
            g.drawImage(layer, 0, MAPHEIGHTMAX,800,HEIGHT-MAPHEIGHTMAX, null);
            g.drawImage(creeper, bird.x, bird.y, bird.width, bird.height, null);

            for (Rectangle pipe : pipes){
                drawPipe(g, pipe);
            }

            g.setColor(Color.white);

            if (!started){
                g.setFont(minecraftTitleFont);
                g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
            }

            if (gameOver){
                g.setFont(minecraftNormalFont);
                g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
                g.drawString("Your score: " + finalScore, 200, HEIGHT / 2 + 50);
                g.setFont(minecraftNormalFont);
                g.drawString("Click to play again",350,HEIGHT / 2 + 150);
            }

            if (!gameOver && started) {
                g.setFont(minecraftNormalFont);
                g.drawString("Score", 25, 710);
                g.setFont(MinecraftScoreFont);
                g.drawString(String.valueOf(score), 65, 750);
            }
        }
    }

    public static void main(String[] args){
        flappyBird = new FlappyBird();
    }

    @Override
    public void mouseClicked(MouseEvent e){
        Point coords = e.getLocationOnScreen();

        if(!begining) {
            jump();
        }
        else if(coords.x < 500 && coords.x > 300 && coords.y < 450 && coords.y > 350) {
            // Użyj Layoutu 1
            begining = false;
        }
        else if(coords.x < 500 && coords.x > 300 && coords.y > 500 && coords.y < 600) {
            // Użyj Layoutu 2
            begining = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            jump();
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

}
