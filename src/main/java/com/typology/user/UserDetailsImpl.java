package com.typology.user;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Authority;
import com.typology.exception.NotFoundException;
import com.typology.repository.AppUserRepository;
import com.typology.repository.AuthoritiesRepository;
import com.typology.service.AppUserService;
import com.typology.service.impl.AppUserServiceImpl;
import com.typology.service.impl.UserDetailsServiceImpl;

public class UserDetailsImpl implements UserDetails
{
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;


	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;

	

	
	
	public UserDetailsImpl(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities)
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(AppUser user)
	{		
		Set<GrantedAuthority> authorities = user.getAuthorities()
												.stream()
												.map(role -> new SimpleGrantedAuthority(role.getName()))
												.collect(Collectors.toSet());
		
		System.out.println("Granted authorities: " + authorities.toString());

		return new UserDetailsImpl(user.getId(), user.getName(), user.getPwd(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{ return authorities; }

	public Long getId()
	{ return id; }

	@Override
	public String getPassword()
	{ return password; }

	@Override
	public String getUsername()
	{ return username; }

	@Override
	public boolean isAccountNonExpired()
	{ return true; }

	@Override
	public boolean isAccountNonLocked()
	{ return true; }

	@Override
	public boolean isCredentialsNonExpired()
	{ return true; }

	@Override
	public boolean isEnabled()
	{ return true; }

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}