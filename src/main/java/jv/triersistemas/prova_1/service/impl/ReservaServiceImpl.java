package jv.triersistemas.prova_1.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jv.triersistemas.prova_1.dto.ClienteDto;
import jv.triersistemas.prova_1.dto.ReservaDto;
import jv.triersistemas.prova_1.entity.ReservaEntity;
import jv.triersistemas.prova_1.enums.StatusEnum;
import jv.triersistemas.prova_1.repository.ClienteRepository;
import jv.triersistemas.prova_1.repository.ReservaRepository;
import jv.triersistemas.prova_1.service.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService{

	@Autowired
	ReservaRepository reRepository;
	
	@Autowired 
	private ClienteRepository clRepository;
	
	private void testeReserva(ReservaDto reserva) throws IllegalArgumentException {
		testeMesa(reserva.getNumeroMesa());
		testePessoas(reserva.getNumeroPessoas());
		testeData(reserva.getDataReserva());
	}
	
	private void testeMesa(Integer mesa) throws IllegalArgumentException {
		if(mesa<1 || mesa >20)
		throw new IllegalArgumentException("Numero da Mesa Invalido");
	}
	private void testePessoas(Integer numeroPessoas) throws IllegalArgumentException {
		if(numeroPessoas<1 || numeroPessoas>10)
			throw new IllegalArgumentException("Quantidade de Pessoas Invalido");
	}
	private void testeData(LocalDate dataReserva) throws IllegalArgumentException {
		if(!dataReserva.isAfter(LocalDate.now()))
			throw new IllegalArgumentException("Data Invalida");
	}
	private boolean isDisponivel(Integer mesa, LocalDate reserva) {
		var reList = reRepository.findByNumeroMesa(mesa);
		return reList.stream().anyMatch(r-> r.getDataReserva().equals(reserva));
	}
	
	private ClienteDto getCliente(ReservaDto reserva, Long idCliente) throws IllegalArgumentException {
		var clOpt =clRepository.findById(idCliente);
		var clEnt = clOpt.orElseThrow(()-> new IllegalArgumentException("Valor do id invalido"));
		return new ClienteDto(clEnt);
	}
	
	private ClienteDto getCliente(ReservaDto reserva, String nomeCliente) {
		var clOpt =clRepository.findByNome(nomeCliente).stream().findFirst();
		var clEnt = clOpt.orElseThrow(()-> new IllegalArgumentException("Valor do nome invalido"));
		return new ClienteDto(clEnt);
	}
	
	@Override
	public String getDisponibilidade(ReservaDto reserva) throws IllegalArgumentException{
		testeMesa(reserva.getNumeroMesa());
		if (isDisponivel(reserva.getNumeroMesa(),reserva.getDataReserva()))
		return "Mesa " + reserva.getNumeroMesa() + " já cadastrada para o dia";
		return "Mesa " + reserva.getNumeroMesa() + " ainda está disponivel";
	}
	
	@Override
	public ReservaDto postReserva(ReservaDto reserva) throws IllegalArgumentException{
		testeReserva(reserva);
		if (!isDisponivel(reserva.getNumeroMesa(),reserva.getDataReserva())) 
			throw new IllegalArgumentException("Mesa já reservada para a data");
		if(reserva.getIdCliente() != null)
			reserva.setCliente(getCliente(reserva, reserva.getIdCliente()));
		else
			reserva.setCliente(getCliente(reserva, reserva.getNomeCliente()));
		var resEnt = reRepository.save(new ReservaEntity(reserva));
		return new ReservaDto(resEnt, resEnt.getCliente().getNome());
	}

	@Override
	public ReservaDto putReserva(ReservaDto reserva) {
		var resOpt = reRepository.findById(reserva.getId());
		var resEnt = resOpt.orElseThrow(()-> new IllegalArgumentException("Valor do id invalido"));
		switch(reserva.getStatus()) {
		case CONCLUIDA:
			if(!resEnt.getDataReserva().isBefore(LocalDate.now())) {
				var resDto = new ReservaDto(resEnt);
				resDto.setStatus(StatusEnum.CONCLUIDA);
				var resEnt1 = reRepository.save(new ReservaEntity(resDto));
				return new ReservaDto(resEnt1, resEnt1.getCliente().getNome());
			}else {
				throw new IllegalArgumentException("Reserva não pode ser concluida, data atual ainda é anterior a data de reserva");
			}
		case CANCELADA:
			if(resEnt.getDataReserva().isBefore(LocalDate.now())) {
				var resDto = new ReservaDto(resEnt);
				resDto.setStatus(StatusEnum.CANCELADA);
				var resEnt1 = reRepository.save(new ReservaEntity(resDto));
				return new ReservaDto(resEnt1, resEnt1.getCliente().getNome());
			}else {
				throw new IllegalArgumentException("Reserva não pode ser cancelada, a data atual é maior que a data da reserva");
			}
		default:
			return null;
		}

	}






}
