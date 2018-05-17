import java.util.LinkedList;
import java.util.Random;

public class Bot implements PlayerInterface
{
    private final char iPebble;
    private final String iName;
    private boolean iFoundSmartMove;

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
        iFoundSmartMove = false;
        final Board board = gameState.getBoard();
        final int boardBottomRow = board.getBottomRow();
        final char[][] boardLayout = board.getBoardLayout();
        int column = getColumn(gameState);
        int row = 0;
        int counter = 1;
        boolean running = true;

        // Determine which row to place pebble
        //
        while (running)
        {
            if (boardLayout[boardBottomRow][column] == '.')
            {
                row = boardBottomRow;
                running = false;
            } else if (boardLayout[boardBottomRow][column] == 'X' || boardLayout[boardBottomRow][column] == 'O')
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

            column = makeSmartMove(gameState);

            if (!iFoundSmartMove)
            {
                column = new Random().nextInt(6);
                System.out.println("No stopping opponent, random " + column);
            }

            System.out.println(column);

            correctUserInput = column > gameState.getBoard().getBoardWidth() - 1;
        }
        return column;
    }


    private int getColumnHorizontal(final char[][] boardLayout, int column, final Move firstMove, final Move thirdMove)
    {
        final boolean thirdMoveOnEdge = thirdMove.getColumn() != 5;
        final boolean firstMoveOnEdge = firstMove.getColumn() != 0;

        if (thirdMoveOnEdge)
        {
            if (boardLayout[thirdMove.getRow()][thirdMove.getColumn() + 1] == '.')
            {
                column = thirdMove.getColumn() + 1;
                iFoundSmartMove = true;
            }
        }
        if (firstMoveOnEdge)
        {
            if (boardLayout[firstMove.getRow()][firstMove.getColumn() - 1] == '.')
            {
                column = firstMove.getColumn() - 1;
                iFoundSmartMove = true;

            }
        }
        return column;
    }


    public String getName()
    {
        return iName;
    }


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
        } else if (iPebble == 'O')
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

    private int getColumnDependingOnMove(int column, final Board board, final LinkedList<Move> moves)
    {
        final Move lastMove = moves.get(moves.size() - 1);

        if (board.verticalCheck(lastMove) == 3)
        {
            if (lastMove.getRow() != 0)
            {
                boolean validMove = board.getBoardLayout()[lastMove.getRow() - 1][lastMove.getColumn()] == '.';

                if (validMove)
                {
                    column = lastMove.getColumn();
                    iFoundSmartMove = true;
                }
            }
        } else if (board.horizontalCheck(lastMove) == 3)
        {
            column = getSmartColumnHorizontal(board, column, lastMove);
        } else if (board.diagonalCheck(lastMove) == 3)
        {
            column = getSmartColumnDiagonal(board, column, lastMove);
        } else if (board.diagonalBackCheck(lastMove) == 3)
        {
            column = getSmartColumnDiagonalBack(board, column, lastMove);
        }
        return column;
    }

    private int getSmartColumnHorizontal(final Board board, int column, final Move lastMove)
    {
        LinkedList<Move> moves = new LinkedList<Move>();

        if (lastMove.getPebble() == 'X')
        {
            moves = board.getXHorizontalMoves();
        } else if (lastMove.getPebble() == 'O')
        {
            moves = board.getOHorizontalMoves();
            ;
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
            } else
            {
                column = getColumnHorizontal(boardLayout, column, firstMove, thirdMove);
            }
        }

        return column;
    }


    private int getSmartColumnDiagonal(final Board board, int column, final Move lastMove)
    {
        // \
        LinkedList<Move> moves = new LinkedList<Move>();

        if (lastMove.getPebble() == 'X')
        {
            moves = board.getXDiagonalMoves();
        } else if (lastMove.getPebble() == 'O')
        {
            moves = board.getODiagonalMoves();
        }

        final Move firstMove = moves.get(2);
        final Move thirdMove = moves.get(0);

        final char[][] boardLayout = board.getBoardLayout();

        final boolean onEdge = firstMove.getColumn() == 5 && firstMove.getRow() == 5;
        final boolean onTop = thirdMove.getRow() == 0 && thirdMove.getColumn() == 0;

        if (!onEdge && !onTop)
        {
            if (boardLayout[firstMove.getColumn() - 1][firstMove.getRow() - 1] == '.')
            {
                column = firstMove.getColumn() - 1;
                iFoundSmartMove = true;
            } else if (boardLayout[thirdMove.getColumn() - 1][thirdMove.getRow() - 1] == '.')
            {
                column = thirdMove.getColumn() - 1;
                iFoundSmartMove = true;
            }
        }
        return column;
    }

    private int getSmartColumnDiagonalBack(final Board board, int column, final Move lastMove)
    {
        // /
        // row is column, column is row, don't ask why...
        //
        LinkedList<Move> moves = new LinkedList<Move>();

        if (lastMove.getPebble() == 'X')
        {
            moves = board.getXDiagonalBackMoves();
        } else if (lastMove.getPebble() == 'O')
        {
            moves = board.getODiagonalBackMoves();
        }

        Move firstMove = moves.get(0);
        Move thirdMove = moves.get(2);

        if (firstMove.getRow() > thirdMove.getRow())
        {
            firstMove = moves.get(2);
            thirdMove = moves.get(0);
        }

        final boolean firstMoveNotOnTop = firstMove.getRow() == 0 && firstMove.getColumn() == 0;
        final boolean thirdMoveNotOnTop = thirdMove.getRow() == 0 && thirdMove.getColumn() == 0;
        final boolean thirdMoveNotInBottomLeftCorner = thirdMove.getRow() == 0 && thirdMove.getColumn() == 5;
        final boolean thirdMoveNotInBottomRightCorner = thirdMove.getColumn() == 5 && thirdMove.getRow() == 5;

        if (!firstMoveNotOnTop && !thirdMoveNotOnTop)
        {
            if (board.getBoardLayout()[firstMove.getRow() - 1][firstMove.getColumn() - 1] == '.')
            {
                column = firstMove.getRow() - 1;
                iFoundSmartMove = true;
            }
            if (!thirdMoveNotInBottomLeftCorner)
            {
                if (board.getBoardLayout()[thirdMove.getRow() + 1][thirdMove.getColumn() + 1] == '.')
                {
                    column = thirdMove.getRow() + 1;
                    iFoundSmartMove = true;
                }
            }
        } else
        {
            if (!thirdMoveNotInBottomRightCorner)
            {
                if (board.getBoardLayout()[thirdMove.getRow() + 1][thirdMove.getColumn() + 1] == '.')
                {
                    column = thirdMove.getRow() + 1;
                    iFoundSmartMove = true;
                } else if (board.getBoardLayout()[firstMove.getRow() + 1][firstMove.getColumn() + 1] == '.')
                {
                    column = firstMove.getRow() + 1;
                    iFoundSmartMove = true;
                }
            }
        }
        return column;
    }
}
