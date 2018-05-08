/**
 * Class to handle the state of the game.
 */
public class GameState
{
    private Board iBoard;

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
}

