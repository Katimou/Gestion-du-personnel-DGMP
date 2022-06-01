package dgmp.gestionpersonnel.controller.web;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dgmp.gestionpersonnel.controller.repositories.TStructureRepository;
import dgmp.gestionpersonnel.controller.repositories.TTypeRepository;
import dgmp.gestionpersonnel.controller.services.IStructureService;
import dgmp.gestionpersonnel.model.entities.TStructure;
import dgmp.gestionpersonnel.model.entities.TType;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/types")
@RequiredArgsConstructor
public class TypeRestController 
{
	private final TTypeRepository typeRep;
	private final IStructureService structureService;
	private final TStructureRepository strRep;
	@GetMapping(path = "/getSousTypeCompatiblesByStrId/{strId}")
	public List<TType> getSousTypeCompatiblesByStrId(@PathVariable Long strId)
	{
		if(!strRep.existsById(strId)) return Collections.emptyList();
		return typeRep.findSousType(typeRep.findByStrId(strId).getTypId());
	}
	
	@GetMapping(path = "/getSousTypeCompatiblesByTypId/{typId}")
	public List<TType> getSousTypeCompatiblesByTypId(@PathVariable Long typId)
	{
		if(!typeRep.existsById(typId)) return Collections.emptyList();
		return typeRep.findSousType(typId);
	}
	
	@GetMapping(path = "/getAllStrFilles/{strId}")
	public TStructure getAllStrFilles(@PathVariable Long strId)
	{
		return structureService.getStructureFilles(strId);
	}
	
}
