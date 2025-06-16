package com.backendproject.shoppingclient.dto;

import java.util.Date;
import java.util.List;

import com.backendproject.shoppingapi.model.Shop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShopDTO {
    @NotBlank
    private String userIdentifier;
    @NotNull
    private Float total;
    @NotNull
    private Date date;
    @NotNull
    private List<ItemDTO> items;


    public String getUserIdentifier() {
        return userIdentifier;
    }
    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }
    public Float getTotal() {
        return total;
    }
    public void setTotal(Float total) {
        this.total = total;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public List<ItemDTO> getItems() {
        return items;
    }
    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public static ShopDTO convert(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setUserIdentifier(shop.getUserIdentifier());
        shopDTO.setTotal(shop.getTotal());
        return shopDTO;
    }
}
