package com.backendproject.shoppingapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.backendproject.shoppingapi.converter.DTOConverter;
import com.backendproject.shoppingapi.model.Shop;
import com.backendproject.shoppingapi.repository.ShopRepository;
import com.backendproject.shoppingclient.dto.ItemDTO;
import com.backendproject.shoppingclient.dto.ProductDTO;
import com.backendproject.shoppingclient.dto.ShopDTO;
import com.backendproject.shoppingclient.dto.ShopReportDTO;

import org.springframework.stereotype.Service;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final ProductService productService;
    private final UserService userService;

    public ShopService(ShopRepository shopRepository, ProductService productService, UserService userService) {
        this.shopRepository = shopRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<ShopDTO> getAll() {
        List<Shop> compras = shopRepository.findAll();
        return compras.stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByUser(String userIdentifier) {
        List<Shop> compras = shopRepository.findByUserIdentifier(userIdentifier);
        return compras.stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByData(ShopDTO dto) {
        List<Shop> compras = shopRepository.findByDateGreaterThan(dto.getDate());
        return compras.stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public ShopDTO findById(long productId) {
        Optional<Shop> compra = shopRepository.findById(productId);
        return compra.map(DTOConverter::convert).orElse(null);
    }

    public ShopDTO save(ShopDTO dto, String key) {
        if (userService.getUserByCpfAndKey(dto.getUserIdentifier(), key) == null) {
            return null;
        }
        if (!validateProducts(dto.getItems())) {
            return null;
        }

        dto.setTotal(dto.getItems().stream()
                .map(ItemDTO::getPrice)
                .reduce((float) 0, Float::sum));

        Shop shop = Shop.convert(dto);
        shop = shopRepository.save(shop);
        return DTOConverter.convert(shop);
    }

    private boolean validateProducts(List<ItemDTO> itens) {
        for (ItemDTO item : itens) {
            ProductDTO productDTO = productService.getProductByIdentifier(item.getProductIdentifier());
            if (productDTO == null) {
                return false;
            }
            item.setPrice(productDTO.getPreco());
        }
        return true;
    }

    public List<ShopDTO> getShopsByFilter(Date dataInicio, Date dataFim, Float valorMinimo) {
        List<Shop> compras = shopRepository.getShopByFilters(dataInicio, dataFim, valorMinimo);
        return compras.stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());

    }

    public ShopReportDTO getReportByDate(Date dataInicio, Date dataFim) {
        return shopRepository.getReportByDate(dataInicio, dataFim);
    }
}