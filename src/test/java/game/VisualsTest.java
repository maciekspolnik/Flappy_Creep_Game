package game;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.awt.*;


public class VisualsTest {
    public Visuals testVisuals = new Visuals();

    @Test
    public void testIfLayoutWorksCorrectly(){
        testVisuals.layout(1);
        Assert.assertEquals(testVisuals.gamePlayer,testVisuals.la1Player);
    }

    @Test
    public void testIfFontTypeIsCorrect(){
        Assert.assertTrue(testVisuals.minecraftNormalFont instanceof Font);
    }



}
