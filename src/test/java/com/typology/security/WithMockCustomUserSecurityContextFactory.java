package com.typology.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.typology.entity.user.AppUser;
 
 
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        AppUser user = MockUserUtils.getMockUser(customUser.username());
        LocalUser localUser = LocalUser.create(user, null, null, null);
        Authentication auth = new UsernamePasswordAuthenticationToken(localUser, user.getPwd(), localUser.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}