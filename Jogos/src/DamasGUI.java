import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DamasGUI extends JFrame {
    private JButton[][] botoes = new JButton[8][8];
    private Tabuleiro tabuleiro = new Tabuleiro();
    private JLabel statusLabel;
    private int linhaOrigem = -1, colunaOrigem = -1;

    public DamasGUI() {
        setTitle("Jogo de Damas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel tabuleiroPanel = new JPanel(new GridLayout(8, 8));
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                botoes[linha][coluna] = new JButton();
                botoes[linha][coluna].setBackground((linha + coluna) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
                botoes[linha][coluna].setOpaque(true);
                botoes[linha][coluna].setBorderPainted(false);
                botoes[linha][coluna].addActionListener(new BotaoClickListener(linha, coluna));
                tabuleiroPanel.add(botoes[linha][coluna]);
            }
        }

        statusLabel = new JLabel("Vez das Brancas", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 18));
        add(statusLabel, BorderLayout.NORTH);
        add(tabuleiroPanel, BorderLayout.CENTER);

        // Botão para retornar ao menu inicial
        JButton botaoRetornar = new JButton("Retornar ao Menu Inicial");
        botaoRetornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuInicial().setVisible(true); // Abre o menu inicial
                dispose(); // Fecha a janela do jogo de Damas
            }
        });
        
        add(botaoRetornar, BorderLayout.SOUTH); // Adiciona o botão ao painel

        atualizarTabuleiro();
    }

    private void atualizarTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Peca peca = tabuleiro.getPeca(linha, coluna);
                if (peca != null) {
                    botoes[linha][coluna].setText(peca.toString());
                    botoes[linha][coluna].setForeground(peca.isBranca() ? Color.WHITE : Color.BLACK);
                } else {
                    botoes[linha][coluna].setText("");
                }
                // Cor de fundo padrão
                botoes[linha][coluna].setBackground(
                    (linha + coluna) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
            }
        }
        
        // Destacar peças com captura obrigatória
        destacarPecasObrigatorias();
        
        // Destacar última peça movida (se houver captura adicional)
        if (tabuleiro.getUltimaPecaMovida() != null) {
            int[] pos = tabuleiro.getUltimaPecaMovida();
            botoes[pos[0]][pos[1]].setBackground(Color.CYAN);
        }
    }

    private void destacarMovimentosValidos(int linha, int coluna) {
        // Primeiro verifica se há capturas obrigatórias
        boolean haCapturas = tabuleiro.existeCapturaDisponivel(tabuleiro.getTurnoBranco());
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tabuleiro.validarMovimento(linha, coluna, i, j)) {
                    // Se há capturas obrigatórias, só destaca capturas
                    if (!haCapturas || Math.abs(i - linha) > 1) {
                        botoes[i][j].setBackground(Color.YELLOW);
                    }
                }
            }
        }
    }

    private void destacarPecasObrigatorias() {
        
        // Destacar peças com captura obrigatória em azul
        for (int[] posicao : tabuleiro.getPecasComCapturaObrigatoria()) {
            botoes[posicao[0]][posicao[1]].setBackground(Color.BLUE);
        }
    }

    private class BotaoClickListener implements ActionListener {
        private int linha, coluna;

        public BotaoClickListener(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
        }

        @Override
public void actionPerformed(ActionEvent e) {
    if (!tabuleiro.podeMoverOutraPeca(linha, coluna)) {
        JOptionPane.showMessageDialog(DamasGUI.this, 
            "Você deve continuar capturando com esta peça!");
        return;
    }
            
    if (linhaOrigem == -1 && colunaOrigem == -1) {
        // Seleciona a peça de origem
        Peca peca = tabuleiro.getPeca(linha, coluna);
        if (peca != null && peca.isBranca() == tabuleiro.getTurnoBranco()) {
            linhaOrigem = linha;
            colunaOrigem = coluna;
            botoes[linha][coluna].setBackground(Color.CYAN); // Destaca a peça selecionada
            destacarMovimentosValidos(linha, coluna); // Destaca os movimentos válidos
        }
    } else if (linhaOrigem == linha && colunaOrigem == coluna) {
        // Cancela a seleção da peça ao clicar duas vezes nela
        botoes[linha][coluna].setBackground((linha + coluna) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
        linhaOrigem = -1;
        colunaOrigem = -1;
        atualizarTabuleiro(); // Atualiza o tabuleiro para remover os destaques
    } else  {
        // Tenta mover a peça para o destino
        if (tabuleiro.validarMovimento(linhaOrigem, colunaOrigem, linha, coluna)) {
            tabuleiro.moverPeca(linhaOrigem, colunaOrigem, linha, coluna);
            atualizarTabuleiro();
            statusLabel.setText(tabuleiro.getTurnoBranco() ? "Vez das Brancas" : "Vez das Pretas");

                    // Verifica se há mais capturas possíveis
                    if (tabuleiro.existeCapturaDisponivel(tabuleiro.getTurnoBranco())){
                        linhaOrigem = linha;
                        colunaOrigem = coluna;
                        botoes[linha][coluna].setBackground(Color.CYAN); // Mantém a peça selecionada
                        destacarMovimentosValidos(linha, coluna); // Destaca os movimentos válidos
                    } else {
                        // Reseta a seleção
                        botoes[linhaOrigem][colunaOrigem].setBackground((linhaOrigem + colunaOrigem) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
                        linhaOrigem = -1;
                        colunaOrigem = -1;
                    }
                } else {
                    JOptionPane.showMessageDialog(DamasGUI.this, "Movimento inválido!");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DamasGUI().setVisible(true));
    }
}