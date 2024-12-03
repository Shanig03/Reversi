public class Move {
    private Position _position;
    private Disc _disc;

    /**
     * The constructor of this class.
     * @param position the position we want to put the disc in.
     * @param disc the disc we want to locate.
     */
    public Move(Position position, Disc disc){
        this._position = position;
        this._disc = disc;
    }

    /**
     * Returns the position of this move.
     * @return the position of this move.
     */
    public Position position(){
        return _position;
    }

    /**
     * Returns the disc of this move.
     * @return the disc of this move.
     */
    public Disc disc(){
        return _disc;
    }

}
