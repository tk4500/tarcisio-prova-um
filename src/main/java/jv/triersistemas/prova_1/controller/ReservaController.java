package jv.triersistemas.prova_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jv.triersistemas.prova_1.dto.ReservaDto;
import jv.triersistemas.prova_1.service.ReservaService;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

	@Autowired
	ReservaService reservaService;
	
	@GetMapping("/disponivel")
	public ResponseEntity<String> getDisponibilidade(@RequestBody ReservaDto reserva) {
		try {
			return ResponseEntity.ok(reservaService.getDisponibilidade(reserva));
			}catch(Exception e) {
			return ResponseEntity.status(404).body(e.getMessage());
			}
		
	}
	
	@PostMapping
	public ResponseEntity<?> postReserva(@RequestBody ReservaDto reserva){
		try {
			return ResponseEntity.ok(reservaService.postReserva(reserva));
			}catch(Exception e) {
			return ResponseEntity.status(404).body(e.getMessage());
			}
	}
	
	@PutMapping("/status")
	public ResponseEntity<?> putReserva(@RequestBody ReservaDto reserva){
		try {
			return ResponseEntity.ok(reservaService.putReserva(reserva));
			}catch(Exception e) {
			return ResponseEntity.status(404).body(e.getMessage());
			}
	}
}
