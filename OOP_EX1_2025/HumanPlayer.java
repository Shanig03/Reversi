public class HumanPlayer extends Player{
    private boolean _isPlayerOne;
    public HumanPlayer(boolean isPlayer1){
        super(isPlayer1);
    }
    @Override
    boolean isHuman() {
        return true;
    }
}
