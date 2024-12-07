package uib.swarchitecture.quepasa.infrastructure.adapters.inbound;

import org.springframework.web.bind.annotation.*;
import uib.swarchitecture.quepasa.application.usecase.SendMessage;
import uib.swarchitecture.quepasa.domain.model.Message;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/messages")
public class MessageController {
    private final SendMessage enviarMensaje;

    public MessageController(SendMessage enviarMensaje) {
        this.enviarMensaje = enviarMensaje;
    }

    @GetMapping("/{destinatario}")
    public List<Message> obtenerMensajesPorDestinatario(@PathVariable String destinatario) {
        // Simulación: lógica para obtener mensajes por destinatario
        return List.of(
                new Message("1", "Alice", destinatario, "Hola, ¿cómo estás?", LocalDateTime.now()),
                new Message("2", "Bob", destinatario, "¿Vienes a la reunión?", LocalDateTime.now())
        );
    }

    @PostMapping
    public Message enviarMensaje(
            @RequestParam String remitente,
            @RequestParam String destinatario,
            @RequestParam String contenido) {
        return enviarMensaje.execute(remitente, destinatario, contenido);
    }
}
