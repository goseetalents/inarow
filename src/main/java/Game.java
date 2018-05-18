import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to control the game.
 */
public class Game
{
    private final GameState iGameState;
    private final Scanner iScanner;
    private final ArrayList<PlayerInterface> iPlayers;

    public Game()
    {
        iGameState = new GameState();
        iScanner = new Scanner(System.in);
        iPlayers = new ArrayList<PlayerInterface>();
    }

    /**
     * Method to set up game and run game loop.
     */
    public void create()
    {
        System.out.println("Please enter name for player: ");
        iPlayers.add(new Player(iScanner.next(), 'X'));

        iPlayers.add(new Bot('O'));

        final Board board = new Board();
        board.create();

        iGameState.printBoard(board);

        System.out.println("Choose column 0-5 to place pebble");

        gameLoop();
    }

    /**
     * Game loop.
     */
    private void gameLoop()
    {
        boolean running = true;

        while (running)
        {
            for (final PlayerInterface player : iPlayers)
            {
                if (iGameState.getXMoves().size() == 18 && iGameState.getOMoves().size() == 18)
                {
                    System.out.println("No more moves! No winner.");
                    running = false;
                }

                final Move move = player.makeMove(iGameState);
                iGameState.updateGame(move);

                if (iGameState.getBoard().checkIfMoveWon(move))
                {
                    System.out.println(player.getName() + " wins!");
                    running = false;
                    break;
                }
            }
        }
    }
}



