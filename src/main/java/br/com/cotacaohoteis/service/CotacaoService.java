package br.com.cotacaohoteis.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.cotacaohoteis.domain.Hotel;

@FeignClient(value = "cotacaoService", url = "https://cvcbackendhotel.herokuapp.com/hotels")
public interface CotacaoService {
	
	
	@GetMapping(value = "/avail/{cityCode}")
	public List<Hotel> getCotacaoHoteisCidade(@PathVariable Integer cityCode);
	
	@GetMapping(value = "/{idHotel}")
	public List<Hotel> getCotacaoHotel(@PathVariable int idHotel);

}
