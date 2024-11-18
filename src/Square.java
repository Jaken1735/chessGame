import javax.swing.*;
import java.awt.*;

class Square extends JButton {
    int row, col;
    Square(int row_index, int col_index) {
        row = row_index;
        col = col_index;
        if ((row + col) % 2 == 0) {
            this.setBackground(Color.ORANGE);
        } else {
            this.setBackground(Color.gray);
        }
        setOpaque(true);
        setBorderPainted(false);
    }

    public void fixIcon(String pieceName) {
        //gamla
        //g_piece = new JLabel(new ImageIcon("/Users/sassysam/Documents/GitHub/DD1385_projectChess/src/images_png/"+pieceName+".png"));
        //g_piece = new JLabel(new ImageIcon("/Users/jaken1735/IdeaProjects/DD1385_projectChess/src/images_png/"+pieceName+".png"));
        //gamla

        this.setIcon(new ImageIcon("/Users/jaken1735/IdeaProjects/DD1385_projectChess/src/images_png/"+pieceName+".png"));
        //this.setIcon(new ImageIcon("/Users/sassysam/Documents/GitHub/DD1385_projectChess/src/images_png/"+pieceName+".png"));
        //this.setIcon(new ImageIcon("C:/Users/pc/Documents/GitHub/DD1385_projectChess/src/images_png/"+pieceName+".png"));
    }

    public void removeIcon() {
        //if (g_piece != null){
        this.setIcon((Icon) null);
        //}
    }
}
