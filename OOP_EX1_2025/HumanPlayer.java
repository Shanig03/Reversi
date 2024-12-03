public class HumanPlayer extends Player{
    private boolean _isPlayerOne;

    /**
     * The constructor of this class.
     * @param isPlayer1 the player we want to put in the object.
     */
    public HumanPlayer(boolean isPlayer1){
        super(isPlayer1);
    }

    /**
     * return true if the player is human.
     * @return true if the player is human.
     */
    @Override
    boolean isHuman() {
        return true;
    }
}
