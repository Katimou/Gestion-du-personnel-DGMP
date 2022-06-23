package dgmp.gestionpersonnel.controller.web;

import java.util.List;

import dgmp.gestionpersonnel.controller.services.IAgentServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import dgmp.gestionpersonnel.controller.repositories.StructureRepository;
import dgmp.gestionpersonnel.controller.repositories.TypeRepository;
import dgmp.gestionpersonnel.controller.services.IStructureService;
import dgmp.gestionpersonnel.model.entities.TStructure;
import dgmp.gestionpersonnel.model.entities.TType;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/structures")
public class StructureController 
{
	private final StructureRepository strRep;
	private final IStructureService strService;
	private final TypeRepository typeRep;
	private final IAgentServices agtService;
	
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
		return "liste";
	}
	
	@GetMapping(path = "/about/{strId}")
	public String goToAboutStructure(Model model, @PathVariable Long strId)
	{
		model.addAttribute("structure", strRep.findById(strId).orElse(null));
		return "structures/about";
	}

	@GetMapping(path = "/organisation")
	public String Organisation(Model model)
	{
		List<TStructure> direction= strRep.findByNiveauStr(2);
		model.addAttribute("direction",direction);
		return "structures/liste";
	}


	@GetMapping (path = "/plusInfos")
	public String PlusInfos(@RequestParam(name = "strId", defaultValue = "") Long id, Model model)
	{
		TStructure strMere=strRep.getStrById(id);
		model.addAttribute("strMere",strMere);
		List<TStructure> sousTutelleDirecte= strService.getStrChildrenTree(id).getStrStructuresFilles();
		List<TStructure> sousTutelleDirecte2= strService.getStrChildrenTree(strService.getStrChildrenTree(id).getStrId()).getStrStructuresFilles();
		model.addAttribute("sousTutelleDirecte",sousTutelleDirecte);
		model.addAttribute("allAgtByStrId",agtService.getAllAgentsByStrId(id));
		return "structures/plusInfos";
	}

}
