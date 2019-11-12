package br.com.infobtc.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.infobtc.config.security.service.TokenService;
import br.com.infobtc.controller.dto.ErroDto;
import br.com.infobtc.controller.dto.RepasseDto;
import br.com.infobtc.controller.form.RepasseForm;
import br.com.infobtc.controller.vo.RepasseParcelaVo;
import br.com.infobtc.dao.RepasseDao;
import br.com.infobtc.model.Repasse;
import br.com.infobtc.model.StatusRepasse;
import br.com.infobtc.model.TipoRecebedor;
import br.com.infobtc.model.TipoRepasse;
import br.com.infobtc.model.Usuario;
import br.com.infobtc.repository.ParcelaRepository;
import br.com.infobtc.repository.RepasseRepository;
import br.com.infobtc.repository.UsuarioRepository;
import br.com.infobtc.service.S3Service;
import javassist.NotFoundException;

@RestController
@RequestMapping("/repasse")
public class RepasseController {

	@Autowired
	private RepasseRepository repasseRepository;

	@Autowired
	private ParcelaRepository parcelaRepository;

	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService; 

	@Autowired
	private RepasseDao repasseDao; 
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(HttpServletRequest request, @Valid @ModelAttribute RepasseForm repasseForm,
			UriComponentsBuilder uriComponentsBuilder) throws MalformedURLException {
		Repasse repasse = new Repasse();
		
		try {
//			TODO: DEFINIR A REGRA
			if (parcelaRepository.buscarRepassePorParcela(repasseForm.getParcela_id(), repasseForm.getTipo_recebedor()).isPresent()) {
				throw new NotFoundException(String.format("A parcela de id %s já foi repassada para o %s.", repasseForm.getParcela_id(), 
						repasseForm.getTipo_recebedor().toString().toLowerCase()));
			}
			
			repasseForm.setarPropriedades(repasse, parcelaRepository);
			setarUsuario(request, repasse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDto(e.getMessage()));
		}
		if (repasseForm.getAnexo() != null) {
			URI uploadFile = s3Service.uploadFile(repasseForm.getAnexo());
			repasse.setAnexo(uploadFile.toURL().toString());
		}

		repasseRepository.save(repasse);

		URI uri = uriComponentsBuilder.path("/repasse/{id}").buildAndExpand(repasse.getId()).toUri();
		return ResponseEntity.created(uri).body(new RepasseDto(repasse));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RepasseDto> buscarPorId(@PathVariable Long id) {
		Optional<Repasse> repasse = repasseRepository.findById(id);

		if (repasse.isPresent()) {
			return ResponseEntity.ok(new RepasseDto(repasse.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("parcela/{id}")
	public ResponseEntity<RepasseDto> buscarRepassePorParcela(@PathVariable Long id, @RequestParam(required = true) TipoRecebedor tipoRecebedor) {
		Optional<Repasse> repasse = parcelaRepository.buscarRepassePorParcela(id, tipoRecebedor);

		if (repasse.isPresent()) {
			return ResponseEntity.ok(new RepasseDto(repasse.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("contrato/{id}")
	public ResponseEntity<List<RepasseDto>> buscarRepassesPorContrato(@PathVariable Long id, TipoRepasse tipoRepasse) {
		List<Repasse> repasses;

		if (id != null && tipoRepasse != null) {
			repasses = repasseRepository.findByParcelaContratoIdAndTipoRepasse(id, tipoRepasse);
		} else if (tipoRepasse != null) {
			repasses = repasseRepository.findByTipoRepasse(tipoRepasse);
		} else {
			repasses = repasseRepository.findByParcelaContratoId(id);
		}
		return ResponseEntity.ok(new RepasseDto().converterPerfis(repasses));
	}
	
	
	@GetMapping("repasses")
	public ResponseEntity<List<?>> consultarRepasses(TipoRecebedor tipoRecebedor, StatusRepasse statusRepasse, String dtInicio, String dtTermino, Long contratoId) {
		
		LocalDate dtInicioParse = dtInicio != null ? LocalDate.parse(dtInicio): null;
		LocalDate dtTerminoParse = dtTermino != null ? LocalDate.parse(dtTermino): null;
		
		List<RepasseParcelaVo> resultado = repasseDao.buscarRepassseAplicandoFiltros(tipoRecebedor, statusRepasse, dtInicioParse, dtTerminoParse, contratoId);
		return ResponseEntity.ok(resultado);
	}
	
	private void setarUsuario(HttpServletRequest request, Repasse repasse) throws NotFoundException {
		String token = tokenService.recuperarToken(request);
		Long usuarioId  = tokenService.getUsuario(token);
		
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		
		if (usuario.isPresent()) {
			repasse.setUsuario(usuario.get());
		} else {
			throw new NotFoundException(String.format("O usuário de id %s não foi encontrado.", usuarioId));
		}
	}
}
