package jv.triersistemas.prova_1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jv.triersistemas.prova_1.dto.ClienteDto;
import jv.triersistemas.prova_1.dto.ReservaDto;
import jv.triersistemas.prova_1.entity.ClienteEntity;
import jv.triersistemas.prova_1.repository.ClienteRepository;
import jv.triersistemas.prova_1.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired 
	private ClienteRepository clRepository;
	
	@Override
	public List<ReservaDto> getReservas(ClienteDto cliente) throws IllegalArgumentException {
		if(cliente.getId() != null) {
			var clOpt = clRepository.findById(cliente.getId());
			var clEnt = clOpt.orElseThrow(()-> new IllegalArgumentException("Valor do id invalido"));
			return clEnt.getReservas().stream().map(ReservaDto::new).toList();
		}else {
			var clOpt = clRepository.findByNome(cliente.getNome());
			var clEnt = clOpt.orElseThrow(()-> new IllegalArgumentException("Nome do cliente invalido"));
			return clEnt.getReservas().stream().map(ReservaDto::new).toList();
		}
		
	}

	@Override
	public ClienteDto postCliente(ClienteDto cliente) {
		validaCliente(cliente);
		testeEmail(cliente.getEmail());
		var cliEnt = clRepository.save(new ClienteEntity(cliente));
		return new ClienteDto(cliEnt);
	}
	
	@Override
	public ClienteDto putCliente(ClienteDto cliente) {
		validaCliente(cliente);
		var cliEnt = clRepository.save(new ClienteEntity(cliente, cliente.getId()));
		return new ClienteDto(cliEnt);
	}
	
	private void validaCliente(ClienteDto cliente) {
		if (cliente.getNome() == null)
			throw new IllegalArgumentException("Valor do nome está nulo/em branco");
		if (cliente.getNome().isBlank())
			throw new IllegalArgumentException("Valor do nome está nulo/em branco");
		if (cliente.getEmail() == null)
			throw new IllegalArgumentException("Valor do email está nulo/em branco");
		if (cliente.getEmail().isBlank())
			throw new IllegalArgumentException("Valor do email está nulo/em branco");
	}
	
	private void testeEmail(String email) {
		if(!clRepository.findByEmail(email).isEmpty())
			throw new IllegalArgumentException("Valor do email já cadastrado");
	}



}
