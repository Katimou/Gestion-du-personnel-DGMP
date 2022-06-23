package dgmp.gestionpersonnel.controller.services;

import dgmp.gestionpersonnel.controller.repositories.StrParamRepository;
import dgmp.gestionpersonnel.controller.repositories.DemandeRepository;
import dgmp.gestionpersonnel.controller.repositories.TraitementRepository;
import dgmp.gestionpersonnel.controller.validator.exception.AppException;
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
    private final DemandeRepository dmeRep;
    private final TraitementRepository traiRep;
    private final ISecurityContextService scs;
    private final StrParamRepository strParamRep;

    @Transactional
    @Override
    public void soumettreDemandeAbsence(Long dmeId)
    {
        TDemande demande = dmeRep.findById(dmeId).orElseThrow(()->new AppException("Demande inexistante"));
        TAgent demandeur = demande.getDmeDemandeur();
        demande.setDmeEtat(EtatDemande.SOUMIS);
        demande.setDmeLieuDepart(demande.getDmeLieuDepart());
        TTraitement traitement = new TTraitement();
        traitement.setTraiDemande(demande);
        traitement.setTraiDate(LocalDateTime.now());
        traitement.setTraiStatutDem(true);
        traitement.setTraiAgtTraiteur(scs.getAuthUser().getAgent());
        TStructure strDestination;
        if(demandeur.getAgtStructure().getStrNiveau()<=2 ) strDestination = strParamRep.findStructureRH().getStructure();
        else strDestination = demandeur.isResponsable() ? demandeur.getAgtStructure().getStrTutelleDirecte(): demandeur.getAgtStructure();
        demande.setDmeDestination(strDestination);
        traitement.setTraiStrDestination(strDestination);
//        traitement.setTraiAgtDestination(strDestination.getStrRespo());
        traiRep.save(traitement);
        dmeRep.save(demande);
    }

    @Override
    public void viserDemandeAbsence(Long dmeId, boolean accepte, String motif) {

        TDemande demande = dmeRep.findById(dmeId).orElseThrow(()->new AppException("Demande inexistante"));
        int niveau = scs.getCurrentAss().getAssStruct().getStrNiveau();
        TAgent traiteur = scs.getAuthUser().getAgent().getAgtStructure().getStrRespo();
        demande.setDmeEtat(EtatDemande.EN_COURS_DE_TRAITEMENT);
        TTraitement traitement = new TTraitement();
        traitement.setTraiDemande(demande);
        traitement.setTraiDate(LocalDateTime.now());
        traitement.setTraiStatutDem(true);
        traitement.setTraiAgtTraiteur(traiteur);
        TStructure strDestination;

        //Si le niveau de la structure est <=2 On transmet la demande au RH pour traitement
        if(niveau<=2 ){
            strDestination = strParamRep.findStructureRH().getStructure();
        }
       /* if(traiteur.getAgtStructure().getStrNiveau()<=2 ){
            strDestination = strParamRep.findStructureRH().getStructure();
        } */
        else strDestination = traiteur.getAgtStructure().getStrTutelleDirecte();//Sinon on transmet la demande à la tutelle directe
        demande.setDmeDestination(strDestination);
        traitement.setTraiStrDestination(strDestination);
        traitement.setTraiAgtDestination(strDestination==null ? null: strDestination.getStrRespo());
        traiRep.save(traitement);
        dmeRep.save(demande);
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
