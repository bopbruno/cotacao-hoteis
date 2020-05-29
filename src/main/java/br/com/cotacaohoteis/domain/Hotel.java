package br.com.cotacaohoteis.domain;

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
@JsonPropertyOrder({ "id", "name", "cityCode", "cityName", "rooms" })
public class Hotel {

	@JsonProperty("id")
	private Integer id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("cityCode")
	private Integer cityCode;
	@JsonProperty("cityName")
	private String cityName;
	@JsonProperty("rooms")
	private List<Room> rooms = null;
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

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("cityCode")
	public Integer getCityCode() {
		return cityCode;
	}

	@JsonProperty("cityCode")
	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
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
	public List<Room> getRooms() {
		return rooms;
	}

	@JsonProperty("rooms")
	public void setRooms(List<Room> rooms) {
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
