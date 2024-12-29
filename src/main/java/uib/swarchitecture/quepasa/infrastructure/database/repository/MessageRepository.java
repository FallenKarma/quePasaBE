package uib.swarchitecture.quepasa.infrastructure.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uib.swarchitecture.quepasa.infrastructure.database.models.MessageJPA;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<MessageJPA, Long> {

    @Query("SELECT COUNT(DISTINCT m) FROM MessageJPA m " +
            "JOIN m.readers r " +
            "WHERE m.chat.id = :chatId AND r.id != :userId")
    int countUnreadMessagesByChatIdAndUserId(@Param("chatId") long chatId, @Param("userId") long userId);

    @Query("SELECT m FROM MessageJPA m WHERE m.chat.id = :chatId ORDER BY m.timestamp DESC")
    List<MessageJPA> findLastMessagesByChatId(@Param("chatId") long chatId, Pageable pageable);
}