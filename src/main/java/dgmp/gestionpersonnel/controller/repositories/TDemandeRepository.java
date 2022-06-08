package dgmp.gestionpersonnel.controller.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import dgmp.gestionpersonnel.model.entities.TDemande;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TDemandeRepository extends JpaRepository<TDemande,Long>
{
    @Query("select t from TDemande t where t.dmeDemandeur.agtId = ?1")
    List<TDemande> findByDemandeur(Long agtId);

    @Query("select t from TDemande t where  t.dmeDemandeur.agtUsername=?1")
    List<TDemande> findByDemandeur(String username);

    @Query("select t.traiDemande from TTraitement t where t.traiStrDestination.strId = ?1")
    List<TDemande> getListDmeSentToStr(Long strId);

    @Query("select d from TDemande d where d.dmeDestination.strId = ?1 ")
    List<TDemande> getListDmeSentToStrDer(Long strId);
}
