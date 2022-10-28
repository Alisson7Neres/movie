package com.movie.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movie.vo.MovieOMDB;

@FeignClient(name = "OMDB", url = "https://www.omdbapi.com/")
public interface MovieFeign {

	@GetMapping
	MovieOMDB getMovie(@RequestParam("t") String tema, @RequestParam("apiKey") String key);
	
	@GetMapping
	MovieOMDB getRandomMovie(@RequestParam("i") String imdbID, @RequestParam("apiKey") String key);
}
