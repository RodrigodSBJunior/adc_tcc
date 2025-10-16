package com.in2dm.adc.tcc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "<h1>ADC TCC - Sistema funcionando!</h1>" +
               "<p>API disponível em:</p>" +
               "<ul>" +
               "<li><a href='/api/teste/usuarios'>Listar usuários</a></li>" +
               "<li>POST /api/cadastro - Cadastrar usuário</li>" +
               "<li>POST /api/teste/usuario-teste - Criar usuário teste</li>" +
               "</ul>";
    }
}