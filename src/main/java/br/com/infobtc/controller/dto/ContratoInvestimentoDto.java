package br.com.infobtc.controller.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.infobtc.model.ContratoInvestimento;
import br.com.infobtc.model.TipoRendimento;

public class ContratoInvestimentoDto {

	private Long id;
	private String dt_inicio;
	private String dt_termino;
	private long quantidade_meses;
	private BigDecimal valor;
	private String status_contrato;
	private String status_financeiro;
	private TipoRendimento tipo_rendimento;
	private String justificativa_reprovacao;
	private boolean repassado;
	private InvestidorDto investidor;
	private ConsultorDto consultor;
	private BancoDto banco;
	private RescisaoDto rescisao;
	private String banco_recebimento_escritorio;
	private List<String> arquivos_url = new ArrayList<String>();
	
	public ContratoInvestimentoDto() {
	}

	public ContratoInvestimentoDto(ContratoInvestimento contratoInvestimento) {
		this.id = contratoInvestimento.getId();
		this.dt_inicio = contratoInvestimento.getDtInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.dt_termino = contratoInvestimento.getDtTermino().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.quantidade_meses = contratoInvestimento.getQuantidadeMeses();
		this.valor = contratoInvestimento.getValor();
		this.investidor = new InvestidorDto(contratoInvestimento.getInvestidor());
		this.consultor = new ConsultorDto(contratoInvestimento.getConsultor());
		this.banco = new BancoDto(contratoInvestimento.getBanco());
		this.status_contrato = contratoInvestimento.getStatusContrato().toString();
		this.status_financeiro = contratoInvestimento.getStatusFinanceiro().toString();
		this.tipo_rendimento = contratoInvestimento.getTipoRendimento();
		this.arquivos_url = contratoInvestimento.getArquivosUrl();
		this.justificativa_reprovacao = contratoInvestimento.getJustificativaReprovacao();
		this.repassado = contratoInvestimento.isRepassado();
		this.banco_recebimento_escritorio = contratoInvestimento.getBancoRecebimentoEscritorio();
		if(contratoInvestimento.getRescisao() != null) {
			this.rescisao = new RescisaoDto(contratoInvestimento.getRescisao());	
		}
		
	}

	public Long getId() {
		return id;
	}

	public String getDt_inicio() {
		return dt_inicio;
	}

	public String getDt_termino() {
		return dt_termino;
	}

	public long getQuantidade_meses() {
		return quantidade_meses;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public boolean isRepassado() {
		return repassado;
	}
	
	public InvestidorDto getInvestidor() {
		return investidor;
	}
	
	public ConsultorDto getConsultor() {
		return consultor;
	}

	public BancoDto getBanco() {
		return banco;
	}

	public String getBanco_recebimento_escritorio() {
		return banco_recebimento_escritorio;
	}
	
	public String getStatus_contrato() {
		return status_contrato;
	}
	
	public String getStatus_financeiro() {
		return status_financeiro;
	}
	
	public TipoRendimento getTipo_rendimento() {
		return this.tipo_rendimento;
	}

	public String getJustificativa_reprovacao() {
		return justificativa_reprovacao;
	}
	
	public List<String> getArquivos_Url() {
		return arquivos_url;
	}

	public Page<ContratoInvestimentoDto> converter(Page<ContratoInvestimento> contratos) {
		return contratos.map(ContratoInvestimentoDto::new);
	}
	
	public List<ContratoInvestimentoDto> converter(List<ContratoInvestimento> contratos) {
		return contratos.stream().map(ContratoInvestimentoDto::new).collect(Collectors.toList());
	}

	public RescisaoDto getRescisao() {
		return rescisao;
	}

	public void setRescisao(RescisaoDto rescisao) {
		this.rescisao = rescisao;
	}

}