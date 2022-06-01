package dgmp.gestionpersonnel.security.services;

import dgmp.gestionpersonnel.controller.repositories.TAssignationRepository;
import dgmp.gestionpersonnel.model.entities.TAssignation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("scs")
public class SecurityContextService implements ISecurityContextService
{
    private final UserDetailsService userDetailsService;
    private final TAssignationRepository assRep;
    @Override
    public String getAuthUsername()
    {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<String> getAuthorities()
    {
        Collection<? extends GrantedAuthority> authorities = userDetailsService.loadUserByUsername(this.getAuthUsername()).getAuthorities();
        return authorities.stream().map(auth->auth.getAuthority()).collect(Collectors.toList());
    }

    @Override
    public TAssignation getCurrentAss() {
        return assRep.getAssCour(getAuthUsername());
    }

    @Override
    public SecurityUser getAuthUser() {
        return (SecurityUser) userDetailsService.loadUserByUsername(getAuthUsername());
    }

    @Override
    public boolean hasAnyAuthority(String... auths) {
        return Arrays.stream(auths).anyMatch(auth1->this.getAuthorities().contains(auth1));
    }


}
