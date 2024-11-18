package game_pieces;

import java.util.ArrayList;

public abstract class GamePiece {
    public String name;
    ArrayList<Integer> move_set;

    Boolean first = true;
    public Boolean isWhite;
    public GamePiece() {};

    public static GamePiece create(String name, Boolean isWhite) {
        if (name == "King") {
            return new King(name, isWhite);
        }
        if (name == "Queen") {
            return new Queen(name, isWhite);
        }
        if (name == "Rook") {
            return new Rook(name, isWhite);
        }
        if (name == "Knight") {
            return new Knight(name, isWhite);
        }
        if (name == "Bishop") {
            return new Bishop(name, isWhite);
        }
        if (name == "Pawn") {
            return new Pawn(name, isWhite);
        }

        return null;
    }
    public Boolean valid_move(Integer sx, Integer sy, Integer x, Integer y, GamePiece[][] board, int check) {
        return false;
    }
}
