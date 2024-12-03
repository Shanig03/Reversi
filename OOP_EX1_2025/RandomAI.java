import java.util.List;
import java.util.Random;

public class RandomAI extends AIPlayer{
    private boolean _isPlayerOne;

    /**
     * The constructor of this class, uses the constructor of the super class.
     * @param isPlayer1 the player we want to put in the object.
     */
    public RandomAI(boolean isPlayer1){
        super(isPlayer1);
    }

    /**
     * This function make a random move by choosing a random position and a random disc, using the Random class.
     * @param gameStatus an object of the PlayableLogic class, which the GameLogic class extends from.
     * @return a random Move.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Random rand = new Random();

        List<Position> possiblesMoves = gameStatus.ValidMoves();

        if (possiblesMoves.isEmpty()) {
            return new Move(null,null);
        }

        int randomPosition = rand.nextInt(possiblesMoves.size());
        Position pos = possiblesMoves.get(randomPosition);

        //chooses the type of disc - bomb, unflippable and simple randomly.
        Disc disc;
        int randomDisc = rand.nextInt(3);
        if (gameStatus.isFirstPlayerTurn()){
            if (randomDisc == 0 && gameStatus.getFirstPlayer().getNumber_of_bombs() > 0){
                disc = new BombDisc(gameStatus.getFirstPlayer());
                gameStatus.getFirstPlayer().reduce_bomb();
            } else if (randomDisc == 1 && gameStatus.getFirstPlayer().getNumber_of_unflippedable() > 0) {
                disc = new UnflippableDisc(gameStatus.getFirstPlayer());
                gameStatus.getFirstPlayer().reduce_unflippedable();
            } else {
                disc = new SimpleDisc(gameStatus.getFirstPlayer());
            }
        } else {
            if (randomDisc == 0 && gameStatus.getSecondPlayer().getNumber_of_bombs() > 0){
                disc = new BombDisc(gameStatus.getSecondPlayer());
                gameStatus.getSecondPlayer().reduce_bomb();
            } else if (randomDisc == 1 && gameStatus.getSecondPlayer().getNumber_of_unflippedable() > 0) {
                disc = new UnflippableDisc(gameStatus.getSecondPlayer());
                gameStatus.getSecondPlayer().reduce_unflippedable();
            } else {
                disc = new SimpleDisc(gameStatus.getSecondPlayer());
            }
        }
        return new Move(pos,disc);
    }
}
