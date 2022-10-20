package com.movie.converter;

import org.springframework.stereotype.Component;

import com.model.dto.MovieDTO;
import com.movie.model.MovieModel;
import com.movie.vo.MovieVO;

@Component
public class MovieConverter {

	public MovieModel converterParaFilme(MovieDTO movieDTO) {
		
		MovieModel movieModel = new MovieModel();
		
		movieModel.setTitle(movieDTO.getTitle());
		movieModel.setYear(movieDTO.getYear());
		movieModel.setReleased(movieDTO.getReleased());
		movieModel.setRuntime(movieDTO.getRuntime());
		movieModel.setGenre(movieDTO.getGenre());
		movieModel.setLanguage(movieDTO.getLanguage());
		movieModel.setCountry(movieDTO.getCountry());
		movieModel.setPoster(movieDTO.getPoster());
		
		return movieModel;
	}
	
	public MovieVO converterParaFilmeVO(MovieModel movieModel) {
		
		MovieVO movieVO = new MovieVO();
		
		movieVO.setId(movieModel.getId());
		movieVO.setTitle(movieModel.getTitle());
		movieVO.setYear(movieModel.getYear());
		movieVO.setReleased(movieModel.getReleased());
		movieVO.setRuntime(movieModel.getRuntime());
		movieVO.setGenre(movieModel.getGenre());
		movieVO.setLanguage(movieModel.getLanguage());
		movieVO.setCountry(movieModel.getCountry());
		movieVO.setPoster(movieModel.getPoster());
		
		return movieVO;
	}

}
