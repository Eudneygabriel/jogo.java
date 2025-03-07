public class Tabuleiro {
    private Peca[][] tabuleiro = new Peca[8][8]; // Tabuleiro 8x8

    public Tabuleiro() {
        // Inicializa o tabuleiro com as peças na posição inicial
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        // Coloca as peças brancas nas 3 primeiras linhas
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 != 0) { // Coloca as peças nas casas escuras
                    tabuleiro[i][j] = new Peca(true); // Peças brancas
                }
            }
        }

        // Coloca as peças pretas nas 3 últimas linhas
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 != 0) { // Coloca as peças nas casas escuras
                    tabuleiro[i][j] = new Peca(false); // Peças pretas
                }
            }
        }
    }

    // Método para obter uma peça do tabuleiro
    public Peca getPeca(int linha, int coluna) {
        return tabuleiro[linha][coluna];
    }

    // Método para mover uma peça no tabuleiro
    public boolean moverPeca(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        // Verificar se a posição de origem contém uma peça
        Peca pecaOrigem = tabuleiro[linhaOrigem][colunaOrigem];
        if (pecaOrigem == null) {
            return false; // Não há peça para mover
        }

        // Verificar se a posição de destino está dentro do tabuleiro e está vazia
        if (linhaDestino < 0 || linhaDestino >= 8 || colunaDestino < 0 || colunaDestino >= 8) {
            return false; // Posição inválida
        }

        if (tabuleiro[linhaDestino][colunaDestino] != null) {
            return false; // A casa de destino já está ocupada
        }

        // Movimento simples (não verificando as regras do jogo de damas)
        tabuleiro[linhaDestino][colunaDestino] = pecaOrigem; // Move a peça
        tabuleiro[linhaOrigem][colunaOrigem] = null; // Limpa a posição original

        // Se a peça atingir a última linha, ela se torna uma dama
        if ((pecaOrigem.isBranca() && linhaDestino == 0) || (!pecaOrigem.isBranca() && linhaDestino == 7)) {
            pecaOrigem.tornarDama(); // Torna a peça uma dama
        }

        return true; // Movimento bem-sucedido
    }

    // Método para imprimir o tabuleiro no console
    public void imprimirTabuleiro() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tabuleiro[i][j] != null) {
                    System.out.print(tabuleiro[i][j].isBranca() ? "B " : "P "); // "B" para peça branca, "P" para preta
                } else {
                    System.out.print(". "); // "." para casas vazias
                }
            }
            System.out.println();
        }
    }
}
