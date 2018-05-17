import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class BotTest
{
    @Test
    public void botAgainstBot()
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

    @Test
    public void botAgainstSimpleBot()
    {
        final ArrayList<PlayerInterface> bots = new ArrayList<PlayerInterface>(2);
        bots.add(new Bot('O'));
        bots.add(new SimpleBot('X'));
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

    @Test
    public void canBotFindWinningMoveHorizontal()
    {
        final ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>(2);
        players.add(new Bot('O'));
        players.add(new Bot('X'));
        final Board board = new Board();
        board.create();
        final GameState gameState = new GameState();
        gameState.setBoard(board);

        final Move firstMove = new Move('X', 2, 5);
        gameState.getBoard().placePebble(firstMove);
        gameState.updateGame(firstMove);

        final Move secondMove = new Move('X', 1, 5);
        gameState.getBoard().placePebble(secondMove);
        gameState.updateGame(secondMove);

        final Move thirdMove = new Move('X', 0, 5);
        gameState.getBoard().placePebble(thirdMove);
        gameState.updateGame(thirdMove);

        final PlayerInterface player = players.get(1);
        final Move move = player.makeMove(gameState);
        gameState.updateGame(move);

        Assert.assertEquals('X', gameState.getBoard().getPebble(2, 5));
    }

    @Test
    public void canBotFindWinningMoveVertical()
    {
        final ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>(2);
        players.add(new Bot('X'));
        players.add(new Bot('O'));
        final Board board = new Board();
        board.create();
        final GameState gameState = new GameState();
        gameState.setBoard(board);

        final Move firstMove = new Move('X', 0, 5);
        gameState.getBoard().placePebble(firstMove);
        gameState.updateGame(firstMove);

        final Move secondMove = new Move('X', 0, 4);
        gameState.getBoard().placePebble(secondMove);
        gameState.updateGame(secondMove);

        final Move thirdMove = new Move('X', 0, 3);
        gameState.getBoard().placePebble(thirdMove);
        gameState.updateGame(thirdMove);

        final PlayerInterface player = players.get(1);
        final Move move = player.makeMove(gameState);
        gameState.updateGame(move);

        Assert.assertEquals('O', gameState.getBoard().getPebble(0, 2));
    }

    @Test
    public void canBotFindWinningMoveDiagonal()
    {
        final ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>(2);
        players.add(new Bot('X'));
        players.add(new Bot('O'));
        final Board board = new Board();
        board.create();
        final GameState gameState = new GameState();
        gameState.setBoard(board);

        final String boardTest =
                          "......\n"
                        + "......\n"
                        + "......\n"
                        + ".0XX..\n"
                        + ".XOXOX\n"
                        + ".OXOXO\n";

        final String boardTest2 =
                "......\n"
                        + "......\n"
                        + "......\n"
                        + "0XX...\n"
                        + "XOXOX.\n"
                        + "OXOXO.\n";

        final String boardTest3 =
                "......\n"
                        + "......\n"
                        + "0XX...\n"
                        + "XOXOX.\n"
                        + "OXOXO.\n"
                        + "OOOOO.\n";

        gameState.updateGameWithString(boardTest3);
        gameState.getBoard().print();

        final PlayerInterface player = players.get(1);
        final Move move = player.makeMove(gameState);
        gameState.updateGame(move);

        Assert.assertEquals('O', gameState.getBoard().getPebble(0,1));
    }

    @Test
    public void canBotFindWinningMoveDiagonalBack()
    {
        final ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>(2);
        players.add(new Bot('X'));
        players.add(new Bot('O'));
        final Board board = new Board();
        board.create();
        final GameState gameState = new GameState();
        gameState.setBoard(board);

        final String boardTest =
                          "......\n"
                        + "......\n"
                        + "......\n"
                        + "..XO..\n"
                        + ".XOO..\n"
                        + "X00O..\n";

        final String boardTest2 =
                        "......\n"
                        + "......\n"
                        + "......\n"
                        + "....XO\n"
                        + "...XOO\n"
                        + "..X00O\n";

        final String boardTest3 =
                        "......\n"
                        + "......\n"
                        + "....XO\n"
                        + "...XOO\n"
                        + "..XOOO\n"
                        + ".OOOOO\n";

        gameState.updateGameWithString(boardTest3);
        gameState.getBoard().print();

        final PlayerInterface player = players.get(1);
        final Move move = player.makeMove(gameState);
        gameState.updateGame(move);

        Assert.assertEquals('O', gameState.getBoard().getPebble(5,1));
    }
}
