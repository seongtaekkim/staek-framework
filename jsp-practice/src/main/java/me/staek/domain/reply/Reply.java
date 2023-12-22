package me.staek.domain.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
    private int id;
    private int userId;
    private int boardId;
    private String content;
    private Timestamp createDate;
}
