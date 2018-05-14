import java.util.LinkedList;

/**
 * Board class to handle board set up and logic.
 */
public class Board
{
    private final char[][] iBoard;
    private final int iBoardHeight;
    private final int iBoardWidth;

    private  LinkedList<Move> iPlayerHorizontalMoves;
    private  LinkedList<Move> iPlayerDiagonalMoves;
    private  LinkedList<Move> iPlayerDiagonalBackMoves;

    private  LinkedList<Move> iBotHorizontalMoves;
    private  LinkedList<Move> iBotDiagonalMoves;
    private  LinkedList<Move> iBotDiagonalBackMoves;



    public Board()
    {
        iBoard = new char[6][6];
        iBoardHeight = 6;
        iBoardWidth = 6;

        iPlayerHorizontalMoves = new LinkedList<Move>();
        iPlayerDiagonalMoves = new LinkedList<Move>();
        iPlayerDiagonalBackMoves = new LinkedList<Move>();

        iBotHorizontalMoves = new LinkedList<Move>();
        iBotDiagonalMoves = new LinkedList<Move>();
        iBotDiagonalBackMoves = new LinkedList<Move>();
    }

    /**
     * Fill the board with "." in 6x6 pattern.
     */
    public void create()
    {
        for (int i = 0; i < iBoardHeight; i++)
        {
            for (int j = 0; j < iBoardWidth; j++)
            {
                iBoard[i][j] = '.';
            }
        }
    }

    /**
     * Print the board at current state.
     */
    public void print()
    {
        for (int i = 0; i < iBoardHeight; i++)
        {
            for (int j = 0; j < iBoardWidth; j++)
            {
                System.out.print(iBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("–––––––––––");
        System.out.println("0 1 2 3 4 5");
        System.out.println();
    }

    public boolean isBoardFull()
    {
        boolean isFull = false;
        for (int i = 0; i < iBoardHeight; i++)
        {
            for (int j = 0; j < iBoardWidth; j++)
            {
                if (iBoard[i][j] == 'X' || iBoard[i][j] == '0')
                {
                    System.out.println("Board full");
                    isFull = true;
                }
            }
        }
        return isFull;
    }


    /**
     * @param move from the user where the pebble will be placed.
     */
    public void placePebble(final Move move)
    {
        iBoard[move.getRow()][move.getColumn()] = move.getPebble();
        print();
    }

    /**
     * @param move, the current move from the player.
     * @return true if the move connected four in a row.
     */
    public boolean checkIfMoveWon(final Move move)
    {
        boolean hasWon = false;

        if ((horizontalCheck(move) >= 4) || (verticalCheck(move) >= 4) || (diagonalCheck(move) >=4) || (diagonalBackCheck(move) >=4))
        {
            hasWon = true;
        }

        clearMoves();
        return hasWon;
    }

    private void clearMoves()
    {
        iPlayerHorizontalMoves.clear();
        iPlayerDiagonalMoves.clear();
        iPlayerDiagonalBackMoves.clear();
        iBotHorizontalMoves.clear();
        iBotDiagonalMoves.clear();
        iBotDiagonalBackMoves.clear();
    }

    /**
     * @param move to be checked horizontal on board.
     * @return true if four or more in a row found.
     */
    public int horizontalCheck(final Move move)
    {
        final int row = move.getRow();
        final int column = move.getColumn();
        final char pebble = move.getPebble();
        int counter = 0;
        int checkColumn = column;

        while (checkColumn < iBoardWidth && iBoard[row][checkColumn] == pebble)
        {
            if (pebble == 'X')
            {
                iPlayerHorizontalMoves.add(new Move(pebble, checkColumn, row));
            }
            else
            {
                iBotHorizontalMoves.add(new Move(pebble, checkColumn, row));
            }
            counter++;
            checkColumn++;
        }
        checkColumn = column - 1;

        while (checkColumn >= 0 && iBoard[row][checkColumn] == pebble)
        {
            if (pebble == 'X')
            {
                iPlayerHorizontalMoves.add(new Move(pebble, checkColumn, row));
            }
            else
            {
                iBotHorizontalMoves.add(new Move(pebble, checkColumn, row));
            }
            counter++;
            checkColumn--;
        }
        return counter;
    }

    /**
     * @param move to be checked vertical on board.
     * @return true if four or more in a row found.
     */
    public int verticalCheck(final Move move)
    {
        final int row = move.getRow();
        final int column = move.getColumn();
        final char pebble = move.getPebble();
        int counter = 0;
        int checkRow = row;

        while (checkRow < iBoardHeight && iBoard[checkRow][column] == pebble)
        {
            counter++;
            checkRow++;
        }
        return counter;
    }

    /**
     * @param move to be checked diagonal on board.
     * @return true if four or more in a row found.
     */
    public int diagonalCheck(final Move move)
    {
        final int row = move.getRow();
        final int column = move.getColumn();
        final char pebble = move.getPebble();
        int counter = 0;
        int checkColumn = column;
        int checkRow = row;

        while (checkColumn < iBoardWidth && checkRow < iBoardHeight && iBoard[checkRow][checkColumn] == pebble)
        {
            if (pebble == 'X')
            {
                iPlayerDiagonalMoves.add(new Move(pebble, checkColumn, checkRow));
            }
            else
            {
                iBotDiagonalMoves.add(new Move(pebble, checkColumn, checkRow));
            }
            counter++;
            checkColumn++;
            checkRow++;
        }
        checkColumn = row - 1;
        checkRow = column - 1;

        while (checkColumn >= 0 && checkRow >= 0 && iBoard[checkRow][checkColumn] == pebble)
        {
            if (pebble == 'X')
            {
                iPlayerDiagonalMoves.add(new Move(pebble, checkColumn, checkRow));
            }
            else
            {
                iBotDiagonalMoves.add(new Move(pebble, checkColumn, checkRow));
            }
            counter++;
            checkColumn--;
            checkRow--;
        }
        return counter;
    }

    /**
     * @param move to be checked diagonal backward on board.
     * @return true if four or more in a row found.
     */
    public int diagonalBackCheck(final Move move)
    {
        final int row = move.getRow();
        final int column = move.getColumn();
        final char pebble = move.getPebble();
        int counter = 0;
        int checkColumn = column;
        int checkRow = row;

        while (checkColumn < iBoardWidth && checkRow >= 0 && iBoard[checkRow][checkColumn] == pebble)
        {
            if (pebble == 'X')
            {
                iPlayerDiagonalBackMoves.add(new Move(pebble, checkRow, checkColumn));
            }
            else
            {
                iBotDiagonalBackMoves.add(new Move(pebble, checkRow, checkColumn));
            }
            counter++;
            checkColumn++;
            checkRow--;
        }
        checkColumn = column - 1;
        checkRow = row + 1;

        while (checkColumn >= 0 && checkRow < iBoardHeight && iBoard[checkRow][checkColumn] == pebble)
        {
            if (pebble == 'X')
            {
                iPlayerDiagonalBackMoves.add(new Move(pebble, checkRow, checkColumn));
            }
            else
            {
                iBotDiagonalBackMoves.add(new Move(pebble, checkRow, checkColumn));
            }
            counter++;
            checkColumn--;
            checkRow++;
        }
        return counter;
    }

    public char[][] getBoardLayout()
    {
        return iBoard;
    }

    public int getBoardHeight()
    {
        return iBoardHeight;
    }

    public int getBoardWidth()
    {
        return iBoardWidth;
    }

    public int getBottomRow()
    {
        return iBoardWidth - 1;
    }

    public LinkedList<Move> getPlayerHorizontalMoves()
    {
        return iPlayerHorizontalMoves;
    }

    public LinkedList<Move> getPlayerDiagonalMoves()
    {
        return iPlayerDiagonalMoves;
    }

    public LinkedList<Move> getPlayerDiagonalBackMoves()
    {
        return iPlayerDiagonalBackMoves;
    }

    public LinkedList<Move> getBotHorizontalMoves()
    {
        return iBotHorizontalMoves;
    }

    public LinkedList<Move> getBotDiagonalMoves()
    {
        return iBotDiagonalMoves;
    }

    public LinkedList<Move> getBotDiagonalBackMoves()
    {
        return iBotDiagonalBackMoves;
    }
}


