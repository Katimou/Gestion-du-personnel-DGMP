package dgmp.gestionpersonnel.controller.services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dgmp.gestionpersonnel.controller.repositories.StrParamRepository;
import dgmp.gestionpersonnel.controller.repositories.TypeRepository;
import dgmp.gestionpersonnel.controller.validator.exception.AppException;
import dgmp.gestionpersonnel.security.services.ISecurityContextService;
import dgmp.gestionpersonnel.security.services.SecurityUser;
import org.springframework.stereotype.Service;

import dgmp.gestionpersonnel.controller.repositories.DemandeRepository;
import dgmp.gestionpersonnel.model.entities.TDemande;
import dgmp.gestionpersonnel.model.enums.EtatDemande;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("dmeService")
@RequiredArgsConstructor
public class DemandeAService implements IDemandeAServices {
	private final DemandeRepository dmeRep;
	private final ISecurityContextService scs;
	private  final TypeRepository typeRep;
	private final StrParamRepository strParamRep;

	@Override
	public TDemande saveDemande(TDemande demande) {
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
		demande.setDmeDate(formatDateTime);
		demande.setDmeEtat(EtatDemande.INITIE);
		demande.setDmeType(demande.getDmeType());
		SecurityUser securityUser = scs.getAuthUser();
		demande.setDmeDemandeur(securityUser.getAgent());
		return dmeRep.save(demande);
	}

	@Override
	public List<TDemande> findDemande() {
		
	return dmeRep.findAll();
		
	}


	public  Long TotalAbsence(){
		Long sum1=dmeRep.couByTypDmeId(12L);
		Long sum2=dmeRep.couByTypDmeId(13L);
		Long sum3=dmeRep.couByTypDmeId(11L);
		return sum1+sum2+sum3 ;
	}
	public TDemande etiderAbsence(Long dmeId) {
		TDemande demande = dmeRep.findById(dmeId).orElseThrow(()->new AppException("Demande inexistante"));
		demande.setDmeDestination(strParamRep.findStructureMere().getStructure());
		return dmeRep.save(demande);
	}
}
