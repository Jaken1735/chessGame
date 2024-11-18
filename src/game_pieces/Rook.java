package game_pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Rook extends GamePiece {
    public Rook(String name, Boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite){
            this.name ="w"+name;
        }
        else{this.name = "b"+name;
        }
    }
    @Override
    public Boolean valid_move(Integer from_x, Integer from_y, Integer to_x, Integer to_y, GamePiece[][] board, int check){
        if (from_x.equals(to_x)) { // Horizontal move
            if (from_y.equals(to_y)){return false;} // Same position
            int d_col, col;
            if (from_y < to_y){
                d_col = 1;
            }
            else {
                d_col = -1;
            }
            for (col = from_y + d_col; col != to_y; col += d_col){
                if (board[to_x][col]  != null){return false;}
            }
            return true;
        } else if (from_y.equals(to_y)) { // Vertical move
            int d_row, row;
            if (from_x < to_x) {
                d_row = 1;
            }
            else {
                d_row = -1;
            }
            for (row  = from_x + d_row; row != to_x; row += d_row){
                if (board[row][to_y] != null){return false;}
            }
            return true;
        }
        else{
            return false; // neither direction is movable
        }
    }
}

