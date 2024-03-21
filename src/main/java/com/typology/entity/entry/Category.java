package com.typology.entity.entry;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public enum Category
{
	@JsonProperty("Person") PERSON, 
	@JsonProperty("Fictional Character") FICTIONAL_CHARACTER, 
	@JsonProperty("Anime") ANIME, 
	@JsonProperty("Band") BAND, 
	@JsonProperty("Video Game") VIDEO_GAME, 
	@JsonProperty("Movie") MOVIE, 
	@JsonProperty("TV Show") TV_SHOW,
	@JsonProperty("Company") COMPANY,
	@JsonProperty("Geographical") GEOGRAPHICAL;
	
	private String categoryType;
	
	private Category() {}
	
	private Category(String categoryType) {
		this.categoryType = categoryType;
	}
}
