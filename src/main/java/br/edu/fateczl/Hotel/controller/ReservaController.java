package br.edu.fateczl.Hotel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fateczl.Hotel.controller.interfaces.Controller;
import br.edu.fateczl.Hotel.model.dto.ReservaDTO;
import br.edu.fateczl.Hotel.model.dto.ServicoDTO;
import br.edu.fateczl.Hotel.model.entity.PessoaID;
import br.edu.fateczl.Hotel.model.entity.QuartoID;
import br.edu.fateczl.Hotel.model.entity.Reserva;
import br.edu.fateczl.Hotel.model.entity.ReservaID;
import br.edu.fateczl.Hotel.model.entity.Servico;
import br.edu.fateczl.Hotel.repository.ReservaRepository;
import br.edu.fateczl.Hotel.repository.ServicoRepository;

@RestController
@RequestMapping("/api")
public class ReservaController extends Controller<ReservaDTO>{
	@Autowired
	ReservaRepository rep;
	
	@Override
	@GetMapping("/reserva")
	public List<ReservaDTO> findAll() {
		List<Reserva> reserva = rep.findAll();
		List<ReservaDTO> res =  new ArrayList<>();
		
		for(Reserva re: reserva) {
			res.add(re.toDTO());
		}
		
		return res;
	}

	@GetMapping("/reserva/{reservaID}")
	public ResponseEntity<ReservaDTO> findOne(@PathVariable(name="documento") PessoaID doc, @PathVariable(name="tipodocumento") PessoaID tipoDoc,
			@PathVariable(name="quartoid") QuartoID qID, @PathVariable(name="dataInicio") String dataInicio) {
		ReservaID id = new ReservaID();
		id.setDocumento(doc);
		id.setQuartoID(qID);
		id.setTipoDocumento(tipoDoc);
		id.setDataInicio(dataInicio);
;		Optional<Reserva> s = rep.findById(id);
		Reserva res = s.orElseThrow(()-> new ResourceNotFoundException(this.notFound("um serviço",id+"")));
		return ResponseEntity.ok().body(res.toDTO());
	}

	@Override
	@PostMapping("/reserva/")
	public ResponseEntity<String> insert(@Valid @RequestBody ReservaDTO obj) {
		rep.save(obj.toEntity());
		return ResponseEntity.ok().body(this.sucesso(1));
	}

	@Override
	@PutMapping("/reserva/")
	public ResponseEntity<String> update(@Valid @RequestBody ReservaDTO obj) {
		rep.save(obj.toEntity());
		return ResponseEntity.ok().body(this.sucesso(2));
	}

	@Override
	@DeleteMapping("/reserva/")
	public ResponseEntity<String> delete(ReservaDTO obj) {
		rep.delete(obj.toEntity());
		return ResponseEntity.ok().body(this.sucesso(3));
	}

	@Override
	public ResponseEntity<ReservaDTO> findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


}