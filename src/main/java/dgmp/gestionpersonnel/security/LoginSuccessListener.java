package dgmp.gestionpersonnel.security;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


//@Component
public class LoginSuccessListener extends SimpleUrlAuthenticationSuccessHandler
{
	/*@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException 
	{
		System.out.println("======login success=====");
		//super.handle(request, response, authentication);
	}*/
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException 
	{
		System.out.println("======Referer=====" + request.getHeader("Authorization"));
		//System.out.println("======getPathInfo=====" + request.getPathInfo());
		//System.out.println("======getPathTranslated=====" + request.getPathTranslated());
		//System.out.println("======getServletPath=====" + request.getServletPath());
		//super.onAuthenticationSuccess(request, response, authentication);
		if(response.isCommitted()) return;
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		//redirectStrategy.sendRedirect(request, response, request.getHeader("Referer"));
		//if()
		redirectStrategy.sendRedirect(request, response, "/agents/choix-assignation");
			
	}
}
