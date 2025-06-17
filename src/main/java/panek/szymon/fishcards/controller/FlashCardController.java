package panek.szymon.fishcards.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import panek.szymon.fishcards.dto.FlashCardCreateRequest;
import panek.szymon.fishcards.dto.FlashCardUpdateRequest;
import panek.szymon.fishcards.entity.FlashCard;
import panek.szymon.fishcards.service.interfaces.FlashCardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flashcard")
@RequiredArgsConstructor
public class FlashCardController {
    private final FlashCardService flashCardService;

    @Operation(summary = "Get a FlashCard by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FlashCard found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlashCard.class))),
            @ApiResponse(responseCode = "404", description = "FlashCard not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<FlashCard>> getFlashCardById(@PathVariable String id) {
        return ResponseEntity.ok(flashCardService.getFlashCardById(id));
    }

    @Operation(summary = "Get all FlashCards assigned to a specific User ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of FlashCards found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlashCard.class))),
            @ApiResponse(responseCode = "204", description = "No FlashCards found for this user",
                    content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FlashCard>> getFlashCardsByUserId(@PathVariable String userId) {
        List<FlashCard> flashCards = flashCardService.getAllFlashCardsByUserId(userId);
        if (flashCards.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(flashCards);
    }

    @Operation(summary = "Update an existing FlashCard by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FlashCard updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlashCard.class))),
            @ApiResponse(responseCode = "404", description = "FlashCard not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<FlashCard> updateFlashCard(
            @PathVariable String id,
            @RequestBody FlashCardUpdateRequest request) {

        FlashCard updated = flashCardService.updateFlashCard(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a FlashCard by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "FlashCard deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "FlashCard not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlashCard(@PathVariable String id) {
        flashCardService.deleteFlashCard(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create multiple FlashCards at once")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "FlashCards created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlashCard.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content)
    })

    @PostMapping("/bulk")
    public ResponseEntity<List<FlashCard>> createMultipleFlashCards(
            @RequestBody(description = "List of FlashCards to be created", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlashCardCreateRequest.class)))
            @org.springframework.web.bind.annotation.RequestBody List<FlashCardCreateRequest> requests) {

        List<FlashCard> createdFlashCards = flashCardService.createMultipleFlashCardsFromDto(requests);
        return new ResponseEntity<>(createdFlashCards, HttpStatus.CREATED);
    }
}
