
public class GameState
{
    private Board iBoard;

    public void updateGame(final Move move)
    {
        iBoard.placePebble(move);
    }

    public void printBoard(final Board board)
    {
        iBoard = board;
        iBoard.print();
    }

    public boolean hasPlayerWon(final char pebble)
    {
        boolean hasWon = false;
        System.out.println(iBoard.checkPebbleDiagonalBackward(pebble));
        if (!iBoard.checkPebbleHorizontal(pebble)
                || !iBoard.checkPebbleVertical(pebble)
                || !iBoard.checkPebbleDiagonal(pebble)
                || !iBoard.checkPebbleDiagonalBackward(pebble))
        {
            hasWon = true;
        }
        return hasWon;
    }

    public Board getBoard()
    {
        return iBoard;
    }
}

