package dgmp.gestionpersonnel.controller.repositories;

import dgmp.gestionpersonnel.model.entities.TDemande;
import org.springframework.data.jpa.repository.JpaRepository;

import dgmp.gestionpersonnel.model.entities.TTraitement;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TraitementRepository extends JpaRepository<TTraitement, Long>{
    @Query("select t from TTraitement t where t.traiAgtTraiteur.agtId = ?1")
    List<TTraitement> findByTraiAgtTraiteur_AgtId(Long agtId);

    @Query("select t from TTraitement t where t.traiDemande.dmeId = ?1 order by t.traiDate desc")
    List<TTraitement> findByDemande(Long dmeId);




}
