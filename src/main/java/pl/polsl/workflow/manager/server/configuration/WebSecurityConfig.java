package pl.polsl.workflow.manager.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.polsl.workflow.manager.server.helper.authentication.CustomAuthenticationEntryPoint;
import pl.polsl.workflow.manager.server.helper.authentication.CustomOncePerRequestFilter;
import pl.polsl.workflow.manager.server.helper.authentication.CustomUserDetailsService;
import pl.polsl.workflow.manager.server.model.Role;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final CustomOncePerRequestFilter requestFilter;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    public WebSecurityConfig(CustomAuthenticationEntryPoint authenticationEntryPoint, CustomUserDetailsService userDetailsService, @Lazy CustomOncePerRequestFilter requestFilter, BCryptPasswordEncoder passwordEncoder, DataSource dataSource) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.requestFilter = requestFilter;
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, role FROM users WHERE username = ?")
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/swagger-ui/index.html**",
                "/webjars/**",
                "/actuator/health");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/authentication/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/**").hasAuthority(Role.COORDINATOR.name())
                .antMatchers(HttpMethod.PATCH, "/user/**").hasAuthority(Role.COORDINATOR.name())
                .antMatchers(HttpMethod.POST, "/task/**").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/task/**").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/task/next/**").hasAuthority(Role.WORKER.name())
                .antMatchers(HttpMethod.GET, "/task/current/**").hasAuthority(Role.WORKER.name())
                .antMatchers(HttpMethod.GET, "/task/worker/**").hasAuthority(Role.WORKER.name())
                .antMatchers(HttpMethod.GET, "/task/manager/**").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/taskWorkerReport/**").hasAuthority(Role.WORKER.name())
                .antMatchers(HttpMethod.POST, "/taskManagerReport/**").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/localization/**").hasAuthority(Role.COORDINATOR.name())
                .antMatchers(HttpMethod.POST, "/group/**").hasAuthority(Role.COORDINATOR.name())
                .antMatchers(HttpMethod.PATCH, "/group/**").hasAuthority(Role.COORDINATOR.name())
                .antMatchers(HttpMethod.GET, "/group/all/**").hasAnyAuthority(Role.COORDINATOR.name(), Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/group/worker/**").hasAuthority(Role.WORKER.name())
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}