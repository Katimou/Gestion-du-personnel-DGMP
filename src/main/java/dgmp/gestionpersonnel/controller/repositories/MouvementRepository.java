package dgmp.gestionpersonnel.controller.repositories;

import dgmp.gestionpersonnel.model.entities.TMouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MouvementRepository extends JpaRepository<TMouvement, Long> {
    @Query("select t from TMouvement t where t.mvtAgent.agtId = ?1")
    TMouvement findByMvtAgent_AgtId(Long agtId);

}
