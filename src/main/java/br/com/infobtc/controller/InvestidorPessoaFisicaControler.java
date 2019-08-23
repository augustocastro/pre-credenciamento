package br.com.infobtc.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.infobtc.config.security.service.TokenService;
import br.com.infobtc.controller.dto.ErroDto;
import br.com.infobtc.controller.dto.InvestidorPessoaFisicaDto;
import br.com.infobtc.controller.form.EnderecoForm;
import br.com.infobtc.controller.form.InvestidorArquivosForm;
import br.com.infobtc.controller.form.InvestidorPessoaFisicaForm;
import br.com.infobtc.model.DadosHash;
import br.com.infobtc.model.Endereco;
import br.com.infobtc.model.InvestidorPessoaFisica;
import br.com.infobtc.repository.DadosHashRepository;
import br.com.infobtc.repository.EnderecoRepository;
import br.com.infobtc.repository.InvestidorPessoaFisicaRepository;
import br.com.infobtc.service.S3Service;

@RestController
@RequestMapping("/investidor-pessoa-fisica")
public class InvestidorPessoaFisicaControler {

	@Autowired
	private InvestidorPessoaFisicaRepository investidorPessoaFisicaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private DadosHashRepository dadosHashRepository;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private TokenService tokenService; 
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(HttpServletRequest request, @Valid @ModelAttribute InvestidorArquivosForm investidorArquivosForm,
			UriComponentsBuilder uriComponentsBuilder) {
		String hash = request.getHeader("HashCode");
		Optional<DadosHash> dadosHash = dadosHashRepository.findByHash(hash);
		
		String token = recuperarToken(request);
		
		boolean isTokenValido = tokenService.isTokenValido(token);
		
		try {
			InvestidorPessoaFisicaForm form = new ObjectMapper().readValue(investidorArquivosForm.getInvestidor(), InvestidorPessoaFisicaForm.class);

			if (dadosHash.isPresent() || isTokenValido) {
				InvestidorPessoaFisica investidor = new InvestidorPessoaFisica();
				Endereco endereco = new Endereco();
				EnderecoForm enderecoForm = form.getEndereco();

				investidor.setEndereco(endereco);

				enderecoForm.setarPropriedades(endereco);
				form.setarPropriedades(investidor);

				if (investidorArquivosForm.getArquivos() != null && investidorArquivosForm.getArquivos().length > 0) {
					for (MultipartFile file : investidorArquivosForm.getArquivos()) {
						URI uploadFile = s3Service.uploadFile(file);
						investidor.getArquivosUrl().add(uploadFile.toURL().toString());
					}
				}

				enderecoRepository.save(endereco);
				investidorPessoaFisicaRepository.save(investidor);

				URI uri = uriComponentsBuilder.path("/investidor/{id}").buildAndExpand(investidor.getId()).toUri();
				
				if (!isTokenValido) {
					dadosHashRepository.deleteById(dadosHash.get().getId());
				}

				return ResponseEntity.created(uri).body(new InvestidorPessoaFisicaDto(investidor));
			}

			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} catch (JsonParseException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroDto("Erro ao converter JSON para objeto Java"));
		} catch (JsonMappingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroDto("Erro na desrealização do JSON devido a erros de mapeamento."));
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroDto("Erro nos arquivos enviados."));
		}
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<InvestidorPessoaFisicaDto> atualizar(@PathVariable Long id,
			@Valid @RequestBody InvestidorPessoaFisicaForm form) {
		Optional<InvestidorPessoaFisica> investidor = investidorPessoaFisicaRepository.findById(id);

		if (investidor.isPresent()) {
			InvestidorPessoaFisica investidorAtualizado = form.atualizar(id, investidorPessoaFisicaRepository, enderecoRepository);
			InvestidorPessoaFisicaDto investidorDto = new InvestidorPessoaFisicaDto(investidorAtualizado);
			return ResponseEntity.ok(investidorDto);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/todos")
	public ResponseEntity<List<InvestidorPessoaFisicaDto>> buscarTodos() {
		List<InvestidorPessoaFisica> investidores = investidorPessoaFisicaRepository.findAll();
		return ResponseEntity.ok(new InvestidorPessoaFisicaDto().converter(investidores));
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}
}
