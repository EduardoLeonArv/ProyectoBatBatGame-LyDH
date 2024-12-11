package al.tonikolaba.audio;

import al.tonikolaba.audio.JukeBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class JukeBoxTest {

    private Clip mockClip;

    @Test
    void testLoadValidClip() {
        JukeBox.load("/resources/Music/level1.mp3", "levelMusic");

        assertTrue(JukeBox.getClips().containsKey("levelMusic"),
                "El clip 'levelMusic' debería cargarse correctamente en el mapa.");
    }

    @Test
    void testLoadInvalidClip() {
        assertDoesNotThrow(() -> JukeBox.load("/resources/invalid_audio.mp3", "invalidClip"),
                "El método load debería manejar excepciones sin fallar.");
    }

    @Test
    void testPlayClip() {
        JukeBox.load("/resources/SFX/playerjump.mp3", "jumpSound");

        JukeBox.play("jumpSound");

        verify(mockClip).setFramePosition(0);
        verify(mockClip).start();
    }

    @Test
    void testStopClip() {
        JukeBox.load("/resources/SFX/playerhit.mp3", "hitSound");

        JukeBox.stop("hitSound");

        verify(mockClip).stop();
    }

    @Test
    void testSetPosition() {
        JukeBox.load("/resources/SFX/explode.mp3", "explodeSound");

        JukeBox.setPosition("explodeSound", 500);

        verify(mockClip).setFramePosition(500);
    }

    @Test
    void testGetPosition() {
        when(mockClip.getFramePosition()).thenReturn(250);

        JukeBox.load("/resources/SFX/enemyhit.mp3", "hitSound");

        int position = JukeBox.getPosition("hitSound");

        assertEquals(250, position, "La posición del clip debería ser 250.");
    }

    @Test
    void testCloseClip() {
        JukeBox.load("/resources/SFX/teleport.mp3", "teleportSound");

        JukeBox.close("teleportSound");

        verify(mockClip).stop();
        verify(mockClip).close();
    }

}
