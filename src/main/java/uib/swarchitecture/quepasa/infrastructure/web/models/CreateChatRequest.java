package uib.swarchitecture.quepasa.infrastructure.web.models;

import lombok.Getter;
import lombok.Setter;
import uib.swarchitecture.quepasa.domain.models.enums.ChatType;

import java.util.List;

@Getter
@Setter
public class CreateChatRequest {
    private String name;
    private ChatType type;
    private List<Long> userIds;
}
