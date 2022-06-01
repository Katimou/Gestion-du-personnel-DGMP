package dgmp.gestionpersonnel.controller.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dgmp.gestionpersonnel.model.entities.TType;

public interface TTypeRepository extends JpaRepository<TType,Long>
{
	List<TType> findByTypCode(String typCode);
	@Query("Select ts from TType ts Where ts.typCode='TYP_STR'")
	List<TType> findTypeStructure();
	@Query("Select ts from TType ts Where ts.typCode='TYP_DMDE'")
	List<TType> findTypeDemande();
	
	@Query("select tsp.tspSousType from TTypeStructureParam tsp where tsp.tspTypeParent.typId=:typId")
	List<TType> findSousType(@Param("typId") Long typId);
	
	@Query("select s.strType from TStructure s where s.strId=:strId")
	TType findByStrId(@Param("strId") Long strId);
	
	TType findByTypNom(String typNom);
}
