package uib.swarchitecture.quepasa.infrastructure.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uib.swarchitecture.quepasa.domain.exceptions.MessageNotFoundException;
import uib.swarchitecture.quepasa.domain.models.Chat;
import uib.swarchitecture.quepasa.domain.models.Message;
import uib.swarchitecture.quepasa.domain.models.UserChat;
import uib.swarchitecture.quepasa.domain.services.ChatService;
import uib.swarchitecture.quepasa.domain.services.MessageService;
import uib.swarchitecture.quepasa.infrastructure.web.controllers.utils.ApiResponse;
import uib.swarchitecture.quepasa.infrastructure.web.models.SendMessageRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final ChatService chatService;

    @Autowired
    public MessageController(MessageService messageService, ChatService chatService) {
        this.messageService = messageService;
        this.chatService = chatService;
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<ApiResponse<List<Message>>> getMessagesFromChat(
            @PathVariable long chatId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication) {
        try {
            // Verificar que el chat pertenece al usuario autenticado
            boolean isOwned = chatService.isChatOwnedByUser(chatId, authentication);

            if (!isOwned) {
                return new ResponseEntity<>(new ApiResponse<>("You are not allowed to access this chat."), HttpStatus.FORBIDDEN);
            }

            List<Message> chatMessages = messageService.getMessagesFromChat(chatId, authentication);
            return new ResponseEntity<>(new ApiResponse<>(chatMessages), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<ApiResponse<Message>> sendMessage(
            @PathVariable long chatId,
            @RequestBody SendMessageRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication
    ) {
        try {
            // Verificar que el chat pertenece al usuario autenticado
            boolean isOwned = chatService.isChatOwnedByUser(chatId, authentication);

            if (!isOwned) {
                return new ResponseEntity<>(new ApiResponse<>("You are not allowed to access this chat."), HttpStatus.FORBIDDEN);
            }
            // Llama al servicio para enviar el mensaje
            Message sentMessage = messageService.sendMessage(chatId, request, authentication);

            // Devuelve una respuesta con el mensaje creado
            return new ResponseEntity<>(new ApiResponse<>(sentMessage), HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

