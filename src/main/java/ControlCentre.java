import java.util.ArrayList;
import java.util.Scanner;

public class ControlCentre
{
    private final GameState iGameState;
    private Scanner iScanner;
    private final ArrayList<Player> iPlayers;

    public ControlCentre()
    {
        iGameState = new GameState();
        iScanner = new Scanner(System.in);
        iPlayers = new ArrayList<Player>();
    }

    public void createGame()
    {
        System.out.println("Please enter name for player one: ");
        iPlayers.add(new Player(iScanner.next(), 'X'));

        System.out.println("Please enter name for player two: ");
        iPlayers.add(new Player(iScanner.next(), 'O'));

        final Board board = new Board();
        board.create();

        iGameState.printBoard(board);

        boolean running = true;
        while (running)
        {
            System.out.println("Choose column 0-5 to place pebble");

            for (final Player player : iPlayers)
            {
                final Move move = player.makeMove(iGameState);
                iGameState.updateGame(move);

                if(iGameState.hasPlayerWon(player.getPebble()))
                {
                    System.out.println(player.getName() + " wins!");
                    running = false;
                    break;
                }
            }
        }
    }
}



