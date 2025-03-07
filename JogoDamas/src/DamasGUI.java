import javax.swing.*;
import java.awt.*;

public class DamasGUI extends JFrame {
    private JButton[][] botoes = new JButton[8][8];
    private Tabuleiro tabuleiro = new Tabuleiro();

    public DamasGUI() {
        setTitle("Jogo de Damas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                botoes[linha][coluna] = new JButton();
                botoes[linha][coluna].setBackground((linha + coluna) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
                add(botoes[linha][coluna]);
            }
        }
        
        atualizarTabuleiro();
    }

    private void atualizarTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Peca peca = tabuleiro.getPeca(linha, coluna);
                if (peca != null) {
                    botoes[linha][coluna].setText(peca.isBranca() ? "B" : "R");
                } else {
                    botoes[linha][coluna].setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DamasGUI().setVisible(true));
    }
}
