package jv.triersistemas.prova_1.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jv.triersistemas.prova_1.dto.ReservaDto;
import jv.triersistemas.prova_1.entity.ClienteEntity;
import jv.triersistemas.prova_1.entity.ReservaEntity;
import jv.triersistemas.prova_1.enums.StatusEnum;
import jv.triersistemas.prova_1.repository.ClienteRepository;
import jv.triersistemas.prova_1.repository.ReservaRepository;
import jv.triersistemas.prova_1.service.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

	@Autowired
	ReservaRepository reRepository;

	@Autowired
	private ClienteRepository clRepository;

	@Override
	public String getDisponibilidade(Integer mesa, LocalDate data) throws IllegalArgumentException {
		testeMesa(mesa);
		if (!isDisponivel(mesa, data)) {
			return "Mesa " + mesa + " já cadastrada para o dia";
		}
		return "Mesa " + mesa + " ainda está disponivel";
	}

	@Override
	public ReservaDto postReserva(ReservaDto reserva) throws IllegalArgumentException {
		testeReserva(reserva);
		if (!isDisponivel(reserva.getNumeroMesa(), reserva.getDataReserva())) {
			throw new IllegalArgumentException("Mesa já reservada para a data");
		}
		var resEnt = new ReservaEntity(reserva);
		resEnt.atualizaCliente(getCliente(reserva, reserva.getIdCliente()));
		return salvar(resEnt);
	}

	@Override
	public ReservaDto putReserva(Long id, StatusEnum status) throws IllegalArgumentException {
		switch (status) {
		case CONCLUIDA:
			return isConcluida(id);
		case CANCELADA:
			return isCancelada(id);
		default:
			return null;
		}

	}

	private void testeReserva(ReservaDto reserva) throws IllegalArgumentException {
		testeMesa(reserva.getNumeroMesa());
		testePessoas(reserva.getNumeroPessoas());
		testeData(reserva.getDataReserva());
	}

	private void testeMesa(Integer mesa) throws IllegalArgumentException {
		if (mesa < 1 || mesa > 20)
			throw new IllegalArgumentException("Numero da Mesa Invalido");
	}

	private void testePessoas(Integer numeroPessoas) throws IllegalArgumentException {
		if (numeroPessoas < 1 || numeroPessoas > 10)
			throw new IllegalArgumentException("Quantidade de Pessoas Invalido");
	}

	private void testeData(LocalDate dataReserva) throws IllegalArgumentException {
		if (dataReserva.isBefore(LocalDate.now()))
			throw new IllegalArgumentException("Data Invalida");
	}

	private boolean isDisponivel(Integer mesa, LocalDate reserva) {
		var reList = reRepository.existsByNumeroMesaAndDataReserva(mesa, reserva);
		return !reList;
	}

	private ClienteEntity getCliente(ReservaDto reserva, Long idCliente) throws IllegalArgumentException {
		var clOpt = clRepository.findById(idCliente);
		var clEnt = clOpt.orElseThrow(() -> new IllegalArgumentException("Valor do id invalido"));
		return clEnt;
	}

	private ReservaDto isConcluida(Long id) throws IllegalArgumentException {
		var resEnt = findById(id);
		if (reservaIsBefore(resEnt)) {
			throw new IllegalArgumentException(
					"Reserva não pode ser concluida, data atual ainda é anterior a data de reserva");
		}
		resEnt.alteraStatus(StatusEnum.CONCLUIDA);
		return salvar(resEnt);

	}

	private ReservaDto isCancelada(Long id) throws IllegalArgumentException {
		var resEnt = findById(id);
		if (!reservaIsBefore(resEnt)) {
			throw new IllegalArgumentException(
					"Reserva não pode ser cancelada, a data atual é maior que a data da reserva");
		}
		resEnt.alteraStatus(StatusEnum.CANCELADA);
		return salvar(resEnt);
		
	}

	private ReservaEntity findById(Long id) throws IllegalArgumentException {
		var resOpt = reRepository.findById(id);
		return resOpt.orElseThrow(() -> new IllegalArgumentException("Valor do id invalido"));
	}

	private boolean reservaIsBefore(ReservaEntity reserva) {
		return reserva.getDataReserva().isBefore(LocalDate.now());
	}
	
	private ReservaDto salvar(ReservaEntity resEnt) {
		var resEnt1 = reRepository.save(resEnt);
		var resDto = new ReservaDto(resEnt1);
		resDto.setNomeCliente(resEnt1.getCliente().getNome());
		resDto.setIdCliente(resEnt1.getCliente().getId());
		return resDto;
	}

}
