package br.com.cotacaohoteis.dto;

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
"pricePerDayAdult",
"pricePerDayChild"
})
public class PriceDetailDto {

@JsonProperty("pricePerDayAdult")
private Double pricePerDayAdult;
@JsonProperty("pricePerDayChild")
private Double pricePerDayChild;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("pricePerDayAdult")
public Double getPricePerDayAdult() {
return pricePerDayAdult;
}

@JsonProperty("pricePerDayAdult")
public void setPricePerDayAdult(Double pricePerDayAdult) {
this.pricePerDayAdult = pricePerDayAdult;
}

@JsonProperty("pricePerDayChild")
public Double getPricePerDayChild() {
return pricePerDayChild;
}

@JsonProperty("pricePerDayChild")
public void setPricePerDayChild(Double pricePerDayChild) {
this.pricePerDayChild = pricePerDayChild;
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