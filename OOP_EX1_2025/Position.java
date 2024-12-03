public class Position {
    private int _row;
    private int _col;

    /**
     * The constructor of this class.
     * @param row row in the game board.
     * @param col col in the game board.
     */
    public Position(int row, int col){
        this._row = row;
        this._col = col;
    }

    /**
     * Returns the row of this position.
     * @return the row of this position.
     */
    public int row(){
        return _row;
    }

    /**
     * Returns the col of this position.
     * @return the col of this position.
     */
    public int col(){
        return _col;
    }
}
