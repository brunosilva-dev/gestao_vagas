package br.com.neomeca.gestao_vagas.modules.jobs.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.neomeca.gestao_vagas.modules.jobs.entities.JobEntity;
import br.com.neomeca.gestao_vagas.modules.jobs.repositories.JobRepository;

@Service
public class CreateJobUseCase {

  @Autowired
  private JobRepository jobRepository;

  public JobEntity execute(JobEntity jobEntity) {
    return this.jobRepository.save(jobEntity);
  }
}
