import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tabuleiro {
    private Peca[][] tabuleiro = new Peca[8][8];
    private boolean turnoBranco = true; // true para brancas, false para pretas

    public Tabuleiro() {
        iniciarTabuleiro();
    }

    private void iniciarTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                if ((linha + coluna) % 2 == 1) {
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
        if (validarMovimento(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) {
            // Se houver captura, remove a peça adversária
            if (Math.abs(linhaDestino - linhaOrigem) == 2) {
                int linhaCapturada = (linhaOrigem + linhaDestino) / 2;
                int colunaCapturada = (colunaOrigem + colunaDestino) / 2;
                tabuleiro[linhaCapturada][colunaCapturada] = null; // Captura a peça
            }
    
            // Move a peça para o novo destino
            tabuleiro[linhaDestino][colunaDestino] = tabuleiro[linhaOrigem][colunaOrigem];
            tabuleiro[linhaOrigem][colunaOrigem] = null;
    
            // Verifica se a peça chegou à última linha para virar dama
            Peca pecaMovida = tabuleiro[linhaDestino][colunaDestino];
            if ((pecaMovida.isBranca() && linhaDestino == 0) || (!pecaMovida.isBranca() && linhaDestino == 7)) {
                pecaMovida.tornarDama();
            }
        }
    }

    public boolean validarMovimento(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        // Verifica se a casa de destino está dentro do tabuleiro
        if (linhaDestino < 0 || linhaDestino >= 8 || colunaDestino < 0 || colunaDestino >= 8) {
            return false;
        }
    
        // Verifica se a peça de origem existe
        Peca pecaOrigem = tabuleiro[linhaOrigem][colunaOrigem];
        if (pecaOrigem == null) {
            return false;
        }
    
        // Verifica se a peça está tentando se mover para uma casa ocupada
        if (tabuleiro[linhaDestino][colunaDestino] != null) {
            return false;
        }
    
        // Verifica se o movimento é diagonal
        if (Math.abs(linhaDestino - linhaOrigem) != Math.abs(colunaDestino - colunaOrigem)) {
            return false;
        }
    
        // Verifica se a peça está movendo uma casa para frente ou para trás, conforme sua cor
        if (!pecaOrigem.isDama()) { // Apenas para peças que não são damas
            if (pecaOrigem.isBranca() && linhaDestino > linhaOrigem) {
                return false; // Peças brancas só podem mover para frente (linha menor)
            }
            if (!pecaOrigem.isBranca() && linhaDestino < linhaOrigem) {
                return false; // Peças pretas só podem mover para frente (linha maior)
            }
    
            // Verifica se a peça está tentando mover mais de uma casa sem captura
            if (Math.abs(linhaDestino - linhaOrigem) > 1) {
                // Verifica se há uma captura possível
                if (Math.abs(linhaDestino - linhaOrigem) == 2) {
                    int linhaMeio = (linhaOrigem + linhaDestino) / 2;
                    int colunaMeio = (colunaOrigem + colunaDestino) / 2;
                    Peca pecaMeio = tabuleiro[linhaMeio][colunaMeio];
    
                    // Verifica se a peça do meio é do adversário
                    if (pecaMeio == null || pecaMeio.isBranca() == pecaOrigem.isBranca()) {
                        return false; // Não há peça adversária para capturar
                    }
                } else {
                    return false; // Movimento de mais de uma casa sem captura
                }
            }
        }
    
        return true;
    }

    public void imprimirTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                System.out.print(tabuleiro[linha][coluna] != null ? tabuleiro[linha][coluna] + " " : ". ");
            }
            System.out.println();
        }
    }

    // Métodos para gerenciar o turno
    public boolean getTurnoBranco() {
        return turnoBranco;
    }

    public void alternarTurno() {
        turnoBranco = !turnoBranco;
    }
} // Fechamento da classe Tabuleiro