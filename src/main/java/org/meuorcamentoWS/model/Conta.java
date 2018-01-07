package org.meuorcamentoWS.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="conta")
public class Conta {

	private int id;
	private String nome;
	private Double valor;
	private LocalDate dataPagamento = LocalDate.now();
	private boolean estado;
	private boolean repetir;
	private TipoConta tipoConta;
	private String chaveGrupoContas;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public LocalDate getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public boolean isRepetir() {
		return repetir;
	}
	public void setRepetir(boolean repetir) {
		this.repetir = repetir;
	}
	public TipoConta getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}
	public String getChaveGrupoContas() {
		return chaveGrupoContas;
	}
	public void setChaveGrupoContas(String chaveGrupoContas) {
		this.chaveGrupoContas = chaveGrupoContas;
	}
	
	
	
}
