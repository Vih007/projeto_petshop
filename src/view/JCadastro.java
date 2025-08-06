package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dao.Dao;
import model.Cliente;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNome;
	private JTextField textFieldCpfCnpj;
	private JTextField textFieldTelefone;
	private JLabel lblNewLabel_3;
	private JTextField textFieldEmail;
	private JLabel lblNewLabel_4;
	private JPanel contentPane;
	private JTextArea textAreaEndereco;

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
						JCadastro frame = new JCadastro(null, null);
						frame.setLocationRelativeTo(frame);
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
		public JCadastro(Cliente clienteSelecionado, JPrincipal jPrincipal) {
			Dao dao = new Dao();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setForeground(new Color(0, 0, 0));
			contentPane.setBackground(new Color(226, 255, 255));

			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Nome");
			lblNewLabel.setBounds(25, 21, 71, 13);
			contentPane.add(lblNewLabel);
			
			textFieldNome = new JTextField();
			textFieldNome.setBounds(25, 37, 389, 28);
			contentPane.add(textFieldNome);
			textFieldNome.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("CPF/CNPJ");
			lblNewLabel_1.setBounds(25, 75, 92, 13);
			contentPane.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("Telefone ");
			lblNewLabel_2.setBounds(246, 75, 71, 13);
			contentPane.add(lblNewLabel_2);
			
			textFieldCpfCnpj = new JTextField();
			textFieldCpfCnpj.setBounds(25, 89, 203, 28);
			contentPane.add(textFieldCpfCnpj);
			textFieldCpfCnpj.setColumns(10);
			
			textFieldTelefone = new JTextField();
			textFieldTelefone.setBounds(246, 89, 168, 28);
			contentPane.add(textFieldTelefone);
			textFieldTelefone.setColumns(10);
			
			lblNewLabel_3 = new JLabel("E-mail");
			lblNewLabel_3.setBounds(25, 125, 71, 13);
			contentPane.add(lblNewLabel_3);
			
			textFieldEmail = new JTextField();
			textFieldEmail.setBounds(25, 138, 389, 28);
			contentPane.add(textFieldEmail);
			textFieldEmail.setColumns(10);
			
			lblNewLabel_4 = new JLabel("Endereço");
			lblNewLabel_4.setBounds(25, 176, 71, 13);
			contentPane.add(lblNewLabel_4);
			
			textAreaEndereco = new JTextArea();
			textAreaEndereco.setBorder(new LineBorder(new Color(0, 0, 0)));
			textAreaEndereco.setBounds(25, 194, 389, 28);
			contentPane.add(textAreaEndereco);
			
			JButton btnNewButton = new JButton(clienteSelecionado==null?"Incluir":"Alterar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Cliente cliente = new Cliente(null, textFieldNome.getText(),textFieldCpfCnpj.getText(),
							textFieldEmail.getText(), textFieldTelefone.getText(), textAreaEndereco.getText());
					
			        if(clienteSelecionado==null) {
			        	if (!"".equalsIgnoreCase(textFieldNome.getText()) && !"".equalsIgnoreCase(textFieldCpfCnpj.getText())) {
			        		dao.cadastrarCliente(cliente);
							abrirTelaPrincipal(jPrincipal);
			        	}else {
			        		JOptionPane.showMessageDialog(null, "Confira os campos Nome e CPF/CNPJ");
			        	}
			
			        }else {
			        	if (!"".equalsIgnoreCase(textFieldNome.getText()) && !"".equalsIgnoreCase(textFieldCpfCnpj.getText())) {
			        		dao.alterarCliente(clienteSelecionado.getId(), cliente);
							abrirTelaPrincipal(jPrincipal);
			        	}else {
			        		JOptionPane.showMessageDialog(null, "Confira os campos Nome e CPF/CNPJ");
			        	}
						
			        }
					}
			});
			btnNewButton.setBounds(329, 232, 85, 21);
			contentPane.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("Excluir");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				dao.excluirCliente(clienteSelecionado.getId());
				abrirTelaPrincipal(jPrincipal);
				}
			});
			btnNewButton_1.setForeground(new Color(255, 255, 255));
			btnNewButton_1.setBackground(new Color(255, 0, 0));
			btnNewButton_1.setBounds(25, 232, 85, 21);
			btnNewButton_1.setVisible(false);
			contentPane.add(btnNewButton_1);
			
			if(clienteSelecionado!=null) {
			preencherCampos(clienteSelecionado);
			btnNewButton_1.setVisible(true);
			}
			
		}
		
		private void preencherCampos(Cliente clienteSelecionado) {
			textFieldNome.setText(clienteSelecionado.getNome());
			textFieldCpfCnpj.setText(clienteSelecionado.getCpfCnpj());
			textFieldEmail.setText(clienteSelecionado.getEmail());
			textFieldTelefone.setText(clienteSelecionado.getTelefone());
			textAreaEndereco.setText(clienteSelecionado.getEndereco());
		}
		
		private void abrirTelaPrincipal(JPrincipal jPrincipal) {
			jPrincipal.dispose();
			dispose();
			jPrincipal = new JPrincipal();
			jPrincipal.setLocationRelativeTo(jPrincipal);
			jPrincipal.setVisible(true);
		}
}

