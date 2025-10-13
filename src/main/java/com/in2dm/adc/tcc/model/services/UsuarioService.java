package com.in2dm.adc.tcc.model.services;

import com.in2dm.adc.tcc.model.entity.Usuario;
import com.in2dm.adc.tcc.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Salvar novo usuário
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Buscar por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Buscar por email
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Verificar se CPF já existe
    public boolean cpfExiste(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    // Verificar se email já existe
    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Listar todos os usuários
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Deletar por ID
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}

