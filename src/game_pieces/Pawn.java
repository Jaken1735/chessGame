package game_pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends GamePiece {
    public Boolean first = true;
    public Pawn(String name, Boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite){
            this.name ="w"+name;
        }
        else{this.name = "b"+name;
        }
    }
    @Override
    public Boolean valid_move(Integer from_x, Integer from_y, Integer to_x, Integer to_y, GamePiece[][] board, int check) {
        int d_row, row;
        if (from_y.equals(to_y)){
            if (first) {
                if (isWhite) {
                    d_row = -1;
                    if (!((from_x-to_x) == 2 || (from_x-to_x) == 1)){
                        return false;
                    }
                }
                else {
                    d_row = 1;
                    if (!((to_x-from_x) == 2 || (to_x-from_x) == 1)){
                        return false;
                    }
                }
                for (row  = from_x + d_row; row != (from_x + (2*d_row)); row += d_row){
                    if (board[row][to_y] != null){return false;}
                }
                if (board[to_x][to_y] != null){
                    return false;
                }
                if (check == 1){
                    return true;
                }
                else{
                    first = false;
                    return true;
                }
            }
            else {
                if (isWhite){
                    if ((from_x - to_x) == 1 && board[to_x][to_y] == null) {
                        return true;
                    }
                    return false;
                }
                else{
                    if ((to_x - from_x) == 1 && board[to_x][to_y] == null) {
                        return true;
                    }
                    return false;
                }
            }
        } else if (board[to_x][to_y] != null && Math.abs(to_x-from_x) == Math.abs(to_y-from_y)) {
            if (Math.abs(to_x - from_x) == 1 && !board[to_x][to_y].name.substring(0, 1).equals(board[from_x][from_y].name.substring(0, 1))){
                if (isWhite) { // Can attack when decreasing rows
                    if (from_x > to_x) {
                        if (first && check == 0) {
                            first = false;
                        }
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else { // Black can attack when increasing rows
                    if (from_x < to_x) {
                        if (first && check == 0) {
                            first = false;
                        }
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            else {
                return false;
            }
        } else {
            return false;
        }
    }
}

// OLD VERSION
//int x  = to_x - from_x;
//int y = to_y - from_y;
//if (first) { //ska kunna gå ett eller två steg
//if (isWhite) {
//if ((x == -1 || x == -2) && y == 0) {
//first = false;
//return true;
//}
//} else { //its black
//if ((x == 1 || x == 2) && y == 0) {
//first = false;
//return true;
//}
//}
//}
//else{
//if (isWhite) {
//if (x == -1 && y == 0) {
//return true;
//}
//} else {
//if (x == 1 && y == 0) {
//return true;
//}
//}
//}
//return false;

