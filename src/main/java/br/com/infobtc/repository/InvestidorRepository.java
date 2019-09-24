package br.com.infobtc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.infobtc.model.Investidor;
import br.com.infobtc.model.Status;

public interface InvestidorRepository extends JpaRepository<Investidor, Long> {

	List<Investidor> findByStatusInvestidor(Status statusInvestidor);

}
