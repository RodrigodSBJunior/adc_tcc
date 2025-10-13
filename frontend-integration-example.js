// Integração completa para seu componente Cadastro.jsx

import axios from 'axios';

// Configuração da API
const api = axios.create({
  baseURL: 'http://localhost:8082'
});

// Função para enviar cadastro
const enviarCadastro = async (dadosFormulario) => {
  try {
    const response = await api.post('/api/cadastro', dadosFormulario);
    return { sucesso: true, dados: response.data };
  } catch (error) {
    if (error.response) {
      return { sucesso: false, erro: error.response.data };
    } else {
      return { sucesso: false, erro: 'Erro de conexão com o servidor' };
    }
  }
};

// SUBSTITUA seu handleSubmit atual por este:
const handleSubmit = async (e) => {
  e.preventDefault();
  
  // Coletar dados do formulário
  const formData = new FormData(e.target);
  const dadosFormulario = {
    nomeCompleto: formData.get('nomeCompleto'),
    cpf: formData.get('cpf'),
    telefone: formData.get('telefone'),
    email: email,
    senha: password,
    sexo: formData.get('sexo'),
    tipoUsuario: userType,
    termosAceitos: acceptTerms,
    dataNascimento: birthDate,
    especialidade: userType === 'profissional' ? formData.get('especialidade') : null
  };

  // Enviar para o backend
  const resultado = await enviarCadastro(dadosFormulario);
  
  if (resultado.sucesso) {
    alert('Cadastro realizado com sucesso!');
    // Usar redirecionamento do servidor ou fallback
    if (resultado.dados.redirectTo) {
      navigate(resultado.dados.redirectTo);
    } else if (userType === 'profissional') {
      navigate('/areaprofissional');
    } else {
      navigate('/areapaciente');
    }
  } else {
    alert(typeof resultado.erro === 'string' ? resultado.erro : 'Erro no cadastro');
  }
};

// ADICIONE os atributos name nos seus inputs:
/*
<input type="text" name="nomeCompleto" placeholder="Nome completo" required />
<input type="text" name="cpf" placeholder="CPF" required />
<input type="tel" name="telefone" placeholder="Telefone" required />
<select name="sexo" required>
  <option value="">Selecione o sexo</option>
  <option value="masculino">Masculino</option>
  <option value="feminino">Feminino</option>
  <option value="outro">Outro</option>
</select>

// Se for profissional, adicione:
{userType === 'profissional' && (
  <input type="text" name="especialidade" placeholder="Especialidade" required />
)}
*/