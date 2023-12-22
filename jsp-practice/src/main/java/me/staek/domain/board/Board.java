package me.staek.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    private int id;
    private int userId;
    private String title;
    private String content;
    private int readCount;
    private Timestamp createDate;
}
