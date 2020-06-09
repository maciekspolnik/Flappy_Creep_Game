package game;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class FlappyCreep implements ActionListener, MouseListener, KeyListener{
    public static FlappyCreep flappyCreep;
    public final int WIDTH = 800, HEIGHT = 800, MAPHEIGHTMAX = 675, MAPHEIGHTMIN = 0;
    public final int PLAYERWIDTH = 25, PLAYERHEIGHT = 25;
    public Renderer renderer;
    public Rectangle player;
    public ArrayList<Rectangle> pipes;
    public int timerTick, yMove, score, finalScore = 0, layout = 1;
    public boolean gameOver, started, begining = true;
    public Random rand;
    public Font minecraftSmallFont, minecraftTitleFont, minecraftNormalFont, MinecraftScoreFont;
    public Image gameBackground, gamePlayer, gameGround, gamePipe, gameMenu,menuButton, menuButtonSmall, homeButton, la1Background, la1Player, la1Ground, la1Pipe, la1Menu, la2Background, la2Player, la2Ground, la2Pipe, la2Menu;

    JFrame jframe = new JFrame();
    Timer timer = new Timer(20, this);

    public FlappyCreep(){
        loadFiles();
        gameSettings();
        initialize();
        layout(layout);
        timer.start();
    }
    public void initialize(){
        player = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, PLAYERWIDTH, PLAYERHEIGHT);
        pipes = new ArrayList<Rectangle>();

        for(int i = 0; i<4;i++){
            addPipe(true);
        }
    }

    public void gameSettings(){
        renderer = new Renderer();
        rand = new Random();
        jframe.add(renderer);
        jframe.setTitle("Flappy Creep");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setResizable(true);
        jframe.setVisible(true);
    }
    public void layout(int num){
        if(num==1) {
            gameBackground = la1Background;
            gamePlayer = la1Player;
            gameGround = la1Ground;
            gamePipe = la1Pipe;
            gameMenu = la1Menu;
        }
        else if(num==0){
            gameBackground = la2Background;
            gamePlayer = la2Player;
            gameGround = la2Ground;
            gamePipe = la2Pipe;
            gameMenu = la2Menu;
        }
    }

    public void loadFiles(){
        try{
            minecraftTitleFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftTitle.ttf")).deriveFont(75f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftTitle.ttf")));
        }
        catch(IOException | FontFormatException e){
            System.exit(1);
        }

        try{
            minecraftNormalFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")).deriveFont(35f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")));
        }
        catch(IOException | FontFormatException e){
            System.exit(1);
        }
        try{
            minecraftSmallFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")));
        }
        catch(IOException | FontFormatException e){
            System.exit(1);
        }
        try{
            MinecraftScoreFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")));
        }
        catch(IOException | FontFormatException e){
            System.exit(1);
        }
        la1Ground = new ImageIcon(".\\src\\main\\java\\game\\img\\dirt.jpg").getImage();
        la1Background = new ImageIcon(".\\src\\main\\java\\game\\img\\back.png").getImage();
        la1Player = new ImageIcon(".\\src\\main\\java\\game\\img\\creeper.png").getImage();
        la1Pipe = new ImageIcon(".\\src\\main\\java\\game\\img\\wood.jpg").getImage();
        la1Menu = new ImageIcon(".\\src\\main\\java\\game\\img\\menu_background.png").getImage();

        la2Ground = new ImageIcon(".\\src\\main\\java\\game\\img\\nether_brick.jpg").getImage();
        la2Background = new ImageIcon(".\\src\\main\\java\\game\\img\\nether1.jpg").getImage();
        la2Player = new ImageIcon(".\\src\\main\\java\\game\\img\\wither-skeleton.png").getImage();
        la2Pipe = new ImageIcon(".\\src\\main\\java\\game\\img\\lava.gif").getImage();
        la2Menu = new ImageIcon(".\\src\\main\\java\\game\\img\\nether_background.png").getImage();

        menuButton = new ImageIcon(".\\src\\main\\java\\game\\img\\button.png").getImage();
        menuButtonSmall = new ImageIcon(".\\src\\main\\java\\game\\img\\button-small.png").getImage();
        homeButton = new ImageIcon(".\\src\\main\\java\\game\\img\\button-home.png").getImage();

    }

    public void addPipe(boolean start){
        int space = 350;
        int width = 100;
        int height = 50 + rand.nextInt(350);

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
        g.drawImage(gamePipe, pipe.x, pipe.y, pipe.width, pipe.height, Color.BLACK,null);
    }

    public void jump(){
        if (gameOver){
            player = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, PLAYERWIDTH, PLAYERHEIGHT);
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
                else if (player.y > MAPHEIGHTMAX || player.y < 0){
                    gameOver = true;
                }
                else if (player.y + yMove >= MAPHEIGHTMAX){
                    player.y = MAPHEIGHTMAX - player.height;
                    gameOver = true;
                }
                finalScore = score;
            }
        }
        renderer.repaint();
    }

    public void refresh(Graphics g){
        if (begining) {
            g.drawImage(gameMenu, 0, 0,WIDTH,HEIGHT, null);

            g.setColor(Color.GRAY);
            g.setFont(minecraftTitleFont);
            g.drawString("Flappy Creep!", 100, HEIGHT / 2 - 150);

            g.setColor(Color.WHITE);
            g.setFont(minecraftNormalFont);
            g.drawImage(menuButton, 150, 400,500,60, null);
            g.drawString("Start Game", 300, 444);
            g.drawImage(menuButtonSmall, 150, 500,240,60, null);
            g.drawString("Layout", 210, 544);
            g.drawImage(menuButtonSmall, 410, 500,240,60, null);
            g.drawString("Quit", 500, 544);
            g.setFont(minecraftSmallFont);
            g.drawString("FlappyCreep v1.0.0", 20,HEIGHT - 50);

            g.setFont(minecraftNormalFont);
        }
        else{
            g.drawImage(gameBackground, 0, 0,WIDTH,MAPHEIGHTMAX, null);
            g.drawImage(gameGround, 0, MAPHEIGHTMAX,800,HEIGHT-MAPHEIGHTMAX, null);
            g.drawImage(gamePlayer, player.x, player.y, player.width, player.height, null);

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
                g.drawString("Game Over!", 300, HEIGHT / 2 - 150);
                if(finalScore <= 10){
                    g.drawString("Your score: " + finalScore, 270, HEIGHT / 2 - 50);
                }
                else{
                    g.drawString("Your score: " + finalScore, 265, HEIGHT / 2 - 50);
                }
                g.setFont(minecraftNormalFont);
                g.drawString("Click to play again",240,HEIGHT / 2 + 50);
                g.drawImage(homeButton,0, 0, 75, 65, null);
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
        flappyCreep = new FlappyCreep();
    }

    @Override
    public void mouseClicked(MouseEvent e){
        Point coords = e.getPoint();
        System.out.print(coords.x);
        System.out.print(' ');
        System.out.println(coords.y);
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
            System.out.println("Górny");
            begining = false;
        }

        else if(coords.x < 390 && coords.x > 160 && coords.y > 530 && coords.y < 590) {
            layout ^= 1;
            layout(layout);
            System.out.println("Dolny lewy");

        }
        else if(coords.x < 650 && coords.x > 420 && coords.y > 540 && coords.y < 590) {
            System.out.println("Dolny prawy");
            //System.exit(0);
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            jump();
        }
        if (e.getKeyCode() == KeyEvent.VK_L){
            layout ^= 1;
            layout(layout);
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
    }
}