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

    public void print()
    {
        for (int i = 0; i < iBoardHeight; i++)
        {
            for (int j = 0; j < iBoardWidth; j++)
            {
                System.out.print(iBoard[i][j]);
            }
            System.out.println();
        }
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

    public void placePebble(final Move move)
    {
        iBoard[move.getRow()][move.getColumn()] = move.getPebble();
        print();
    }

    public boolean checkPebbleHorizontal(final char pebble)
    {
        boolean running = true;
        int counter = 0;
        while (running)
        {
            for (int i = 0; i < iBoardWidth; i++)
            {
                for (int j = 0; j < iBoardHeight; j++)
                {
                    if (iBoard[i][j] == pebble)
                    {
                        counter++;
                    }
                    else
                    {
                        counter = 0;
                    }
                    if (counter >= 4)
                    {
                        running = false;
                    }
                }
            }
            break;
        }
        return running;
    }

    public boolean checkPebbleVertical(final char pebble)
    {
        boolean running = true;
        int counter = 0;
        while (running)
        {
            for (int i = 0; i < iBoardHeight; i++)
            {
                for (int j = 0; j < iBoardWidth; j++)
                {
                    if (iBoard[j][i] == pebble)
                    {
                        counter++;
                    }
                    else
                    {
                        counter = 0;
                    }
                    if (counter >= 4)
                    {
                        running = false;
                    }
                }
            }
            break;
        }
        return running;
    }

    public boolean checkPebbleDiagonal(final char pebble)
    {
        boolean checkDiagonalForward = true;
        boolean running = true;
        int checkColumn = 1;
        int checkRow = 1;
        int counter = 0;

        while (running)
        {
            for (int i = 0; i < iBoardWidth; i++)
            {
                for (int j = 0; j < iBoardHeight; j++)
                {
                    if (iBoard[i][j] == pebble)
                    {
                        counter++;
                        while (checkDiagonalForward)
                        {
                            if (checkColumn + i <= iBoardWidth - 1 && checkRow + j <= iBoardHeight - 1)
                            {
                                if (iBoard[i + checkColumn][j + checkRow] == pebble)
                                {
                                    counter++;
                                }
                            }

                            checkColumn++;
                            checkRow++;

                            if (checkColumn == iBoardWidth - 1 || checkRow == iBoardHeight - 1)
                            {
                                checkDiagonalForward = false;
                                break;
                            }
                            if (counter >= 4)
                            {
                                checkDiagonalForward = false;
                                running = false;
                                break;
                            }
                        }
                    }
                    if (counter >= 4)
                    {
                        running = false;
                        break;
                    }

                    counter = 0;
                    checkColumn = 1;
                    checkRow = 1;
                }
            }
            break;
        }
        return running;
    }

    public boolean checkPebbleDiagonalBackward(final char pebble)
    {
        boolean checkDiagonalBackward = true;
        boolean running = true;
        int checkColumn = 1;
        int checkRow = 1;
        int counter = 0;

        while (running)
        {
            for (int i = 0; i < iBoardWidth; i++)
            {
                for (int j = 0; j < iBoardHeight; j++)
                {
                    if (iBoard[i][j] == pebble)
                    {
                        counter++;

                        while (checkDiagonalBackward)
                        {
                            if (i - checkColumn >= 0 && j - checkRow >= 0)
                            {
                                if (iBoard[i - checkColumn][j - checkRow] == pebble)
                                {
                                    counter++;
                                }
                            }

                            checkColumn++;
                            checkRow++;

                            if (checkColumn == 0 || checkRow == iBoardHeight - 1)
                            {
                                checkDiagonalBackward = false;
                                break;
                            }
                            if (counter >= 4)
                            {
                                checkDiagonalBackward = false;
                                running = false;
                                break;
                            }
                        }
                    }
                    if (counter >= 4)
                    {
                        running = false;
                        break;
                    }
                    counter = 0;
                    checkColumn = 1;
                    checkRow = 1;
                }
            }
            break;
        }
        return running;
    }
}


