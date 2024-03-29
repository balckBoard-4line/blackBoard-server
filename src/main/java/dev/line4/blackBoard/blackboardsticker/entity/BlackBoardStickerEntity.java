package dev.line4.blackBoard.blackboardsticker.entity;

import dev.line4.blackBoard.blackboard.dto.AddBlackBoardDto;
import dev.line4.blackBoard.blackboard.entity.BlackBoardEntity;

import javax.persistence.*;

import dev.line4.blackBoard.utils.entity.BaseEntity;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BLACKBOARD_STICKERS")
public class BlackBoardStickerEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blackboard_sticker_id")
    private Long id;

    private Long num;

    @Column(name = "position_x")
    private double positionX;

    @Column(name = "position_y")
    private double positionY;

    @Column(name = "img")
    private Long img;

    @Column(name = "width")
    private Double width;

    @Column(name = "angle")
    private Double angle;

    @Column(name = "mirror")
    private Long mirror;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blackboard_id")
    private BlackBoardEntity blackboard;

    // 생성 메서드
    public static BlackBoardStickerEntity createBlackBoardSticker(AddBlackBoardDto.Req.Sticker req) {
        return req.toEntity();
    }

    // 연관관계 편의 메서드
    public void setBlackBoard(BlackBoardEntity blackBoard) {
        this.blackboard = blackBoard;
        blackboard.getBlackBoardStickers().add(this);
    }

}
