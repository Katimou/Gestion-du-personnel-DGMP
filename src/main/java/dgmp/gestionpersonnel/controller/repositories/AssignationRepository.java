package dgmp.gestionpersonnel.controller.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dgmp.gestionpersonnel.model.entities.TAssignation;

public interface AssignationRepository extends JpaRepository<TAssignation, Long>
{
	List<TAssignation> findByAssAgentAgtUsername(String username);
	
	@Query(value="select a from TAssignation a where a.assAgent.agtUsername = :username and a.assCour = true")
	TAssignation getAssCour(@Param("username") String username);

	@Query("select t from TAssignation t where t.assAgent.agtUsername = :agtUsername")
	List<TAssignation> findByUsername(@Param("agtUsername") String agtUsername);

	@Query("select t from TAssignation t where t.assAgent.agtId = ?1")
	List<TAssignation> findByAgtId(Long agtId);

	@Query("select (count(t) > 0) from TAssignation t where t.assAgent.agtId = ?1 and t.assRole.rleId = ?2 and t.assStruct.strId = ?3 and t.assCour = true")
	boolean existsByAssAgent_AgtIdAndAssRole_RleIdAndAssStruct_StrId(Long agtId, Long rleId, Long strId);

	@Query("select t from TAssignation t where t.assAgent.agtId = ?1 and t.assStruct.strId = ?2 and t.assRole.rleId = ?3")
	TAssignation findByAgtStrRle(Long agtId, Long strId, Long rleId);


	
}
