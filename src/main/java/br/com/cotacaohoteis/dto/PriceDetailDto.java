package br.com.cotacaohoteis.dto;

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
"pricePerDayAdult",
"pricePerDayChild"
})
public class PriceDetailDto {

@JsonProperty("pricePerDayAdult")
private BigDecimal pricePerDayAdult;
@JsonProperty("pricePerDayChild")
private BigDecimal pricePerDayChild;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("pricePerDayAdult")
public BigDecimal getPricePerDayAdult() {
return pricePerDayAdult;
}

@JsonProperty("pricePerDayAdult")
public void setPricePerDayAdult(BigDecimal pricePerDayAdult) {
this.pricePerDayAdult = pricePerDayAdult;
}

@JsonProperty("pricePerDayChild")
public BigDecimal getPricePerDayChild() {
return pricePerDayChild;
}

@JsonProperty("pricePerDayChild")
public void setPricePerDayChild(BigDecimal pricePerDayChild) {
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