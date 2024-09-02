package jv.triersistemas.prova_1.service;

import java.util.List;

import jv.triersistemas.prova_1.dto.ClienteDto;
import jv.triersistemas.prova_1.dto.ReservaDto;

public interface ClienteService {

	List<ReservaDto> getReservas(Long id);

	ClienteDto postCliente(ClienteDto cliente);

	ClienteDto putCliente(ClienteDto cliente);

}
