package dgmp.gestionpersonnel.controller.services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dgmp.gestionpersonnel.controller.repositories.TypeRepository;
import dgmp.gestionpersonnel.security.services.ISecurityContextService;
import dgmp.gestionpersonnel.security.services.SecurityUser;
import org.springframework.stereotype.Service;

import dgmp.gestionpersonnel.controller.repositories.DemandeRepository;
import dgmp.gestionpersonnel.model.entities.TDemande;
import dgmp.gestionpersonnel.model.enums.EtatDemande;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class DemandeAService implements IDemandeAServices {
	private final DemandeRepository dmeRep;
	private final ISecurityContextService scs;
	private  final TypeRepository typeRep;

	@Override
	public TDemande saveDemande(TDemande demande) {
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
		demande.setDmeDate(formatDateTime);
		demande.setDmeEtat(EtatDemande.INITIE);
		demande.setDmeType(typeRep.findByTypNom("DEMANDE_SORTIE_TERRITOIRE"));
		SecurityUser securityUser = scs.getAuthUser();
		demande.setDmeDemandeur(securityUser.getAgent());
		return dmeRep.save(demande);
		
	}

	@Override
	public List<TDemande> findDemande() {
		
	return dmeRep.findAll();
		
	}

}
