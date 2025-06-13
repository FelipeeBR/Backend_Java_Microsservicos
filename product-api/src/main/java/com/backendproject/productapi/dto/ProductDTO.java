package com.backendproject.productapi.dto;

import com.backendproject.productapi.model.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

    @NotBlank
    private String productIndentifier;
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private Float preco;
    @NotNull
    private CategoryDTO category;


    public String getProductIndentifier() {
        return productIndentifier;
    }
    public void setProductIndentifier(String productIndentifier) {
        this.productIndentifier = productIndentifier;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Float getPreco() {
        return preco;
    }
    public void setPreco(Float preco) {
        this.preco = preco;
    }
    public CategoryDTO getCategory() {
        return category;
    }
    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public static ProductDTO convert(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNome(product.getNome());
        productDTO.setPreco(product.getPreco());
        productDTO.setProductIndentifier(product.getProductIdentifier());
        productDTO.setDescricao(product.getDescricao());
        if(product.getCategory() != null) {
            productDTO.setCategory(CategoryDTO.convert(product.getCategory()));
        }
        return productDTO;
    }
}
