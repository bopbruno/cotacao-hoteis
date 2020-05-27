package br.com.cotacaohoteis.domain;

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
"adult",
"child"
})
public class Price {

@JsonProperty("adult")
private Double adult;
@JsonProperty("child")
private Double child;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("adult")
public Double getAdult() {
return adult;
}

@JsonProperty("adult")
public void setAdult(Double adult) {
this.adult = adult;
}

@JsonProperty("child")
public Double getChild() {
return child;
}

@JsonProperty("child")
public void setChild(Double child) {
this.child = child;
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