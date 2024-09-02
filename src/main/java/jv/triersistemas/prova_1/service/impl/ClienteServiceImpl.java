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
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clRepository;

	@Override
	public List<ReservaDto> getReservas(Long id) throws IllegalArgumentException {
		var clEnt = findById(id);
		return clEnt.getReservas().stream().map(ReservaDto::new).toList();
	}

	@Override
	public ClienteDto postCliente(ClienteDto cliente) throws IllegalArgumentException {
		if (emailExists(cliente.getEmail())) {
			throw new IllegalArgumentException("Email já cadastrado");
		}
		var cliEnt = clRepository.save(new ClienteEntity(cliente));
		return new ClienteDto(cliEnt);

	}

	@Override
	public ClienteDto putCliente(ClienteDto cliente) throws IllegalArgumentException {
		if (!emailEqualsId(cliente.getEmail(), cliente.getId())) {
			throw new IllegalArgumentException("Email já cadastrado");
		}
		var cliEntAt = clRepository.save(atualizaCliente(cliente));
		return new ClienteDto(cliEntAt);
	}

	private ClienteEntity findById(Long id) throws IllegalArgumentException {
		return clRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Valor do id invalido"));
	}

	private boolean emailExists(String email) {
		return clRepository.findByEmail(email).isPresent();
	}

	private boolean emailEqualsId(String email, Long id) {
		var clOpt = clRepository.findByEmail(email);
		var clBool = clOpt.map(l -> l.getId().equals(id));
		return clBool.orElse(true);
	}

	private ClienteEntity atualizaCliente(ClienteDto cliente) {
		var cliEnt = findById(cliente.getId());
		cliEnt.atualizaCliente(cliente);
		return cliEnt;
	}

}
