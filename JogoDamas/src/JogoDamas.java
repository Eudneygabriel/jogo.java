import java.util.Scanner;

public class JogoDamas {
    private static Scanner scanner = new Scanner(System.in);
    private static Tabuleiro tabuleiro = new Tabuleiro();

    public static void main(String[] args) {
        while (true) {
            tabuleiro.imprimirTabuleiro();
            if (tabuleiro.getTurnoBranco()) {
                System.out.println("Vez das Brancas!");
            } else {
                System.out.println("Vez das Pretas!");
            }

            try {
                // Solicitar movimento
                System.out.print("Digite a linha e coluna de origem (ex: 5 2): ");
                int linhaOrigem = scanner.nextInt();
                int colunaOrigem = scanner.nextInt();

                System.out.print("Digite a linha e coluna de destino (ex: 4 3): ");
                int linhaDestino = scanner.nextInt();
                int colunaDestino = scanner.nextInt();

                // Validar e mover
                if (tabuleiro.validarMovimento(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) {
                    tabuleiro.moverPeca(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
                    tabuleiro.alternarTurno(); // Alterna entre os turnos
                } else {
                    System.out.println("Movimento inválido! Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Tente novamente.");
                scanner.nextLine(); // Limpa o buffer do scanner
            }
        }
    }
}
