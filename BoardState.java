package rushhour;

import java.util.Arrays;

public class BoardState {

    //HOLDS THE CURRENT BOARD
    char[][] boardState = new char[6][6];

    //HOLDS THE PARENT BOARD
    BoardState parentBoardState;

    //HOLDS THE PARENT MOVE
    String parentMove;


    //CONSTRUCTOR FOR BOARDSTATE (FIRST ITERATION)
    public BoardState(char[][] input) {
        boardState = input;
        parentBoardState = null;
        parentMove = null;
    }

    //CONSTRUCTOR FOR BOARDSTATE (ALL SUBSEQUENT ITERATION)
    public BoardState(char[][] input, BoardState parentBoardState, String parentMove) {
        boardState = input;
        this.parentBoardState = parentBoardState;
        this.parentMove = parentMove;
    }

    public char getValue(int x, int y) {
        return boardState[x][y];
    }

    public char[][] getBoardState() {
        return boardState;
    }

    public BoardState getParentBoardState() {
        return parentBoardState;
    }

    public String getParentMove() {
        return parentMove;
    }

//    @Override
    public boolean isEqual(BoardState otherBoardState) {
        char[][] otherBoard = otherBoardState.getBoardState();

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(otherBoard[i][j] != boardState[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(boardState);
    }
}
