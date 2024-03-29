package dev.line4.blackBoard.blackboardsticker.repository;

import dev.line4.blackBoard.blackboardsticker.entity.BlackBoardStickerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackBoardStickerRepository extends JpaRepository<BlackBoardStickerEntity, Long> {
}
