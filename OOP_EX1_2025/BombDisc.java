public class BombDisc implements Disc{
    private Player _player;

    /**
     * The constructor for this class.
     */
    public BombDisc(Player player){
        this._player = player;
    }

    /**
     * Returns the player that owns the disc.
     * @return Returns the player that owns the disc.
     */
    @Override
    public Player getOwner() {
        return _player;
    }

    /**
     * Set the player that owns the disc.
     * @param player the owner that we want to set for the disc.
     */
    @Override
    public void setOwner(Player player) {
        this._player = player;
    }

    /**
     * Returns the type of the disc.
     * @return the type of the disc "💣".
     */
    @Override
    public String getType() {
        return "💣";
    }
}
