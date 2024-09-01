package fr.univ.lille.referencement.service;

import org.springframework.stereotype.Service;
import fr.univ.lille.referencement.configuration.Encoder;

/**
 * Service to encrypt a message.
 */
@Service
public class ChiffrementService {

    /**
     * Encoder used to encrypt the message.
     */
    private final Encoder encoder;

    /**
     * Constructor.
     * @param encoder Encoder used to encrypt the message.
     */
    public ChiffrementService(Encoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Encrypt a message.
     * @param message Message to encrypt.
     * @return Encrypted message.
     */
    public String chiffrer(String message) {
        return this.encoder.encode(message);
    }
}
