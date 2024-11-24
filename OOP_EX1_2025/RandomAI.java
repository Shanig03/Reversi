import java.util.List;
import java.util.Random;

public class RandomAI extends AIPlayer{
    private boolean _isPlayerOne;
    public RandomAI(boolean isPlayer1){
        super(isPlayer1);
    }
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Random rand = new Random();
        List<Position> possiblesMoves = gameStatus.ValidMoves();
        int randomPosition = rand.nextInt(possiblesMoves.size());
        Position pos = possiblesMoves.get(randomPosition);

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
