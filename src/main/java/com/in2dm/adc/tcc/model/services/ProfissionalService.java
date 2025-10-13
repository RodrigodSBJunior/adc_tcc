package com.in2dm.adc.tcc.model.services;

import com.in2dm.adc.tcc.model.entity.Profissional;
import com.in2dm.adc.tcc.model.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    @Autowired
    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    // Salvar novo profissional
    public Profissional salvar(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }

    // Buscar por ID
    public Optional<Profissional> buscarPorId(Long id) {
        return profissionalRepository.findById(id);
    }

    // Buscar por email
    public Profissional buscarPorEmail(String email) {
        return profissionalRepository.findByEmail(email);
    }

    // Verificar se CPF já existe
    public boolean cpfExiste(String cpf) {
        return profissionalRepository.existsByCpf(cpf);
    }

    // Verificar se email já existe
    public boolean emailExiste(String email) {
        return profissionalRepository.existsByEmail(email);
    }

    // Listar todos os profissionais
    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    // Deletar por ID
    public void deletar(Long id) {
        profissionalRepository.deleteById(id);
    }
}
