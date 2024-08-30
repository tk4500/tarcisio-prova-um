package jv.triersistemas.prova_1.service;

import jv.triersistemas.prova_1.dto.ReservaDto;

public interface ReservaService {

	String getDisponibilidade(ReservaDto reserva);

	ReservaDto postReserva(ReservaDto reserva);

	ReservaDto putReserva(ReservaDto reserva);

}
