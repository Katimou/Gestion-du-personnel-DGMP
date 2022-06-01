package dgmp.gestionpersonnel.controller.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dgmp.gestionpersonnel.controller.repositories.StrParamRepository;
import org.springframework.stereotype.Service;

import dgmp.gestionpersonnel.controller.repositories.TAgentRepository;
import dgmp.gestionpersonnel.controller.repositories.TStructureRepository;
import dgmp.gestionpersonnel.controller.validator.exception.AppException;
import dgmp.gestionpersonnel.model.entities.TStructure;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StructureService implements IStructureService 
{
	private final TStructureRepository strRep;
	private final TAgentRepository agentRep;
	@Override
	public TStructure createStructure(TStructure structure) {
		TStructure tutelleDIrecte = structure.getStrTutelleDirecte();
		structure.setStrNiveau(tutelleDIrecte==null ? 1 : tutelleDIrecte.getStrNiveau()+1);
		return strRep.save(structure);
	}

	@Override
	public TStructure updateStructure(Long strId, TStructure structure) {
		TStructure loadedStructure = strRep.findById(strId).orElseThrow(()->new AppException("Structure inexistante"));
		loadedStructure.setStrNomStruc(structure.getStrNomStruc());
		loadedStructure.setStrSigle(structure.getStrSigle());
		loadedStructure.setStrSiteGeo(structure.getStrSiteGeo());
		
		return strRep.save(loadedStructure);
	}

	@Override
	public TStructure getStructureFilles(Long strId) 
	{
		TStructure str = strRep.findById(strId).orElse(null);
		if(str==null) return null;
		List<TStructure> strFilles = strRep.findByStrTutelleDirecte_StrId(strId);
		str.setStrStructuresFilles(strFilles);
		strFilles.forEach(sf->getStructureFilles(sf.getStrId()));
		return str;
	}
	
	
	@Override
	public List<TStructure> getAllStructureFilles(Long strId) 
	{
		TStructure str = strRep.findById(strId).orElse(null);
		if(str==null) return Collections.emptyList();
		return getAllStructuresFillesAsStream(strId).collect(Collectors.toList());
	}
	
	private Stream<TStructure> getAllStructuresFillesAsStream(Long strId)
	{
		TStructure str = getStructureFilles(strId);
		if(str==null) return null;
		Stream<TStructure> structures = Stream.concat(Stream.of(str), str.getStrStructuresFilles().stream().flatMap(sf->getAllStructuresFillesAsStream(sf.getStrId())));
		return structures;
	}
}
