package dgmp.gestionpersonnel.controller.services;

import dgmp.gestionpersonnel.controller.repositories.AgentRepository;
import dgmp.gestionpersonnel.controller.repositories.MouvementRepository;
import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TMouvement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MouvementService implements IMouvementService{
    public final AgentRepository agtRep;
    public final MouvementRepository mvtRep;

    @Override
    public void Affecter( TMouvement mvt,Long id) {
        TAgent agent= agtRep.findById(id).orElse(null);
        agent.setAgtStructure(mvt.getMvtStructureDes());
        agtRep.save(agent);
        mvtRep.save(mvt);

    }
}
