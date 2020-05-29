package br.com.cotacaohoteis.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "cityName", "rooms" })
public class HotelDto {

	@JsonProperty("id")
	private Integer id;
	@JsonProperty("cityName")
	private String cityName;
	@JsonProperty("rooms")
	private List<RoomDto> rooms = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("cityName")
	public String getCityName() {
		return cityName;
	}

	@JsonProperty("cityName")
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@JsonProperty("rooms")
	public List<RoomDto> getRooms() {
		return rooms;
	}

	@JsonProperty("rooms")
	public void setRooms(List<RoomDto> rooms) {
		this.rooms = rooms;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}