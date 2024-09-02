package jv.triersistemas.prova_1.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jv.triersistemas.prova_1.dto.ClienteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "cliente")
public class ClienteEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false, unique = true)
	private String email;
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.DETACH)
	private List<ReservaEntity> reservas;
	
	public ClienteEntity(ClienteDto dto) {
		nome = dto.getNome();
		email = dto.getEmail();
	}
	public ClienteEntity(ClienteDto dto, Long idCliente) {
		id = idCliente;
		nome = dto.getNome();
		email = dto.getEmail();
	}
}
