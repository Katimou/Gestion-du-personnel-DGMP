package dgmp.gestionpersonnel.controller.services;

import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TMouvement;

public interface IMouvementService {
    void Affecter(TMouvement mvt,Long id);

}
