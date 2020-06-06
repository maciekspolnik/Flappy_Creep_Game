package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener, KeyListener
{

    public static FlappyBird flappyBird;

    public final int WIDTH = 800, HEIGHT = 800;

    public Renderer renderer;

    public Rectangle bird;

    public ArrayList<Rectangle> pipes;

    public int timerTick, yMove, score;

    public boolean gameOver, started;

    public Random rand;

    public FlappyBird()
    {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);

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

        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        pipes = new ArrayList<Rectangle>();

        for(int i = 0; i<4;i++){
            addPipe(true);

        }

        timer.start();
    }

    public void addPipe(boolean start)
    {
        int space = 300;
        int width = 100;
        int height = 50 + rand.nextInt(300);

        if (start)
        {
            pipes.add(new Rectangle(WIDTH + width + pipes.size() * 300, HEIGHT - height - 120, width, height));
            pipes.add(new Rectangle(WIDTH + width + (pipes.size() - 1) * 300, 0, width, HEIGHT - height - space));
        }
        else
        {
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, HEIGHT - height - 120, width, height));
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    public void drawPipe(Graphics g, Rectangle pipe)
    {
        g.setColor(Color.green.darker());
        g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
    }

    public void jump()
    {
        if (gameOver)
        {
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            pipes.clear();
            yMove = 0;
            score = 0;

            for(int i = 0; i<4;i++){
                addPipe(true);

            }

            gameOver = false;
        }

        if (!started)
        {
            started = true;
        }
        else {
            if (yMove > 0)
            {
                yMove = 0;
            }

            yMove -= 10;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int speed = 10;

        timerTick++;

        if (started)
        {
            for (Rectangle pipe : pipes) {
                pipe.x -= speed;
            }

            if (timerTick % 2 == 0 && yMove < 15)
            {
                yMove += 2;
            }

            for (int i = 0; i < pipes.size(); i++)
            {
                Rectangle pipe = pipes.get(i);

                if (pipe.x + pipe.width < 0)
                {
                    pipes.remove(pipe);

                    if (pipe.y == 0)
                    {
                        addPipe(false);
                    }
                }
            }

            bird.y += yMove;

            for (Rectangle pipe : pipes)
            {
                if (pipe.y == 0 && bird.x + bird.width / 2 > pipe.x + pipe.width / 2 - 10 && bird.x + bird.width / 2 < pipe.x + pipe.width / 2 + 10)
                {
                    score++;
                }

                if (pipe.intersects(bird))
                {
                    gameOver = true;

                    if (bird.x <= pipe.x)
                    {
                        bird.x = pipe.x - bird.width;

                    }
                    else
                    {
                        if (pipe.y != 0)
                        {
                            bird.y = pipe.y - bird.height;
                        }
                        else if (bird.y < pipe.height)
                        {
                            bird.y = pipe.height;
                        }
                    }
                }
            }

            if (bird.y > HEIGHT - 120 || bird.y < 0)
            {
                gameOver = true;
            }

            if (bird.y + yMove >= HEIGHT - 120)
            {
                bird.y = HEIGHT - 120 - bird.height;
                gameOver = true;
            }
        }

        renderer.repaint();
    }

    public void refresh(Graphics g)
    {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);

        g.setColor(Color.green.darker());
        g.fillRect(0, HEIGHT - 120, WIDTH, 20);

        g.setColor(Color.blue);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for (Rectangle pipe : pipes)
        {
            drawPipe(g, pipe);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 100));

        if (!started)
        {
            g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
        }

        if (gameOver)
        {
            g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("Click to play again",150,HEIGHT / 2 + 50);
            g.setFont(new Font("Arial", Font.PLAIN, 100));
        }

        if (!gameOver && started)
        {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }
    }

    public static void main(String[] args)
    {
        flappyBird = new FlappyBird();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        jump();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
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