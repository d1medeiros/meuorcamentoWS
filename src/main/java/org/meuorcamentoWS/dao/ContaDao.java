package org.meuorcamentoWS.dao;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.meuorcamentoWS.model.Conta;
import org.meuorcamentoWS.model.TipoConta;
import org.meuorcamentoWS.util.TokenGenerator;

public class ContaDao {

	private HashSet<Conta> db = new HashSet<>();
	
	private static ContaDao dao = new ContaDao();
	
	private ContaDao() {
		
		
		
		String generateToken = TokenGenerator.generateToken("A");
		db.add(gera(1, "vivo", 100.12, LocalDate.now(), false, false, TipoConta.GASTOS, generateToken));
		db.add(gera(2, "claro", 100.12, LocalDate.now(), false, false, TipoConta.GASTOS, TokenGenerator.generateToken("A")));
		db.add(gera(3, "oi", 100.12, LocalDate.now(), false, false, TipoConta.GASTOS, TokenGenerator.generateToken("A")));
		db.add(gera(4, "itau", 100.12, LocalDate.now(), false, false, TipoConta.GASTOS, TokenGenerator.generateToken("A")));
		db.add(gera(5, "carro", 100.12, LocalDate.now(), false, false, TipoConta.GASTOS, TokenGenerator.generateToken("A")));
		db.add(gera(6, "vaga", 100.12, LocalDate.now(), false, false, TipoConta.GASTOS, TokenGenerator.generateToken("A")));
		db.add(gera(7, "seguro", 100.12, LocalDate.now(), false, false, TipoConta.GASTOS, TokenGenerator.generateToken("A")));
		db.add(gera(8, "presenteas", 100.12, LocalDate.now(), false, false, TipoConta.GASTOS, TokenGenerator.generateToken("A")));
		db.add(gera(9, "iptu", 100.12, LocalDate.now(), false, false, TipoConta.GASTOS, TokenGenerator.generateToken("A")));
		db.add(gera(10, "salario", 10000.12, LocalDate.now(), false, false, TipoConta.GANHO, TokenGenerator.generateToken("A")));
		db.add(gera(11, "vivo", 100.12, LocalDate.now().plusMonths(1), false, false, TipoConta.GASTOS, generateToken));
		db.add(gera(12, "vivo", 100.12, LocalDate.now().plusMonths(2), false, false, TipoConta.GASTOS, generateToken));
		
		System.out.println("construtor");
	}
	
	Conta gera(int id, String nome, Double valor, LocalDate dataPagamento, boolean estado, boolean repetir, TipoConta tipoConta, String chaveGrupoContas) {
		Conta conta = new Conta();
		conta.setId(id);
		conta.setNome(nome);
		conta.setValor(valor);
		conta.setDataPagamento(dataPagamento);
		conta.setEstado(estado);
		conta.setRepetir(repetir);
		conta.setTipoConta(tipoConta);
		conta.setChaveGrupoContas(chaveGrupoContas);
		return conta;
	}
	
	public static ContaDao generateDAO() {
		return dao;
	}
	
	private Conta geraContasParaDozeMeses(int plusMonth, Conta conta) {
		Conta contaFutura = new Conta();
		contaFutura.setNome(conta.getNome());
		contaFutura.setValor(conta.getValor());
		contaFutura.setDataPagamento(conta.getDataPagamento().plusMonths(plusMonth));
		contaFutura.setEstado(conta.isEstado());
		contaFutura.setTipoConta(conta.getTipoConta());
		contaFutura.setChaveGrupoContas(conta.getChaveGrupoContas());
		return contaFutura;
	}
	
	public void inserir(Conta conta) {
		conta.setChaveGrupoContas(TokenGenerator.generateToken(conta.getNome()));
		
		if(conta.isRepetir()) {
			for(int i=0;i<13;i++) {
				db.add(geraContasParaDozeMeses(i, conta));
			}
		}else {
			db.add(conta);
		}

	}
	
	public void alterar(Conta conta) {
		List<Conta> mesesExistentes = mesesExistentes(conta);
		int count = 0;
		if(conta.isRepetir()) {
			for (Conta c : mesesExistentes) {
				db.add(geraContasParaDozeMeses(count++, c));
			}

		}else {
			db.add(conta);
		}
		
	}
	
	public void remove(int id) {
		db.stream()
			.filter(c -> c.getId() != id)
			.collect(Collectors.toSet());
	}
	
	public Conta getContaById(int id) {
		System.out.println("DAO getConta db-> " + db.size());
		return db.stream().filter(c -> c.getId() == id).collect(Collectors.toList()).get(0);
	}
	
	/**
	 * O limite e de 6 meses
	 * @return lista de contas
	 */
	public List<Conta> listaTodos() {
		List<Conta> contas = db.stream().collect(Collectors.toList());
		return contas;
	}
	
	/**
	 * O limite e de 6 meses
	 * @return lista de contas
	 */
	public List<Conta> listaPorTipoConta(TipoConta tipoConta) {
		List<Conta> contas = null;
		return contas;
	}
	
	public List<Conta> listaMesAtual() {
		
		List<Conta> todos = listaTodos();
		return todos.stream()
					.filter(conta -> conta.getDataPagamento().getMonth() == LocalDate.now().getMonth())
					.collect(Collectors.toList());
		
		
	}
	
	public List<Conta> listaMesPorNumero(int mes, int ano) {
		
		List<Conta> todos = listaTodos();
		return todos.stream()
				.filter(conta -> conta.getDataPagamento().getMonth() == LocalDate.now().withMonth(mes).getMonth())
				.filter(conta -> conta.getDataPagamento().getYear() == LocalDate.now().withYear(ano).getYear())
				.collect(Collectors.toList());
		
		
	}

	private LocalDate dataParaSeisMeses() {
		return LocalDate.now().plusMonths(6).with(TemporalAdjusters.lastDayOfMonth());
	}

	public List<Conta> mesesExistentes(Conta conta) {
		List<Conta> contas = db.stream().filter(c -> c.getChaveGrupoContas().equals(conta.getChaveGrupoContas())).collect(Collectors.toList());
		return contas;
	}
	
}
