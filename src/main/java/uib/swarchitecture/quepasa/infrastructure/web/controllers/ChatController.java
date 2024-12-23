package uib.swarchitecture.quepasa.infrastructure.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uib.swarchitecture.quepasa.domain.models.UserChat;
import uib.swarchitecture.quepasa.domain.services.ChatService;
import uib.swarchitecture.quepasa.infrastructure.web.controllers.utils.ApiResponse;

import java.util.List;

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
        } catch(IllegalArgumentException e) {
            // No había chat con el ID, o no había otro participante en el chat directo
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            // Manejar otras excepciones
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
