package uib.swarchitecture.quepasa.infrastructure.database.repository;

import org.springframework.data.repository.CrudRepository;
import uib.swarchitecture.quepasa.infrastructure.database.models.ChatJPA;

import java.util.List;

public interface ChatRepository extends CrudRepository<ChatJPA, Long> {
    List<ChatJPA> findByParticipants_Id(long userId);

}
