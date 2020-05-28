package br.com.cotacaohoteis.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"roomID",
"categoryName",
"price"
})
public class Room {

@JsonProperty("roomID")
private Integer roomID;
@JsonProperty("categoryName")
private String categoryName;
@JsonProperty("price")
private Map<String, BigDecimal> price;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("roomID")
public Integer getRoomID() {
return roomID;
}

@JsonProperty("roomID")
public void setRoomID(Integer roomID) {
this.roomID = roomID;
}

@JsonProperty("categoryName")
public String getCategoryName() {
return categoryName;
}

@JsonProperty("categoryName")
public void setCategoryName(String categoryName) {
this.categoryName = categoryName;
}

@JsonProperty("price")
public Map<String, BigDecimal> getPrice() {
return price;
}

@JsonProperty("price")
public void setPrice(Map<String, BigDecimal> price) {
this.price = price;
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