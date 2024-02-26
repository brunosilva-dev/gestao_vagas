package br.com.neomeca.gestao_vagas.modules.jobs.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.neomeca.gestao_vagas.modules.jobs.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

}
