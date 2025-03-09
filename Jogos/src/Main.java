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

        // Botão para retornar ao menu inicial
        JButton botaoRetornar = new JButton("Retornar ao Menu Inicial");
        botaoRetornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuInicial().setVisible(true); // Abre o menu inicial
                frame.dispose(); // Fecha a janela do jogo de Bolas
            }
        });
        
        frame.add(botaoRetornar, BorderLayout.SOUTH); // Adiciona o botão ao painel

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

        // Adiciona 6 bolas de cada cor
        addBallsOfColor(Color.RED, 6);
        addBallsOfColor(Color.BLUE, 6);
        addBallsOfColor(Color.GREEN, 6);
        addBallsOfColor(Color.YELLOW, 6);
        addBallsOfColor(Color.MAGENTA, 6);
        addBallsOfColor(Color.CYAN, 6);

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

    // Método para adicionar 6 bolas de uma cor específica
    private void addBallsOfColor(Color color, int count) {
        for (int i = 0; i < count; i++) {
            balls.add(new Ball(rand.nextInt(500), rand.nextInt(500), rand.nextInt(5), rand.nextInt(5), color));
        }
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
