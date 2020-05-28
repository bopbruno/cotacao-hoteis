package br.com.cotacaohoteis.controller;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotacaohoteis.domain.Hotel;
import br.com.cotacaohoteis.domain.Room;
import br.com.cotacaohoteis.dto.HotelDto;
import br.com.cotacaohoteis.dto.PriceDetailDto;
import br.com.cotacaohoteis.dto.RoomDto;
import br.com.cotacaohoteis.service.CotacaoService;
import io.swagger.annotations.ApiParam;

@RestController
public class CotacaoController {
	
	@Autowired
	private CotacaoService cotacaoService;
	
	@Autowired
	private ServerController sc;
	
	@GetMapping(value = "/hotel/cidade/{cityCode}")
	public List<HotelDto> getContacaoHoteisCidade(@PathVariable int cityCode,
			@ApiParam(value = "ddMMyyyy",required = true) @RequestParam(required = true) @DateTimeFormat(pattern="ddMMyyyy") Date checkin,
			@ApiParam(value = "ddMMyyyy",required = true) @RequestParam(required = true) @DateTimeFormat(pattern="ddMMyyyy") Date checkout,
			@RequestParam(required = true) int quantidadeAdultos,
			@RequestParam(required = true) int quantidadeCriancas) {
		
		long diffInMillies = Math.abs(checkout.getTime() - checkin.getTime());
   	    int quantidadeDias = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		
		List<Hotel> hoteis = cotacaoService.getCotacaoHoteisCidade(cityCode);
   	    //List<Hotel> hoteis = sc.getListaHotel();
		List<HotelDto> hoteisDto = new ArrayList<HotelDto>();
		long start = System.nanoTime();
		int tamanho = hoteis.size();
		for(int i =0; i < tamanho; i++) {
			hoteisDto.add(this.converterHotelParaHotelDto(hoteis.get(i),quantidadeAdultos,quantidadeCriancas,quantidadeDias));
		}
		
		long finish = System.nanoTime();
		long timeElapsed = finish - start;
		System.out.println((double)timeElapsed / 1_000_000_000.0);
		return hoteisDto;
	}

	@GetMapping(value = "/hotel/{idHotel}")
	public HotelDto getCotacaoHotel(@PathVariable int idHotel,
			@ApiParam(value = "ddMMyyyy",required = true) @RequestParam(required = true) @DateTimeFormat(pattern="ddMMyyyy") Date checkin,
			@ApiParam(value = "ddMMyyyy",required = true) @RequestParam(required = true) @DateTimeFormat(pattern="ddMMyyyy") Date checkout,
			@RequestParam(required = true) int quantidadeAdultos,
			@RequestParam(required = true) int quantidadeCriancas) {
		
		long diffInMillies = Math.abs(checkout.getTime() - checkin.getTime());
   	    int quantidadeDias = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		
		Hotel hotel = cotacaoService.getCotacaoHotel(idHotel).get(0);
		
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
			
			Room room = rooms.get(i);
			
			PriceDetailDto priceDetailDto = new PriceDetailDto();
			
			BigDecimal valorComissao = new BigDecimal("0.7");
			
			BigDecimal precoPorDiaAdulto = BigDecimal.valueOf(room.getPrice().getAdult());
			BigDecimal precoPorDiaCrianca = BigDecimal.valueOf(room.getPrice().getChild());
			
			precoPorDiaAdulto = precoPorDiaAdulto.divide(valorComissao, 2,RoundingMode.FLOOR);
			precoPorDiaCrianca = precoPorDiaCrianca.divide(valorComissao, 2,RoundingMode.FLOOR);
			
			priceDetailDto.setPricePerDayAdult(precoPorDiaAdulto);
			priceDetailDto.setPricePerDayChild(precoPorDiaCrianca);
			
			BigDecimal precoTotal = precoPorDiaAdulto.multiply(new BigDecimal(quantidadeAdulto).multiply(new BigDecimal(quantidadeDias))).add(precoPorDiaCrianca.multiply(new BigDecimal(quantidadeCrianca).multiply(new BigDecimal(quantidadeDias))));
			
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
