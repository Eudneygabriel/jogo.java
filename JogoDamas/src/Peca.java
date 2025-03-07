public class Peca {
    private boolean ehBranca; // Define se a peça é branca ou preta
    private boolean ehDama;   // Define se a peça é promovida (dama)

    public Peca(boolean ehBranca) {
        this.ehBranca = ehBranca;
        this.ehDama = false; // No início, nenhuma peça é dama
    }

    public boolean isBranca() {
        return ehBranca;
    }

    public boolean isDama() {
        return ehDama;
    }

    public void tornarDama() {
        this.ehDama = true;
    }

    // Remover o método toString()
}
