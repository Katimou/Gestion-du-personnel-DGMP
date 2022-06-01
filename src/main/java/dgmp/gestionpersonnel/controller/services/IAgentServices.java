package dgmp.gestionpersonnel.controller.services;

import java.util.List;

import dgmp.gestionpersonnel.model.entities.TAgent;

public interface IAgentServices {
	TAgent createAgent(TAgent agent);
	List<TAgent> getAllAgentsByStrId(long strId);
	
}
