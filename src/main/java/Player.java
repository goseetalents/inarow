import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Player class to make move.
 */
public class Player implements PlayerInterface
{
    private final String iName;
    private final char iPebble;
    private Scanner iScanner;

    public Player(final String name, final char pebble)
    {
        iName = name;
        iPebble = pebble;
        iScanner = new Scanner(System.in);
    }

    /**
     * @param gameState the current state of our game.
     * @return the move with which row and column the pebble will be placed.
     */
    public Move makeMove(final GameState gameState)
    {
        System.out.println(iName + "'s turn");

        final Board board = gameState.getBoard();
        int column = 0;
        int row = 0;
        int counter = 1;

        try
        {
            column = iScanner.nextInt();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Enter a number between 0-5");
            iScanner = new Scanner(System.in);
        }

        // Determine which row to place pebble
        //
        while (true)
        {
                if (column > board.getBoardWidth() - 1)
                {
                    System.out.println("That's not a valid column");
                    break;
                }
                if (board.getBoardLayout()[board.getBottomRow()][column] == '.')
                {
                    row = board.getBottomRow();
                    break;
                }
                else if (board.getBoardLayout()[board.getBottomRow()][column] == 'X'
                        || board.getBoardLayout()[board.getBottomRow()][column] == 'O')
                {
                    if (board.getBoardLayout()[board.getBottomRow() - counter][column] == '.')
                    {
                        row = board.getBottomRow() - counter;
                        break;
                    }
                }
                if (counter == board.getBoardWidth())
                {
                    System.out.println("That column is full");
                    break;
                }

            counter++;
        }
        return new Move(iPebble, column, row);
    }

    public String getName()
    {
        return iName;
    }

    public char getPebble()
    {
        return iPebble;
    }

}
