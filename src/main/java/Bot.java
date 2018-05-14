import java.util.LinkedList;
import java.util.Random;

public class Bot implements PlayerInterface
{
    private final char iPebble;
    private final String iName;
    private boolean iChangeColumn;

    public Bot(final char pebble)
    {
        iPebble = pebble;
        iName = "Bot";
    }

    /**
     * @param gameState the current state of our game.
     * @return the move with which row and column the pebble will be placed.
     */
    public Move makeMove(final GameState gameState)
    {
        final Board board = gameState.getBoard();
        int column = getColumn(gameState);
        int row = 0;
        int counter = 1;
        boolean running = true;
        iChangeColumn = true;

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
     * @param gameState current state of game.
     * @return column where Bot will place pebble.
     */
    private int getColumn(final GameState gameState)
    {
        int column = 0;
        boolean correctUserInput = true;
        while (correctUserInput)
        {
            System.out.println(iName + "'s turn");

            column = makeWinningMove(gameState);
            System.out.println("Make winning move " + column);

            if (iChangeColumn)
            {
                System.out.println("No winning move, stop opponent " + column);
                column = stopOpponentFromWinning(gameState);

                if (iChangeColumn)
                {
                    System.out.println("No stopping opponent, random " + column);
                    column = new Random().nextInt(6);
                }
            }

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

    /**
     * @param gameState current state of game.
     * @return column if Bot has 3 in row.
     */
    private int makeWinningMove(final GameState gameState)
    {
        int column = 0;
        final LinkedList<Move> botMoves = gameState.getBotMoves();
        if (botMoves.size() > 2)
        {
            final Move lastBotMove = botMoves.get(botMoves.size() - 1);

            if (gameState.getBoard().verticalCheck(lastBotMove) == 3)
            {
                if (gameState.getBoard().getBoardLayout()[lastBotMove.getColumn()][lastBotMove.getRow() - 1] == '.')
                {
                    column = lastBotMove.getColumn();
                    iChangeColumn = false;
                }
                System.out.println("Found 3 in a row vertical, winning with " + column);
            }
            if (gameState.getBoard().horizontalCheck(lastBotMove) == 3 && iChangeColumn)
            {
                column = getColumnWinningHorizontal(gameState.getBoard(), column);
                System.out.println("Found 3 in a row horizontal, winning with " + column);
            }
            if (gameState.getBoard().diagonalCheck(lastBotMove) == 3 && iChangeColumn)
            {
                column = getColumnWinningDiagonal(gameState.getBoard(), column);
                System.out.println("Found 3 in a row diagonal, winning with " + column);

            }
            if (gameState.getBoard().diagonalBackCheck(lastBotMove) == 3 && iChangeColumn)
            {
                column = getColumnWinningDiagonalBack(gameState.getBoard(), column);
                System.out.println("Found 3 in a row diagonal back, winning with " + column);

            }
        }
        return column;
    }

    /**
     * @param board to place pebble on.
     * @param column to be set to horizontal win.
     * @return column to win horizontal.
     */
    private int getColumnWinningHorizontal(final Board board, int column)
    {
        final LinkedList<Move> botHorizontalMoves = board.getBotHorizontalMoves();
        final Move firstMove = botHorizontalMoves.get(2);
        final Move secondMove = botHorizontalMoves.get(1);
        final Move thirdMove = botHorizontalMoves.get(0);

        if (firstMove.getColumn() > secondMove.getColumn() && secondMove.getColumn() > thirdMove.getColumn())
        {
            if (firstMove.getColumn() != 5)
            {
                if (board.getBoardLayout()[firstMove.getColumn() + 1][firstMove.getRow()] == '.')
                {
                    column = firstMove.getColumn() + 1;
                    iChangeColumn = false;
                }
            }
            if (thirdMove.getColumn() != 0)
            {
                if (board.getBoardLayout()[thirdMove.getColumn() - 1][firstMove.getRow()] == '.')
                {
                    column = thirdMove.getColumn() - 1;
                    iChangeColumn = false;
                }
            }
        }
        if (firstMove.getColumn() < secondMove.getColumn() && secondMove.getColumn() < thirdMove.getColumn())
        {
            if(firstMove.getColumn() == 0)
            {
                if (board.getBoardLayout()[thirdMove.getColumn() + 1][thirdMove.getRow()] == '.')
                {
                    column = thirdMove.getColumn() + 1;
                    iChangeColumn = false;
                }
            }
            else
            {
                if (board.getBoardLayout()[firstMove.getColumn() - 1][firstMove.getRow()] == '.')
                {
                    column = firstMove.getColumn() - 1;
                    iChangeColumn = false;
                }
            }
        }
        return column;
    }

    /**
     * @param board to place pebble on.
     * @param column to be set to diagonal win.
     * @return column to win diagonal.
     */
    private int getColumnWinningDiagonal(final Board board, int column)
    {
        final LinkedList<Move> botDiagonalMoves = board.getBotDiagonalMoves();
        final Move firstMove = botDiagonalMoves.get(2);
        final Move thirdMove = botDiagonalMoves.get(0);

        if ((firstMove.getRow() != 5 && firstMove.getColumn() != 5)
                && (thirdMove.getRow() != 0 && thirdMove.getColumn() != 0))
        {
            if (board.getBoardLayout()[firstMove.getColumn() + 1][firstMove.getRow() + 1] == '.')
            {
                column = firstMove.getColumn() + 1;
                iChangeColumn = false;
            }
            else if (board.getBoardLayout()[thirdMove.getColumn() - 1][thirdMove.getRow() - 1] == '.')
            {
                column = thirdMove.getColumn() - 1;
                iChangeColumn = false;
            }
        }
        return column;
    }

    /**
     * @param board to place pebble on.
     * @param column to be set to diagonal back win.
     * @return column to win diagonal back.
     */
    private int getColumnWinningDiagonalBack(final Board board, int column)
    {
        final LinkedList<Move> botDiagonalBackMoves = board.getBotDiagonalBackMoves();
        final Move firstMove = botDiagonalBackMoves.get(2);
        final Move thirdMove = botDiagonalBackMoves.get(0);

        if ((firstMove.getRow() != 0 && firstMove.getColumn() !=0)
                && (thirdMove.getRow() != 0 && thirdMove.getColumn() != 0))
        {
            if (board.getBoardLayout()[firstMove.getRow() - 1][firstMove.getColumn() - 1] == '.')
            {
                column = firstMove.getColumn() - 1;
                iChangeColumn = false;
            }
            if (thirdMove.getColumn() != 5 && thirdMove.getRow() != 5)
            {
                if (board.getBoardLayout()[thirdMove.getRow() + 1][thirdMove.getColumn() + 1] == '.')
                {
                    column = thirdMove.getColumn() + 1;
                    iChangeColumn = false;
                }
            }
        }
        return column;
    }


    /**
     * @param gameState the current state of game.
     * @return column stopping opponent from winning.
     */
    private int stopOpponentFromWinning(final GameState gameState)
    {
        int column = 0;

        final LinkedList<Move> playerMoves = gameState.getPlayerMoves();
        if (playerMoves.size() > 2)
        {
            final Move lastPlayerMove = playerMoves.get(playerMoves.size() - 1);

            if (gameState.getBoard().verticalCheck(lastPlayerMove) == 3)
            {
                if (lastPlayerMove.getRow() != 0)
                {
                    System.out.println("Row not 0");
                    column = lastPlayerMove.getColumn();
                    iChangeColumn = false;
                }
                System.out.println("Found 3 in a row vertical, stopping with " + column);
            }
            if (gameState.getBoard().horizontalCheck(lastPlayerMove) == 3 && iChangeColumn)
            {
                column = getColumnStoppingOpponentHorizontal(gameState.getBoard(), column);
                System.out.println("Found 3 in a row horizontal, stopping with " + column);

            }
            if (gameState.getBoard().diagonalCheck(lastPlayerMove) == 3 && iChangeColumn)
            {
                column = getColumnStoppingOpponentDiagonal(gameState.getBoard(), column);
                System.out.println("Found 3 in a row diagonal, stopping with " + column);

            }
            if (gameState.getBoard().diagonalBackCheck(lastPlayerMove) == 3 && iChangeColumn)
            {
                column = getColumnStoppingOpponentDiagonalBack(gameState.getBoard(), column);
                System.out.println("Found 3 in a row diagonal back, stopping with " + column);

            }
        }
        return column;
    }

    /**
     * @param board to place pebble.
     * @param column to be set to stop opponent.
     * @return column to stop opponent.
     */
    private int getColumnStoppingOpponentHorizontal(final Board board, int column)
    {
        final LinkedList<Move> playerHorizontalMoves = board.getPlayerHorizontalMoves();
        final Move firstMove = playerHorizontalMoves.get(2);
        final Move thirdMove = playerHorizontalMoves.get(0);

        if (firstMove.getColumn() > thirdMove.getColumn())
        {
            if (firstMove.getColumn() != 5)
            {
                if (board.getBoardLayout()[firstMove.getColumn() + 1][firstMove.getRow()] == '.')
                {
                    column = firstMove.getColumn() + 1;
                    iChangeColumn = false;
                }
            }

            if (thirdMove.getColumn() != 0)
            {
                if (board.getBoardLayout()[thirdMove.getColumn() - 1][firstMove.getRow()] == '.')
                {
                    column = thirdMove.getColumn() - 1;
                    iChangeColumn = false;
                }
            }
        }
        if (firstMove.getColumn() < thirdMove.getColumn())
        {
            if(firstMove.getColumn() == 0)
            {
                if (board.getBoardLayout()[thirdMove.getColumn() + 1][thirdMove.getRow()] == '.')
                {
                    column = thirdMove.getColumn() + 1;
                    iChangeColumn = false;
                }
            }
            else
            {
                if (thirdMove.getColumn() != 5)
                {
                    if (board.getBoardLayout()[thirdMove.getColumn() + 1][thirdMove.getRow()] == '.')
                    {
                        column = thirdMove.getColumn() + 1;
                        iChangeColumn = false;

                    }
                }
                if(firstMove.getColumn() != 0)
                {
                    if (board.getBoardLayout()[firstMove.getColumn() - 1][firstMove.getRow()] == '.')
                    {
                        column = firstMove.getColumn() - 1;
                        iChangeColumn = false;

                    }
                }
            }
        }
        return column;
    }

    /**
     * @param board to place pebble.
     * @param column to be set to stop opponent.
     * @return column to stop opponent.
     */
    private int getColumnStoppingOpponentDiagonal(final Board board, int column)
    {
        // \
        final LinkedList<Move> playerDiagonalMoves = board.getPlayerDiagonalMoves();
        final Move firstMove = playerDiagonalMoves.get(2);
        final Move thirdMove = playerDiagonalMoves.get(0);

        if ((firstMove.getRow() != 5 && firstMove.getColumn() != 5)
                && (thirdMove.getRow() != 0 && thirdMove.getColumn() != 0))
        {
            if (board.getBoardLayout()[firstMove.getColumn() + 1][firstMove.getRow() + 1] == '.')
            {
                column = firstMove.getColumn() + 1;
                iChangeColumn = false;
            }
            else if (board.getBoardLayout()[thirdMove.getColumn() - 1][thirdMove.getRow() - 1] == '.')
            {
                column = thirdMove.getColumn() - 1;
                iChangeColumn = false;
            }
        }
        else
        {
            if (thirdMove.getColumn() != 0 && thirdMove.getRow() != 0)
            {
                if (board.getBoardLayout()[thirdMove.getColumn() -1][thirdMove.getRow() - 1] == '.')
                {
                    column = thirdMove.getColumn() - 1;
                    iChangeColumn = false;
                }
            }
        }
        return column;
    }

    /**
     * @param board to place pebble.
     * @param column to be set to stop opponent.
     * @return column to stop opponent.
     */
    private int getColumnStoppingOpponentDiagonalBack(final Board board, int column)
    {
        // /
        final LinkedList<Move> playerDiagonalBackMoves = board.getPlayerDiagonalBackMoves();
        final Move firstMove = playerDiagonalBackMoves.get(2);
        final Move thirdMove = playerDiagonalBackMoves.get(0);

        if ((firstMove.getRow() != 0 && firstMove.getColumn() !=0) && (thirdMove.getRow() != 0 && thirdMove.getColumn() != 0))
        {
            if (board.getBoardLayout()[firstMove.getRow() - 1][firstMove.getColumn() - 1] == '.')
            {
                column = firstMove.getColumn() - 1;
                iChangeColumn = false;
            }
            if (thirdMove.getColumn() != 5 && thirdMove.getRow() != 5)
            {
                if (board.getBoardLayout()[thirdMove.getRow() + 1][thirdMove.getColumn() + 1] == '.')
                {
                    column = thirdMove.getColumn() + 1;
                    iChangeColumn = false;
                }
            }
        }
        else
        {
            if (thirdMove.getColumn() != 5 && thirdMove.getRow() != 5)
            {
                if (board.getBoardLayout()[thirdMove.getRow() + 1][thirdMove.getColumn() + 1] == '.')
                {
                    column = thirdMove.getColumn() + 1;
                    iChangeColumn = false;
                }
            }
        }
        return column;
    }

    public String getName()
    {
        return iName;
    }
}
