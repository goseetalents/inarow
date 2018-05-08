import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to control the game.
 */
public class Game
{
    private final GameState iGameState;
    private Scanner iScanner;
    private final ArrayList<Player> iPlayers;

    public Game()
    {
        iGameState = new GameState();
        iScanner = new Scanner(System.in);
        iPlayers = new ArrayList<Player>();
    }

    /**
     * Method to set up game and run game loop.
     */
    public void createGame()
    {
        System.out.println("Please enter name for player one: ");
        iPlayers.add(new Player(iScanner.next(), 'X'));

        System.out.println("Please enter name for player two: ");
        iPlayers.add(new Player(iScanner.next(), 'O'));

        final Board board = new Board();
        board.create();

        iGameState.printBoard(board);

        System.out.println("Choose column 0-5 to place pebble");

        gameLoop();
    }

    private void gameLoop()
    {
        boolean running = true;

        while (running)
        {

            for (final Player player : iPlayers)
            {
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



