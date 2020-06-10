package game;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Visuals {

    public Font minecraftTitleFont, minecraftNormalFont, minecraftSmallFont, minecraftScoreFont;
    public Image gameBackground, gamePlayer, gameGround, gamePipe, gameMenu, menuButton, menuButtonSmall, homeButton, la1Background, la1Player, la1Ground, la1Pipe, la1Menu, la2Background, la2Player, la2Ground, la2Pipe, la2Menu;

    public Visuals() {
        try {
            minecraftTitleFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftTitle.ttf")).deriveFont(75f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftTitle.ttf")));
        } catch (IOException | FontFormatException e) {
            System.exit(1);
        }

        try {
            minecraftNormalFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")).deriveFont(35f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")));
        } catch (IOException | FontFormatException e) {
            System.exit(1);
        }
        try {
            minecraftSmallFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")));
        } catch (IOException | FontFormatException e) {
            System.exit(1);
        }
        try {
            minecraftScoreFont = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\game\\font\\minecraftRegular.otf")));
        } catch (IOException | FontFormatException e) {
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

    public void drawPipe(Graphics g, Rectangle pipe){
        g.setColor(Color.black.darker());
        g.drawImage(gamePipe, pipe.x, pipe.y, pipe.width, pipe.height, Color.BLACK,null);
    }

    public void drawGame(Graphics g, Rectangle player){
        g.drawImage(gameBackground, 0, 0, Constants.SIZE, Constants.MAPMAXHEIGHT, null);
        g.drawImage(gameGround, 0, Constants.MAPMAXHEIGHT, 800, Constants.SIZE - Constants.MAPMAXHEIGHT, null);
        g.drawImage(gamePlayer, player.x, player.y, player.width, player.height, null);

    }
    public void layout(int num) {
        if (num == 1) {
            gameBackground = la1Background;
            gamePlayer = la1Player;
            gameGround = la1Ground;
            gamePipe = la1Pipe;
            gameMenu = la1Menu;
        }
        else if (num == 0) {
            gameBackground = la2Background;
            gamePlayer = la2Player;
            gameGround = la2Ground;
            gamePipe = la2Pipe;
            gameMenu = la2Menu;
        }
    }
    public void gameOverScreen(Graphics g, int score){
        int tooLong;
        g.setFont(minecraftNormalFont);
        g.drawString("Game Over!", 300, Constants.SIZE / 2 - 150);
        if(score <= 10) tooLong = 0;  else tooLong = 5;
        g.drawString("Your score: " + score, 270 - tooLong, Constants.SIZE / 2 - 50);
        g.setFont(minecraftNormalFont);
        g.drawString("Click to play again",240,Constants.SIZE / 2 + 50);
        g.drawImage(homeButton,0, 0, 75, 65, null);
    }
    public void homeScreen(Graphics g){
        g.drawImage(gameMenu, 0, 0, Constants.SIZE, Constants.SIZE, null);

        g.setColor(Color.GRAY);
        g.setFont(minecraftTitleFont);

        g.drawString("Flappy Creep!", 100, Constants.SIZE / 2 - 150);

        g.setColor(Color.WHITE);
        g.setFont(minecraftNormalFont);

        g.drawImage(menuButton, 150, 400, 500, 60, null);
        g.drawString("Start Game", 300, 444);

        g.drawImage(menuButtonSmall, 150, 500, 240, 60, null);
        g.drawString("Layout", 210, 544);

        g.drawImage(menuButtonSmall, 410, 500, 240, 60, null);
        g.drawString("Quit", 500, 544);

        g.setFont(minecraftSmallFont);
        g.drawString("FlappyCreep v1.0.0", 20, Constants.SIZE - 50);

        g.setFont(minecraftNormalFont);
    }
    public void scoreBar(Graphics g, int score){
        g.setFont(minecraftNormalFont);
        g.drawString("Score", 25, 710);
        g.setFont(minecraftScoreFont);
        g.drawString(String.valueOf(score), 65, 750);
    }
    public void startBanner(Graphics g){
        g.setFont(minecraftTitleFont);
        g.drawString("Click to start!", 75, Constants.SIZE / 2 - 50);
    }
}
