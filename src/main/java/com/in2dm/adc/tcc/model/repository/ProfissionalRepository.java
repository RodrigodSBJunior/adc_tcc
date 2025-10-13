package com.in2dm.adc.tcc.model.repository;

import com.in2dm.adc.tcc.model.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    // Buscar por email
    Profissional findByEmail(String email);

    // Verificar se CPF já existe
    boolean existsByCpf(String cpf);

    // Verificar se email já existe
    boolean existsByEmail(String email);
}
