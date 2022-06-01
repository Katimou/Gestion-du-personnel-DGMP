/*
 * package dgmp.gestionpersonnel.security;
 * 
 * import org.springframework.context.annotation.Configuration; import
 * org.springframework.web.servlet.config.annotation.EnableWebMvc; import
 * org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
 * import
 * org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
 * import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 * 
 * @EnableWebMvc
 * 
 * @Configuration public class WebConfiguration implements WebMvcConfigurer {
 * 
 * 
 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
 * 
 * registry.addResourceHandler("/resources/**").addResourceLocations(
 * "/WEB-INF/resources/"); }
 * 
 * @Override public void addViewControllers(ViewControllerRegistry registry) {
 * registry.addViewController("/GoToLogin").setViewName("login");
 * 
 * }
 * 
 * }
 */