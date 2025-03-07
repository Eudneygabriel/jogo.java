import java.util.Scanner;

public class JogoDamas {
    private Tabuleiro tabuleiro;
    private boolean turnoBranco = true; // Começam as peças brancas

    public JogoDamas() {
        tabuleiro = new Tabuleiro();
    }

    public void iniciarJogo() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTabuleiro Atual:");
            tabuleiro.imprimirTabuleiro();
            System.out.println((turnoBranco ? "Brancas" : "Pretas") + ", faça sua jogada!");

            System.out.print("Digite linha e coluna de origem (ex: 2 3): ");
            int linhaOrigem = scanner.nextInt();
            int colunaOrigem = scanner.nextInt();

            System.out.print("Digite linha e coluna de destino (ex: 3 4): ");
            int linhaDestino = scanner.nextInt();
            int colunaDestino = scanner.nextInt();

            if (tabuleiro.getPeca(linhaOrigem, colunaOrigem) != null && 
                tabuleiro.getPeca(linhaOrigem, colunaOrigem).isBranca() == turnoBranco) {

                tabuleiro.moverPeca(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
                turnoBranco = !turnoBranco;
            } else {
                System.out.println("Movimento inválido! Tente novamente.");
            }
        }
    }

    public static void main(String[] args) {
        JogoDamas jogo = new JogoDamas();
        jogo.iniciarJogo();
    }
}
