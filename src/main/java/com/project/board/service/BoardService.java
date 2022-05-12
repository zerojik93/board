package com.project.board.service;

import com.project.board.entity.Board;
import com.project.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired  //spring bean이 알아서 주입한다.
    private BoardRepository boardRepository;

    //글 작성
    public void write(Board board, MultipartFile file, String mode)throws Exception{
        //프로젝트 경로를 조합해서 저장경로 설정 담아준다.
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
        //파일에 붙일 랜덤이름 생성
        UUID uuid = UUID.randomUUID();
        //파일명
        String fileName;

        //기존 파일 유무 확인
        if(mode == "신규") {   //신규일 경우
            //첨부파일이 존재하지 않을 경우 기본이미지 사용
            if(file.getOriginalFilename().contains(".")) {
                //랜덤 + 원래 파일 이름
                fileName = uuid + "_" + file.getOriginalFilename();
                File saveFile = new File(projectPath, fileName);
                file.transferTo(saveFile);
            }
            else {
                //기본 파일명
                fileName = "default.png";
            }
            //DB에 첨부파일 명 및 경로 저장
            board.setFilename(fileName);
            board.setFilepath("/files/"+fileName);
        }else{
            fileName = board.getFilepath()+board.getFilename();
        }


        //DB에 날짜 저장
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        board.setDate(date.format(today).toString());

        //DB에 게시물 저장
        boardRepository.save(board);
    }

    //게시글 리스트
    public Page<Board> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Page<Board> boardList2(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

}
