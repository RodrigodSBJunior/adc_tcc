package com.in2dm.adc.tcc.model.services;

import com.in2dm.adc.tcc.model.entity.Usuario;
import com.in2dm.adc.tcc.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    // Salvar novo usuário
    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            logger.info("Tentando salvar usuário: {}", usuario.getEmail());
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            logger.info("Usuário salvo com sucesso. ID: {}", usuarioSalvo.getId());
            return usuarioSalvo;
        } catch (Exception e) {
            logger.error("Erro ao salvar usuário: {}", e.getMessage(), e);
            throw e;
        }
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

