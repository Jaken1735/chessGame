package game_pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends GamePiece {
    public Knight(String name, Boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite){
            this.name ="w"+name;
        }
        else{this.name = "b"+name;
        }
        move_set = new ArrayList<Integer>(Arrays.asList(-17,-15,-10,-6,6,10,15,17));
    }
    @Override
    public Boolean valid_move(Integer from_x, Integer from_y, Integer to_x, Integer to_y, GamePiece[][] board, int check){
        //int x = to_x - from_x;
        //int y = to_y - from_y;
        //if (move_set.contains(8*x + y)){
            //return true;
        //}
        //return false;
        if(Math.abs(to_x - from_x) == 2 && Math.abs(to_y - from_y) == 1){
            return true;
        }
        if(Math.abs(to_x - from_x) == 1 && Math.abs(to_y - from_y) == 2){
            return true;
        }
        return false;
    }
}
