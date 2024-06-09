package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleReportProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<ReportMinDTO> reportSaleBySeller(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate dateMax = getMaxDate(maxDate);
		LocalDate dateMin = getMinDate(minDate, dateMax);
		Page<SaleReportProjection> projectionPage = repository.searchSaleBySeller(dateMin, dateMax, name, pageable);
		return projectionPage.map(x -> new ReportMinDTO(x));
	}


	private LocalDate getMaxDate(String date){
		LocalDate maxDate;
		if (date.isEmpty()){
			maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		else {
			maxDate = LocalDate.parse(date, format);
		}
		return maxDate;
	}

	private LocalDate getMinDate(String date, LocalDate dateMax){
		LocalDate minDate;
		if (date.isEmpty()){
			minDate = dateMax.minusYears(1L);
		}
		else {
			minDate = LocalDate.parse(date, format);
		}
		return minDate;
	}
}
