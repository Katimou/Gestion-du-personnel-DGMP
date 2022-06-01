package dgmp.gestionpersonnel.controller.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dgmp.gestionpersonnel.controller.repositories.TStructureRepository;
import dgmp.gestionpersonnel.controller.repositories.TTypeRepository;
import dgmp.gestionpersonnel.controller.services.IStructureService;
import dgmp.gestionpersonnel.model.entities.TStructure;
import dgmp.gestionpersonnel.model.entities.TType;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/structures")
public class StructureController 
{
	private final TStructureRepository strRep;
	private final IStructureService strService;
	private final TTypeRepository  typeRep;
	
	@GetMapping(path = "/goToSaveForm")
	public String goToSaveStructure(Model model)
	{  
		List<TType> types=typeRep.findTypeStructure();
		List<TStructure> structures= strRep.findAll();
		model.addAttribute("structures",structures);
		model.addAttribute("types",types);
		model.addAttribute("structure", new TStructure());
		return "structures/saveForm";
	}
	
	@PostMapping(path = "/save")
	public String saveStructure(Model model, TStructure structure)
	{
		structure = strService.createStructure(structure);
//		return "redirecte:/about/" + structure.getStrId();
		return "";
	}
	
	@GetMapping(path = "/goToUpdateForm")
	public String goToUpdateStructure(Model model)
	{
		model.addAttribute("structure", new TStructure());
		return "structures/updateForm";
	}
	
	@PostMapping(path = "/update")
	public String updateStructure(Model model, TStructure structure)
	{
		structure = strService.updateStructure(structure.getStrId(), structure);
		return "redirecte:/about/" + structure.getStrId();
	}
	
	
	
	@GetMapping(path = "/list")
	public String goToListStructures(Model model)
	{
		model.addAttribute("structure", new TStructure());
		return "structures/list";
	}
	
	@GetMapping(path = "/about/{strId}")
	public String goToAboutStructure(Model model, @PathVariable Long strId)
	{
		model.addAttribute("structure", strRep.findById(strId).orElse(null));
		return "structures/about";
	}
}
