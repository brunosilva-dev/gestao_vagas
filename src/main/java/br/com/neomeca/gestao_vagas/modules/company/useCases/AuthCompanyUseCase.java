package br.com.neomeca.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.neomeca.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.neomeca.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

    // Company não existe ele lançara uma exceção
    var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("User not found");
        });

    // A Company existe, verificar se a senha é igual ou não, se não for igual(Erro)
    // e se for igual vai gerar o token
    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

    // Senhas diferentes
    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    // Senhas iguais -> Gerar o token
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var token = JWT.create().withIssuer("javagas")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(company.getId().toString())
        .sign(algorithm);

    return token;
  }
}
