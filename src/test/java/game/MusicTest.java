package game;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class MusicTest {
    public Music testMusic = new Music();
    @Test
    public void testMusicFileCreation(){ Assert.assertTrue(testMusic.musicPath.exists()); }
    @Test
    public void testIfClipIsOpen(){ Assert.assertTrue(testMusic.clip.isOpen()); }
    @Test
    public void testIfMusicPlays(){ Assert.assertTrue(testMusic.clip.isRunning()); }
}
