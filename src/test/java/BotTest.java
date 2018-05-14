import org.junit.Test;

import java.util.ArrayList;


public class BotTest
{
    @Test
    public void createBotGame()
    {
        final ArrayList<PlayerInterface> bots = new ArrayList<PlayerInterface>(2);
        bots.add(new Bot('X'));
        bots.add(new Bot('O'));
        final Board board = new Board();
        board.create();
        final GameState gameState = new GameState();
        gameState.setBoard(board);

        boolean running = true;

        while (running)
        {
            for (final PlayerInterface bot : bots)
            {
                final Move move = bot.makeMove(gameState);
                gameState.updateGame(move);

                if (gameState.getBoard().checkIfMoveWon(move))
                {
                    System.out.println(bot.getName() + " wins!");
                    running = false;
                    break;
                }
            }
        }
    }
}
