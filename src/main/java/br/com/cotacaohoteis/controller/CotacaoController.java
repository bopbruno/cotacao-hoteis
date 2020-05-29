package br.com.cotacaohoteis.controller;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.cotacaohoteis.domain.Hotel;
import br.com.cotacaohoteis.domain.Room;
import br.com.cotacaohoteis.dto.HotelDto;
import br.com.cotacaohoteis.dto.RoomDto;
import br.com.cotacaohoteis.service.CotacaoService;
import io.swagger.annotations.ApiParam;

@RestController
public class CotacaoController {
	
	@Autowired
	private CotacaoService cotacaoService;
	
	private BigDecimal VALOR_COMISSAO = new BigDecimal("0.7");
	
	@GetMapping(value = "/hotel/cidade")
	public List<HotelDto> getContacaoHoteisCidade(@RequestParam(required = true) int cityCode,
			@ApiParam(value = "ddMMyyyy",required = true) @RequestParam(required = true) @DateTimeFormat(pattern="ddMMyyyy") Date checkin,
			@ApiParam(value = "ddMMyyyy",required = true) @RequestParam(required = true) @DateTimeFormat(pattern="ddMMyyyy") Date checkout,
			@RequestParam(required = true) int quantidadeAdultos,
			@RequestParam(required = true) int quantidadeCriancas) {
		
		if(checkout.before(checkin)) {
			throw new ResponseStatusException(
			    	HttpStatus.BAD_REQUEST, "Data checkout anterior a data checkin");
		}
		
		long diffInMillies = Math.abs(checkout.getTime() - checkin.getTime());
		
   	    int quantidadeDias = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		List<Hotel> hoteis = cotacaoService.getCotacaoHoteisCidade(cityCode);
		
		if(hoteis.isEmpty()) {
			throw new ResponseStatusException(
			    	HttpStatus.NOT_FOUND, "Cidade com id "+cityCode+" não encontrada");
		}
		
		List<HotelDto> hoteisDto = new ArrayList<HotelDto>();
		
		hoteisDto = hoteis.stream().map(hotel -> converterHotelParaHotelDto(hotel,quantidadeAdultos,quantidadeCriancas,quantidadeDias)).collect(Collectors.toList());
		
		return hoteisDto;
	}

	@GetMapping(value = "/hotel")
	public HotelDto getCotacaoHotel(@RequestParam(required = true) int idHotel,
			@ApiParam(value = "ddMMyyyy",required = true) @RequestParam(required = true) @DateTimeFormat(pattern="ddMMyyyy") Date checkin,
			@ApiParam(value = "ddMMyyyy",required = true) @RequestParam(required = true) @DateTimeFormat(pattern="ddMMyyyy") Date checkout,
			@RequestParam(required = true) int quantidadeAdultos,
			@RequestParam(required = true) int quantidadeCriancas) {
		
		if(checkout.before(checkin)) {
			return new HotelDto();
		}
				
		long diffInMillies = Math.abs(checkout.getTime() - checkin.getTime());
   	    int quantidadeDias = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
   	    List<Hotel> hoteis = cotacaoService.getCotacaoHotel(idHotel);
   	    
   	    if(hoteis.isEmpty()) {
   	    	throw new ResponseStatusException(
   	             HttpStatus.NOT_FOUND, "Hotel com id "+idHotel+" não encontrado");
   	    }
		
		Hotel hotel = hoteis.get(0);
		
		HotelDto hotelDto = this.converterHotelParaHotelDto(hotel,quantidadeAdultos,quantidadeCriancas,quantidadeDias);
		
		return hotelDto;
	}
	
	private HotelDto converterHotelParaHotelDto(Hotel hotel, int quantidadeAdulto, int quantidadeCrianca, int quantidadeDias) {
		
		HotelDto hotelDto = new HotelDto();
		
		hotelDto.setId(hotel.getId());
		hotelDto.setCityName(hotel.getCityName());
		hotelDto.setRooms(this.converterRoomsParaRoomsDto(hotel.getRooms(),quantidadeAdulto,quantidadeCrianca,quantidadeDias));
		
		return hotelDto;
	}

	private List<RoomDto> converterRoomsParaRoomsDto(List<Room> rooms, int quantidadeAdulto, int quantidadeCrianca, int quantidadeDias) {
		List<RoomDto> roomDtos = new ArrayList<RoomDto>();
		
		int tamanho = rooms.size();
		
		for(int i=0;i<tamanho;i++) {
			
			Room room = rooms.remove(0);
			
			Map<String, BigDecimal> priceDetailDto = new HashMap<>();
			
			BigDecimal precoPorDiaAdulto = room.getPrice().get("adult");
			BigDecimal precoPorDiaCrianca = room.getPrice().get("child");
			
			//calcula a comissao
			precoPorDiaAdulto = precoPorDiaAdulto.divide(VALOR_COMISSAO, 2,RoundingMode.FLOOR);
			precoPorDiaCrianca = precoPorDiaCrianca.divide(VALOR_COMISSAO, 2,RoundingMode.FLOOR);
			
			priceDetailDto.put("pricePerDayAdult", precoPorDiaAdulto);
			priceDetailDto.put("pricePerDayChild", precoPorDiaCrianca);
			
			BigDecimal precoTotalPorAdulto = precoPorDiaAdulto.multiply(new BigDecimal(quantidadeDias)).multiply(new BigDecimal(quantidadeAdulto));
			BigDecimal precoTotalPorCrianca = precoPorDiaCrianca.multiply(new BigDecimal(quantidadeDias)).multiply(new BigDecimal(quantidadeCrianca));
			
			BigDecimal precoTotal = precoTotalPorAdulto.add(precoTotalPorCrianca);
			
			RoomDto roomDto = new RoomDto();
			roomDto.setRoomID(room.getRoomID());
			roomDto.setCategoryName(room.getCategoryName());
			roomDto.setTotalPrice(precoTotal);
			roomDto.setPriceDetail(priceDetailDto);
			
			roomDtos.add(roomDto);
		}
		
		return roomDtos;
	}	

}
