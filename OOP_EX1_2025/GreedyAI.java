import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GreedyAI extends AIPlayer{
    private final boolean _isPlayerOne;

    /**
     * The constructor for this class, uses the constructor of the super class.
     * @param isPlayer1 the player we want to put in the object.
     */
    public GreedyAI(boolean isPlayer1){
        super(isPlayer1);
        _isPlayerOne = isPlayer1;
    }

    /**
     * This function returns a greedy move, by using an integer comparator,
     * that choosing the move that will flip the most discs, and the most down and right one.
     * @param gameStatus an object of the PlayableLogic class, which the GameLogic class extends from.
     * @return a greedy Move.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> possiblesMoves = new ArrayList<>(gameStatus.ValidMoves());

        if (possiblesMoves.isEmpty()){
            return new Move(null, null);
        }

        //Define the comparator.
        Comparator<Position> positionComparator = (pos1, pos2) -> {
            int flipsCompare = Integer.compare(gameStatus.countFlips(pos1),gameStatus.countFlips(pos2));
            if (flipsCompare != 0){
                return flipsCompare;
            }
            int colCompare = Integer.compare(pos1.col(), pos2.col());
            if (colCompare != 0 ){
                return colCompare;
            }
            return Integer.compare(pos1.row(), pos2.row());
        };

        //Compare between every position in the list.
        Position maxMove = possiblesMoves.get(0);
        for (int i = 0; i < possiblesMoves.size(); i++) {
            if (positionComparator.compare(maxMove, possiblesMoves.get(i)) > 0){
                maxMove = possiblesMoves.get(i);
            }
        }

        Disc disc;
        if (_isPlayerOne){
            disc = new SimpleDisc(gameStatus.getFirstPlayer());
        }else {
            disc = new SimpleDisc(gameStatus.getSecondPlayer());
        }

        //Returns a greedy move.
        return new Move(maxMove, disc);
    }
}
