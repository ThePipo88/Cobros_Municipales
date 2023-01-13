package org.una.municipalidad.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.una.municipalidad.entities.Usuario;
import org.una.municipalidad.repositories.IUsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IUserDetailsService   {

    UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException;

}
