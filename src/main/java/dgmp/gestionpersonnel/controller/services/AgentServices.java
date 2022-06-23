package dgmp.gestionpersonnel.controller.services;

import dgmp.gestionpersonnel.controller.repositories.StrParamRepository;
import dgmp.gestionpersonnel.controller.repositories.AgentRepository;
import dgmp.gestionpersonnel.controller.repositories.RoleRepository;
import dgmp.gestionpersonnel.controller.repositories.StructureRepository;
import dgmp.gestionpersonnel.controller.utilities.ArchivageConstants;
import dgmp.gestionpersonnel.controller.utilities.IFilesManager;
import dgmp.gestionpersonnel.controller.validator.exception.AppException;
import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TRole;
import dgmp.gestionpersonnel.model.entities.TStructure;
import lombok.RequiredArgsConstructor;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("agtService")
@RequiredArgsConstructor
public class AgentServices implements IAgentServices
{
	private final PasswordEncoder encoder;
	private final AgentRepository agentRep;
	private final IStructureService strService;
	private final StructureRepository strRep;
	private final IFilesManager filesManager;
	private  final IAssService assService;
	private  final StrParamRepository strParamRep;
	private  final RoleRepository rleRep;
   @Override
	@Transactional
	public TAgent createAgent(TAgent agent)
	{
		agent.setAgtActive(true);
		System.out.println("Password = " + agent.getAgtPasswword());
		if(!agent.getAgtPasswword().equals(agent.getAgtRePasswword())) throw new AppException("Le mot de passe de confirmation doit être identique au mot de passe");
		agent.setAgtPasswword(encoder.encode(agent.getAgtPasswword()));
		TStructure structure=agent.getAgtStructure()!=null ?agent.getAgtStructure():strParamRep.findStructureMere().getStructure();
		agent.setAgtStructure(structure);
		agent = agentRep.save(agent);
		TRole role=  rleRep.findByRleNom("AGENT");
		assService.createAss(agent.getAgtId(),role.getRleId(),agent.getAgtStructure().getStrId());
		System.out.println("Agent Save with Id : " + agent.getAgtId());
		if (agent.getAgtPhotoFile() != null) 
		{
			filesManager.uploadFile(agent.getAgtPhotoFile(),
					ArchivageConstants.AGENT_UPLOADS_DIR + "/photo_" + agent.getAgtId());
			agent.setAgtPhotoPath(ArchivageConstants.AGENT_UPLOADS_DIR + "/photo_" + agent.getAgtId()+"."+FilenameUtils.getExtension(agent.getAgtPhotoFile().getOriginalFilename()));
			
			System.out.println("After saving file: ");
			System.out.println("Before update agent: " + agent.getAgtId());
			agent = agentRep.save(agent);
		}
		return agent;
	}
	@Override
	public List<TAgent> getAllAgentsByStrId(long strId) 
	{   
		if(!strRep.existsById(strId)) return Collections.emptyList();
		return strService.getAllStructureFilles(strId).stream().flatMap(str->agentRep.findByAgtStructure_strId(str.getStrId()).stream()).collect(Collectors.toList());
	}
}
