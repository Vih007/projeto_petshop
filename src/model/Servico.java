package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Servico {
	private String IdServico;
	private String clienteID;
	private String petNome;
	private String tipoPet;
	private double preco;
	private String horario;
	private String data;
 	
 	public Servico() {
 		
 	}
 	
 	public Servico(String idBanho, String clienteID, String petNome, String tipoPet, double preco, String horario,
			String data) {
		super();
		IdServico = idBanho;
		this.clienteID = clienteID;
		this.petNome = petNome;
		this.tipoPet = tipoPet;
		this.preco = preco;
		this.horario = horario;
		this.data = data;
 	}
 	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getClienteID() {
 		return clienteID;
 	}
 	public void setClienteID(String clienteID) {
 		this.clienteID = clienteID;
 	}
 	public String getPetNome() {
 		return petNome;
 	}
 	public void setPetNome(String petNome) {
 		this.petNome = petNome;
 	}
 	public String getTipoPet() {
 		return tipoPet;
 	}
 	public void setTipoPet(String tipoPet) {
 		this.tipoPet = tipoPet;
 	}
 	public double getPreco() {
 		return preco;
 	}
 	public void setPreco(double preco) {
 		this.preco = preco;
 	}
 	public String getHorario() {
 		return horario;
 	}
 	public void setHorario(String horario) {
 		this.horario = horario;
 	}
 
 	public String getIdBanho() {
		return IdServico;
	}
	public void setIdBanho(String idBanho) {
		IdServico = idBanho;
	}

 	
 	 public String obterDescricaoServico() {
         return String.format("Cliente: %s\nPet: %s\nTipo de Pet: %s\nPorte: %s\nPreço: R$ %.2f\nHorário marcado: %s",
                              clienteID, petNome, tipoPet, preco, horario);
     }
 	
}


 

