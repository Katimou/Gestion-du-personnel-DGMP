package dgmp.gestionpersonnel.security.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dgmp.gestionpersonnel.SpringContext;
import dgmp.gestionpersonnel.controller.repositories.TAssignationRepository;
import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TAssignation;
import lombok.Data;

@Data
public class SecurityUser implements UserDetails
{
	private TAgent agent;
	
	private TAssignationRepository assRep = SpringContext.getBean(TAssignationRepository.class);
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		TAssignation assCour = assRep.getAssCour(agent.getAgtUsername());
		if (assCour==null) return new ArrayList<>();
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
		grantedAuthorities.add(new SimpleGrantedAuthority(assCour.getAssRole().getRleNom()));
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return agent.getAgtPasswword();
	}

	@Override
	public String getUsername() {
		return agent.getAgtUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return agent.isAgtActive();
	}

	public SecurityUser(TAgent agent) {
		this.agent = agent;
	}

}
