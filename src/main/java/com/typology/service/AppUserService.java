package com.typology.service;

import java.util.Optional;

import com.typology.entity.user.AppUser;

public interface AppUserService
{
	Optional<AppUser> getAppUserByName(String name);
	AppUser saveAppUser(AppUser appUser);
	AppUser findById(long id);
}
