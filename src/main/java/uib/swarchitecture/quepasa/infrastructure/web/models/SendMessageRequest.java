package uib.swarchitecture.quepasa.infrastructure.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uib.swarchitecture.quepasa.domain.models.enums.MessageType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMessageRequest {
    private String content;
    private MessageType type;
}
