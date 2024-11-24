public class Position {
    private int _row;
    private int _col;

    public Position(int row, int col){
        this._row = row;
        this._col = col;
    }
    public int row(){
        return _row;
    }
    public int col(){
        return _col;
    }
}
