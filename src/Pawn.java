import javafx.scene.image.Image;

public class Pawn extends ChessPiece {

    private final boolean[] movablePositionsWithoutKill = {false, true, false}; // topleft, top, topright positions respectively

    public Pawn(Image image){
        super(image);
    }

    /*
     * Method called when a chess piece is selected
     *
     */
    public void pieceSelected(int pieceSelectedPosition, int xPosition, int yPosition){
        this.pieceSelectedPosition = pieceSelectedPosition;
        ChessBoardManager chessBoardManager = ChessBoardManager.getInstance();
        ChessBoard chessBoard = chessBoardManager.getChessBoard();

        System.out.println("xPosition: " + xPosition + " yPosition: " + yPosition);

        int topPosition = getPositionFromCoordinates(xPosition,(color == white) ? yPosition - 1 : yPosition + 1);

        int topRightPosition = getPositionFromCoordinates((color == white) ? xPosition + 1 : xPosition - 1
                , (color == white) ? yPosition - 1 : yPosition + 1);

        int topLeftPosition = getPositionFromCoordinates((color == white) ? xPosition - 1: xPosition + 1
                , (color == white) ? yPosition - 1 : yPosition + 1);

        if((yPosition == 6 && color == white) || (yPosition == 1 && color == black)){ // special move that pawn can make at starting position
            movablePosition(getPositionFromCoordinates(xPosition, (color == white) ? yPosition - 2 : yPosition + 2), chessBoard, true);
        }

        int[] movablePositionsToCheck = {topLeftPosition, topPosition, topRightPosition};
        findMovablePositions(movablePositionsToCheck, chessBoard);
        colorMovablePositions(chessBoard);
    }

    @Override
    protected void findMovablePositions(int[] movablePositions, ChessBoard chessBoard){
        for (int i = 0; i < movablePositions.length; i++){
            movablePosition(movablePositions[i], chessBoard, movablePositionsWithoutKill[i]);
        }
    }

    /**
     * Method that checks if the chess piece can move to a square
     * @param position
     * @return
     */
    private void movablePosition(int position, ChessBoard chessBoard, boolean canMoveWithoutKill){
        if (position >= 0 && position < 64){
            ChessSquare chessSquare = (ChessSquare)chessBoard.getChildren().get(position);
            if(checkSquareForPiece(chessSquare, canMoveWithoutKill)) {
                movablePositions.add(position);
            }
        }
    }

    /**
     * Helper method to moveablePosition to check whether a chess piece can move to a square that contains another chess piece
     * @param chessSquare
     * @return
     */
    private boolean checkSquareForPiece(ChessSquare chessSquare, boolean canMoveWithoutKill){
        if(chessSquare.getChildren().size() == 2){
            ChessPiece chessPiece = (ChessPiece)chessSquare.getChildren().get(1);
            if(chessPiece.getColor() != color && !canMoveWithoutKill){
                return true;
            } else {
                return false;
            }
        } else if(canMoveWithoutKill){
            return true;
        } else {
            return false;
        }
    }
}
