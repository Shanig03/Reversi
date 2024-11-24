public class SimpleDisc implements Disc{
    private Player _player;


    public SimpleDisc(Player player){
        this._player = player;
    }
    @Override
    public Player getOwner() {
        return _player;
    }

    @Override
    public void setOwner(Player player) {
        this._player = player;
    }

    @Override
    public String getType() {
        return "⬤";
    }
}
