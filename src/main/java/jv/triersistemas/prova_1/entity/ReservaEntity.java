package jv.triersistemas.prova_1.entity;

import java.time.LocalDate;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	@Column(nullable = false)
	private LocalDate dataReserva;
	@Column(nullable = false)
	private Integer numeroPessoas;
	@Column(nullable = false)
	private Integer numeroMesa;
	@Enumerated(EnumType.ORDINAL)
	private StatusEnum status;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "cliente_id", nullable = false)
	private ClienteEntity cliente;
	
	public ReservaEntity(ReservaDto dto) {
		dataReserva = dto.getDataReserva();
		numeroPessoas = dto.getNumeroPessoas();
		numeroMesa = dto.getNumeroMesa();
		status = Optional.ofNullable(dto.getStatus()).orElse(StatusEnum.FEITA);
		cliente = new ClienteEntity(dto.getCliente(), dto.getIdCliente());
	}
}
