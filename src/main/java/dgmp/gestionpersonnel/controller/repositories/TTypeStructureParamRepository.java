package dgmp.gestionpersonnel.controller.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dgmp.gestionpersonnel.model.entities.TType;
import dgmp.gestionpersonnel.model.entities.TTypeStructureParam;

public interface TTypeStructureParamRepository extends JpaRepository<TTypeStructureParam, Long> 
{
	public List<TTypeStructureParam> findByTspTypeParent_typId(Long typId);
	
}
