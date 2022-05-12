package com.project.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;

@Entity //테이블을 의미한다.
@Data
public class Board {    //DB의 테이블 명과 동일하게 사용하기 추천

    @Id //PK를 의미한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //mysql mariaDB에서 사는 시퀀스 같은 것.
    private Integer id;

    private String title;

    private String content;

    private String filename;

    private String filepath;

    private String date;

}
