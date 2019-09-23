import com.topGame.entity.Game;
import com.topGame.repository.GameRepository;
import com.topGame.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Before
    public void setUp() {

        Game game = new Game("CS:GO");
        gameService.save(game);
        Mockito.when(gameRepository.findByName("CS:GO")).thenReturn(game);

    }

    @Test
    public void getAllLabOutputTest() {

        String name = "CS:GO";
        Game game = gameRepository.findByName(name);

        assertNotNull(game);
        assertEquals(name, game.getName());

    }
}