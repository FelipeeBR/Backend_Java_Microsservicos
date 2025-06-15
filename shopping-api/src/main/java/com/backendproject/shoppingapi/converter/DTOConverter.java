package com.backendproject.shoppingapi.converter;

import java.util.stream.Collectors;

import com.backendproject.shoppingapi.model.Item;
import com.backendproject.shoppingapi.model.Shop;
import com.backendproject.shoppingclient.dto.ItemDTO;
import com.backendproject.shoppingclient.dto.ShopDTO;

public class DTOConverter {
    public static ItemDTO convert(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProductIdentifier(
        item.getProductIdentifier());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }
    public static ShopDTO convert(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setUserIdentifier(shop.getUserIdentifier());
        shopDTO.setTotal(shop.getTotal());
        shopDTO.setDate(shop.getDate());
        shopDTO.setItems(shop
        .getItems()
        .stream()
        .map(DTOConverter::convert)
        .collect(Collectors.toList()));
        return shopDTO;
    } 
}
