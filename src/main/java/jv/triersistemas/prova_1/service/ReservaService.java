package jv.triersistemas.prova_1.service;

import java.time.LocalDate;

import jv.triersistemas.prova_1.dto.ReservaDto;
import jv.triersistemas.prova_1.enums.StatusEnum;

public interface ReservaService {

	String getDisponibilidade(Integer mesa, LocalDate data);

	ReservaDto postReserva(ReservaDto reserva);

	ReservaDto putReserva(Long id, StatusEnum status);

}
