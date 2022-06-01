package dgmp.gestionpersonnel.controller.services;

import dgmp.gestionpersonnel.controller.repositories.StrParamRepository;
import dgmp.gestionpersonnel.controller.repositories.TDemandeRepository;
import dgmp.gestionpersonnel.controller.repositories.TTraitementRepository;
import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TDemande;
import dgmp.gestionpersonnel.model.entities.TStructure;
import dgmp.gestionpersonnel.model.entities.TTraitement;
import dgmp.gestionpersonnel.model.enums.EtatDemande;
import dgmp.gestionpersonnel.security.services.ISecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TraitementService implements ITraitementService
{
    private final TDemandeRepository dmeRep;
    private final TTraitementRepository traiRep;
    private final ISecurityContextService scs;
    private final StrParamRepository strParamRep;

    @Transactional
    @Override
    public void soumettreDemandeAbsence(Long dmeId)
    {
        TDemande demande = dmeRep.findById(dmeId).orElse(null);
        TAgent demandeur = demande.getDmeDemandeur();
        demande.setDmeEtat(EtatDemande.SOUMIS);
        TTraitement traitement = new TTraitement();
        traitement.setTraiDate(LocalDateTime.now());
        traitement.setTraiStatutDem(true);
        traitement.setTraiAgtTraiteur(scs.getAuthUser().getAgent());
        TStructure strDestination = new TStructure();
        if(demandeur.getAgtStructure().getStrNiveau()<=2 ) strDestination = strParamRep.findStructureRH().getStructure();
        else strDestination = demandeur.isResponsable() ? demandeur.getAgtStructure().getStrTutelleDirecte(): demandeur.getAgtStructure();
        traitement.setTraiStrDestination(strDestination);
        traitement.setTraiAgtDestination(strDestination.getStrRespo());
        traiRep.save(traitement);
        dmeRep.save(demande);
    }

    @Override
    public void viserDemandeAbsence(Long dmeId, boolean accepte, String motif) {

    }

    @Override
    public void editerDemandeAbsence(Long dmeId) {

    }

    @Override
    public void attacherAutorisationAbsence(Long dmeId, MultipartFile autorisation) {

    }

    @Override
    public void soumettreDemandeAct(Long dmeId) {

    }

    @Override
    public void editerAct(Long dmeId) {

    }

    @Override
    public void attacherAct(Long dmeId, MultipartFile act) {

    }
}
