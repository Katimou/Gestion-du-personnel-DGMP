package dgmp.gestionpersonnel.controller.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dgmp.gestionpersonnel.model.entities.TStructure;

public interface StructureRepository extends JpaRepository<TStructure, Long>
{
	@Query("select t from TStructure t where t.strNiveau = ?1")
	List<TStructure> findByNiveauStr(int strNiveau);
	//public List<TAgent> findByDmeId(Long id);
	/*
	 * public Optional<TAgent> findByAgtUsername(String username); public
	 * List<TAgent> findByAgtNom(String nom);
	 * 
	 * @Query("select a from TAgent a where a.agtNom like %:keyword% or a.agtPrenom like %:keyword% or a.agtEmail like %:keyword% or a.agtAdresse like %:keyword% or a.agtTel like %:keyword%"
	 * ) public List<TAgent> findByKeyWord(@Param(value = "keyword") String
	 * keyword); public List<TAgent> findByAgtActive(boolean active); public
	 * List<TAgent> findByAgtActiveTrue(); public List<TAgent>
	 * findAllByOrderByAgtIdAsc();
	 */
	
	@Query("select s from TStructure s where s.strTutelleDirecte.strId=:strId")
	public List<TStructure> findStructureFilles(@Param("strId") Long strId);
	@Query("select t from TStructure t where t.strTutelleDirecte.strId = :strId")
	public List<TStructure> findByStrTutelleDirecte_StrId (@Param("strId") Long strId);

	@Query("select t from TStructure t where t.strTutelleDirecte.strTutelleDirecte.strId = ?1")
	TStructure findByStrTutelleDirecte_StrTutelleDirecte_StrId(Long strId);


	public List<TStructure> findByStrNomStruc(String nomStructure);

	@Query("select t from TStructure t where t.strId = ?1")
	 TStructure getStrById(Long strId);



	
	
}
