package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link FruitViewer}.
 * We ensure draw(...) calls the correct Graphics method based on 'type'.
 */
class FruitViewerTest {

    private FruitViewer fruitViewer;
    private Graphics graphics;
    private Fruit fruit;

    @BeforeEach
    void setUp() {
        fruitViewer = new FruitViewer();
        graphics = mock(Graphics.class);
        fruit = mock(Fruit.class);
        when(fruit.getPosition()).thenReturn(new Position(5,5));
    }

    @Test
    @DisplayName("draw(...) with type=1 => drawAppleFruit(...)")
    void testDrawApple() {
        fruitViewer.draw(fruit, graphics, 1);
        verify(graphics).drawAppleFruit(new Position(5,5));
    }

    @Test
    @DisplayName("draw(...) with type=2 => drawBananaFruit(...)")
    void testDrawBanana() {
        fruitViewer.draw(fruit, graphics, 2);
        verify(graphics).drawBananaFruit(new Position(5,5));
    }

    @Test
    @DisplayName("draw(...) with type=3 => drawCherryFruit(...)")
    void testDrawCherry() {
        fruitViewer.draw(fruit, graphics, 3);
        verify(graphics).drawCherryFruit(new Position(5,5));
    }

    @Test
    @DisplayName("draw(...) with type=4 => drawPineappleFruit(...)")
    void testDrawPineapple() {
        fruitViewer.draw(fruit, graphics, 4);
        verify(graphics).drawPineappleFruit(new Position(5,5));
    }

    @Test
    @DisplayName("draw(...) with type=5 => drawStrawberryFruit(...)")
    void testDrawStrawberry() {
        fruitViewer.draw(fruit, graphics, 5);
        verify(graphics).drawStrawberryFruit(new Position(5,5));
    }

    @Test
    @DisplayName("draw(...) with unknown type => no calls (mutation coverage for 'switch' completeness)")
    void testDrawUnknownType() {
        fruitViewer.draw(fruit, graphics, 99);
        // No calls => we can verify none of the known methods are triggered
        verify(graphics, never()).drawAppleFruit(any());
        verify(graphics, never()).drawBananaFruit(any());
        verify(graphics, never()).drawCherryFruit(any());
        verify(graphics, never()).drawPineappleFruit(any());
        verify(graphics, never()).drawStrawberryFruit(any());
    }
}
