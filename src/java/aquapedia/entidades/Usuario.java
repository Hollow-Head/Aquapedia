/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aquapedia.entidades;

/**
 *
 * @author Aloísio Marques Lingo Filho BV3032558
 */
public class Usuario {
    
    private int id;
    private String nome;
    private String email;
    private String senha;
    private int progresso;
    private int foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getProgresso() {
        return progresso;
    }

    public void setProgresso(int progresso) {
        this.progresso = progresso;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
    
    
}
