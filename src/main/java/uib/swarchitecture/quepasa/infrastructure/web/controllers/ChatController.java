package uib.swarchitecture.quepasa.infrastructure.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uib.swarchitecture.quepasa.domain.models.UserChat;
import uib.swarchitecture.quepasa.domain.services.ChatService;
import uib.swarchitecture.quepasa.infrastructure.web.controllers.utils.ApiResponse;
import uib.swarchitecture.quepasa.infrastructure.web.models.CreateChatRequest;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserChat>>> getUserChats(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication) {
        try {
            List<UserChat> userChats = chatService.getUserChats(authentication);

            return new ResponseEntity<>(new ApiResponse<>(userChats), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // No había chat con el ID, o no había otro participante en el chat directo
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejar otras excepciones
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserChat>> createUserChat(@RequestBody final CreateChatRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication) {
        try {
            UserChat chat = chatService.createChat(request, authentication);

            return new ResponseEntity<>(new ApiResponse<>(chat), HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Los parámetros de la solicitud no son válidos
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            // No se pudo crear el chat porque no se encontró algún usuario
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejar otras excepciones
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
