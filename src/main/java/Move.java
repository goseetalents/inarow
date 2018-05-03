public class Move
{
    private final char iPebble;
    private final int iColumn;
    private final int iRow;

    public Move(final char pebble, final int column, final int row)
    {
        iPebble = pebble;
        iColumn = column;
        iRow = row;
    }

    public char getPebble()
    {
        return iPebble;
    }

    public int getColumn()
    {
        return iColumn;
    }

    public int getRow()
    {
        return iRow;
    }
}
