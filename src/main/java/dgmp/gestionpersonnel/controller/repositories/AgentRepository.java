package dgmp.gestionpersonnel.controller.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TStructure;

public interface AgentRepository extends JpaRepository<TAgent, Long>
{
	public List<TAgent> findByAgtFonction(String fonction);
	public Optional<TAgent> findByAgtUsername(String username);
	public List<TAgent> findByAgtNom(String nom);
	
	@Query("select a from TAgent a where a.agtNom like %:keyword% or a.agtPrenom like %:keyword% or a.agtEmail like %:keyword% or a.agtAdresse like %:keyword% or a.agtTel like %:keyword%")
	public List<TAgent> findByKeyWord(@Param(value = "keyword") String keyword);
	public List<TAgent> findByAgtActive(boolean active);
    public List<TAgent> findByAgtActiveTrue();
    public List<TAgent> findAllByOrderByAgtIdAsc();
    public List<TAgent> findByAgtStructure_strId(Long strId);

	@Query("select t from TAgent t where t.agtId = ?1")
	TAgent getAgtById(Long agtId);




	
}
