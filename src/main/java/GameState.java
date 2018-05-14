import java.util.LinkedList;

/**
 * Class to handle the state of the game.
 */
public class GameState
{
    private Board iBoard;
    private final LinkedList<Move> iPlayerMoves;
    private final LinkedList<Move> iBotMoves;

    public GameState()
    {
        iPlayerMoves = new LinkedList<Move>();
        iBotMoves = new LinkedList<Move>();
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
            iPlayerMoves.add(move);
        }
        else
        {
            iBotMoves.add(move);
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

    public LinkedList<Move> getPlayerMoves()
    {
        return iPlayerMoves;
    }

    public LinkedList<Move> getBotMoves()
    {
        return iBotMoves;
    }
}

