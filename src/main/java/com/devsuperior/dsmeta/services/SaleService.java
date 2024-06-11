package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
		Page<ReportMinDTO> dto = repository.searchSaleBySeller(dateMin, dateMax, name, pageable);
		return dto;
	}

	public List<SummaryMinDTO> summarySaleBySeller(String minDate, String maxDate) {
		LocalDate dateMax = getMaxDate(maxDate);
		LocalDate dateMin = getMinDate(minDate, dateMax);
		List<SummaryMinDTO> dtos = repository.searchSummarySaleBySeller(dateMin, dateMax);
		return dtos;
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
