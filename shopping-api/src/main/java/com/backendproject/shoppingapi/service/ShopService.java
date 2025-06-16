package com.backendproject.shoppingapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendproject.shoppingapi.converter.DTOConverter;
import com.backendproject.shoppingapi.model.Shop;
import com.backendproject.shoppingapi.repository.ReportRepository;
import com.backendproject.shoppingapi.repository.ShopRepository;
import com.backendproject.shoppingclient.dto.ItemDTO;
import com.backendproject.shoppingclient.dto.ProductDTO;
import com.backendproject.shoppingclient.dto.ShopDTO;
import com.backendproject.shoppingclient.dto.ShopReportDTO;

@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;
    private ReportRepository reportRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    
    public List<ShopDTO> getAll() {
        List<Shop> shops = shopRepository.findAll();
        return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public List<ShopDTO> getByUser(String userIdentifier) {
        List<Shop> shops = shopRepository.findAllByUserIdentifier(userIdentifier);
        return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public List<ShopDTO> getByDate(ShopDTO shopDTO) {
        List<Shop> shops = shopRepository.findAllByDateGreaterThanEqual(shopDTO.getDate());
        return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public ShopDTO findById(Long productId) {
        Optional<Shop> shop = shopRepository.findById(productId);
        if(shop.isPresent()) {
            return DTOConverter.convert(shop.get());
        }
        return null;
    }

    public ShopDTO save(ShopDTO shopDTO, String key) { 
        if (userService.getUserByCpf(shopDTO.getUserIdentifier(), key) == null) {
            return null;
        }
        if (!validateProducts(shopDTO.getItems())) {
            return null;
        }

        shopDTO.setTotal(shopDTO.getItems().stream()
                .map(ItemDTO::getPrice)
                .reduce((float) 0, Float::sum));

        Shop shop = Shop.convert(shopDTO);
        shop = shopRepository.save(shop);
        return DTOConverter.convert(shop);
    }

    public List<ShopDTO> getShopsByFilter(Date dataInicio, Date dataFim, Float valorMinimo) {
        List<Shop> shops = reportRepository.getShopByFilters(dataInicio, dataFim, valorMinimo);
        return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public ShopReportDTO getReportByDate(Date dataInicio, Date dataFim) {
        return reportRepository.getReportByDate(dataInicio, dataFim);
    }

    private boolean validateProducts(List<ItemDTO> items) {
        for(ItemDTO item : items) {
            ProductDTO productDTO = productService.getProductByIdentifier(item.getProductIdentifier());
            if(productDTO == null) {
                return false;
            }       
            item.setPrice(productDTO.getPreco());
        }
        return true; 
    }
}
