package dgmp.gestionpersonnel.controller.repositories;

import dgmp.gestionpersonnel.model.entities.TDemande;
import org.springframework.data.jpa.repository.JpaRepository;

import dgmp.gestionpersonnel.model.entities.TTraitement;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TTraitementRepository extends JpaRepository<TTraitement, Long>{
    @Query("select t from TTraitement t where t.traiAgtTraiteur.agtId = ?1")
    List<TTraitement> findByTraiAgtTraiteur_AgtId(Long agtId);




}
