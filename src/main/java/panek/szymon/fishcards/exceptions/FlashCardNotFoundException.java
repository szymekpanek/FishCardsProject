package panek.szymon.fishcards.exceptions;

public class FlashCardNotFoundException extends RuntimeException {
    public FlashCardNotFoundException(String message) {
        super(message);
    }
}
