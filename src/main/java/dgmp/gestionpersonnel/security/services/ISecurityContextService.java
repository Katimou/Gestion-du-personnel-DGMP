package dgmp.gestionpersonnel.security.services;
import dgmp.gestionpersonnel.model.entities.TAssignation;
import dgmp.gestionpersonnel.model.entities.TDemande;
import dgmp.gestionpersonnel.model.entities.TStructure;

import java.util.List;

public interface ISecurityContextService
{
    String getAuthUsername();
    List<String> getAuthorities();
    TAssignation getCurrentAss();
    SecurityUser getAuthUser();
    boolean hasAnyAuthority(String ...auth);
}
