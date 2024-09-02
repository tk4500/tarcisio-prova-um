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
		ClienteEntity clEnt;
		if(cliente.getId() == null) 
			clEnt = findByNome(cliente);
		else 
			clEnt = findById(cliente);
		return clEnt.getReservas().stream().map(ReservaDto::new).toList();
	}

	@Override
	public ClienteDto postCliente(ClienteDto cliente) throws IllegalArgumentException{
		if(emailExists(cliente.getEmail())) 
			throw new IllegalArgumentException("Email já cadastrado");
		var cliEnt = clRepository.save(new ClienteEntity(cliente));
		return new ClienteDto(cliEnt);
		
	}
	
	@Override
	public ClienteDto putCliente(ClienteDto cliente) {
		if (!emailEqualsId(cliente.getEmail(), cliente.getId()))
			throw new IllegalArgumentException("Email já cadastrado");
		var cliEnt = clRepository.save(new ClienteEntity(cliente, cliente.getId()));
		return new ClienteDto(cliEnt);
	}
	
	private ClienteEntity findById(ClienteDto cliente) throws IllegalArgumentException {
		return clRepository.findById(cliente.getId()).orElseThrow(()-> new IllegalArgumentException("Valor do id invalido"));
	}
	private ClienteEntity findByNome(ClienteDto cliente) throws IllegalArgumentException {
		return clRepository.findByNome(cliente.getNome()).stream().findFirst().orElseThrow(()-> new IllegalArgumentException("Valor do id invalido"));
	}
	
	private boolean emailExists(String email) {
		return clRepository.findByEmail(email).isPresent();
	}
	private boolean emailEqualsId(String email, Long id) {
		var clOpt = clRepository.findByEmail(email);
		var clBool = clOpt.map(l-> l.getId().equals(id));
		return clBool.orElse(true);
	}



}
