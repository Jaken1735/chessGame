import game_pieces.*;
import java.util.ArrayList;
import java.util.Objects;


class ChessModel implements Boardgame {
    private String currentMessage = "No message yet";
    GamePiece[][] board = new GamePiece[8][8];
    Boolean picked = false;
    Boolean playerWhite = true;
    public Integer savedx;
    public Integer savedy;
    public ArrayList<Integer> vMovelist = new ArrayList<>();
    public int autoCoor;


    ChessModel(){
        initChessBoard();
    }

    @Override
    public boolean move(int x, int y) {
        if (!picked){
            if (board[x][y] != null) { //om rutan har något på sig
                if ((playerWhite && board[x][y].isWhite) || (!playerWhite && !board[x][y].isWhite)) { //player picks its own character
                    currentMessage = "picked " + board[x][y].name.substring(1);
                    savedx = x;
                    savedy = y;
                    picked = true;
                    identidfylegalMoves(savedx, savedy); //uppdaterar vMovelist
                    if (vMovelist.size() == 1) {  //AUTO REPLACEMENT
                        autoCoor = vMovelist.get(0);
                        int r = autoCoor / 8;
                        int c = autoCoor % 8;
                        if (board[savedx][savedy].name.substring(1).equals("Pawn")) {
                            board[savedx][savedy].valid_move(savedx, savedy, r, c, board, 0);
                        }
                        completeMove(r, c);
                        return true;

                    } else {
                        return true;
                    }
                }
            }
            currentMessage = "please pick your own character";
            return false;
        }
        else {
            if ((board[x][y] == null || !board[x][y].name.substring(0, 1).equals(board[savedx][savedy].name.substring(0, 1))) && board[savedx][savedy].valid_move(savedx, savedy, x, y, board, 0)){
                completeMove(x,y);
                return true;
            }
            else {
                if (board[x][y] == board[savedx][savedy]){
                    if (playerWhite) {
                        currentMessage = "Player1's Turn";
                    }
                    else {
                        currentMessage = "Player2's Turn";
                    }
                }
                else{
                    currentMessage = "Unvalid move, try again!";
                }
                picked = false;
                return false;
            }
        }
    }

    @Override
    public String getStatus(int x, int y) {
        if (this.board[x][y] == null){
            return "None";
        }
        return this.board[x][y].name;
    }

    @Override
    public String getMessage() {
        return currentMessage;
    }

    @Override
    public ArrayList<Integer> getIdentifyLegalMoves(){
        return vMovelist;
    }

    @Override
    public void initLegalMoves(int from_x, int from_y){
        identidfylegalMoves(from_x, from_y);
    }

    @Override
    public boolean getPlayer() {
        if (playerWhite) {
            return true;
        }
        else {
            return false;
        }
    }

//
//  @brief Complete the move that the player has decided to make.
//         All the moves that come to this function are classified as legal moves,
//         and therefore will be completed.
//
//
//

    void completeMove(int x, int y) {
        String t;
        String c;
        int pv;

        if (playerWhite) {c = "w"; t = "2"; pv = 0;}
        else {c = "b"; t = "1"; pv = 7;}
        if (x == pv && this.getStatus(savedx, savedy).equals(c+"Pawn")){      ////Promovering
            board[pv][y] = GamePiece.create("Queen", playerWhite);    ////Promovering
        }
        else{
            board[x][y] = board[savedx][savedy];
        }
        board[savedx][savedy] = null;

        if(!checkKingNotInDanger()) {
            currentMessage = "Successfully moved, Player"+t+"'s king is in danger and is now in check!";
        }
        else {
            currentMessage = "Successfully moved, it is now Player"+t+"'s turn";
        }
        playerWhite = !(playerWhite);
        picked = false;
    }



//
//  @brief This function identifies all the legal moves that are possible for the piece
//         that is standing on from_x, from_y. The algorithm runs through all the squares on the
//         table and adds all the squares which the piece can move to on the board.
//
//

    void identidfylegalMoves(int from_x, int from_y) {
        if (board[from_x][from_y] != null) {
            vMovelist.clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[from_x][from_y].valid_move(from_x, from_y, i, j, board, 1)) {
                        if ((board[i][j] == null ||!board[i][j].name.substring(0, 1).equals(board[from_x][from_y].name.substring(0, 1)))) {
                            vMovelist.add(i * 8 + j);
                        }
                    }
                }
            }
        }
    }

//
//  @brief Function which checks if the king is in danger after the opponent has made a move.
//         Scans the board and identifies the location of said king, and then
//         simulates all the moves the opposite pieces can make based on their current
//         position. If a move equals the position of the king, then
//         the king is in danger.
//

    boolean checkKingNotInDanger(){

        ArrayList<Integer> coorList  = new ArrayList<>();
        String p,k;
        int king_row = 0, king_col = 0;

        for (int i = 0; i <  8; i++) {
            for (int j = 0; j < 8; j++) {
                if (playerWhite){ p = "w"; k="b";} // Identify position of white king and all black pieces
                else{p="b";k="w";}
                if (Objects.equals(this.getStatus(i, j), k+"King")){
                    king_row = i;
                    king_col = j;
                }
                if (Objects.equals(this.getStatus(i, j), p+"Pawn")){
                    coorList.add(i * 8 + j);
                }
                if (Objects.equals(this.getStatus(i, j), p+"Rook")){
                    coorList.add(i * 8 + j);
                }
                if (Objects.equals(this.getStatus(i, j), p+"Knight")){
                    coorList.add(i * 8 + j);
                }
                if (Objects.equals(this.getStatus(i, j), p+"Bishop")){
                    coorList.add(i * 8 + j);
                }
                if (Objects.equals(this.getStatus(i, j), p+"Queen")){
                    coorList.add(i * 8 + j);
                }
                if (Objects.equals(this.getStatus(i, j), p+"King")){
                    coorList.add(i * 8 + j);
                }
            }
        }
        ArrayList<Integer> kingDangerList;

        for(int piece : coorList) {
            int x = piece / 8;
            int y =  piece % 8;
            identidfylegalMoves(x, y);
            kingDangerList = getIdentifyLegalMoves();
            for(int move : kingDangerList) {
                int r = move / 8;
                int c =  move % 8;
                if (r == king_row && c ==  king_col) {
                    return false;
                }
            }
            kingDangerList.clear();
        }
        return true;
    }

//
//  @brief Create all the pieces on the chessboard.
//         Using Factory-Method when creating the pieces.
//
//


    void initChessBoard() {
        this.board[0][0] = GamePiece.create("Rook", false);
        this.board[0][1] = GamePiece.create("Knight", false);
        this.board[0][2] = GamePiece.create("Bishop", false);
        this.board[0][3] = GamePiece.create("Queen", false);
        this.board[0][4] = GamePiece.create("King", false);
        this.board[0][5] = GamePiece.create("Bishop", false);
        this.board[0][6] = GamePiece.create("Knight", false);
        this.board[0][7] = GamePiece.create("Rook", false);

        this.board[7][0] = GamePiece.create("Rook", true);
        this.board[7][1] = GamePiece.create("Knight", true);
        this.board[7][2] = GamePiece.create("Bishop", true);
        this.board[7][3] = GamePiece.create("Queen", true);
        this.board[7][4] = GamePiece.create("King", true);
        this.board[7][5] = GamePiece.create("Bishop", true);
        this.board[7][6] = GamePiece.create("Knight", true);
        this.board[7][7] = GamePiece.create("Rook", true);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0) {
                    this.board[1][j] = GamePiece.create("Pawn", false);
                }
                else {
                    this.board[6][j] = GamePiece.create("Pawn", true);
                }
            }
        }
    }
}