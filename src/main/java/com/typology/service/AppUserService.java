package com.typology.service;

import com.typology.entity.user.AppUser;

public interface AppUserService
{
	AppUser getAppUserByName(String name);
}
