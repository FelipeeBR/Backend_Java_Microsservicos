package com.backendproject.shoppingapi.repository;

import java.util.Date;
import java.util.List;

import com.backendproject.shoppingclient.dto.ShopReportDTO;
import com.backendproject.shoppingapi.model.Shop;

public interface ReportRepository {

    public List<Shop> getShopByFilters(Date dataInicio, Date dataFim, Float valorMinimo);

    public ShopReportDTO getReportByDate(Date dataInicio, Date dataFim);
}
