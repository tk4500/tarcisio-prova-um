package jv.triersistemas.prova_1.dto;

import java.time.LocalDate;

import jv.triersistemas.prova_1.entity.ReservaEntity;
import jv.triersistemas.prova_1.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservaDto {
	private Long id;
	private LocalDate dataReserva;
	private Integer numeroPessoas;
	private Integer numeroMesa;
	private StatusEnum status;
	private ClienteDto cliente;
	private String nomeCliente;
	private Long idCliente;
	
	public ReservaDto(ReservaEntity entity) {
		id = entity.getId();
		dataReserva = entity.getDataReserva();
		numeroPessoas = entity.getNumeroPessoas();
		numeroMesa = entity.getNumeroMesa();
		status = entity.getStatus();
		cliente = new ClienteDto(entity.getCliente());
	}
	public ReservaDto(ReservaEntity entity, String nome) {
		id = entity.getId();
		dataReserva = entity.getDataReserva();
		numeroPessoas = entity.getNumeroPessoas();
		numeroMesa = entity.getNumeroMesa();
		status = entity.getStatus();
		nomeCliente = nome;
	}
	public ReservaDto(ReservaEntity entity, Long idCl) {
		id = entity.getId();
		dataReserva = entity.getDataReserva();
		numeroPessoas = entity.getNumeroPessoas();
		numeroMesa = entity.getNumeroMesa();
		status = entity.getStatus();
		idCliente = idCl;
	}
}
