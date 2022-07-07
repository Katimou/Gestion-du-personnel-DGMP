package dgmp.gestionpersonnel.controller.repositories;

import dgmp.gestionpersonnel.model.entities.StrParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("paramRep")
public interface StrParamRepository extends JpaRepository<StrParam, Long>
{
    @Query("select s from StrParam s where s.strRole = 'STR_MERE'")
    StrParam findStructureMere();

    @Query("select s from StrParam s where s.strRole = 'RH'")
    StrParam findStructureRH();
}