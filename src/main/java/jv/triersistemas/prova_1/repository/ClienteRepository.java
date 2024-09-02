package jv.triersistemas.prova_1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jv.triersistemas.prova_1.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

	List<ClienteEntity> findByNome(String nome);
	Optional<ClienteEntity> findByEmail(String email);
}
