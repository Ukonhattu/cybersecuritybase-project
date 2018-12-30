package sec.project.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;
import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));

        return "done";
    }

    @RequestMapping(value = "/adminlogin", method = RequestMethod.GET)
    public String adminLogin(Model model) {
        return "adminForm";
    }

    @RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
    public String adminLoginForm(Model model, @RequestParam String username, @RequestParam String pw) {
        String s = "Kirjautuminen epäonnistui";
        if (username.equals("admin") && pw.equals("admin")) {
            s = "kirjautuminen onnistui";
        }
        model.addAttribute("message", s);
        return "loggedin";
    }

    @RequestMapping(value = "/admin")
    public String adminPanel(Model model) {

        List<Signup> entries = signupRepository.findAll();

        model.addAttribute("entries", entries);

        return "show";
    }

    @RequestMapping("/test")
    public String testError() {
        return "not_exists";
    }

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

}
