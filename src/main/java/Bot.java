import org.apache.commons.lang.ArrayUtils;
import java.util.LinkedList;
import java.util.Random;

public class Bot implements PlayerInterface
{
    private final char iPebble;
    private final String iName;
    private boolean iFoundSmartMove;
    private static int[] iColumnToRandomFrom;

    public Bot(final char pebble)
    {
        iPebble = pebble;
        iName = "Bot";
        iColumnToRandomFrom = new int[6];
        iColumnToRandomFrom[0] = 0;
        iColumnToRandomFrom[1] = 1;
        iColumnToRandomFrom[2] = 2;
        iColumnToRandomFrom[3] = 3;
        iColumnToRandomFrom[4] = 4;
        iColumnToRandomFrom[5] = 5;
    }

    /**
     * @param gameState the current state of our game.
     * @return the move with which row and column the pebble will be placed.
     */
    public Move makeMove(final GameState gameState)
    {
        final Board board = gameState.getBoard();
        final int boardBottomRow = board.getBottomRow();
        final char[][] boardLayout = board.getBoardLayout();

        int column = getColumn(gameState);
        int row = 0;
        int counter = 1;

        boolean running = true;
        iFoundSmartMove = false;

        // Determine which row to place pebble
        //
        while (running)
        {
            if (boardLayout[boardBottomRow][column] == '.')
            {
                row = boardBottomRow;
                running = false;
            }
            else if (boardLayout[boardBottomRow][column] == 'X' || boardLayout[boardBottomRow][column] == 'O')
            {
                if (counter > 0 && counter < 6)
                {
                    if (boardLayout[boardBottomRow - counter][column] == '.')
                    {
                        row = boardBottomRow - counter;
                        running = false;
                    }
                }
            }
            if (counter == board.getBoardWidth())
            {
                System.out.println("That column is full");
                iColumnToRandomFrom = ArrayUtils.removeElement(iColumnToRandomFrom, column);
                column = iColumnToRandomFrom[(new Random().nextInt(iColumnToRandomFrom.length))];
                System.out.println(column);
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
            column = makeSmartMove(gameState);

            if (!iFoundSmartMove)
            {
                column = new Random().nextInt(6);
            }

            System.out.println(column);
            correctUserInput = column > gameState.getBoard().getBoardWidth() - 1;
        }
        return column;
    }

    /**
     * @param gameState the current state of the game.
     * @return smart column to place pebble.
     */
    private int makeSmartMove(final GameState gameState)
    {
        int column = 0;
        final Board board = gameState.getBoard();

        LinkedList<Move> botMoves = new LinkedList<Move>();
        LinkedList<Move> opponentMoves = new LinkedList<Move>();

        if (iPebble == 'X')
        {
            botMoves = gameState.getXMoves();
            opponentMoves = gameState.getOMoves();
        }
        else if (iPebble == 'O')
        {
            botMoves = gameState.getOMoves();
            opponentMoves = gameState.getXMoves();
        }
        if (botMoves.size() > 2)
        {
            column = getColumnDependingOnMove(column, board, botMoves);
        }
        if (opponentMoves.size() > 2 && !iFoundSmartMove)
        {
            column = getColumnDependingOnMove(column, board, opponentMoves);
        }
        return column;
    }

    /**
     * @param column to be be assigned new value.
     * @param board to place pebble onto.
     * @param moves from either Bot or opponent.
     * @return new value of column if any three in row found.
     */
    private int getColumnDependingOnMove(int column, final Board board, final LinkedList<Move> moves)
    {
        final Move lastMove = moves.get(moves.size() - 1);

        if (board.verticalCheck(lastMove) == 3 && lastMove.getRow() != 0)
        {
            boolean validMove = board.getBoardLayout()[lastMove.getRow() - 1][lastMove.getColumn()] == '.';

            if (validMove)
            {
                column = lastMove.getColumn();
                iFoundSmartMove = true;
            }
        }
        else if (board.horizontalCheck(lastMove) == 3)
        {
            column = getSmartColumnHorizontal(board, column, lastMove);
        }
        else if (board.diagonalCheck(lastMove) == 3)
        {
            column = getSmartColumnDiagonal(board, column, lastMove);
        }
        else if (board.diagonalBackCheck(lastMove) == 3)
        {
            column = getSmartColumnDiagonalBack(board, column, lastMove);
        }
        return column;
    }

    /**
     * @param board to place pebble on and retrieve horizontal moves from.
     * @param column to be assigned a horizontal value.
     * @param lastMove giving a three in row from either Bot or opponent.
     * @return new value of column if Bot can make a smart move horizontal.
     */
    private int getSmartColumnHorizontal(final Board board, int column, final Move lastMove)
    {
        LinkedList<Move> moves = new LinkedList<Move>();

        if (lastMove.getPebble() == 'X')
        {
            moves = board.getXHorizontalMoves();
        }
        else if (lastMove.getPebble() == 'O')
        {
            moves = board.getOHorizontalMoves();
        }

        final Move firstMove = moves.get(2);
        final Move thirdMove = moves.get(0);

        final char[][] boardLayout = board.getBoardLayout();

        if (firstMove.getColumn() > thirdMove.getColumn())
        {
            column = getColumnHorizontal(boardLayout, column, thirdMove, firstMove);
        }

        if (firstMove.getColumn() < thirdMove.getColumn())
        {
            if (firstMove.getColumn() == 0)
            {
                if (boardLayout[thirdMove.getRow()][thirdMove.getColumn() + 1] == '.')
                {
                    column = thirdMove.getColumn() + 1;
                    iFoundSmartMove = true;
                }
            }
            else
            {
                column = getColumnHorizontal(boardLayout, column, firstMove, thirdMove);
            }
        }
        return column;
    }

    /**
     * @param boardLayout to place pebble on.
     * @param column to be assigned new horizontal value.
     * @param firstMove of the found horizontal moves.
     * @param thirdMove of the found horizontal moves.
     * @return new value of column if smart move horizontal found.
     */
    private int getColumnHorizontal(final char[][] boardLayout, int column, final Move firstMove, final Move thirdMove)
    {
        final int firstMoveRow = firstMove.getRow();
        final int firstMoveColumn = firstMove.getColumn();
        final int thirdMoveRow = thirdMove.getRow();
        final int thirdMoveColumn = thirdMove.getColumn();

        final boolean thirdMoveOnEdge = thirdMoveColumn == 5;
        final boolean firstMoveOnEdge = firstMoveColumn == 0;

        if (!thirdMoveOnEdge)
        {
            if (boardLayout[thirdMoveRow][thirdMoveColumn + 1] == '.')
            {
                column = thirdMoveColumn + 1;
                iFoundSmartMove = true;
            }
            else if (!firstMoveOnEdge)
            {
                if (boardLayout[firstMoveRow][firstMoveColumn - 1] == '.')
                {
                    column = firstMoveColumn - 1;
                    iFoundSmartMove = true;
                }
            }
        }
        else if (!firstMoveOnEdge)
        {
            if (boardLayout[firstMoveRow][firstMoveColumn - 1] == '.')
            {
                column = firstMoveColumn - 1;
                iFoundSmartMove = true;
            }
        }
        return column;
    }

    /**
     * @param board to place pebble on an retrieve diagonal moves from.
     * @param column to be assigned new diagonal value.
     * @param lastMove giving a three in row from either Bot or opponent.
     * @return new value of column if smart move diagonal found.
     */
    private int getSmartColumnDiagonal(final Board board, int column, final Move lastMove)
    {
        LinkedList<Move> moves = new LinkedList<Move>();

        if (lastMove.getPebble() == 'X')
        {
            moves = board.getXDiagonalMoves();
        }
        else if (lastMove.getPebble() == 'O')
        {
            moves = board.getODiagonalMoves();
        }

        Move firstMove = moves.get(0);
        Move secondMove = moves.get(1);
        Move thirdMove = moves.get(2);

        if (thirdMove.getRow() < secondMove.getRow())
        {
            thirdMove = secondMove;
        }

        if (firstMove.getRow() > thirdMove.getRow())
        {
            firstMove = thirdMove;
            thirdMove = firstMove;
        }

        final char[][] boardLayout = board.getBoardLayout();

        final int firstMoveColumn = firstMove.getColumn();
        final int firstMoveRow = firstMove.getRow();
        final int thirdMoveRow = thirdMove.getRow();
        final int thirdMoveColumn = thirdMove.getColumn();

        final boolean firstMoveOnTop = firstMoveRow == 0;
        final boolean firstMoveOnEdge = firstMoveColumn == 0;
        final boolean thirdMoveOnTop = thirdMoveRow == 0;

        final boolean thirdMoveBottom = thirdMoveRow == 5;
        final boolean thirdMoveOnEdge = thirdMoveColumn == 5;
        final boolean firstMoveInBottomRightCorner = firstMoveColumn == 5 && firstMoveRow == 5;

        if (!firstMoveOnTop && !firstMoveOnEdge)
        {
            if (boardLayout[firstMoveRow - 1][firstMoveColumn - 1] == '.')
            {
                column = firstMoveColumn - 1;
                iFoundSmartMove = true;
            }
            else if (!thirdMoveOnTop)
            {
                if (boardLayout[thirdMoveRow - 1][thirdMoveColumn - 1] == '.')
                {
                    column = thirdMoveColumn - 1;
                    iFoundSmartMove = true;
                }
            }
        }
        else if (!firstMoveOnEdge)
        {
            if (boardLayout[firstMoveRow + 1][firstMoveColumn + 1] == '.')
            {
                column = firstMoveColumn + 1;
                iFoundSmartMove = true;
            }
            else if (!thirdMoveOnEdge && !thirdMoveBottom)
            {
                if (boardLayout[thirdMoveRow + 1][thirdMoveColumn + 1] == '.')
                {
                    column = thirdMoveColumn + 1;
                    iFoundSmartMove = true;
                }
            }
        }
        return column;
    }

    /**
     * @param board to place pebble on an retrieve diagonal back moves from.
     * @param column to be assigned new diagonal back value.
     * @param lastMove giving a three in row from either Bot or opponent.
     * @return new value of column if smart move diagonal back found.
     */
    private int getSmartColumnDiagonalBack(final Board board, int column, final Move lastMove)
    {
        LinkedList<Move> moves = new LinkedList<Move>();
        if (lastMove.getPebble() == 'X')
        {
            moves = board.getXDiagonalBackMoves();
        }
        else if (lastMove.getPebble() == 'O')
        {
            moves = board.getODiagonalBackMoves();
        }

        Move firstMove = moves.get(0);
        Move secondMove = moves.get(1);
        Move thirdMove = moves.get(2);

        if (thirdMove.getRow() > secondMove.getRow())
        {
             thirdMove = secondMove;
        }

        if (firstMove.getRow() > thirdMove.getRow())
        {
            firstMove = moves.get(2);
            thirdMove = moves.get(0);
        }

        final int firstMoveColumn = firstMove.getRow();
        final int firstMoveRow = firstMove.getColumn();
        final int thirdMoveColumn = thirdMove.getRow();
        final int thirdMoveRow = thirdMove.getColumn();

        final boolean firstMoveOnTop = firstMoveRow == 0;
        final boolean thirdMoveOnTop = thirdMoveRow == 0;

        final boolean thirdMoveOnEdge = thirdMoveColumn == 5;
        final boolean thirdMoveOnBottom = thirdMoveRow == 5;

        final boolean firstMoveOnEdge = firstMoveColumn == 0 || firstMoveColumn == 5;
        final boolean firstMoveOnBottom = firstMoveRow == 5;

        if (!firstMoveOnEdge && !firstMoveOnTop && !firstMoveOnBottom)
        {
            if (board.getBoardLayout()[firstMoveRow + 1][firstMoveColumn - 1] == '.')
            {
                column = firstMoveColumn - 1;
                iFoundSmartMove = true;
            }
            else if (!thirdMoveOnTop && !thirdMoveOnBottom && !thirdMoveOnEdge)
            {
                if (board.getBoardLayout()[thirdMoveRow - 1][thirdMoveColumn + 1] == '.')
                {
                    column = thirdMoveColumn + 1;
                    iFoundSmartMove = true;
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
