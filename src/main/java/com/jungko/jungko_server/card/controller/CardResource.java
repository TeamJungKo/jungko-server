package com.jungko.jungko_server.card.controller;

import com.jungko.jungko_server.card.dto.CardDTO;
import com.jungko.jungko_server.card.service.CardService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/cards", produces = MediaType.APPLICATION_JSON_VALUE)
public class CardResource {

    private final CardService cardService;

    public CardResource(final CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards() {
        return ResponseEntity.ok(cardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCard(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(cardService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCard(@RequestBody @Valid final CardDTO cardDTO) {
        final Long createdId = cardService.create(cardDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCard(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CardDTO cardDTO) {
        cardService.update(id, cardDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCard(@PathVariable(name = "id") final Long id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
