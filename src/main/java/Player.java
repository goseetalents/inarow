import java.util.InputMismatchException;
import java.util.Random;
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
        final Board board = gameState.getBoard();
        int column;
        if (iPebble == 'O')
        {
            column = getRandomColumn(board);
        }
        else
        {
            column = getColumn(board);
        }

        int row = 0;
        int counter = 1;

        // Determine which row to place pebble
        //
        while (true)
        {
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

    /**
     * @param board to place pebble on.
     * @return a valid random column.
     */
    private int getRandomColumn(final Board board)
    {
        int column = 0;
        boolean correctUserInput = true;
        while (correctUserInput)
        {
            System.out.println(iName + "'s turn");

            column = new Random().nextInt(6);
            System.out.println(column);
            if (column > board.getBoardWidth() - 1)
            {
                System.out.println("That's not a valid column");
                correctUserInput = true;
            }
            else
            {
                correctUserInput = false;
            }
        }
        return column;
    }

    /**
     * @param board to check.
     * @return a valid column from the player.
     */
    private int getColumn(final Board board)
    {
        int column = 0;
        boolean correctUserInput = true;
        while (correctUserInput)
        {
            System.out.println(iName + "'s turn");
            try
            {
                column = iScanner.nextInt();
                correctUserInput = false;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Enter a number between 0-5");
                iScanner = new Scanner(System.in);
                correctUserInput = true;
            }
            if (column > board.getBoardWidth() - 1)
            {
                System.out.println("That's not a valid column");
                correctUserInput = true;
            }
        }
        return column;
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
