package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.projections.SaleReportProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name AS sellerName " +
            "FROM tb_sales " +
            "INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id " +
            "WHERE tb_sales.date BETWEEN :dateMin AND :dateMax AND UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<SaleReportProjection> searchSaleBySeller(LocalDate dateMin, LocalDate dateMax, String name, Pageable pageable);


    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryMinDTO(obj.seller.name, SUM(obj.amount) AS total) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :dateMin AND :dateMax " +
            "GROUP BY obj.seller.name")
    List<SummaryMinDTO> searchSummarySaleBySeller(LocalDate dateMin, LocalDate dateMax);
}
