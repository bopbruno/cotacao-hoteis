package br.com.cotacaohoteis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.cotacaohoteis.domain.Hotel;
import br.com.cotacaohoteis.domain.Price;
import br.com.cotacaohoteis.domain.Room;

@Service
public class ServerController {
	
	public List<Hotel> getListaHotel(){
		
		List<Hotel> hoteis = new ArrayList<Hotel>();
		
		for(int i =0; i< 1999999; i++) {
			Hotel hotel = new Hotel();
			Room room = new Room();
			Price price = new Price();
			List<Room> rooms = new ArrayList<>();
			
			price.setAdult(15.0);
			price.setChild(10.0);
			
			room.setRoomID(1);
			room.setCategoryName("Standard");
			room.setPrice(price);			
			rooms.add(room);
			hotel.setId(1);
			hotel.setCityName("SP");
			hotel.setRooms(rooms);
			
			hoteis.add(hotel);
		}
		
		return hoteis;
	}
}