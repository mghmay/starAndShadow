package com.starAndShadow.may.sakila.service;

import com.starAndShadow.may.sakila.dto.FilmDTO;
import com.starAndShadow.may.sakila.model.Film;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FilmService {
    List<FilmDTO> getAllFilms(String category, String title, Integer pageNo, Integer pageSize, String sortBy);
    FilmDTO getFilmById(Integer id);
    Film saveFilm(FilmDTO film);
    FilmDTO updateFilmById(Integer id, Map changes);
    void deleteFilmById(Integer id);
}
