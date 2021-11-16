package nl.abnambro.recipes.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(getJwtUserDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/authenticate", "/register","/swagger-ui.html").permitAll().
                anyRequest().authenticated().and().
                exceptionHandling().authenticationEntryPoint(getJwtAuthenticationEntryPoint()).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.logout(logout -> logout
                .logoutUrl("/logout")
                .addLogoutHandler((request, response, auth) -> {
                    final String requestTokenHeader = request.getHeader("Authorization");
                    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                        String jwtToken = requestTokenHeader.substring(7);
                        try {
                            String username = getJwtTokenUtil().getUsernameFromToken(jwtToken);
                            if (username != null) {
                                getJwtTokenRepository().deleteToken(username);
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Unable to get JWT Token");
                        } catch (ExpiredJwtException e) {
                            System.out.println("JWT Token has expired");
                        }
                    }
                })
        );
        httpSecurity.addFilterBefore(getJwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    public JwtAuthenticationEntryPoint getJwtAuthenticationEntryPoint() {
        return jwtAuthenticationEntryPoint;
    }

    public void setJwtAuthenticationEntryPoint(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    public UserDetailsService getJwtUserDetailsService() {
        return jwtUserDetailsService;
    }

    public void setJwtUserDetailsService(UserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    public JwtRequestFilter getJwtRequestFilter() {
        return jwtRequestFilter;
    }

    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    public JwtTokenUtil getJwtTokenUtil() {
        return jwtTokenUtil;
    }

    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public JwtTokenRepository getJwtTokenRepository() {
        return jwtTokenRepository;
    }

    public void setJwtTokenRepository(JwtTokenRepository jwtTokenRepository) {
        this.jwtTokenRepository = jwtTokenRepository;
    }
}
