
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

//
//  @brief Gui for the chess boardgame. Projects everything that happens in chessModel.
//
//
//
//

class ViewControl extends JFrame implements ActionListener {
    private JFrame gameWindow;
    private JLabel mess = new JLabel("Greetings Players, start by pressing START GAME!");
    private JPanel cPanel, label_panel, startb_panel;

    private Boardgame game;
    private Square[][] board;
    private GridBagConstraints gridcon = new GridBagConstraints();
    private int prev_row = -1, prev_col = -1;
    private JButton startbutton, endbutton;
    private Color prev_paint;
    private ArrayList<Integer> paintGreen = new ArrayList<>();
    private ArrayList<Integer> paintBack = new ArrayList<>();
    private ArrayList<Integer> autoList = new ArrayList<>();
    private Queue<Color> remColor = new LinkedList<>();

//
//  @brief Setup and paint the chessboard. In this section the pieces are also placed
//         on the board. The Viewcontrol constructor setups all graphical interface
//         player/players.
//
//

    ViewControl(Boardgame gm) {

        // Step 1: Set up gamewindow for gui
        gameWindow = new JFrame("Chess by Jacob & Sam");
        game = gm;
        board = new Square[8][8];
        initFrame();
        gameWindow.setLayout(new GridBagLayout());
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cPanel = new JPanel();
        label_panel = new JPanel();
        startb_panel = new JPanel();
        cPanel.setLayout(new GridLayout(8, 8));

        startbutton = new JButton("START GAME");
        endbutton = new JButton("END GAME");
        startbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mess.setText("Player 1 can start the game by moving the desired white piece!");

                startb_panel.remove(startbutton);
                startb_panel.add(endbutton);
            }
        });

        endbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Step 2: Paint the gamewindow
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) { // Want the color to alternate between black and white
                Square rut = new Square(row, col);
                this.board[row][col] = rut;
                this.board[row][col].addActionListener(this::actionPerformed);
                this.cPanel.add(rut);
                if (!Objects.equals(gm.getStatus(row, col), "None")){
                    this.board[row][col].fixIcon(game.getStatus(row, col));
                }
            }
        }
        mess.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        this.label_panel.add(this.mess);
        this.startb_panel.add(this.startbutton);


        gridcon.gridx = 0;
        gridcon.gridy = 0;
        gameWindow.add(this.label_panel, gridcon);
        gridcon.gridx = 0;
        gridcon.gridy = 2;
        gameWindow.add(this.cPanel, gridcon);
        gridcon.gridx = 0;
        gridcon.gridy = 4;
        gameWindow.add(this.startb_panel, gridcon);


    }

//
//  @brief Update the board based on the moves that the player/players are making.
//         Functions such as auto-move, painting the possible moves green and
//         moving pieces across the board.
//
//
    @Override
    public void actionPerformed (ActionEvent e){
        Square selected_p = (Square) e.getSource();
        this.game.initLegalMoves(selected_p.row, selected_p.col);
        autoList = this.game.getIdentifyLegalMoves();

        // I och med att vi går åt andra hållet så klaffar inte viewcontrol och chessmodel
        // behöver sätta begränsningar för att eliminera buggar
        if (autoList.size() == 1 && !(this.game.getMessage().startsWith("picked")) && ((this.game.getPlayer() && game.getStatus(selected_p.row, selected_p.col).substring(0, 1).equals("w")) ||  !(this.game.getPlayer()) && game.getStatus(selected_p.row, selected_p.col).substring(0, 1).equals("b"))) { // Auto-Move
            int autocr = autoList.get(0);
            int autor = autocr / 8;
            int autoc = autocr % 8;

            // Auto-Promovering, ändrar bild för pjäsen
            String temp_img = game.getStatus(selected_p.row, selected_p.col);
            if (this.game.getPlayer() && game.getStatus(selected_p.row, selected_p.col).equals("wPawn") && autor == 0) {
                board[selected_p.row][selected_p.col].removeIcon();
                board[autor][autoc].fixIcon("wQueen");
            } else if (!(this.game.getPlayer()) && game.getStatus(selected_p.row, selected_p.col).equals("bPawn") && autor == 7) {
                board[selected_p.row][selected_p.col].removeIcon();
                board[autor][autoc].fixIcon("bQueen");
            }
            else {
                board[selected_p.row][selected_p.col].removeIcon();
                board[autor][autoc].fixIcon(temp_img);
            }

            int yesOrno = JOptionPane.showConfirmDialog(this.gameWindow, "Do you confirm the automated move?", "Auto-Move", JOptionPane.YES_NO_OPTION);

            if (yesOrno == 1) { // Reverse auto-move
                String temp_img2 = this.game.getStatus(autor, autoc);
                board[selected_p.row][selected_p.col].fixIcon(temp_img);
                board[autor][autoc].fixIcon(temp_img2);
                if (game.getStatus(selected_p.row, selected_p.col).substring(0, 1).equals("w")) {
                    this.mess.setText("Player1's turn!");
                }
                else {
                    this.mess.setText("Player2's turn!");
                }
            }
            else { // Complete auto-move
                this.game.move(selected_p.row, selected_p.col);
                String cmessage = this.game.getMessage();
                showKingBox(autor, autoc, cmessage);
                this.mess.setText(cmessage);

            }
        }
        else {

            boolean invoked = this.game.move(selected_p.row, selected_p.col);
            if (invoked) {
                String cmessage = this.game.getMessage(); // Get the current message


                if (cmessage.startsWith("picked")) {

                    prev_row = selected_p.row;
                    prev_col = selected_p.col;
                    prev_paint = board[selected_p.row][selected_p.col].getBackground();
                    board[selected_p.row][selected_p.col].setBackground(Color.CYAN);

                    paintGreen = autoList;
                    for (int i = 0; i < paintGreen.size(); i++) {
                        int convertCor = paintGreen.get(i);
                        paintBack.add(convertCor); // Använder PaintBack för att PaintGreen uppdateras pga checkKing i Chessmodel
                        int x = convertCor / 8;
                        int y = convertCor % 8;
                        remColor.add(board[x][y].getBackground());
                        board[x][y].setBackground(Color.GREEN);
                    }

                }  else {
                    String temp_img = game.getStatus(selected_p.row, selected_p.col);
                    board[prev_row][prev_col].removeIcon();
                    board[prev_row][prev_col].setBackground(prev_paint);
                    paintBackBoard();
                    board[selected_p.row][selected_p.col].fixIcon(temp_img);
                    paintBack.clear();
                    showKingBox(selected_p.row, selected_p.col, cmessage);
                }
                this.mess.setText(cmessage); // Show it to the player
            }
            else{

                if (!(prev_row == -1 && prev_col == -1)){ // För att första rutan inte ska ta bort färgen
                    board[prev_row][prev_col].setBackground(prev_paint);
                }
                paintBackBoard();
                String cmessage = this.game.getMessage(); // Get the current message
                paintBack.clear();
                this.mess.setText(cmessage); // Show it to the player
            }

        }
    }

//
//  @brief Show a messagebox to the player when the opposite player sets the king
//         check.
//
//
//

    void showKingBox(int kr, int kc, String kmessage) {
        if (kmessage.endsWith("check!")) {
            if (game.getStatus(kr, kc).substring(0, 1).equals("w")) {
                JOptionPane.showMessageDialog(this.gameWindow, "Player2's king is in danger, be careful when making your next move.","King in danger!", JOptionPane.WARNING_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this.gameWindow, "Player1's king is in danger, be careful when making your next move.","King in danger!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

//
//  @brief Paint back the board after some squares have been changed to green or cyan
//         depending on situation.
//
//
//

    void paintBackBoard() {
        for (int i = 0; i < paintBack.size(); i++) {
            int convertCor = paintBack.get(i);
            int x = convertCor / 8;
            int y = convertCor % 8;
            Color convertPaint = remColor.remove();
            board[x][y].setBackground(convertPaint);
        }

    }

//
//  @brief Set the dimensions for the window.
//
//
//
//

    void initFrame() { // Initialize frame for the gui
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        gameWindow.setSize(width/2, height/2);
        gameWindow.setLocationRelativeTo(null);
    }

}