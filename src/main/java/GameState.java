import java.util.LinkedList;

/**
 * Class to handle the state of the game.
 */
public class GameState
{
    private Board iBoard;
    private final LinkedList<Move> iXMoves;
    private final LinkedList<Move> iOMoves;

    public GameState()
    {
        iXMoves = new LinkedList<Move>();
        iOMoves = new LinkedList<Move>();
    }

    /**
     * @param board to be set to GameState.
     */
    public void setBoard(final Board board)
    {
        iBoard = board;
    }

    /**
     * @param move from user to be placed on board.
     */
    public void updateGame(final Move move)
    {
        if (move.getPebble() == 'X')
        {
            iXMoves.add(move);
        }
        else if (move.getPebble() == 'O')
        {
            iOMoves.add(move);
        }
        iBoard.placePebble(move);
    }

    /**
     * Method to print the current board.
     * @param board the game board.
     */
    public void printBoard(final Board board)
    {
        iBoard = board;
        iBoard.print();
    }

    public Board getBoard()
    {
        return iBoard;
    }

    public LinkedList<Move> getXMoves()
    {
        return iXMoves;
    }

    public LinkedList<Move> getOMoves()
    {
        return iOMoves;
    }

    /**
     * Method for testing.
     * @param boardTest with mock up board.
     */
    public void updateGameWithString(final String boardTest)
    {
        final char[][] board = new char[6][6];

        final String[] split = boardTest.split("\n");

        for (int row = 0; row < 6; row++)
        {
            for (int column = 0; column < 6; column++)
            {
                final char c = split[row].charAt(column);
                updateGame(new Move(c, column, row));
                board[row][column] = c;
            }
        }
        iBoard.setBoard(board);
    }
}

