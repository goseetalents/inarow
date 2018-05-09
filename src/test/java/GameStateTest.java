import org.junit.Assert;
import org.junit.Test;

public class GameStateTest
{
    @Test
    public void testVertical()
    {
        final GameState gameState = new GameState();
        final Board board = new Board();
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
    public void testHorizontal()
    {
        final GameState gameState = new GameState();
        final Board board = new Board();
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
    public void testHorizontalBackwards()
    {
        final GameState gameState = new GameState();
        final Board board = new Board();
        board.create();
        gameState.setBoard(board);
        gameState.getBoard().placePebble(new Move('X', 3, 5));
        gameState.getBoard().placePebble(new Move('X', 2, 5));
        gameState.getBoard().placePebble(new Move('X', 1, 5));
        final Move move = new Move('X', 0, 5);
        gameState.getBoard().placePebble(move);
        gameState.getBoard().checkIfMoveWon(move);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(move));
    }

    @Test
    public void testCheckXDiagonal()
    {
        final GameState gameState = new GameState();
        final Board board = new Board();
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
    public void testDiagonalDecending()
    {
        final GameState gameState = new GameState();
        final Board board = new Board();
        board.create();
        gameState.setBoard(board);
        gameState.getBoard().placePebble(new Move('X', 2, 2));
        gameState.getBoard().placePebble(new Move('X', 3, 3));
        gameState.getBoard().placePebble(new Move('X', 4, 4));
        final Move move = new Move('X', 5, 5);
        gameState.getBoard().placePebble(move);
        gameState.getBoard().checkIfMoveWon(move);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(move));
    }


    @Test
    public void testDiagonalNotInOrder()
    {
        final GameState gameState = new GameState();
        final Board board = new Board();
        board.create();
        gameState.setBoard(board);

        final Move firstMove = new Move('X', 5, 5);
        gameState.getBoard().placePebble(firstMove);

        final Move secondMove = new Move('X', 4, 4);
        gameState.getBoard().placePebble(secondMove);

        final Move thirdMove = new Move('X', 2, 2);
        gameState.getBoard().placePebble(thirdMove);

        final Move forthMove = new Move('X',1,1);
        gameState.getBoard().placePebble(forthMove);

        final Move finalMove = new Move('X', 3, 3);
        gameState.getBoard().placePebble(finalMove);

        gameState.getBoard().checkIfMoveWon(finalMove);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(finalMove));
    }
    @Test
    public void testCheckXDiagonalBack()
    {
        final GameState gameState = new GameState();
        final Board board = new Board();
        board.create();
        gameState.setBoard(board);
        gameState.getBoard().placePebble(new Move('X', 2, 5));
        gameState.getBoard().placePebble(new Move('X', 3, 4));
        gameState.getBoard().placePebble(new Move('X', 4, 3));
        final Move move = new Move('X', 5, 2);
        gameState.getBoard().placePebble(move);
        gameState.getBoard().checkIfMoveWon(move);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(move));
    }

    @Test
    public void tesDiagonalBackDecending()
    {
        final GameState gameState = new GameState();
        final Board board = new Board();
        board.create();
        gameState.setBoard(board);
        gameState.getBoard().placePebble(new Move('X', 3, 2));
        gameState.getBoard().placePebble(new Move('X', 2, 3));
        gameState.getBoard().placePebble(new Move('X', 1, 4));
        final Move move = new Move('X', 0, 5);
        gameState.getBoard().placePebble(move);
        gameState.getBoard().checkIfMoveWon(move);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(move));
    }


    @Test
    public void testDiagonalBackNotInOrder()
    {
        final GameState gameState = new GameState();
        final Board board = new Board();
        board.create();
        gameState.setBoard(board);

        final Move secondMove = new Move('X', 1, 4);
        gameState.getBoard().placePebble(secondMove);

        final Move thirdMove = new Move('X', 3, 2);
        gameState.getBoard().placePebble(thirdMove);


        final Move firstMove = new Move('X', 0, 5);
        gameState.getBoard().placePebble(firstMove);


        final Move lastMove = new Move('X', 2, 3);
        gameState.getBoard().placePebble(lastMove);

        gameState.getBoard().checkIfMoveWon(lastMove);

        Assert.assertTrue("Expected to win", gameState.getBoard().checkIfMoveWon(lastMove));
    }

}