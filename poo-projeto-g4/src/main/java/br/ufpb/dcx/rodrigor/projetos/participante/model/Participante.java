package br.ufpb.dcx.rodrigor.projetos.participante.model;

import org.bson.types.ObjectId;

public class Participante {

    // Funcionais
    private ObjectId id;
    private String matricula;
    private String periodoDeEntrada;
    private String curso;
    private String cpf;
    private String rg;
    private String orgaoExpedidor;
    private CategoriaParticipante categoria;

    // Contato
    private String nome;
    private String sexo;
    private String sobrenome;
    private String telefone;
    private String email;
    private String instagram;
    private String linkedIn;
    private String GitHub;
    private String pais;
    private String estado;
    private String cidade;
    private String cep;
    private String bairro;
    private String rua;
    private String numero;


    // Construtor

    public Participante() {
    }

    public Participante(ObjectId id, String matricula, String periodoDeEntrada, String curso,
                        String cpf, String rg, String orgaoExpedidor, CategoriaParticipante categoria,
                        String nome, String sexo, String sobrenome, String telefone, String email, String instagram,
                        String linkedIn, String gitHub, String pais, String estado, String cidade, String cep,
                        String bairro, String rua, String numero) {
        this.id = id;
        this.matricula = matricula;
        this.periodoDeEntrada = periodoDeEntrada;
        this.curso = curso;
        this.cpf = cpf;
        this.rg = rg;
        this.orgaoExpedidor = orgaoExpedidor;
        this.categoria = categoria;
        this.nome = nome;
        this.sexo = sexo;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.email = email;
        this.instagram = instagram;
        this.linkedIn = linkedIn;
        this.GitHub = gitHub;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.cep = cep;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }

    // Getters and Setters


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPeriodoDeEntrada() {
        return periodoDeEntrada;
    }

    public void setPeriodoDeEntrada(String periodoDeEntrada) {
        this.periodoDeEntrada = periodoDeEntrada;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getOrgaoExpedidor() {
        return orgaoExpedidor;
    }

    public void setOrgaoExpedidor(String orgaoExpedidor) {
        this.orgaoExpedidor = orgaoExpedidor;
    }

    public CategoriaParticipante getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaParticipante categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getGitHub() {
        return GitHub;
    }

    public void setGithub(String github) {
        this.GitHub = github;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }
    public void setCEP(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", periodoDeEntrada='" + periodoDeEntrada + '\'' +
                ", curso='" + curso + '\'' +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", orgaoExpedidor='" + orgaoExpedidor + '\'' +
                ", categoria=" + categoria +
                ", nome='" + nome + '\'' +
                ", sexo='" + sexo + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", instagram='" + instagram + '\'' +
                ", linkedIn='" + linkedIn + '\'' +
                ", GitHub='" + GitHub + '\'' +
                ", pais='" + pais + '\'' +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                ", cep='" + cep + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }

}