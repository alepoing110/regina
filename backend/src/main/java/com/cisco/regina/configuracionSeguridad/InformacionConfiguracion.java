package com.cisco.regina.configuracionSeguridad;

import java.util.HashMap;
import java.util.Map;

import com.cisco.regina.modelo.Personas;
import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.repositorio.PersonasRepositorio;
import com.cisco.regina.repositorio.UsuariosRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class InformacionConfiguracion implements TokenEnhancer {

    @Autowired
	PersonasRepositorio personasRepositorio;

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new HashMap<String, Object>();
        Usuarios usuario = usuariosRepositorio.findById(Long.parseLong(authentication.getName())).orElse(null);
		Personas persona = personasRepositorio.findById(usuario.getIdpersona()).orElse(null);
		info.put("nombre", persona.getPrimerapellido()+ " "+ persona.getPrimernombre());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;        

    }

}
