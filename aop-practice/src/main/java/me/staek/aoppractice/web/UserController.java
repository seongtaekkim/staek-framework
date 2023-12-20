package me.staek.aoppractice.web;


import jakarta.validation.Valid;
import me.staek.aoppractice.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;


    // UserRepository 이 타입 검색 후 DI 됨.
    // 생성자 없다면, 롬복을 이용할 수 있음. @RequiredArgsConstructor
    // 옛날방식: @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public CommonDto<List<User>>  findAll() {
        System.out.println("findall");
//        return userRepository.findAll(); // MessageConverter -> JavaObject를 jsonobject 로 변경 후 클라이언트로 리턴됨
        return new CommonDto<>(HttpStatus.OK.value(),userRepository.findAll());
    }

    @GetMapping("/user/{id}")
    public CommonDto<User> findById(@PathVariable int id) {
        System.out.println("findById " + id );
        return new CommonDto<>(HttpStatus.OK.value(),userRepository.findById(id));
    }


    @CrossOrigin // CORS 허용
    @PostMapping("/user")
    // x-www-form-urlencoded => 리플렉션 request.parameter() 에서 파싱 후 삽입
    // text/plain => @RequetsBody 어노테이션이 있으면 받을 ㅅ ㅜ잇다.
    // application/json => @ResponseBody 어노테이션 + 오브젝트로 받기
    //{
    //    "username":"awef",
    //    "password":"awegf",
    //    "email":"awefa"
    //}
//    public void save(String username, String password, String email) {
//    public void save(@RequestBody String body) {
//    public ResponseEntity<String> save(@RequestBody User user) {
    public CommonDto<?> save(@Valid @RequestBody JoinReqDto user, BindingResult bindingResult) {
        System.out.println("save" + user);

        userRepository.save(user);


//        return new ResponseEntity<>("ok", HttpStatus.OK);
        return new CommonDto<>(HttpStatus.OK.value(), "ok");
    }

    @DeleteMapping("/user/{id}")
    public CommonDto<String> delete(@PathVariable int id) {
        System.out.println("delete");
        userRepository.delete(id);
        return new CommonDto<>(HttpStatus.OK.value());
    }

    @PutMapping("/user/{id}")
    public CommonDto<?> update(@PathVariable int id, @Valid @RequestBody UptateReqDto dto, BindingResult bindingResult) {
        System.out.println("update");

//        throw new IllegalArgumentException("bad argument");
        userRepository.update(dto);
        return new CommonDto<>(HttpStatus.OK.value());
    }

}
