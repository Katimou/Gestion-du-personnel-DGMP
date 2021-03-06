package dgmp.gestionpersonnel.controller.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import dgmp.gestionpersonnel.controller.repositories.AssignationRepository;
import dgmp.gestionpersonnel.controller.repositories.MouvementRepository;
import dgmp.gestionpersonnel.controller.services.IMouvementService;
import dgmp.gestionpersonnel.model.entities.TMouvement;
import dgmp.gestionpersonnel.security.services.ISecurityContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import dgmp.gestionpersonnel.controller.repositories.AgentRepository;
import dgmp.gestionpersonnel.controller.services.IAgentServices;
import dgmp.gestionpersonnel.controller.utilities.IFilesManager;
import dgmp.gestionpersonnel.model.entities.TAgent;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/agents")
@Slf4j
public class AgentController {

//	private final AgentController agentService;
	private final AgentRepository agentRep;
	private final IFilesManager filesManager;
	private final IAgentServices agentServices;
	private final ISecurityContextService scs;
	private final AssignationRepository assRep;
    private final MouvementRepository mvtRep;
	/*
	 * @GetMapping(path="/index") public String goToIndex(Model model) {
	 * List<ChefServce> agentServices=agentService.getAgent();
	 * model.addAttribute("agentServices",agentServices); return "agents/index"; }
	 */

	@GetMapping(path = "/index")
	public String getListAgents(Model model) {

		model.addAttribute("agents", agentRep.findByAgtActiveTrue());
		return "agents/index";
	}

	@GetMapping(path = "/agents-by-fonction/{fonction}")
	public String getAgentByFonction(@PathVariable String fonction, Model model) {
		model.addAttribute("agents", agentRep.findByAgtFonction(fonction));
		return "";
	}

	@GetMapping(path = "/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "redirect:/agents/login";
	}

	@GetMapping(path = "/GoToaddAgent")
	public String goToAdd(Model model) {
		model.addAttribute("agent", new TAgent());
		return "agents/addAgent";
	}

	@PostMapping(path = "/saveAgent")
	public String goToSave(TAgent agent, Model model) throws IOException {
		
		agent = agentServices.createAgent(agent);
		return "redirect:/agents/addSuccess?agtId=" + agent.getAgtId();
	}
	@GetMapping(path = "/GoToListeAgent")
	@PreAuthorize("hasAnyAuthority('RH', 'RESPONSABLE', 'SECRETAIRE')")
	public String goToListe(Model model) {
		model.addAttribute("agent", agentServices.getAllAgentsByStrId(scs.getCurrentAss().getAssStruct().getStrId()));
		return "agents/listAgent";
	}

	@GetMapping(path = "/gotoUpdateForm")
	public String goToUpdateForm(@RequestParam(name = "id", defaultValue = "") Long id, Model model) {
		TAgent agent = agentRep.findById(id).orElse(null);
		model.addAttribute("agents", agent);
		return "agents/updateForm";
	}

	@GetMapping(path = "/goToConsult")
	public String goToConsult(@RequestParam(name = "idAgent", defaultValue = "") Long id, Model model) {
		TAgent agent = agentRep.findById(id).orElse(null);
		TMouvement mvt= mvtRep.findByMvtAgent_AgtId(id);
		if (agent == null)
		return "redirect:/agents/GoToListeAgent";
		model.addAttribute("agents", agent);
		model.addAttribute("mvt",mvt);
//		model.addAttribute("agentPhoto", filesManager.downloadFile(agent.getAgtPhotoPath()));
		return "agents/aboutAgent";
	}

	@PostMapping(path = "/update")
	public String Update(@ModelAttribute TAgent agent, @RequestParam(name = "idAgent", defaultValue = "") Long id,
			Model model) {

		agent.setAgtActive(true);
		System.out.println("agentId" + ' ' + id);
		agent = agentRep.save(agent);
        return "redirect:/agents/updateSuccess?agtId=" + agent.getAgtId();
	}

	@GetMapping(path = "/search")
	public String list(@RequestParam(name = "keyword", defaultValue = "") String nom, Model model)

	{
		System.out.println(nom);
		// List<TAgent> agent = agentRep.findByAgtNom(nom);
		List<TAgent> agent = agentRep.findByKeyWord(nom);
		model.addAttribute("agent", agent);
		model.addAttribute("keyword", "");
		return "agents/listeAgent";

	}

	@GetMapping(path = "/delete")
	public String Delete(@RequestParam(name = "idAgent", defaultValue = "0") Long idAgent, Model model) {
		System.out.println("idAgent=" + idAgent);
		TAgent agent = agentRep.findById(idAgent).orElse(null);
		if (agent == null)
			return "redirect:/agents/index";
		agent.setAgtActive(false);
		agentRep.save(agent);
		return "redirect:/agents/GoToListeAgent";
	}

	@GetMapping(path = "/accueil")
	public String GoToAccueil() {
		return "agents/accueil";
	}

	@GetMapping(path = "/login")
	public String goToLogin(Model model, @RequestParam(name = "error", defaultValue = "false") boolean hasError) {
		model.addAttribute("error", hasError);
		return "agents/login";
	}


	/*
	 * @PostMapping(path = "/loginTest") public String
	 * goToTestLogin(@RequestParam(name = "agtUsername", defaultValue = "0") String
	 * username ) { TAgent agt=agentRep.findByAgtUsername(username).orElse(null);
	 * if(agt!= null) { return "redirect:/agents/accueil"; } return "agents/login";
	 */
	

	@PostMapping(path = "/loginValidation")
	public String GoToValidate() {

		return "agents/accueil";
	}
	@GetMapping(path = "/addSuccess")
	public String GoToSuccess(@RequestParam(name = "agtId", defaultValue = "") Long id, Model model) {
		TAgent agent = agentRep.findById(id).orElse(null);
		model.addAttribute("agent", agent);
		return "agents/SucessSave";
	}

	@GetMapping(path = "/updateSuccess")
	public String GoToUpateSucess(@RequestParam(name = "agtId", defaultValue = "") Long id, Model model) {
		TAgent agent = agentRep.findById(id).orElse(null);
		model.addAttribute("agent", agent);
		return "agents/updateSuccess";
	}
	
	@GetMapping(path = "/displayPhoto/{agentId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
	@ResponseBody
	public byte[] displayPhoto(@PathVariable Long agentId) {
		
		TAgent agent = agentRep.findById(agentId).orElse(null);
		if(agent==null) return null;
		return filesManager.downloadFile(agent.getAgtPhotoPath());
	}

	@GetMapping(path = "/gotoChangeAss")
	public String gotoChangeAss(Model model)
	{
		model.addAttribute("assignations", assRep.findByUsername(scs.getAuthUsername()));
		return "/agents/changeAss";
	}


	
	/*
	 * @GetMapping(path = "/updateSuccess") public String GoToUpdateSuccess(Model
	 * model) { List<TAgent> agent = agentRep.findAllById(agtId);
	 * model.addAttribute("agents",agent); return "agents/SucessSave";
	 */

}
