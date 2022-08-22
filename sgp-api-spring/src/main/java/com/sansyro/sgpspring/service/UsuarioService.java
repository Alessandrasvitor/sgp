package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.entity.Usuario;
import com.sansyro.sgpspring.entity.dto.UsuarioRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.UsuarioRepository;
import com.sansyro.sgpspring.util.GeralUtil;
import com.sansyro.sgpspring.util.SegurancaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscar(Long id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);
        if(usuarioOp.isPresent()) {
            return usuarioOp.get();
        }
        throw new ServiceException("Usuário não encontrado");
    }

    public void salvar(Usuario usuario) {
        validarUsuarioNull(usuario);
        validarUsuarioDuplicado(usuario.getEmail());
        usuario.setSenha(validarSenha(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }

    public Usuario alterar(Long id, UsuarioRequest usuario) {
        validarUsuarioNull(usuario.mapperEntity());
        Usuario usuarioAlterado = buscar(id);
        usuarioAlterado.setNome(usuario.getNome());
        usuarioAlterado.setEmail(usuario.getEmail());
        return usuarioRepository.save(usuarioAlterado);
    }

    public Usuario alterarSenha(Long id, String senha) {
        Usuario usuarioAlterado = buscar(id);
        usuarioAlterado.setSenha(validarSenha(senha));
        return usuarioRepository.save(usuarioAlterado);
    }

    private String validarSenha(String senha) {
        if(GeralUtil.stringNullOrEmpty(senha)){
            throw new ServiceException("A Senha do usuário é obrigatória");
        }
        return SegurancaUtil.criptografarSHA256(senha);
    }

    private void validarUsuarioDuplicado(String email) {
        if(usuarioRepository.findByEmail(email) != null) {
            throw new ServiceException("Email já cadastrado na base");
        }

    }

    private void validarUsuarioNull(Usuario usuario) {

        if(GeralUtil.stringNullOrEmpty(usuario.getNome())){
            throw new ServiceException("Nome do usuário é obrigatório");
        }

        if(GeralUtil.stringNullOrEmpty(usuario.getEmail())){
            throw new ServiceException("Email do usuário é obrigatório");
        }

    }
}
