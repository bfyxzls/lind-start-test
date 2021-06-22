package com.lind.oauth.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.lind.uaa.impl.AbstractRedisClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class RedisClientDetailsService extends AbstractRedisClientDetailsService {

    @Autowired
    public RedisClientDetailsService(DataSource dataSource, StringRedisTemplate stringRedisTemplate) {
        super(dataSource, stringRedisTemplate);
    }

    @Override
    protected void loadAllClientToCache() {
        if (stringRedisTemplate.hasKey(CACHE_CLIENT_KEY)) {
            return;
        }
        log.info("将oauth_client_details全表刷入redis");
        List<ClientDetails> list;
        try {
            list = super.listClientDetails();
            if (CollectionUtils.isEmpty(list)) {
                log.error("oauth_client_details表数据为空，请检查");
                return;
            }
        } catch (Exception ex) {
            ClientDetails clientDetails = new ClientDetails() {
                @Override
                public String getClientId() {
                    return "app";
                }

                @Override
                public Set<String> getResourceIds() {
                    return ImmutableSet.of("app");
                }

                @Override
                public boolean isSecretRequired() {
                    return false;
                }

                @Override
                public String getClientSecret() {
                    return new BCryptPasswordEncoder().encode("123456");
                }

                @Override
                public boolean isScoped() {
                    return true;
                }

                @Override
                public Set<String> getScope() {
                    return ImmutableSet.of("app");
                }

                @Override
                public Set<String> getAuthorizedGrantTypes() {
                    return ImmutableSet.of("password");
                }

                @Override
                public Set<String> getRegisteredRedirectUri() {
                    return null;
                }

                @Override
                public Collection<GrantedAuthority> getAuthorities() {
                    return null;
                }

                @Override
                public Integer getAccessTokenValiditySeconds() {
                    return null;
                }

                @Override
                public Integer getRefreshTokenValiditySeconds() {
                    return null;
                }

                @Override
                public boolean isAutoApprove(String s) {
                    return false;
                }

                @Override
                public Map<String, Object> getAdditionalInformation() {
                    return null;
                }
            };
            list = Arrays.asList(clientDetails);
        }

        list.parallelStream().forEach(client -> {
            try {
                stringRedisTemplate.boundHashOps(CACHE_CLIENT_KEY).put(client.getClientId(), new ObjectMapper().writeValueAsString(client));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
