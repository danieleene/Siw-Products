package it.uniroma3.siw.repository;


import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Credenziali;


public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {
    Credenziali findByEmail(String email);
     @Query("SELECT c FROM Credenziali c JOIN FETCH c.utente WHERE c.username = :username")
    Credenziali findByUsername(@Param("username") String username);
}

