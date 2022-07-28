package dgmp.gestionpersonnel.controller.repositories;

import dgmp.gestionpersonnel.model.entities.TMouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("mvtRep")
public interface MouvementRepository extends JpaRepository<TMouvement, Long> {
    @Query("select (count(t) > 0) from TMouvement t where t.mvtAgent.agtId = ?1")
    boolean existsByMvtAgent_AgtId(Long agtId);

    @Query("select t from TMouvement t where t.mvtAgent.agtId = ?1")
    TMouvement findByMvtAgent_AgtId(Long agtId);


}
