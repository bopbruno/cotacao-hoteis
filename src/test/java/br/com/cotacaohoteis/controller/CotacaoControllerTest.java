package br.com.cotacaohoteis.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.cotacaohoteis.domain.Hotel;
import br.com.cotacaohoteis.domain.Room;
import br.com.cotacaohoteis.service.CotacaoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CotacaoControllerTest {
	
	@MockBean
	private CotacaoService cotacaoService;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testBuscarHotelComSucesso() throws Exception {
		
		List<Hotel> hoteis = new ArrayList<>();
		Map<String, BigDecimal> price = new HashMap<>();
		
		price.put("adult", new BigDecimal("1184.22"));
		price.put("child", new BigDecimal("956.46"));
		
		Room room = new Room();
		room.setRoomID(0);
		room.setCategoryName("Standard");
		room.setPrice(price);
		
		List<Room> rooms = new ArrayList<Room>();
		rooms.add(room);
		
		Hotel hotel = new Hotel();
		hotel.setCityName("São Paulo");
		hotel.setId(0);
		hotel.setCityCode(9626);
		hotel.setName("Hotel Teste 0");
		hotel.setRooms(rooms);
		
		hoteis.add(hotel);
		
		BDDMockito.given(this.cotacaoService.getCotacaoHoteisCidade(Mockito.anyInt())).willReturn(hoteis);
		
		mvc.perform(MockMvcRequestBuilders.get("/hotel/cidade?cityCode=9626&checkin=27052020&checkout=28052020&quantidadeAdultos=1&quantidadeCriancas=1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].id").value(0))
		.andExpect(jsonPath("$.[0].cityName").value("São Paulo"))
		.andExpect(jsonPath("$.[0].rooms.[0].roomID").value(0))
		.andExpect(jsonPath("$.[0].rooms.[0].categoryName").value("Standard"))
		.andExpect(jsonPath("$.[0].rooms.[0].totalPrice").value("3058.11"))		
		.andExpect(jsonPath("$.[0].rooms.[0].priceDetail.pricePerDayAdult").value("1691.74"))
		.andExpect(jsonPath("$.[0].rooms.[0].priceDetail.pricePerDayChild").value("1366.37"))
		;
		
	}

}
