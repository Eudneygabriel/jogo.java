public class Tabuleiro {
    private Peca[][] tabuleiro = new Peca[8][8];

    public Tabuleiro() {
        iniciarTabuleiro();
    }

    private void iniciarTabuleiro() {
        // Colocar as peças nos lugares iniciais (linhas 0, 1, 2 e 5, 6, 7)
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                if ((linha + coluna) % 2 == 1) { // Apenas nas casas escuras
                    if (linha < 3) {
                        tabuleiro[linha][coluna] = new Peca(false); // Peças pretas
                    } else if (linha > 4) {
                        tabuleiro[linha][coluna] = new Peca(true); // Peças brancas
                    }
                }
            }
        }
    }

    public Peca getPeca(int linha, int coluna) {
        return tabuleiro[linha][coluna];
    }

    public void moverPeca(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        tabuleiro[linhaDestino][colunaDestino] = tabuleiro[linhaOrigem][colunaOrigem];
        tabuleiro[linhaOrigem][colunaOrigem] = null;
    }

    public void imprimirTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                System.out.print(tabuleiro[linha][coluna] != null ? tabuleiro[linha][coluna] + " " : ". ");
            }
            System.out.println();
        }
    }
}
