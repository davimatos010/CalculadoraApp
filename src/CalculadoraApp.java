import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CalculadoraApp extends JFrame {
    private JTextField display;
    private String operador = "";
    private double num1 = 0;
    private boolean novoNumero = true;
    private final ArrayList<String> historico = new ArrayList<>();

    public CalculadoraApp() {
        setTitle("CalculadoraApp");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Cores
        Color fundoCinza = Color.decode("#2c2c2c");
        Color azul = Color.decode("#3498db");
        Color cinzaClaro = Color.decode("#bdc3c7");

        // Fundo
        getContentPane().setBackground(fundoCinza);

        // Display
        display = new JTextField();
        display.setFont(new Font("Consolas", Font.BOLD, 28));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBackground(fundoCinza);
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(5, 4, 5, 5));
        painelBotoes.setBackground(fundoCinza);

        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "CE", "Hist", ""
        };

        for (String texto : botoes) {
            if (texto.equals("")) {
                painelBotoes.add(new JLabel());
                continue;
            }

            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 20));
            botao.setForeground(Color.WHITE);
            botao.setFocusPainted(false);
            botao.setBorderPainted(false);
            botao.setOpaque(true);

            // Definindo a cor do botão
            if (texto.matches("\\d") || texto.equals(".")) {
                botao.setBackground(azul); // Números
            } else {
                botao.setBackground(cinzaClaro); // Símbolos e funções
            }

            painelBotoes.add(botao);
            botao.addActionListener(e -> processarComando(e.getActionCommand()));
        }

        add(painelBotoes, BorderLayout.CENTER);
        setVisible(true);
    }

    private void processarComando(String comando) {
        switch (comando) {
            case "C":
                display.setText("");
                operador = "";
                num1 = 0;
                novoNumero = true;
                break;
            case "CE":
                String textoAtual = display.getText();
                if (!textoAtual.isEmpty()) {
                    display.setText(textoAtual.substring(0, textoAtual.length() - 1));
                }
                break;
            case "+", "-", "*", "/":
                if (!display.getText().isEmpty()) {
                    num1 = Double.parseDouble(display.getText());
                    operador = comando;
                    novoNumero = true;
                }
                break;
            case "=":
                if (!display.getText().isEmpty() && !operador.isEmpty()) {
                    double num2 = Double.parseDouble(display.getText());
                    double resultado = calcular(num1, num2, operador);
                    String expressao = num1 + " " + operador + " " + num2 + " = " + resultado;
                    historico.add(expressao);
                    display.setText(String.valueOf(resultado));
                    operador = "";
                    novoNumero = true;
                }
                break;
            case "Hist":
                mostrarHistorico();
                break;
            case ".":
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
                break;
            default:
                if (novoNumero) {
                    display.setText(comando);
                    novoNumero = false;
                } else {
                    display.setText(display.getText() + comando);
                }
                break;
        }
    }

    private double calcular(double n1, double n2, String op) {
        return switch (op) {
            case "+" -> n1 + n2;
            case "-" -> n1 - n2;
            case "*" -> n1 * n2;
            case "/" -> n2 == 0 ? 0 : n1 / n2;
            default -> 0;
        };
    }

    private void mostrarHistorico() {
        if (historico.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma operação ainda.");
        } else {
            StringBuilder sb = new StringBuilder("Histórico de Operações:\n");
            for (String entrada : historico) {
                sb.append(entrada).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraApp::new);
    }
}

