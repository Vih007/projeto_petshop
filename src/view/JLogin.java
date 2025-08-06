package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Criptografia;

public class JLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        	System.err.println(ex);
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JLogin frame = new JLogin();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 934, 521);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(226, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
            BufferedImage imgOriginal = ImageIO.read(new File("C:\\Users\\melov\\Downloads\\veterinario.png"));

            // Redimensionar a imagem com Graphics2D
            Image imgRedimensionada = imgOriginal.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            BufferedImage imgBuffer = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = imgBuffer.createGraphics();
            g2d.drawImage(imgRedimensionada, 0, 0, null);
            g2d.dispose();

            ImageIcon resizedIcon = new ImageIcon(imgBuffer);
            JLabel lblImagem = new JLabel(resizedIcon);
            lblImagem.setBounds(398, 16, 100, 100);
            getContentPane().add(lblImagem);

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(141, 222, 206));
		panel.setBounds(319, 126, 265, 243);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuário");
		lblNewLabel.setBounds(59, 67, 45, 13);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		panel.add(lblNewLabel);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(59, 80, 160, 30);
		panel.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(59, 129, 45, 13);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.setBounds(97, 197, 85, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Criptografia criptografia = new Criptografia(passwordField.getText(), Criptografia.MD5);
				System.out.println(criptografia.criptografar());
				if(textFieldUsuario.getText()!=null && 
						!textFieldUsuario.getText().isEmpty() &&
						passwordField.getText()!=null &&
						!passwordField.getText().isEmpty()) {
					if(criptografia.criptografar().equals("827CCB0EEA8A706C4C34A16891F84E7B")) {
					JOptionPane.showMessageDialog(btnNewButton, "Informações válidas!");
					dispose();
					JPrincipal jPrincipal = new JPrincipal();
					jPrincipal.setLocationRelativeTo(jPrincipal);
					jPrincipal.setVisible(true);
					}
					
				}else {
					JOptionPane.showMessageDialog(btnNewButton, "Verifique as informações!", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBackground(new Color(1, 78, 66));
		btnNewButton.setForeground(Color.WHITE);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Bem-vinde");
		lblNewLabel_2.setBounds(79, 30, 119, 13);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel.add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(59, 143, 160, 30);
		panel.add(passwordField);
	}
}
