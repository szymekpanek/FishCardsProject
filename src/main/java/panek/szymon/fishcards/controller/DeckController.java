package panek.szymon.fishcards.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import panek.szymon.fishcards.dto.DeckUpdateRequest;
import panek.szymon.fishcards.entity.Deck;
import panek.szymon.fishcards.service.interfaces.DeckService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/deck")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Deck>> getDeckById(@PathVariable String id) {
        return ResponseEntity.ok(deckService.getDeckById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Deck>> getDecksByUserId(@PathVariable String userId) {
        List<Deck> decks = deckService.getAllDecksByUserId(userId);
        if (decks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(decks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deck> updateDeck(@PathVariable String id, @RequestBody DeckUpdateRequest request) {
        Deck updated = deckService.updateDeck(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeck(@PathVariable String id){
        deckService.deleteDeck(id);
        return ResponseEntity.noContent().build();
    }
}
