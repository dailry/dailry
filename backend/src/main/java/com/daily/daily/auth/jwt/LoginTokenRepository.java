package com.daily.daily.auth.jwt;

import com.daily.daily.auth.dto.LoginTokenDTO;
import org.springframework.data.repository.CrudRepository;

public interface LoginTokenRepository extends CrudRepository<LoginTokenDTO,String> {
}
