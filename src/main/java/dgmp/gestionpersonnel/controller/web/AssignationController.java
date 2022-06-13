package dgmp.gestionpersonnel.controller.web;

import dgmp.gestionpersonnel.controller.repositories.TAgentRepository;
import dgmp.gestionpersonnel.controller.repositories.TAssignationRepository;
import dgmp.gestionpersonnel.controller.services.IAgentServices;
import dgmp.gestionpersonnel.controller.services.IAssService;
import dgmp.gestionpersonnel.controller.utilities.IFilesManager;
import dgmp.gestionpersonnel.model.entities.TAssignation;
import dgmp.gestionpersonnel.security.services.ISecurityContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/agents")
@Slf4j
public class AssignationController {
    private final ISecurityContextService scs;
    private final TAssignationRepository assRep;
    private final IAssService assService;
    @PostMapping(path = "/change")
    public String changeAss(@RequestParam(name = "assId") long assId)
    {
        scs.getCurrentAss().setAssCour(false);
        TAssignation tAssignation= assRep.findById(assId).orElse(null);
        tAssignation.setAssCour(true);
        tAssignation = assRep.save(tAssignation);
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(auth->System.out.println("AVANT ACTUALISATION : "+ auth));
        Authentication authentication = new UsernamePasswordAuthenticationToken(scs.getAuthUser(), null, scs.getAuthUser().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/agents/index";
    }
    @PostMapping(path = "/create")
    public String createAss(@RequestParam(defaultValue = "0") long agtId, @RequestParam(defaultValue = "0") long rleId, @RequestParam(defaultValue = "0") long strId)
    {
        assService.createAss(agtId,rleId,strId);
        return "redirect:/agents/index";
    }


}
