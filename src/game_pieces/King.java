package game_pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends GamePiece {
    private boolean hasMoved;
    public King(String name, Boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite){
            this.name ="w"+name;
        }
        else{this.name = "b"+name;
        }
        move_set = new ArrayList<Integer>(Arrays.asList(-9,-8,-7,-1,1,7,8,9));
    }
    @Override
    public Boolean valid_move(Integer from_x, Integer from_y, Integer to_x, Integer to_y, GamePiece[][] board, int check){
        int x = to_x - from_x;
        int y = to_y - from_y;
        if (move_set.contains(8*x + y)){
            return true;
        }
        return false;
    }
}
