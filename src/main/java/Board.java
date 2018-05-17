import java.util.LinkedList;

/**
 * Board class to handle board set up and logic.
 */
public class Board
{
    private char[][] iBoard;
    private final int iBoardHeight;
    private final int iBoardWidth;

    private LinkedList<Move> iXHorizontalMoves;
    private LinkedList<Move> iXDiagonalMoves;
    private LinkedList<Move> iXDiagonalBackMoves;

    private LinkedList<Move> iOHorizontalMoves;
    private LinkedList<Move> iODiagonalMoves;
    private LinkedList<Move> iODiagonalBackMoves;

    public Board()
    {
        iBoard = new char[6][6];
        iBoardHeight = 6;
        iBoardWidth = 6;

        iXHorizontalMoves = new LinkedList<Move>();
        iXDiagonalMoves = new LinkedList<Move>();
        iXDiagonalBackMoves = new LinkedList<Move>();

        iOHorizontalMoves = new LinkedList<Move>();
        iODiagonalMoves = new LinkedList<Move>();
        iODiagonalBackMoves = new LinkedList<Move>();
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

        if ((horizontalCheck(move) >= 4) || (verticalCheck(move) >= 4)
                || (diagonalCheck(move) >=4) || (diagonalBackCheck(move) >=4))
        {
            hasWon = true;
        }

        clearMoves();
        return hasWon;
    }

    private void clearMoves()
    {
        iXHorizontalMoves.clear();
        iXDiagonalMoves.clear();
        iXDiagonalBackMoves.clear();
        iOHorizontalMoves.clear();
        iODiagonalMoves.clear();
        iODiagonalBackMoves.clear();
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
                iXHorizontalMoves.add(new Move(pebble, checkColumn, row));
            }
            else
            {
                iOHorizontalMoves.add(new Move(pebble, checkColumn, row));
            }
            counter++;
            checkColumn++;
        }
        checkColumn = column - 1;

        while (checkColumn >= 0 && iBoard[row][checkColumn] == pebble)
        {
            if (pebble == 'X')
            {
                iXHorizontalMoves.add(new Move(pebble, checkColumn, row));
            }
            else
            {
                iOHorizontalMoves.add(new Move(pebble, checkColumn, row));
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
                iXDiagonalMoves.add(new Move(pebble, checkColumn, checkRow));
            }
            else
            {
                iODiagonalMoves.add(new Move(pebble, checkColumn, checkRow));
            }
            counter++;
            checkColumn++;
            checkRow++;
        }
        checkColumn = column - 1;
        checkRow = row - 1;

        while (checkColumn >= 0 && checkRow >= 0 && iBoard[checkRow][checkColumn] == pebble)
        {
            if (pebble == 'X')
            {
                iXDiagonalMoves.add(new Move(pebble, checkColumn, checkRow));
            }
            else
            {
                iODiagonalMoves.add(new Move(pebble, checkColumn, checkRow));
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
                iXDiagonalBackMoves.add(new Move(pebble, checkRow, checkColumn));
            }
            else
            {
                iODiagonalBackMoves.add(new Move(pebble, checkRow, checkColumn));
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
                iXDiagonalBackMoves.add(new Move(pebble, checkRow, checkColumn));
            }
            else
            {
                iODiagonalBackMoves.add(new Move(pebble, checkRow, checkColumn));
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

    public int getBoardWidth()
    {
        return iBoardWidth;
    }

    public int getBottomRow()
    {
        return iBoardWidth - 1;
    }

    public LinkedList<Move> getXHorizontalMoves()
    {
        return iXHorizontalMoves;
    }

    public LinkedList<Move> getXDiagonalMoves()
    {
        return iXDiagonalMoves;
    }

    public LinkedList<Move> getXDiagonalBackMoves()
    {
        return iXDiagonalBackMoves;
    }

    public LinkedList<Move> getOHorizontalMoves()
    {
        return iOHorizontalMoves;
    }

    public LinkedList<Move> getODiagonalMoves()
    {
        return iODiagonalMoves;
    }

    public LinkedList<Move> getODiagonalBackMoves()
    {
        return iODiagonalBackMoves;
    }

    public char getPebble(final int column, final int row)
    {
        return iBoard[row][column];
    }

    public void setBoard(final char[][] board)
    {
        iBoard = board;
    }
}


