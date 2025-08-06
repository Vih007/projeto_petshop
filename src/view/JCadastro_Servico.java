package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.Dao;
import model.Cliente;
import model.Servico;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class JCadastro_Servico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_id;
	private JTextField textField_nome_pet;
	private JTextField textField_tipo_Pet;
	private JTextField textField_Preco;
	private JTextField textField_horario;
	private JTextField textField_data;
	private JButton btnNewButton;

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
					JCadastro_Servico frame = new JCadastro_Servico(null, null);
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
	public JCadastro_Servico(Servico servicoSelecionado, JServico jServico) {
		setBackground(new Color(255, 255, 255));
		Dao dao = new Dao();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 336);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(226, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID_Cliente");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(31, 30, 79, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nome_Pet");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(202, 30, 67, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_tipo_pet = new JLabel("Tipo_Pet");
		lblNewLabel_tipo_pet.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_tipo_pet.setBounds(31, 101, 79, 13);
		contentPane.add(lblNewLabel_tipo_pet);
		
		JLabel lblNewLabel_preco = new JLabel("Preço");
		lblNewLabel_preco.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_preco.setBounds(202, 101, 45, 13);
		contentPane.add(lblNewLabel_preco);
		
		JLabel lblNewLabel_horario = new JLabel("Horário");
		lblNewLabel_horario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_horario.setBounds(31, 176, 45, 13);
		contentPane.add(lblNewLabel_horario);
		
		JLabel lblNewLabel_data = new JLabel("Data");
		lblNewLabel_data.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_data.setBounds(202, 176, 45, 13);
		contentPane.add(lblNewLabel_data);
		
		textField_id = new JTextField();
		textField_id.setBounds(31, 53, 125, 30);
		contentPane.add(textField_id);
		textField_id.setColumns(10);
		
		textField_nome_pet = new JTextField();
		textField_nome_pet.setBounds(202, 53, 163, 30);
		contentPane.add(textField_nome_pet);
		textField_nome_pet.setColumns(10);
		
		textField_tipo_Pet = new JTextField();
		textField_tipo_Pet.setBounds(31, 116, 120, 30);
		contentPane.add(textField_tipo_Pet);
		textField_tipo_Pet.setColumns(10);
		
		textField_Preco = new JTextField();
		textField_Preco.setBounds(201, 116, 120, 30);
		contentPane.add(textField_Preco);
		textField_Preco.setColumns(10);
		
		textField_horario = new JTextField();
		textField_horario.setBounds(31, 193, 120, 30);
		contentPane.add(textField_horario);
		textField_horario.setColumns(10);
		
		textField_data = new JTextField();
		textField_data.setBounds(201, 193, 120, 30);
		contentPane.add(textField_data);
		textField_data.setColumns(10);
		
		JButton btnNewButton_marcar = new JButton(servicoSelecionado==null?"Marcar":"Alterar");
		btnNewButton_marcar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Servico servico = new Servico(null, textField_id.getText(), textField_nome_pet.getText(), textField_tipo_Pet.getText(), 
						Double.parseDouble(textField_Preco.getText()), textField_horario.getText(), textField_data.getText());
				
				if(servicoSelecionado==null) {
		        	if (!"".equalsIgnoreCase(textField_id.getText()) && !"".equalsIgnoreCase(textField_nome_pet.getText())) {
		        		dao.inserirServico(servico);
						abrirTelaServico(jServico);
		        	}else {
		        		JOptionPane.showMessageDialog(null, "Confira os campos ID_Cliente e Nome_Pet");
		        	}
		
		        }else {
		        	if (!"".equalsIgnoreCase(textField_id.getText()) && !"".equalsIgnoreCase(textField_nome_pet.getText())) {
		        		dao.inserirServico(servico);
		        		gerarComprovante(servico); 
						abrirTelaServico(jServico);
		        	}else {
		        		JOptionPane.showMessageDialog(null, "Confira os campos ID_Cliente e Nome_Pet");
		        	}
					
		        }
				}
			}
		);
		btnNewButton_marcar.setBounds(280, 259, 85, 25);
		contentPane.add(btnNewButton_marcar);
		
		btnNewButton = new JButton("Desmarcar");
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dao.excluirServico(servicoSelecionado.getIdBanho());
				abrirTelaServico(jServico);
			}
		});
		btnNewButton.setBounds(31, 259, 100, 25);
		btnNewButton.setVisible(false);
		contentPane.add(btnNewButton);
		
		
		if(servicoSelecionado!=null) {
			preencherCampos(servicoSelecionado);
			btnNewButton.setVisible(true);
			}

		
	}
	
	private void preencherCampos(Servico servicoSelecionado) {
		textField_id.setText(servicoSelecionado.getClienteID());
		textField_nome_pet.setText(servicoSelecionado.getPetNome());
		textField_tipo_Pet.setText(servicoSelecionado.getTipoPet());
		textField_Preco.setText(String.valueOf(servicoSelecionado.getPreco()));
		textField_horario.setText(servicoSelecionado.getHorario());
		textField_data.setText(servicoSelecionado.getData());
	}
	
	private void abrirTelaServico(JServico jServico) {
		jServico.dispose();
		dispose();
		jServico = new JServico();
		jServico.setLocationRelativeTo(jServico);
		jServico.setVisible(true);
	}
	
	private void gerarComprovante(Servico servico) {
        // Caminho do arquivo para salvar o comprovante
        String caminhoArquivo = "comprovantes/Comprovante_" + servico.getClienteID() + ".txt";
        
        // Criação do diretório caso não exista
        File diretorio = new File("comprovantes");
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }

        // Gerar o conteúdo do comprovante
        String conteudoComprovante = "Comprovante de Serviço\n\n" +
                                     "ID Cliente: " + servico.getClienteID() + "\n" +
                                     "Nome do Pet: " + servico.getPetNome() + "\n" +
                                     "Tipo de Pet: " + servico.getTipoPet() + "\n" +
                                     "Preço: R$ " + servico.getPreco() + "\n" +
                                     "Data: " + servico.getData() + "\n" +
                                     "Horário: " + servico.getHorario() + "\n";

        // Salvar o comprovante em um arquivo de texto
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write(conteudoComprovante);
            JOptionPane.showMessageDialog(null, "Comprovante gerado com sucesso em: " + caminhoArquivo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o comprovante.");
            e.printStackTrace();
        }
    }
}
