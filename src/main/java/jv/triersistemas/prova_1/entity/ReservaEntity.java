package jv.triersistemas.prova_1.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jv.triersistemas.prova_1.dto.ReservaDto;
import jv.triersistemas.prova_1.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "reserva")
public class ReservaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDate dataReserva;
	private Integer numeroPessoas;
	private Integer numeroMesa;
	@Enumerated(EnumType.ORDINAL)
	private StatusEnum status = StatusEnum.FEITA;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "cliente_id", nullable = false)
	private ClienteEntity cliente;
	
	public ReservaEntity(ReservaDto dto) {
		dataReserva = dto.getDataReserva();
		numeroPessoas = dto.getNumeroPessoas();
		numeroMesa = dto.getNumeroMesa();
		status = dto.getStatus();
		cliente = new ClienteEntity(dto.getCliente(), dto.getIdCliente());
	}
}
