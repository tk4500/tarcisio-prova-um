package jv.triersistemas.prova_1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jv.triersistemas.prova_1.entity.ReservaEntity;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
	List<ReservaEntity> findByNumeroMesa(Integer numeroMesa);
	
}
