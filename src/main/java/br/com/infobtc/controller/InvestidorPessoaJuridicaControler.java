package br.com.infobtc.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.infobtc.controller.dto.InvestidorPessoaJuridicaDto;
import br.com.infobtc.controller.form.EnderecoForm;
import br.com.infobtc.controller.form.InvestidorPessoaJuridicaForm;
import br.com.infobtc.model.Endereco;
import br.com.infobtc.model.InvestidorPessoaJuridica;
import br.com.infobtc.repository.ConsultorRepository;
import br.com.infobtc.repository.EnderecoRepository;
import br.com.infobtc.repository.InvestidorPessoaJuridicaRepository;

@RestController
@RequestMapping("/investidor-pessoa-juridica")
public class InvestidorPessoaJuridicaControler {

	@Autowired
	private InvestidorPessoaJuridicaRepository investidorPessoaJuridicaRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ConsultorRepository consultorRepository; 
	
	@PostMapping
	@Transactional
	public ResponseEntity<InvestidorPessoaJuridicaDto> cadastrar(@RequestBody @Valid InvestidorPessoaJuridicaForm pessoaForm, UriComponentsBuilder uriComponentsBuilder) {
		InvestidorPessoaJuridica investidor = new InvestidorPessoaJuridica();
		Endereco endereco = new Endereco();
		EnderecoForm enderecoForm = pessoaForm.getEndereco();

		investidor.setEndereco(endereco);
		
		enderecoForm.setarPropriedades(endereco);
		pessoaForm.setarPropriedades(investidor, consultorRepository);

		enderecoRepository.save(endereco);
		investidorPessoaJuridicaRepository.save(investidor);

		URI uri = uriComponentsBuilder.path("/investidor-pessoa-juridica/{id}").buildAndExpand(investidor.getId()).toUri();
		return ResponseEntity.created(uri).body(new InvestidorPessoaJuridicaDto(investidor));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<InvestidorPessoaJuridicaDto> atualizar(@PathVariable Long id, @Valid @RequestBody InvestidorPessoaJuridicaForm form) {
		Optional<InvestidorPessoaJuridica> investidor = investidorPessoaJuridicaRepository.findById(id);

		if (investidor.isPresent()) {
			InvestidorPessoaJuridica investidorAtualizado = form.atualizar(id, investidorPessoaJuridicaRepository, enderecoRepository, consultorRepository);
			return ResponseEntity.ok(new InvestidorPessoaJuridicaDto(investidorAtualizado));
		}

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/todos")
	public ResponseEntity<List<InvestidorPessoaJuridicaDto>> buscarTodos() {
		List<InvestidorPessoaJuridica> investidores = investidorPessoaJuridicaRepository.findAll();
		return ResponseEntity.ok(new InvestidorPessoaJuridicaDto().converter(investidores));
	}
	
}
