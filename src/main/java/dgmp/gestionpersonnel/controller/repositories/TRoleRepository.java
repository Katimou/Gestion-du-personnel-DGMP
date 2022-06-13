package dgmp.gestionpersonnel.controller.repositories;

import dgmp.gestionpersonnel.model.entities.TRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TRoleRepository extends JpaRepository<TRole, Long>
{
    @Query("select t from TRole t where t.rleId = ?1")
    TRole getRleById(Long rleId);

    @Query("select t from TRole t where t.rleNom = ?1")
    TRole findByRleNom(String rleNom);




  
}
