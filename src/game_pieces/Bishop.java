package game_pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends GamePiece {
    private ArrayList<Integer> bishopValid_moves = new ArrayList<>();
    public Bishop(String name, Boolean isWhite) {
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
            //bishopValid_moves.add(x * 4 + y); // Saving the coordinate
            if(board[x][y] != null){
                return false;
            }
            y += colOffset;
        }
        return true;

    }

    //@Override
    //public ArrayList<Integer> getValid_moves(){
        //ArrayList<Integer> ret_list = bishopValid_moves;
        //bishopValid_moves.clear();
        //return ret_list;
    //}

}

