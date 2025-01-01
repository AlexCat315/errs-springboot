import com.x.backend.pojo.ResultEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user/account")
class AccountController {

    @PostMapping("/register")
    fun register() : ResultEntity<String> {
       return ResultEntity.success("register")
    }

}