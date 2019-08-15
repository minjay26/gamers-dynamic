package org.minjay.gamers.dynamic.resource.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.minjay.gamers.security.jackson.SecurityLoginJackson2Module;
import org.minjay.gamers.security.userdetails.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/dynamics/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public static LoginUserJwtAccessTokenConverter loginUserJwtAccessTokenConverter() {
        LoginUserJwtAccessTokenConverter tokenConverter = new LoginUserJwtAccessTokenConverter();
        tokenConverter.setUserTokenConverter(new LoginUserAuthenticationConverter());
        return tokenConverter;
    }

    static class LoginUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
        public static final Logger LOGGER = LoggerFactory.getLogger(LoginUserAuthenticationConverter.class);
        private static ObjectMapper objectMapper;

        static {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new SecurityLoginJackson2Module());
        }

        @SuppressWarnings("unchecked")
        @Override
        public Authentication extractAuthentication(Map<String, ?> map) {
            Authentication authentication = null;
            Map<String, Object> authenticationMap = null;
            String value = (String) map.get("sub");
            try {
                authenticationMap = objectMapper.readValue(value, Map.class);
            } catch (Exception ex) {
                LOGGER.error("extract authentication error : {}", ex.getMessage());
            }
            if (!CollectionUtils.isEmpty(authenticationMap)) {
                LoginUser loginUser = new LoginUser.LoginUserBuilder()
                        .userId(Long.parseLong(String.valueOf(authenticationMap.get("userId"))))
                        .username((String) authenticationMap.get("username"))
                        .password("PROTECT")
                        .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                                .collectionToCommaDelimitedString((ArrayList) authenticationMap.get("authorities"))))
                        .build();
                authentication = new UsernamePasswordAuthenticationToken(loginUser,
                        null, null);
            }
            return authentication;
        }
    }

    static class LoginUserJwtAccessTokenConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

        @Override
        public void configure(JwtAccessTokenConverter converter) {
            converter.setAccessTokenConverter(this);
        }
    }
}
