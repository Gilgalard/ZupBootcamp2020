package br.com.nossobancodigital.nossobanco.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import br.com.nossobancodigital.nossobanco.entities.ProposalEntity;

@Component
public interface ProposalRepository extends JpaRepository<ProposalEntity, Long> {
	Optional<ProposalEntity> findById(Long id);
	ProposalEntity findByEmailIgnoreCase(String email);
}