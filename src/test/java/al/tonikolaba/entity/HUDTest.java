package al.tonikolaba.entity;

import al.tonikolaba.entity.HUD;
import al.tonikolaba.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HUDTest {

    private Player mockPlayer;
    private Graphics2D mockGraphics;
    private HUD hud;

    @BeforeEach
    void setUp() {
        mockPlayer = mock(Player.class);
        mockGraphics = mock(Graphics2D.class);
        hud = new HUD(mockPlayer);
    }

    @Test
    void testConstructorInitializesImages() {
        assertNotNull(hud, "El HUD debería inicializarse correctamente.");
        // Aquí puedes agregar más validaciones si los recursos pueden exponerse.
    }

    @Test
    void testConstructorHandlesExceptions() {
        Player faultyPlayer = null;
        assertDoesNotThrow(() -> new HUD(faultyPlayer),
                "El constructor debería manejar excepciones sin lanzar errores.");
    }

    @Test
    void testDrawHealthAndLives() {
        // Configurar el jugador para devolver valores de salud y vidas
        when(mockPlayer.getHealth()).thenReturn(3);
        when(mockPlayer.getLives()).thenReturn(2);
        when(mockPlayer.getTimeToString()).thenReturn("00:30");

        // Llamar al método draw
        hud.draw(mockGraphics);

        // Verificar que las imágenes de salud y vidas se dibujan correctamente
        verify(mockGraphics, times(3)).drawImage(any(BufferedImage.class), anyInt(), eq(10), eq(null));
        verify(mockGraphics, times(2)).drawImage(any(BufferedImage.class), anyInt(), eq(25), eq(null));
        verify(mockGraphics).drawString("00:30", 290, 15);
    }

    @Test
    void testDrawHandlesUnexpectedValues() {
        when(mockPlayer.getHealth()).thenReturn(-1);
        when(mockPlayer.getLives()).thenReturn(-1);

        assertDoesNotThrow(() -> hud.draw(mockGraphics),
                "El método draw debería manejar valores inesperados sin lanzar excepciones.");
    }
}
