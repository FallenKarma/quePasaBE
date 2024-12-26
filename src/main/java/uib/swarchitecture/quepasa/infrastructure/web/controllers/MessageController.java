package uib.swarchitecture.quepasa.infrastructure.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uib.swarchitecture.quepasa.domain.models.Message;
import uib.swarchitecture.quepasa.domain.models.UserChat;
import uib.swarchitecture.quepasa.domain.services.MessageService;
import uib.swarchitecture.quepasa.infrastructure.web.controllers.utils.ApiResponse;
import uib.swarchitecture.quepasa.infrastructure.web.models.SendMessageRequest;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable long messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<ApiResponse<List<Message>>> getMessagesFromChat(@PathVariable long chatId) {
        List<Message> chatMessages = messageService.getMessagesFromChat(chatId);
        return new ResponseEntity<>(new ApiResponse<>(chatMessages), HttpStatus.OK);
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<Message> sendMessage(
            @PathVariable long chatId,
            @RequestBody SendMessageRequest request,
            @RequestHeader("Authorization") String authToken
    ) {
        // Llama al servicio para enviar el mensaje
        Message sentMessage = messageService.sendMessage(chatId, request, authToken);

        // Devuelve una respuesta con el mensaje creado
        return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
    }
}

