public class Game
{
    private final Player iPlayerX;
    private final Player iPlayerO;
    private final Board iBoard;

    public Game(final Player playerX, final Player playerO, final Board board)
    {
        iPlayerX = playerX;
        iPlayerO = playerO;
        iBoard = board;
    }

    public Player getPlayerX()
    {
        return iPlayerX;
    }

    public Player getPlayerO()
    {
        return iPlayerO;
    }

    public Board getBoard()
    {
        return iBoard;
    }
}

