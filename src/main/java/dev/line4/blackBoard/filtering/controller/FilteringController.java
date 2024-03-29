package dev.line4.blackBoard.filtering.controller;

import dev.line4.blackBoard.filtering.dto.FilterBlackBoardDto;
import dev.line4.blackBoard.filtering.dto.FilterLetterDto;
import dev.line4.blackBoard.filtering.service.FilteringServiceImpl;
import dev.line4.blackBoard.utils.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/filtering")
@RequiredArgsConstructor
@Api(tags = "비속어 필터링")
public class FilteringController {

    private final FilteringServiceImpl filteringService;

    @PostMapping("blackboard")
    @ApiOperation(value = "칠판 생성때 비속어 필터링", notes = "칠판을 생성할 때 호출합니다.")
    public ResponseEntity<ApiResponse<?>> filterBlackBoard(@RequestBody FilterBlackBoardDto.Req req) throws Exception {
        ResponseEntity<ApiResponse<?>> result = filteringService.filterBlackBoard(req);
        return result;
    }

    @PostMapping("letter")
    @ApiOperation(value = "편지 생성 때 비속어 필터링", notes = "편지을 생성할 때 호출합니다.")
    public ResponseEntity<ApiResponse<?>> filterLetter(@RequestBody FilterLetterDto.Req req) throws Exception {
        ResponseEntity<ApiResponse<?>> result = filteringService.filterLetter(req);
        return result;
    }
}
