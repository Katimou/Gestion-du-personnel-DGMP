package dgmp.gestionpersonnel.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dgmp.gestionpersonnel.controller.repositories.TAgentRepository;
import dgmp.gestionpersonnel.model.entities.TAgent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService
{
	private final TAgentRepository agentRep;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		TAgent agent = agentRep.findByAgtUsername(username).orElseThrow(()->new UsernameNotFoundException("login ou mot de passe incorrect"));
		return new SecurityUser(agent);
	}

}
