import game_pieces.GamePiece;

import java.util.ArrayList;

public interface Boardgame {
    public boolean move(int x, int y);
    public String getStatus(int x, int y);
    public String getMessage();
    public ArrayList<Integer> getIdentifyLegalMoves();
    public void initLegalMoves(int x, int y);
    public boolean getPlayer();
}
