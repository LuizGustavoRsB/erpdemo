package com.demoerp.erp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O CPF/CNPJ é obrigatório")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "CPF deve conter 11 dígitos ou CNPJ deve conter 14 dígitos")
    private String cpfCnpj;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter entre 10 e 11 dígitos numéricos")
    private String telefone;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
} 