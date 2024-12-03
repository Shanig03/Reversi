import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class GameLogic implements PlayableLogic{

    private Player _firstPlayer;
    private Player _secondPlayer;
    private final Disc[][] _Board;
    private Player _currentPlayer; // hold the current player.
    private int _numberOfTurns; // turns counter.
    private final List<Position> _posOfFlipDiscs; // list of positions that need to be flipped in the current turn.
    private final Stack<List<Position>> movesStack; // stack of all the previous list of positions that need to be flipped.
    private final List<Position> listOfDisc; // list of all the discs that were located during the game.

    /**
     * The constructor of this class.
     */
    public GameLogic(){
        this._Board = new Disc[8][8];
        _posOfFlipDiscs = new ArrayList<>();
        movesStack = new Stack<>();
        listOfDisc = new ArrayList<>();
        _numberOfTurns = 0;
    }

    /**
     * This function receives a location and a disk and tries to place the disk on the board. If successful returns true.
     * @param a The position for locating a new disc on the board.
     * @param disc The disc that should be located.
     * @return True if the placement was successful, false otherwise.
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (a == null || disc == null){
            _numberOfTurns++;
            if (ValidMoves().isEmpty()){
                return false;
            }
            return false;
        }

        int countF = countFlips(a);
        if (_Board[a.row()][a.col()] == null && countF > 0){ //check is this is a valid move.
            _Board[a.row()][a.col()] = disc;// locate the disc.
            listOfDisc.add(a); // add 'a' to the list of discs we put on the board.
            if (_currentPlayer == _firstPlayer){
                System.out.println("Player 1 placed a " + disc.getType() + " in (" + a.row() + ", " + a.col() + ")");
            }else {
                System.out.println("Player 2 placed a " + disc.getType() + " in (" + a.row() + ", " + a.col() + ")");
            }
            flipDiscs(a);
            System.out.println("");
            _numberOfTurns++; // add one to the turns count.
            return true;
        }
        return false;
    }

    /**
     * This function flip the discs that are in the list of discs intended for flipping.
     */
    private void flipDiscs(Position p){
        addFlipsToList(p);
        movesStack.push(new ArrayList<>(_posOfFlipDiscs)); //push to the stack the list of all the position we need to flip in this turn.
        for (int i = 0; i < _posOfFlipDiscs.size(); i++) {
            if (isFirstPlayerTurn()){
                _Board[_posOfFlipDiscs.get(i).row()][_posOfFlipDiscs.get(i).col()].setOwner(_firstPlayer);
                System.out.println("Player 1 flipped the " + _Board[_posOfFlipDiscs.get(i).row()][_posOfFlipDiscs.get(i).col()].getType() + " in (" + _posOfFlipDiscs.get(i).row() + ", " + _posOfFlipDiscs.get(i).col() + ")");
            }else {
                _Board[_posOfFlipDiscs.get(i).row()][_posOfFlipDiscs.get(i).col()].setOwner(_secondPlayer);
                System.out.println("Player 2 flipped the " + _Board[_posOfFlipDiscs.get(i).row()][_posOfFlipDiscs.get(i).col()].getType() + " in (" + _posOfFlipDiscs.get(i).row() + ", " + _posOfFlipDiscs.get(i).col() + ")");
            }
        }
    }

    /**
     * The function returns the disk that is on the board in the position it receives.
     * @param position The position for which to retrieve the disc.
     * @return The disc that is in this location on the board.
     */
    @Override
    public Disc getDiscAtPosition(Position position) {
        return _Board[position.row()][position.col()];
    }

    /**
     * the function returns the length of the board.
     * @return board length.
     */
    @Override
    public int getBoardSize() {
        return _Board.length;
    }

    /**
     * The function checks the entire board for valid moves for the current player and returns a list of them.
     * @return A list of valid positions to the current player.
     */
    @Override
    public List<Position> ValidMoves() {
        ArrayList<Position> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i,j); //make a position for all the locations in the board.
                int flips = countFlips(pos);
                if (_Board[i][j] == null && flips > 0){ //check if the location is empty, and if there is a valid move for this location.
                    moves.add(pos); //add the position to the lists of move.
                }
            }
        }
        return moves;
    }

    /**
     * The function checks how many disks will be flipped if we place a disk in a certain position.
     * @param a A location for which the function checks how many disks will be flipped if we place a disk there.
     * @return The number of discs that will be flip.
     */
    @Override
    public int countFlips(Position a) {
        addFlipsToList(a);
        return _posOfFlipDiscs.size(); //returns the size of the list that contains all the discs that need to be flipped.
    }

    /**
     * The function put in a list all the disks that will be turned over if we place a disk in the given position.
     * @param a location for which the function add all the disks that will be flipped if we place a disk there to a list.
     */
    public void addFlipsToList(Position a) {
        _posOfFlipDiscs.clear();
        int y = a.row();
        int x =  a.col();
        for (int i = 0 ; i < 8; i++) { //choose a direction to go through.
            if (i <= 2){
                y--;
            }
            if (4 <= i && i <= 6){
                y++;
            }
            if (2 <= i && i <= 4){
                x++;
            }
            if (6 <= i || i == 0){
                x--;
            }
            List<Position> tempPosOfFlips = new ArrayList<>();
            while ((0 <= x && x <= 7) && (0 <= y && y <= 7) && (_Board[y][x] != null) && (_Board[y][x].getOwner().isPlayerOne() != _currentPlayer.isPlayerOne())){
                if (!_Board[y][x].getType().equals("⭕")){ // add to the temp list only if the disc is not unflappable disc.
                    tempPosOfFlips.add(new Position(y,x));
                }
                if (i <= 2){ // proceed to look in the same direction we choose before.
                    y--;
                }
                if (4 <= i && i <= 6){
                    y++;
                }
                if (2 <= i && i <= 4){
                    x++;
                }
                if (6 <= i || i == 0){
                    x--;
                }
            }
            if ((0 <= x && x <= 7) && (0 <= y && y <= 7) && (_Board[y][x] != null) && (_Board[y][x].getOwner().isPlayerOne() == _currentPlayer.isPlayerOne())){
                for (int j = 0; j < tempPosOfFlips.size(); j++) {
                    if (!isIncluded(tempPosOfFlips.get(j),_posOfFlipDiscs)){ // add the positions from the temp list to the list, only if it's not already in the list.
                        _posOfFlipDiscs.add(tempPosOfFlips.get(j));
                    }
                }
                for (int j = 0; j < _posOfFlipDiscs.size(); j++) { // checks all the positions to see if there are bomb discs.
                    if (_Board[_posOfFlipDiscs.get(j).row()][_posOfFlipDiscs.get(j).col()].getType().equals("💣")){
                        bomb(_posOfFlipDiscs.get(j)); // all the bombs go to the bomb function.
                    }
                }
            }
            tempPosOfFlips.clear();
            y = a.row();
            x =  a.col();
        }
    }

    /**
     * Returns the first player.
     * @return First player.
     */
    @Override
    public Player getFirstPlayer() {
        return _firstPlayer;
    }
    /**
     * Returns the second player.
     * @return Second player.
     */
    @Override
    public Player getSecondPlayer() {
        return _secondPlayer;
    }

    /**
     * The function initializes the players, turns and the board to be as needed at the start of the game.
     * @param player1 The first player.
     * @param player2 The second player.
     */
    @Override
    public void setPlayers(Player player1, Player player2) {
        _firstPlayer = player1;
        _secondPlayer = player2;
        _currentPlayer = _firstPlayer; // initialize the current player to be the first player.
        _numberOfTurns = 0;

        _Board[3][4] = new SimpleDisc(_secondPlayer); // initialize the start board with the discs.
        _Board[4][3] = new SimpleDisc(_secondPlayer);
        _Board[3][3] = new SimpleDisc(_firstPlayer);
        _Board[4][4] = new SimpleDisc(_firstPlayer);
    }

    /**
     * This function checks according to _numberOfTurns who is the current player - when the number of turns is even,
     * it is the turn of the first player, and when it is odd, it is the turn of the second player.
     * @return True if it is the first player turn, and false if it is the second player turn.
     */
    @Override
    public boolean isFirstPlayerTurn() {
        if (_numberOfTurns % 2 == 0){ // if the number of turns is even, it's the first player turn.
            _currentPlayer = _firstPlayer; // set the current player to be the first player.
            return true;
        }
        _currentPlayer = _secondPlayer; //set the current player to be the second player.
        return false;
    }

    /**
     * This function checks whether the game is over or not, i.e. if there are still available moves and space on the board.
     * @return True if the is finished, false otherwise.
     */
    @Override
    public boolean isGameFinished() {
        List<Position> moves = ValidMoves(); // checks for valid moves.
        if (moves.isEmpty()){
            int numOfDiscsPlayer1 = 0;
            int numOfDiscsPlayer2 = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (_Board[i][j] != null){
                        if (_Board[i][j].getOwner().isPlayerOne()){ // count player one's and player two's discs on the board.
                            numOfDiscsPlayer1++;
                        }else {
                            numOfDiscsPlayer2++;
                        }
                    }
                }
            }
            if (numOfDiscsPlayer1 > numOfDiscsPlayer2){
                _firstPlayer.wins++;
                System.out.println("Player 1 wins with " + numOfDiscsPlayer1 +
                        " discs! Player 2 had " + numOfDiscsPlayer2 + " discs.");
            } else if (numOfDiscsPlayer1 < numOfDiscsPlayer2) {
                _secondPlayer.wins++;
                System.out.println("Player 2 wins with " + numOfDiscsPlayer2 +
                        " discs! Player 1 had " + numOfDiscsPlayer1 + " discs.");
            }

            return true;
        }
        return false;
    }

    /**
     * The function resets the game back to its starting point.
     */
    @Override
    public void reset() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                _Board[i][j] = null; // initialize all the positions on the board to null.
            }
        }
        _Board[3][4] = new SimpleDisc(_secondPlayer); // initialize the start board with the discs.
        _Board[4][3] = new SimpleDisc(_secondPlayer);
        _Board[3][3] = new SimpleDisc(_firstPlayer);
        _Board[4][4] = new SimpleDisc(_firstPlayer);
        listOfDisc.clear();
        _currentPlayer = _firstPlayer; //initialize the current player to be the first player..
        _numberOfTurns = 0; // reset the turns counter.
    }

    /**
     * This function goes back one move, that is, deletes the last disc that was placed and flips back
     * all the discs that were flipped because of it.
     */
    @Override
    public void undoLastMove() {
        if (movesStack.isEmpty() || listOfDisc.isEmpty()) {
            System.out.println("\tNo previous move available to undo.\n");
            return;
        }else {
            Position lastDisc = listOfDisc.getLast();
            System.out.println("Undoing last move: \n\tUndo: removing " + _Board[lastDisc.row()][lastDisc.col()].getType() + " from (" + lastDisc.row() + ", " + lastDisc.col() + ")");
            _Board[lastDisc.row()][lastDisc.col()] = null; // removing the last disc.
            listOfDisc.removeLast();
            List<Position> lastMove = movesStack.pop();
            if (!lastMove.isEmpty()) {
                for (int i = 0; i < lastMove.size(); i++) { // flipping back all the discs that were flipped in the last turn.
                    if (isFirstPlayerTurn()) {
                        _Board[lastMove.get(i).row()][lastMove.get(i).col()].setOwner(_firstPlayer);
                        System.out.println("\tUndo: flipping back " +
                                _Board[lastMove.get(i).row()][lastMove.get(i).col()].getType() +
                                " in (" + lastMove.get(i).row() + ", " + lastMove.get(i).col() + ")");
                    } else {
                        _Board[lastMove.get(i).row()][lastMove.get(i).col()].setOwner(_secondPlayer);
                        System.out.println("\tUndo: flipping back " +
                                _Board[lastMove.get(i).row()][lastMove.get(i).col()].getType() +
                                " in (" + lastMove.get(i).row() + ", " + lastMove.get(i).col() + ")");
                    }
                }
            }
            System.out.println("");
            _numberOfTurns--; // going back one turn.
        }
    }

    /**
     * The function receives the Position of a bomb and puts in a list all the disks that will be flipped by it.
     * @param p The position of the bomb.
     */
    private void bomb(Position p){
        int y = p.row();
        int x = p.col();
        if (_Board[p.row()][p.col()] != null){
            for (int i = 0 ; i < 8; i++) { // choosing a direction to go through.
                int newY = y;
                int newX = x;
                if (i <= 2) {
                    newY--;
                }
                if (4 <= i && i <= 6) {
                    newY++;
                }
                if (2 <= i && i <= 4) {
                    newX++;
                }
                if (6 <= i || i == 0) {
                    newX--;
                }
                if ((0 <= newX && newX <= 7) && (0 <= newY && newY <= 7) && _Board[newY][newX] != null &&
                        (_Board[newY][newX].getOwner().isPlayerOne()) != (_currentPlayer.isPlayerOne())){
                    Position pos = new Position(newY, newX);
                    if (!isIncluded(pos, _posOfFlipDiscs)){ // adding the position to the list only if It's not already there, and if not an unflippable disc.
                        if (!_Board[newY][newX].getType().equals("⭕")){
                            _posOfFlipDiscs.add(pos);
                        }
                    }
                }
            }
        }
    }

    /**
     * The function receives a Position and a list of Positions and checks whether the Position is already in the list of Positions.
     * @param p The position that need to be checked.
     * @param positions The list of positions.
     * @return True if the position is already in the list, and false if not.
     */
    private boolean isIncluded(Position p, List<Position> positions){
        for (int i = 0; i < positions.size(); i++) {
            if (p.row() == positions.get(i).row() && p.col() == positions.get(i).col()){ // checks for repetitions.
                return true;
            }
        }
        return false;
    }
}
