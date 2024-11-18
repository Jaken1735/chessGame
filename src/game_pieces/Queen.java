package game_pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends GamePiece {
    public Queen(String name, Boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite){
            this.name ="w"+name;
        }
        else{this.name = "b"+name;
        }
        //move_set = new ArrayList<Integer>(Arrays.asList(-17,-15,-10,-6,6,10,15,17));
    }
    @Override
    public Boolean valid_move(Integer from_x, Integer from_y, Integer to_x, Integer to_y, GamePiece[][] board, int check){
        if (checkBisPath(from_x, from_y, to_x, to_y, board) || checkRookpath(from_x, from_y, to_x, to_y, board)) {
            return true;
        }
        return false;

        //if ((x==0 || y==0) || (Math.abs(x)==Math.abs(y)) ){ //kan röra sig som torn ELLER löpare
            //return true;
        //}
        //return false;
    }
    private boolean checkBisPath (Integer from_x, Integer from_y, Integer to_x, Integer to_y, GamePiece[][] board){
        if(from_x.equals(to_x) || from_y.equals(to_y)){
            return false;
        }
        if(Math.abs(to_x - from_x) != Math.abs(to_y - from_y)){
            return false;
        }
        int rowOffset, colOffset;

        if(from_x < to_x){
            rowOffset = 1;
        }else{
            rowOffset = -1;
        }
        if(from_y < to_y){
            colOffset = 1;
        }else{
            colOffset = -1;
        }
        int y = from_y + colOffset;
        for(int x = from_x + rowOffset; x != to_x; x += rowOffset){
            if(board[x][y] != null){
                return false;
            }
            y += colOffset;
        }
        return true;
    }

    private boolean checkRookpath (Integer from_x, Integer from_y, Integer to_x, Integer to_y, GamePiece[][] board){
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
