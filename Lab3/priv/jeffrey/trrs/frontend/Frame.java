package priv.jeffrey.trrs.frontend;

public class Frame extends javax.swing.JFrame{
    public Frame(){
        super("Teaching & Researching Registration System");
        this.setSize(960, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}
