import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Configuração da janela do jogo
        JFrame frame = new JFrame("Jogo das Bolas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Adicionando o painel de jogo
        GamePanel panel = new GamePanel();
        frame.add(panel);

        // Tornando a janela visível
        frame.setVisible(true);
    }
}

class GamePanel extends JPanel implements MouseListener {

    private ArrayList<Ball> balls;
    private Random rand;

    public GamePanel() {
        balls = new ArrayList<>();
        rand = new Random();

        // Adiciona algumas bolas ao jogo
        for (int i = 0; i < 10; i++) {
            balls.add(new Ball(rand.nextInt(500), rand.nextInt(500), rand.nextInt(5), rand.nextInt(5), randColor()));
        }

        addMouseListener(this); // Para detectar cliques
        setBackground(Color.white);
    }

    // Método para desenhar as bolas no painel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Ball ball : balls) {
            g.setColor(ball.color);
            g.fillOval(ball.x, ball.y, 30, 30);
        }
    }

    // Método para detecção de clique nas bolas
    @Override
    public void mouseClicked(MouseEvent e) {
        for (Ball ball : balls) {
            if (e.getX() >= ball.x && e.getX() <= ball.x + 30 && e.getY() >= ball.y && e.getY() <= ball.y + 30) {
                // Simula a explosão (remover bola)
                balls.remove(ball);
                repaint();
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    // Método para gerar uma cor aleatória
    private Color randColor() {
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }
}

class Ball {
    int x, y, dx, dy;
    Color color;

    public Ball(int x, int y, int dx, int dy, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }
}
