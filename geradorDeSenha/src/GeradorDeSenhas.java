import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.security.SecureRandom;

public class GeradorDeSenhas extends JFrame {
    private JTextField comprimentoField;
    private JCheckBox maiusculasBox, minusculasBox, numerosBox, especiaisBox;
    private JButton gerarButton, copiarButton;
    private JLabel senhaLabel;
    private SecureRandom random;

    public GeradorDeSenhas() {
        setTitle("Gerador de Senhas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        random = new SecureRandom();

        // Painel de Entrada de Dados
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));

        // Comprimento da senha
        panel.add(new JLabel("Comprimento da Senha:"));
        comprimentoField = new JTextField();
        panel.add(comprimentoField);

        // Opções de caracteres
        maiusculasBox = new JCheckBox("Incluir Letras Maiúsculas");
        minusculasBox = new JCheckBox("Incluir Letras Minúsculas");
        numerosBox = new JCheckBox("Incluir Números");
        especiaisBox = new JCheckBox("Incluir Caracteres Especiais");

        panel.add(maiusculasBox);
        panel.add(minusculasBox);
        panel.add(numerosBox);
        panel.add(especiaisBox);

        // Botões e Exibição da senha gerada
        gerarButton = new JButton("Gerar Senha");
        copiarButton = new JButton("Copiar Senha");
        senhaLabel = new JLabel("Senha: ", SwingConstants.CENTER);
        senhaLabel.setFont(new Font("Arial", Font.BOLD, 16));

        gerarButton.addActionListener(e -> gerarSenha());
        copiarButton.addActionListener(e -> copiarSenha());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(gerarButton);
        buttonPanel.add(copiarButton);

        // Adicionando componentes ao frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(senhaLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void gerarSenha() {
        try {
            int comprimento = Integer.parseInt(comprimentoField.getText().trim());
            if (comprimento <= 0) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um comprimento válido (positivo).");
                return;
            }

            // Opções de caracteres
            String maiusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String minusculas = "abcdefghijklmnopqrstuvwxyz";
            String numeros = "0123456789";
            String especiais = "!@#$%^&*()-_=+[]{}|;:,.<>?";

            // Construir a lista de caracteres com base nas opções selecionadas
            StringBuilder caracteres = new StringBuilder();
            if (maiusculasBox.isSelected()) caracteres.append(maiusculas);
            if (minusculasBox.isSelected()) caracteres.append(minusculas);
            if (numerosBox.isSelected()) caracteres.append(numeros);
            if (especiaisBox.isSelected()) caracteres.append(especiais);

            if (caracteres.length() == 0) {
                JOptionPane.showMessageDialog(this, "Selecione pelo menos uma opção de caractere.");
                return;
            }

            // Geração da senha aleatória
            StringBuilder senha = new StringBuilder();
            for (int i = 0; i < comprimento; i++) {
                int index = random.nextInt(caracteres.length());
                senha.append(caracteres.charAt(index));
            }

            senhaLabel.setText("Senha: " + senha.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para o comprimento da senha.");
        }
    }

    private void copiarSenha() {
        String senha = senhaLabel.getText().replace("Senha: ", "");
        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma senha para copiar!");
            return;
        }

        StringSelection stringSelection = new StringSelection(senha);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        JOptionPane.showMessageDialog(this, "Senha copiada para a área de transferência!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GeradorDeSenhas::new);
    }
}
