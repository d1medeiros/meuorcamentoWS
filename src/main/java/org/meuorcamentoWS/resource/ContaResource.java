package org.meuorcamentoWS.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.meuorcamentoWS.dao.ContaDao;
import org.meuorcamentoWS.model.Conta;
import org.meuorcamentoWS.model.TipoConta;

@Path("conta")
@Produces({MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_JSON })
public class ContaResource {
	
	private ContaDao dao;
	
	public ContaResource() {
		dao = ContaDao.generateDAO();
	}
	
	@GET
	@Path("/gastos")
	public Response getContaGastos() {
		List<Conta> conta = dao.listaPorTipoConta(TipoConta.GASTOS);
		return Response.ok(conta).build();
	}
	
	@GET
	@Path("/ganho")
	public Response getContaGanho() {
		List<Conta> conta = dao.listaPorTipoConta(TipoConta.GANHO);
		return Response.ok(conta).build();
	}

	@GET
	@Path("/atual")
	public List<Conta> getContasAtual() {
		dao = ContaDao.generateDAO();
		return dao.listaMesAtual();
	}

	@GET
	@Path("{id}")
	public Conta getConta(@PathParam("id") int id) {
		dao = ContaDao.generateDAO();
		System.out.println("ContaResource getConta id: " + id);
		return dao.getContaById(id);
	}

	@POST
	@Path("/remove/{id}")
	public Response removeConta(@PathParam("id") int id) {
		dao.remove(id);
		return Response.noContent().build();
	}
	
	@GET
	@Path("/mesano/{mesAno}")
	public List<Conta> getContasPorNumero(@PathParam("mesAno") String mesAno) {
		int mes = Integer.valueOf(mesAno.split("-")[0]);
		int ano = Integer.valueOf(mesAno.split("-")[1]);
		return dao.listaMesPorNumero(mes, ano);
	}
	
	@GET
	@Path("/seisMeses")
	public List<Conta> getContasAll() {
		return dao.listaTodos();
	}
	
	@POST
	@Path("/salva")
	@Produces({MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON })
	public Response salva(Conta conta) {
		
		dao.inserir(conta);
		
		return Response.noContent().build();
	}
	
	@POST
	@Path("/altera")
	@Produces({MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON })
	public Response altera(Conta conta) {
		
		dao.alterar(conta);
		return Response.noContent().build();
	}


}
