import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tabuleiro {
    private Peca[][] tabuleiro = new Peca[8][8];
    private boolean turnoBranco = true;

    public Tabuleiro() {
        iniciarTabuleiro();
    }

    private void iniciarTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                if ((linha + coluna) % 2 == 1) {
                    if (linha < 3) {
                        tabuleiro[linha][coluna] = new Peca(false);
                    } else if (linha > 4) {
                        tabuleiro[linha][coluna] = new Peca(true);
                    }
                }
            }
        }
    }

    public Peca getPeca(int linha, int coluna) {
        return tabuleiro[linha][coluna];
    }

    public boolean validarMovimento(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
          // Verifica se há capturas obrigatórias disponíveis
              // Verifica se há capturas obrigatórias disponíveis
        Peca peca = tabuleiro[linhaOrigem][colunaOrigem];
        if (peca != null && existeCapturaDisponivel(peca.isBranca()) && Math.abs(linhaDestino - linhaOrigem) == 1) {
            return false; // Movimento normal não permitido quando há capturas
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
    
        // Verifica se a peça é uma dama e se o caminho está livre
        if (pecaOrigem.isDama()) {
            int deltaLinha = Integer.compare(linhaDestino, linhaOrigem);
            int deltaColuna = Integer.compare(colunaDestino, colunaOrigem);
    
            int linhaAtual = linhaOrigem + deltaLinha;
            int colunaAtual = colunaOrigem + deltaColuna;
    
            while (linhaAtual != linhaDestino || colunaAtual != colunaDestino) {
                if (tabuleiro[linhaAtual][colunaAtual] != null) {
                    return false; // Há uma peça bloqueando o caminho
                }
                linhaAtual += deltaLinha;
                colunaAtual += deltaColuna;
            }
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

    public void moverPeca(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        if (!validarMovimento(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) {
            return;
        }

        // Captura de peça
        if (Math.abs(linhaDestino - linhaOrigem) == 2) {
            int linhaCapturada = (linhaOrigem + linhaDestino) / 2;
            int colunaCapturada = (colunaOrigem + colunaDestino) / 2;
            tabuleiro[linhaCapturada][colunaCapturada] = null;
        }

        // Move a peça
        Peca peca = tabuleiro[linhaOrigem][colunaOrigem];
        tabuleiro[linhaDestino][colunaDestino] = peca;
        tabuleiro[linhaOrigem][colunaOrigem] = null;

        // Promoção a dama
        if (peca != null) {
            if ((peca.isBranca() && linhaDestino == 0) || (!peca.isBranca() && linhaDestino == 7)) {
                peca.tornarDama();
                alternarTurno();
                return;
            }
        }

        // Verifica capturas adicionais
        if (Math.abs(linhaDestino - linhaOrigem) == 2 && haCapturaPossivel(linhaDestino, colunaDestino)) {
            return;
        }

        alternarTurno();
    }

    public boolean haCapturaPossivel(int linha, int coluna) {
        Peca peca = tabuleiro[linha][coluna];
        if (peca == null) return false;
    
        int[][] direcoes;
        if (peca.isDama()) {
            direcoes = new int[][]{{-1,-1}, {-1,1}, {1,-1}, {1,1}};
            // Verificação especial para damas (pode capturar a qualquer distância)
            for (int[] dir : direcoes) {
                int distancia = 1;
                while (true) {
                    int linhaMeio = linha + dir[0] * distancia;
                    int colunaMeio = coluna + dir[1] * distancia;
                    int linhaDestino = linha + dir[0] * (distancia + 1);
                    int colunaDestino = coluna + dir[1] * (distancia + 1);
                    
                    if (!estaDentroTabuleiro(linhaMeio, colunaMeio) || 
                        !estaDentroTabuleiro(linhaDestino, colunaDestino)) {
                        break;
                    }
                    
                    Peca pecaMeio = tabuleiro[linhaMeio][colunaMeio];
                    Peca pecaDestino = tabuleiro[linhaDestino][colunaDestino];
                    
                    if (pecaMeio != null && pecaMeio.isBranca() != peca.isBranca() && 
                        pecaDestino == null) {
                        return true;
                    }
                    
                    if (pecaMeio != null) break; // Encontrou obstáculo
                    distancia++;
                }
            }
        } else {
            // Verificação para peças normais
            direcoes = peca.isBranca() ? new int[][]{{-1,-1}, {-1,1}} : new int[][]{{1,-1}, {1,1}};
            for (int[] dir : direcoes) {
                int linhaMeio = linha + dir[0];
                int colunaMeio = coluna + dir[1];
                int linhaDestino = linha + 2 * dir[0];
                int colunaDestino = coluna + 2 * dir[1];
                
                if (estaDentroTabuleiro(linhaMeio, colunaMeio) && 
                    estaDentroTabuleiro(linhaDestino, colunaDestino)) {
                    Peca pecaMeio = tabuleiro[linhaMeio][colunaMeio];
                    Peca pecaDestino = tabuleiro[linhaDestino][colunaDestino];
                    
                    if (pecaMeio != null && pecaMeio.isBranca() != peca.isBranca() && 
                        pecaDestino == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean estaDentroTabuleiro(int linha, int coluna) {
        return linha >= 0 && linha < 8 && coluna >= 0 && coluna < 8;
    }

    public boolean existeCapturaDisponivel(boolean paraPecasBrancas) {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Peca peca = getPeca(linha, coluna);
                if (peca != null && peca.isBranca() == paraPecasBrancas) {
                    if (podeCapturar(linha, coluna)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean podeCapturar(int linha, int coluna) {
        Peca peca = getPeca(linha, coluna);
        if (peca == null) return false;
    
        int[] direcoes = peca.isDama() ? new int[]{-1, 1} : new int[]{peca.getDirecao()};
    
        for (int dirLinha : direcoes) {
            for (int dirColuna : new int[]{-1, 1}) {
                int linhaAlvo = linha + dirLinha;
                int colunaAlvo = coluna + dirColuna;
                int linhaDestino = linha + 2 * dirLinha;
                int colunaDestino = coluna + 2 * dirColuna;
    
                if (linhaDestino >= 0 && linhaDestino < 8 && colunaDestino >= 0 && colunaDestino < 8) {
                    Peca pecaAlvo = getPeca(linhaAlvo, colunaAlvo);
                    Peca pecaDestino = getPeca(linhaDestino, colunaDestino);
    
                    if (pecaAlvo != null && 
                        pecaAlvo.isBranca() != peca.isBranca() && 
                        pecaDestino == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void imprimirTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                System.out.print(tabuleiro[linha][coluna] != null ? tabuleiro[linha][coluna] + " " : ". ");
            }
            System.out.println();
        }
    }

    public boolean getTurnoBranco() {
        return turnoBranco;
    }

    public void alternarTurno() {
        turnoBranco = !turnoBranco;
    }

    public boolean jogadorVenceu() {
        // Verifica se não há peças do jogador adversário
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Peca peca = tabuleiro[linha][coluna];
                if (peca != null && peca.isBranca() != turnoBranco) {
                    return false; // Se encontrar uma peça adversária, o jogo continua
                }
            }
        }
        return true;
    }
}