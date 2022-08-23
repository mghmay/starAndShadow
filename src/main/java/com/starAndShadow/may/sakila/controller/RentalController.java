package com.starAndShadow.may.sakila.controller;

import com.starAndShadow.may.sakila.dto.FilmDTO;
import com.starAndShadow.may.sakila.dto.RentalDTO;
import com.starAndShadow.may.sakila.model.Film;
import com.starAndShadow.may.sakila.model.Rental;
import com.starAndShadow.may.sakila.response.ResponseHandler;
import com.starAndShadow.may.sakila.service.RentalService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController  //handles GET, POST, DELETE, PUT requests
@RequestMapping("/rental")
@AllArgsConstructor
@NoArgsConstructor
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping
    public @ResponseBody ResponseEntity<Object> addRental(@RequestBody RentalDTO rentalDTO ) {
        try {
            Rental result = rentalService.saveRental(rentalDTO);
            return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}