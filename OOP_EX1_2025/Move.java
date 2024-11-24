public class Move {
    private Position _position;
    private Disc _disc;

    public Move(Position position, Disc disc){
        this._position = position;
        this._disc = disc;
    }

    public Position position(){
        return _position;
    }
    public Disc disc(){
        return _disc;
    }

}
