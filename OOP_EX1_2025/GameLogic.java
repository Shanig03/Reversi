import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class GameLogic implements PlayableLogic{

    //לתקן bomb
    //לתקן אנדו
    //להוסיף התייחסות לפצצה בcountflip
    //לבדוק את הבעיה ברנדום
    //לבדוק לגבי הדפסות

    private Player _firstPlayer;
    private Player _secondPlayer;
    private Disc[][] _Board;
    private Player _currentPlayer;
    private int _numberOfTurns;
    private List<Disc> _numOfFlips;
    private List<Position> _posOfFlipDiscs;//??
    private List<Disc> _numOfBombFlip;//??
    private Stack<List<Disc>> movesStack;
    private List<Position> listOfDisc;

    public GameLogic(){
        this._Board = new Disc[8][8];
        _numOfFlips = new ArrayList<>();
        _posOfFlipDiscs = new ArrayList<>();
        _numOfBombFlip = new ArrayList<>();//??
        movesStack = new Stack<>();
        listOfDisc = new ArrayList<>();
        _numberOfTurns = 0;
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        int countF = countFlips(a);
        if (_Board[a.row()][a.col()] == null && countF > 0){
            _Board[a.row()][a.col()] = disc;
            listOfDisc.add(a);
            addFlipsToList(a);
            flipDiscs();
            _numOfFlips.clear();
            _posOfFlipDiscs.clear();//??
            _numOfBombFlip.clear();//??
            _numberOfTurns++;
            return true;
        }
        return false;
    }

    private void flipDiscs(){
        movesStack.push(new ArrayList<>(_numOfFlips));
        for (int i = 0; i < _numOfFlips.size(); i++) {
            if (isFirstPlayerTurn()){
                _numOfFlips.get(i).setOwner(_firstPlayer);
            }else {
                _numOfFlips.get(i).setOwner(_secondPlayer);
            }
        }
        for (int i = 0; i < _numOfFlips.size(); i++) {//לבדוק
            if (_numOfFlips.get(i).getType().equals("💣")){
                bomb(_posOfFlipDiscs.get(i));
            }
        }
        for (int i = 0; i < _numOfBombFlip.size(); i++) {//לבדוק
            if (isFirstPlayerTurn()){
                _numOfBombFlip.get(i).setOwner(_firstPlayer);
            }else {
                _numOfBombFlip.get(i).setOwner(_secondPlayer);
            }
        }
    }
    private boolean containsBomb(List<Disc> discs){//מיותר?
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i).getType().equals("💣")){
                return true;
            }
        }
        return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        return _Board[position.row()][position.col()];
    }

    @Override
    public int getBoardSize() {
        return _Board.length;
    }

    @Override
    public List<Position> ValidMoves() {
        ArrayList<Position> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i,j);
                int flips = countFlips(pos);
                if (_Board[i][j] == null && flips > 0){
                    moves.add(pos);
                }
            }
        }
        return moves;
    }

    @Override
    public int countFlips(Position a) {
        int counter = 0;
        int y = a.row();
        int x =  a.col();
        for (int i = 0 ; i < 8; i++) {
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
            int temp = 0;
            while ((0 <= x && x <= 7) && (0 <= y && y <= 7) && (_Board[y][x] != null) && (_Board[y][x].getOwner().isPlayerOne() != _currentPlayer.isPlayerOne())){
                if (!_Board[y][x].getType().equals("⭕")){
                    temp++;
                }
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
            }
            if ((0 <= x && x <= 7) && (0 <= y && y <= 7) && (_Board[y][x] != null) && (_Board[y][x].getOwner().isPlayerOne() == _currentPlayer.isPlayerOne())){
                counter += temp;
            }
            temp = 0;
            y = a.row();
            x =  a.col();
        }
        return counter;
    }

    public void addFlipsToList(Position a) {
        int y = a.row();
        int x =  a.col();
        for (int i = 0 ; i < 8; i++) {
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
            List<Disc> tempNumOfFlips = new ArrayList<>();
            List<Position> tempPosOfFlips = new ArrayList<>();
            while ((0 <= x && x <= 7) && (0 <= y && y <= 7) && (_Board[y][x] != null) && (_Board[y][x].getOwner().isPlayerOne() != _currentPlayer.isPlayerOne())){
                if (!_Board[y][x].getType().equals("⭕")){
                    tempNumOfFlips.add(_Board[y][x]);
                    tempPosOfFlips.add(new Position(y,x));//לבדוק
                }
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
            }
            if ((0 <= x && x <= 7) && (0 <= y && y <= 7) && (_Board[y][x] != null) && (_Board[y][x].getOwner().isPlayerOne() == _currentPlayer.isPlayerOne())){
                for (int j = 0; j < tempNumOfFlips.size(); j++) {
                    _numOfFlips.add(tempNumOfFlips.get(j));
                    _posOfFlipDiscs.add(tempPosOfFlips.get(j));//לבדוק
                }
            }
            tempNumOfFlips.clear();
            tempPosOfFlips.clear();//לבדוק
            y = a.row();
            x =  a.col();
        }
    }

    @Override
    public Player getFirstPlayer() {
        return _firstPlayer;
    }

    @Override
    public Player getSecondPlayer() {
        return _secondPlayer;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        _firstPlayer = player1;
        _secondPlayer = player2;
        _currentPlayer = _firstPlayer;
        _numberOfTurns = 0;

        _Board[3][4] = new SimpleDisc(_secondPlayer);
        _Board[4][3] = new SimpleDisc(_secondPlayer);
        _Board[3][3] = new SimpleDisc(_firstPlayer);
        _Board[4][4] = new SimpleDisc(_firstPlayer);
    }

    @Override
    public boolean isFirstPlayerTurn() {
        if (_numberOfTurns % 2 == 0){
            _currentPlayer = _firstPlayer;
            return true;
        }
        _currentPlayer = _secondPlayer;
        return false;
    }

    @Override
    public boolean isGameFinished() {
        List<Position> moves = ValidMoves();
        if (moves.isEmpty()){
            int numOfDiscsPlayer1 = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (_Board[i][j] != null){
                        if (_Board[i][j].getOwner().isPlayerOne()){
                            numOfDiscsPlayer1++;
                        }
                    }
                }
            }
            if (numOfDiscsPlayer1 > 32){
                _firstPlayer.wins++;
            } else if (numOfDiscsPlayer1 < 32) {
                _secondPlayer.wins++;
            }
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                _Board[i][j] = null;
            }
        }
        _Board[3][4] = new SimpleDisc(_secondPlayer);
        _Board[4][3] = new SimpleDisc(_secondPlayer);
        _Board[3][3] = new SimpleDisc(_firstPlayer);
        _Board[4][4] = new SimpleDisc(_firstPlayer);
        _currentPlayer = _firstPlayer;
        _numberOfTurns = 0;
    }

    @Override
    public void undoLastMove() {
        List<Disc> lastMove = movesStack.pop();
        if (!lastMove.isEmpty()) {//הוספתי בדיקה אם הרשימה ריקה
            for (int i = 0; i < lastMove.size(); i++) {
                if (isFirstPlayerTurn()) {
                    lastMove.get(i).setOwner(_firstPlayer);
                } else {
                    lastMove.get(i).setOwner(_secondPlayer);
                }
            }
        }
        Position lastDisc = listOfDisc.getLast();
        _Board[lastDisc.row()][lastDisc.col()] = null;
        listOfDisc.removeLast();
        _numberOfTurns--;
    }

    private int bomb(Position p){
        int y = p.row();
        int x = p.col();
        if (_Board[p.row()][p.col()] != null){
            for (int i = 0 ; i < 8; i++) {
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
                if ((0 <= newX && newX <= 7) && (0 <= newY && newY <= 7) && _Board[newY][newX] != null && (_Board[newY][newX].getOwner().isPlayerOne()) != (_currentPlayer.isPlayerOne())){
                    boolean containsDisc = false;
                    if (!_numOfBombFlip.isEmpty()) {
                        for (int j = 0; j < _numOfBombFlip.size(); j++) {
                            if (!(_numOfBombFlip.get(j) == _Board[newY][newX])) {
                                containsDisc = true;
                                break;//לבדוק
                            }
                        }
                    }
                    if (!containsDisc){
                        _numOfBombFlip.add(_Board[newY][newX]);
                        if (_Board[newY][newX].getType().equals("💣")){
                            return bomb(new Position(newY,newX));
                        }
                    }
                }
            }
        }
        return 0;
    }
}
