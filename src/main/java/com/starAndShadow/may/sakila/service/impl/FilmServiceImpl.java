package com.starAndShadow.may.sakila.service.impl;

import com.starAndShadow.may.sakila.dto.FilmDTO;
import com.starAndShadow.may.sakila.exception.ResourceNotFoundException;
import com.starAndShadow.may.sakila.model.Actor;
import com.starAndShadow.may.sakila.model.Category;
import com.starAndShadow.may.sakila.model.Film;
import com.starAndShadow.may.sakila.model.Inventory;
import com.starAndShadow.may.sakila.repository.FilmRepository;
import com.starAndShadow.may.sakila.service.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmRepository filmRepository;

    public List<FilmDTO> getAllFilms(String category,
                                     String title,
                                     Integer pageNo,
                                     Integer pageSize,
                                     String sortBy) throws ResourceNotFoundException {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Film> pagedResult = filmRepository.findByTitleAndFilmCategoryNameContainingIgnoreCase(category, title, paging);

        if(pagedResult.hasContent()) {
            return pagedResult
                    .getContent()
                    .stream()
                    .map(this::convertEntityToDTO)
                    .toList();
        } else {
            throw new ResourceNotFoundException("films", "category and title", String.format("%s, %s", category, title));
        }
    }

    public FilmDTO getFilmById(Integer id) throws ResourceNotFoundException {
        Optional<Film> response = filmRepository.findById(id);
        Film film;
        if (response.isPresent()) {
            film = response.get();
        } else {
            throw new ResourceNotFoundException("films", "id", id);
        }
        return this.convertEntityToDTO(film);
    }
    public FilmDTO updateFilmById(Integer id, Map changes) throws ResourceNotFoundException {
        Optional<Film> response = filmRepository.findById(id);
        Film film;
        if (response.isPresent()) {
            film = response.get();
        } else {
            throw new ResourceNotFoundException("films", "id", id);
        }
        this.update(film, changes);
        filmRepository.save(film);
        return this.convertEntityToDTO(film);
    }
    public void deleteFilmById(Integer id) throws ResourceNotFoundException {
        Optional<Film> response = filmRepository.findById(id);
        Film film;
        if (response.isPresent()) {
            film = response.get();
        } else {
            throw new ResourceNotFoundException("films", "id", id);
        }
        filmRepository.deleteById(film.getFilmId());
    }
    public Film saveFilm(FilmDTO filmDTO) {
        Film film = this.convertDTOToEntity(filmDTO);
        LocalDateTime now = LocalDateTime.now();
        film.setLastUpdate(String.valueOf(now));
        return filmRepository.save(film);
    };
    private FilmDTO convertEntityToDTO(Film film) {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setFilmId(film.getFilmId());
        filmDTO.setLength(film.getLength());
        filmDTO.setTitle(film.getTitle());
        filmDTO.setLanguage(film.getLanguage().getName());
        filmDTO.setRentalRate(film.getRentalRate());
        filmDTO.setRentalDuration(film.getRentalDuration());
        filmDTO.setRating(film.getRating());
        filmDTO.setReleaseYear(film.getReleaseYear());
        filmDTO.setDescription(film.getDescription());
        filmDTO.setLength(film.getLength());
        if (film.getSpecialFeatures() != null) {
            String[] features = film.getSpecialFeatures().split(",");
            filmDTO.setSpecialFeatures(features);
        } else {
            filmDTO.setSpecialFeatures(new String[0]);
        }
        filmDTO.setCast(film.getActors()
                .stream()
                .map(Actor::getFullName)
                .toList());
        HashMap<String, List<Integer>> addressInventory = new HashMap<>();
        film.getInventory()
                .forEach((Inventory inventory) -> {
                    String address = inventory.getStore().getAddress().getAddressName();
                    int id = inventory.getInventoryId();
                    if (addressInventory.containsKey(address)) {
                        addressInventory.get(address).add(id);
                    } else {
                        addressInventory.put(address, new ArrayList<>(List.of(id)));
                    }
                });
        filmDTO.setInventory(addressInventory);
        filmDTO.setCategory(film.getFilmCategory()
                .stream()
                .map(Category::getName)
                .toList());
        return filmDTO;
    }
    private Film convertDTOToEntity(FilmDTO filmDTO) {  // title, description, languageId, rentalDuration, rentalRate, replacementCost,
        Film film = new Film();
        film.setTitle(filmDTO.getTitle());
        film.setDescription(filmDTO.getDescription());
        film.setLanguageId(filmDTO.getLanguageId());
        film.setRentalDuration(filmDTO.getRentalDuration());
        film.setRentalRate(filmDTO.getRentalRate());
        film.setReplacementCost(filmDTO.getReplacementCost());

        return film;
    }
    private void update(Film film, Map<String, Object> changes) {
        changes.forEach(
            (change, value) -> {
                switch (change) {
                    case "title":
                        film.setTitle((String) value);
                        break;
                    case "description":
                        film.setDescription((String) value);
                        break;
                    case "releaseYear":
                        film.setReleaseYear((Integer) value);
                        break;
                    case "languageId":
                        film.setLanguageId((Integer) value);
                        break;
                    case "originalLanguageId":
                        film.setOriginalLanguageId((Integer) value);
                        break;
                    case "rentalDuration":
                        film.setRentalDuration((Integer) value);
                        break;
                    case "rentalRate":
                        film.setRentalRate((BigDecimal) value);
                        break;
                    case "length":
                        film.setLength((Integer) value);
                        break;
                    case "replacementCost":
                        film.setReplacementCost((BigDecimal) value);
                        break;
                    case "rating":
                        film.setRating((String) value);
                        break;
                    case "specialFeatures":
                        film.setSpecialFeatures((String) value);
                        break;
                }
            }
        );
        LocalDateTime now = LocalDateTime.now();
        film.setLastUpdate(String.valueOf(now));
    }
}
