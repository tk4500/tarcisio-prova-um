package jv.triersistemas.prova_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jv.triersistemas.prova_1.dto.ClienteDto;
import jv.triersistemas.prova_1.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@GetMapping("/reservas/{id}")
	public ResponseEntity<?> getReservasporCliente(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(clienteService.getReservas(id));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> postCliente(@RequestBody ClienteDto cliente) {
		try {
			return ResponseEntity.ok(clienteService.postCliente(cliente));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	@PutMapping
	public ResponseEntity<?> putCliente(@RequestBody ClienteDto cliente) {
		try {
			return ResponseEntity.ok(clienteService.putCliente(cliente));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}
}
