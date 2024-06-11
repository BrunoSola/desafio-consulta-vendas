package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.ReportMinDTO(obj.id, obj.date, obj.amount, obj.seller.name AS sellerName) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :dateMin AND :dateMax AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<ReportMinDTO> searchSaleBySeller(LocalDate dateMin, LocalDate dateMax, String name, Pageable pageable);


    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryMinDTO(obj.seller.name, SUM(obj.amount) AS total) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :dateMin AND :dateMax " +
            "GROUP BY obj.seller.name")
    List<SummaryMinDTO> searchSummarySaleBySeller(LocalDate dateMin, LocalDate dateMax);
}
