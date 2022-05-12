package com.project.board.controller;

import com.project.board.entity.Board;
import com.project.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller //spring이 컨트롤러인지 인지
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PreAuthorize("isAuthenticated()")  //무조건 로그인 후 접속
    @GetMapping("/") //localhost:8080
    public String boardHome(Model model, @PageableDefault(size = 1000,  sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Board> list = boardService.boardList2(pageable);
        model.addAttribute("list",list);
        return "home";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm(){
        return "write";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{
//        System.out.println("제목 : " + board.getTitle());
//        System.out.println("내용 : " + board.getContent());
        String mode = "신규";
        boardService.write(board,file,mode);  //디비에 값을 집어 넣는다.

        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/");
        return "message";

    }

//    @GetMapping("/board/list")
//    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){   //model에 담에 페이지에 전달
//        Page<Board> list = boardService.boardList(pageable);
//
//        int nowPage = list.getPageable().getPageNumber() + 1; //현재페이지
//        int startPage = Math.max(nowPage - 4,1); //블럭에 보여줄 시작 페이지
//        int endPage = Math.min(nowPage + 5,list.getTotalPages()); //블럭에 보여줄 마지막 페이지
//
//        model.addAttribute("list",list);
//        model.addAttribute("nowPage",nowPage);
//        model.addAttribute("startPage",startPage);
//        model.addAttribute("endPage",endPage);
//        return "boardlist";
//    }

    @GetMapping("/board/view")  // */board/view?id=1
    public String boardView(Model model, Integer id){
        model.addAttribute("board",boardService.boardView(id));
        return "view";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model){
        model.addAttribute("board",boardService.boardView(id));
        //@PathVariable은 url에서 {}값을 인식해서 인수에 값을 전달
        return "modify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board,MultipartFile file) throws Exception{
        String mode = "수정";
        Board boardTemp = boardService.boardView(id);   //기존 내용 가져오고
        boardTemp.setTitle(board.getTitle());   //새로운 내용으로 업데이트
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file,mode);


        return "redirect:/";
    }
}
