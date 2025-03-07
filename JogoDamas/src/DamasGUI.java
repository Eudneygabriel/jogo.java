import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DamasGUI extends JFrame {
    private JButton[][] botoes = new JButton[8][8];
    private Tabuleiro tabuleiro = new Tabuleiro();
    private boolean turnoBranco = true;  // Determina o turno, verdadeiro para as peças brancas, falso para as pretas
    private Peca pecaSelecionada = null;
    private int linhaOrigem, colunaOrigem;

    public DamasGUI() {
        setTitle("Jogo de Damas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                botoes[linha][coluna] = new JButton();
                botoes[linha][coluna].setBackground((linha + coluna) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
                botoes[linha][coluna].setBorderPainted(false);  // Remover a borda padrão do JButton
                botoes[linha][coluna].setFocusPainted(false);  // Remover o foco no botão
                botoes[linha][coluna].addActionListener(new BotaoClickListener(linha, coluna));  // Adiciona o evento de clique
                add(botoes[linha][coluna]);
            }
        }

        atualizarTabuleiro();
    }

    // Classe interna para tratar os cliques nos botões
    private class BotaoClickListener implements ActionListener {
        private int linha, coluna;

        public BotaoClickListener(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Peca peca = tabuleiro.getPeca(linha, coluna);

            // Se uma peça foi selecionada
            if (peca != null) {
                // Se é o turno do jogador correto e a peça é da cor do jogador
                if ((turnoBranco && peca.isBranca()) || (!turnoBranco && !peca.isBranca())) {
                    // Se não houver peça selecionada, seleciona a peça
                    if (pecaSelecionada == null) {
                        pecaSelecionada = peca;
                        linhaOrigem = linha;
                        colunaOrigem = coluna;
                    } else {
                        // Caso já tenha uma peça selecionada, tenta mover
                        if (validarMovimento(pecaSelecionada, linha, coluna)) {
                            // Movimenta a peça
                            tabuleiro.moverPeca(linhaOrigem, colunaOrigem, linha, coluna);
                            pecaSelecionada = null; // Desmarca a peça
                            turnoBranco = !turnoBranco; // Alterna o turno
                            atualizarTabuleiro(); // Atualiza o tabuleiro
                        } else {
                            // Se o movimento não for válido, apenas desmarque a peça
                            pecaSelecionada = null;
                        }
                    }
                }
            }
        }
    }

    // Valida se o movimento é permitido
    private boolean validarMovimento(Peca peca, int linhaDestino, int colunaDestino) {
        // Verificar regras de movimento (exemplo: movimentos diagonais)
        return true;  // Implementar as regras de movimento aqui
    }

    // Atualiza a interface gráfica com as peças
    private void atualizarTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Peca peca = tabuleiro.getPeca(linha, coluna);
                if (peca != null) {
                    desenharPeca(botoes[linha][coluna].getGraphics(), peca, coluna, linha);
                } else {
                    botoes[linha][coluna].repaint();
                }
            }
        }
    }

    // Desenha a peça no botão
    private void desenharPeca(Graphics g, Peca peca, int x, int y) {
        if (g != null) {
            g.clearRect(0, 0, 60, 60);  // Limpa o fundo do botão antes de desenhar a peça
            g.setColor(peca.isBranca() ? Color.WHITE : Color.BLACK);  // Define a cor da peça
            g.fillOval(10, 10, 40, 40);  // Desenha um círculo no centro do botão

            if (peca.isDama()) {
                g.setColor(Color.RED);  // Caso a peça seja dama, adiciona um indicativo
                g.fillOval(25, 25, 10, 10);  // Um pequeno círculo vermelho no centro
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DamasGUI().setVisible(true));
    }
}
