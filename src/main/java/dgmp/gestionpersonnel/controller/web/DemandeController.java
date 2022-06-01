package dgmp.gestionpersonnel.controller.web;
import java.util.List;

import dgmp.gestionpersonnel.controller.repositories.TDemandeRepository;
import dgmp.gestionpersonnel.security.services.ISecurityContextService;
import dgmp.gestionpersonnel.security.services.SecurityUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dgmp.gestionpersonnel.controller.repositories.TTypeRepository;
import dgmp.gestionpersonnel.controller.services.IDemandeAServices;
import dgmp.gestionpersonnel.model.entities.TDemande;
import dgmp.gestionpersonnel.model.entities.TType;
import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/demandes")
public class DemandeController {
private final IDemandeAServices demandeAServices;
private final TDemandeRepository dmeRep;
private final TTypeRepository typeRep;
private final ISecurityContextService scs;
	
@GetMapping(path = "/GoToFormDemande")
	public String goToFormDmde(Model model) {
	List<TType> typeDmdeAbs=typeRep.findSousType(typeRep.findByTypNom("DEMANDE_ABSENCE").getTypId());
	typeDmdeAbs.removeIf(typ->typ.getTypNom().equalsIgnoreCase("DEMANDE_SORTIE_TERRITOIRE"));
	 model.addAttribute("typeDmdeAbs", typeDmdeAbs);
	 model.addAttribute("demande", new TDemande());
		return "DemandeA/absence";
	}
	@GetMapping(path = "/GoToFormDemandeSortie")
	public String goToFormDemandeSortie(Model model) {

	TType typeDmdeAbs=typeRep.findByTypNom("DEMANDE_SORTIE_TERRITOIRE");
	model.addAttribute("typeDmdeAbs", typeDmdeAbs);
	model.addAttribute("demande", new TDemande());

		return "DemandeA/sortieDuTerritoire";
	}



	@PostMapping(path = "/save")
    public String saveDemandeA(@ModelAttribute TDemande demande, Model model) {
	  	TDemande dme = demandeAServices.saveDemande(demande);
	  return "redirect:/demandes/GoToDmeWorkflow?DmeId";
}

	@PostMapping(path = "/saveDemandeSortie")
	public String saveSortie(@ModelAttribute TDemande demande, Model model) {
		TDemande dme = demandeAServices.saveDemande(demande);
		return "redirect:/demandes/GoToDmeWorkflow?DmeId";
	}
   @GetMapping(path = "/GoToDmeWorkflow")
   public String GoToDmeWorkflow() {
	  
	   return "DemandeA/demandeWorkflow";
	
	
}
   
   @GetMapping(path = "/goToListeDemande")
	public String GoToSuccess(Model model) {
	   List<TDemande> demande= demandeAServices.findDemande();
		model.addAttribute("demandes", demande);
		return "DemandeA/listeDemande";
	}

	@GetMapping(path = "/goToMesDemandes")
	public String goToMesDemandes(Model model) {
		List<TDemande> demandes= dmeRep.findByDemandeur(scs.getAuthUsername());
		model.addAttribute("demandes", demandes);
		return "DemandeA/listeDemande";
	}

   @GetMapping(path = "/ListeDemande")
	public String GoToListeAbscence() {
	   

		return "redirect:/demandes/goToListeDemande";
	}
   

}
