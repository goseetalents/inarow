/**
 * Board class to handle board set up and logic.
 */
public class Board
{
    private final char[][] iBoard;
    private final int iBoardHeight;
    private final int iBoardWidth;

    public Board()
    {
        iBoard = new char[6][6];
        iBoardHeight = 6;
        iBoardWidth = 6;
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
        return iBoardWidth-1;
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

        if (horizontalCheck(move) || verticalCheck(move) ||  diagonalCheck(move) || diagonalBackCheck(move))
        {
            hasWon = true;
        }

        return hasWon;
    }

    public boolean horizontalCheck(final Move move)
    {
        final int row = move.getRow();
        final int column = move.getColumn();
        final char pebble = move.getPebble();
        int counter = 0;
        int checkColumn = column;

        while (checkColumn < iBoardWidth && iBoard[row][checkColumn] == pebble)
        {
            counter++;
            checkColumn++;
        }
        checkColumn = column - 1;
        while (checkColumn >= 0 && iBoard[row][checkColumn] == pebble)
        {
            counter++;
            checkColumn--;
        }

        return counter == 4;
    }


    public boolean verticalCheck(final Move move)
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
        return counter == 4;
    }

    public boolean diagonalCheck(final Move move)
    {
        final int row = move.getRow();
        final int column = move.getColumn();
        final char pebble = move.getPebble();
        int counter = 0;
        int checkColumn = column;
        int checkRow = row;

        while (checkColumn < iBoardWidth && checkRow < iBoardHeight && iBoard[checkColumn][checkRow] == pebble)
        {
            counter++;
            checkColumn++;
            checkRow++;
        }
        checkColumn = row - 1;
        checkRow= column - 1;

        while (checkColumn >= 0 && checkRow >= 0 && iBoard[checkColumn][checkRow] == pebble)
        {
            counter++;
            checkColumn--;
            checkRow--;
        }
        return counter == 4;
    }

    public boolean diagonalBackCheck(final Move move)
    {
        final int row = move.getRow();
        final int column = move.getColumn();
        final char pebble = move.getPebble();
        int counter = 0;
        int checkColumn = column;
        int checkRow = row;

        while (checkColumn < iBoardWidth && checkRow >= 0 && iBoard[checkColumn][checkRow] == pebble)
        {
            counter++;
            checkColumn++;
            checkRow--;
        }
        checkColumn = column - 1;
        checkRow = row + 1;

        while (checkColumn >= 0 && checkRow < iBoardHeight && iBoard[checkColumn][checkRow] == pebble)
        {
            counter++;
            checkColumn--;
            checkRow++;
        }
        return counter == 4;
    }
}


