package dgmp.gestionpersonnel.controller.services;
import java.util.List;

import dgmp.gestionpersonnel.model.entities.TDemande;

public interface IDemandeAServices {
	TDemande saveDemande(TDemande demande);
	List<TDemande> findDemande();
}
