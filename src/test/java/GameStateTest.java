import org.junit.Assert;
import org.junit.Test;

public class GameStateTest
{
    @Test
    public void testCheckXVertical()
    {
        final GameState gameState = new GameState();
        Board board = new Board();
        board.create();
        gameState.setBoard(board);
        gameState.getBoard().placePebble(new Move('X', 0, 0));
        gameState.getBoard().placePebble(new Move('X', 1, 0));
        gameState.getBoard().placePebble(new Move('X', 2, 0));
        final Move move = new Move('X', 3, 0);
        gameState.getBoard().placePebble(move);
        gameState.getBoard().checkIfMoveWon(move);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(move));
    }

    @Test
    public void testCheckXHorizontal()
    {
        GameState gameState = new GameState();
        Board board = new Board();
        board.create();
        gameState.setBoard(board);
        gameState.getBoard().placePebble(new Move('X', 0, 5));
        gameState.getBoard().placePebble(new Move('X', 0, 4));
        gameState.getBoard().placePebble(new Move('X', 0, 3));
        final Move move = new Move('X', 0, 2);
        gameState.getBoard().placePebble(move);
        gameState.getBoard().checkIfMoveWon(move);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(move));
    }

    @Test
    public void testCheckXDiagonal()
    {
        GameState gameState = new GameState();
        Board board = new Board();
        board.create();
        gameState.setBoard(board);
        gameState.getBoard().placePebble(new Move('X', 5, 5));
        gameState.getBoard().placePebble(new Move('X', 4, 4));
        gameState.getBoard().placePebble(new Move('X', 3, 3));
        final Move move = new Move('X', 2, 2);
        gameState.getBoard().placePebble(move);
        gameState.getBoard().checkIfMoveWon(move);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(move));
    }

    @Test
    public void testCheckXDiagonalBack()
    {
        GameState gameState = new GameState();
        Board board = new Board();
        board.create();
        gameState.setBoard(board);
        gameState.getBoard().placePebble(new Move('X', 0, 5));
        gameState.getBoard().placePebble(new Move('X', 1, 4));
        gameState.getBoard().placePebble(new Move('X', 2, 3));
        final Move move = new Move('X', 3, 2);
        gameState.getBoard().placePebble(move);
        gameState.getBoard().checkIfMoveWon(move);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(move));
    }


}