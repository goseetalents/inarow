import java.util.Random;

/**
 * Simple Bot will only make random moves.
 */
public class SimpleBot implements PlayerInterface
{
    private final char iPebble;
    private final String iName;

    public SimpleBot(final char pebble)
    {
        iPebble = pebble;
        iName = "Simple Bot";
    }

    /**
     * @param gameState to determine move.
     * @return random move.
     */
    public Move makeMove(final GameState gameState)
    {
        final Board board = gameState.getBoard();
        int column = getColumn(gameState);
        int row = 0;
        int counter = 1;
        boolean running = true;

        // Determine which row to place pebble
        //
        while (running)
        {
            if (board.getBoardLayout()[board.getBottomRow()][column] == '.')
            {
                row = board.getBottomRow();
                running = false;
            }
            else if (board.getBoardLayout()[board.getBottomRow()][column] == 'X'
                    || board.getBoardLayout()[board.getBottomRow()][column] == 'O')
            {
                if (counter > 0 && counter < 6)
                {
                    if (board.getBoardLayout()[board.getBottomRow() - counter][column] == '.')
                    {
                        row = board.getBottomRow() - counter;
                        running = false;
                    }
                }
            }
            if (counter == board.getBoardWidth())
            {
                System.out.println("That column is full");
                column = getColumn(gameState);
                running = true;
            }

            counter++;
        }
        return new Move(iPebble, column, row);
    }

    /**
     * @param gameState to find valid column.
     * @return a valid random column.
     */
    private int getColumn(final GameState gameState)
    {
        int column = 0;
        boolean correctUserInput = true;
        while (correctUserInput)
        {
            System.out.println(iName + "'s turn");

            column = new Random().nextInt(6);

            System.out.println(column);
            if (column > gameState.getBoard().getBoardWidth() - 1)
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

    public String getName()
    {
        return iName;
    }
}
