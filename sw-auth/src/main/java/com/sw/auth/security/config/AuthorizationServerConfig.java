package com.sw.auth.security.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.sw.auth.security.core.clientdetails.ClientDetailsServiceImpl;
import com.sw.auth.security.core.userdetails.user.SysUserDetailsServiceImpl;
import com.sw.auth.security.extension.captcha.CaptchaTokenGranter;
import com.sw.auth.security.extension.refresh.PreAuthenticatedUserDetailsService;
import com.sw.common.constant.SecurityConstants;
import com.sw.common.result.Result;
import com.sw.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.security.KeyPair;
import java.util.*;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 17:41
 * @desc ??????????????????
 * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????access_token???????????????
 **/
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final ClientDetailsServiceImpl clientDetailsService;
    private final SysUserDetailsServiceImpl sysUserDetailsService;
//    private final MemberUserDetailsServiceImpl memberUserDetailsService;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * OAuth2?????????????????????
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * ???????????????authorization??????????????????token?????????????????????????????????(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // Token??????
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        // ??????????????????????????????(???????????????????????????????????????????????????????????????)????????????
        List<TokenGranter> granterList = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));

        // ????????????????????????????????????
        granterList.add(new CaptchaTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager, stringRedisTemplate
        ));

        // ???????????????????????????????????????????????????
//        granterList.add(new SmsCodeTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
//                endpoints.getOAuth2RequestFactory(), authenticationManager
//        ));

        // ????????????????????????????????????
//        granterList.add(new WechatTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
//                endpoints.getOAuth2RequestFactory(), authenticationManager
//        ));

        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .tokenGranter(compositeTokenGranter)
                /** refresh token????????????????????????????????????(true)??????????????????(false)????????????true
                 *  1 ???????????????access token?????????????????? refresh token?????????????????????????????????????????????????????????
                 *  2 ??????????????????access token?????????????????? refresh token????????????????????????refresh token??????????????????????????????????????????????????????????????????
                 */
                .reuseRefreshTokens(true)
                .tokenServices(tokenServices(endpoints))
        ;
    }


    public DefaultTokenServices tokenServices(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        // ???????????????????????????token?????????????????????ID??? UserDetailService ?????????Map
        Map<String, UserDetailsService> clientUserDetailsServiceMap = new HashMap<>();
        clientUserDetailsServiceMap.put(SecurityConstants.ADMIN_CLIENT_ID, sysUserDetailsService); // ?????????????????????
//        clientUserDetailsServiceMap.put(SecurityConstants.APP_CLIENT_ID, memberUserDetailsService); // Android???IOS???H5 ???????????????
//        clientUserDetailsServiceMap.put(SecurityConstants.WEAPP_CLIENT_ID, memberUserDetailsService); // ????????????????????????

        // ??????token?????????????????????????????????????????????AuthenticationManager??????????????????????????????ID?????????????????????????????????????????????????????????
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService<>(clientUserDetailsServiceMap));
        tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
        return tokenServices;

    }

    /**
     * ??????????????????????????????token??????
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * ???????????????????????????(??????+??????)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        KeyPair keyPair = factory.getKeyPair("jwt", "123456".toCharArray());
        return keyPair;
    }

    /**
     * JWT????????????
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = CollectionUtil.newHashMap();
            Object principal = authentication.getUserAuthentication().getPrincipal();
//            if (principal instanceof SysUserDetails) {
//                SysUserDetails sysUserDetails = (SysUserDetails) principal;
//                additionalInfo.put("userId", sysUserDetails.getUserId());
//                additionalInfo.put("username", sysUserDetails.getUsername());
//                if (StrUtil.isNotBlank(sysUserDetails.getAuthenticationMethod())) {
//                    additionalInfo.put("authenticationMethod", sysUserDetails.getAuthenticationMethod());
//                }
//            } else if (principal instanceof MemberUserDetails) {
//                MemberUserDetails memberUserDetails = (MemberUserDetails) principal;
//                additionalInfo.put("userId", memberUserDetails.getUserId());
//                additionalInfo.put("username", memberUserDetails.getUsername());
//                if (StrUtil.isNotBlank(memberUserDetails.getAuthenticationMethod())) {
//                    additionalInfo.put("authenticationMethod", memberUserDetails.getAuthenticationMethod());
//                }
//            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }


    /**
     * ?????????????????????????????????
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.HTTP_OK);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            Result result = Result.failed(ResultCode.CLIENT_AUTHENTICATION_FAILED);
            response.getWriter().print(JSONUtil.toJsonStr(result));
            response.getWriter().flush();
        };
    }

}
