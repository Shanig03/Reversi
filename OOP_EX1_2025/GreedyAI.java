import java.util.List;
import java.util.Random;

public class GreedyAI extends AIPlayer{
    private boolean _isPlayerOne; // לבדוק
    public GreedyAI(boolean isPlayer1){
        super(isPlayer1);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Random rand = new Random();
        List<Position> possiblesMoves = gameStatus.ValidMoves();
        Position pos = possiblesMoves.get(0);
        int maxMove = gameStatus.countFlips(pos);
        for (int i = 0; i < possiblesMoves.size(); i++) {
            int temp = gameStatus.countFlips(possiblesMoves.get(i));
            if (temp > maxMove){
                pos = possiblesMoves.get(i);
            }
        }
        Disc disc;
        int myRand = rand.nextInt(3);
        if (gameStatus.isFirstPlayerTurn()){
            if (myRand == 0 && gameStatus.getFirstPlayer().getNumber_of_bombs() > 0){
                disc = new BombDisc(gameStatus.getFirstPlayer());
                gameStatus.getFirstPlayer().reduce_bomb();
            } else if (myRand == 1 && gameStatus.getFirstPlayer().getNumber_of_unflippedable() > 0) {
                disc = new UnflippableDisc(gameStatus.getFirstPlayer());
                gameStatus.getFirstPlayer().reduce_unflippedable();
            } else {
                disc = new SimpleDisc(gameStatus.getFirstPlayer());
            }
        } else {
            if (myRand == 0 && gameStatus.getSecondPlayer().getNumber_of_bombs() > 0){
                disc = new BombDisc(gameStatus.getSecondPlayer());
                gameStatus.getSecondPlayer().reduce_bomb();
            } else if (myRand == 1 && gameStatus.getSecondPlayer().getNumber_of_unflippedable() > 0) {
                disc = new UnflippableDisc(gameStatus.getSecondPlayer());
                gameStatus.getSecondPlayer().reduce_unflippedable();
            } else {
                disc = new SimpleDisc(gameStatus.getSecondPlayer());
            }
        }
        return new Move(pos, disc);
    }
}
