package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.controllers;


import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model.*;
import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ClothingRepository clothingRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PolRepository polRepository;






    @GetMapping(value = "/")
    public String indexPage(Model model){
        List<Clothing> clothing = clothingRepository.findAll();
        model.addAttribute("clothing" , clothing);


        return "index";
    }



    @GetMapping(value = "/login")
    public String login(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "login";
    }




    @PostMapping(value = "/adduser")
    public String adduser(@RequestParam(name = "user_email")String email,
                          @RequestParam(name = "user_password")String password,
                          @RequestParam(name = "user_full_name")String name){
            Users users = new Users();
            users.setEmail(email);
            users.setPassword(password);
            users.setFullName(name);
            userRepository.save(users);

        return "redirect:/login";
    }



    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "profile";
    }

    @GetMapping(value = "/adminpanel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String admin(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "adminpanel";
    }






    @GetMapping(value = "/moderatorpanel")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String moderator(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "moderatorpanel";
    }

    @GetMapping(value = "/403")
    public String accessDeniedPage(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "403";
    }

    private Users getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users currentUser = (Users) authentication.getPrincipal();
            return currentUser;
        }
        return null;
    }

    @GetMapping(value = "/addClothing")
    public String ClothingAdd(Model model){
        List <Clothing> clothing = clothingRepository.findAll();
        model.addAttribute("clothing" , clothing);

        List <Brand> brands = brandRepository.findAll();
        model.addAttribute("brand" , brands);

        List <Category> categories = categoryRepository.findAll();
        model.addAttribute("category" , categories);

        List <Pol> pols = polRepository.findAll();
        model.addAttribute("pol" , pols);

        return "addClothing";
    }

    @PostMapping(value = "/addClothing")
    public String addPlayer(@RequestParam(name = "size")int size,
                            @RequestParam(name = "price")int price,
                            @RequestParam(name = "brand_id") Long brandId,
                            @RequestParam(name = "pol_id") Long polId,
                            @RequestParam(name = "categ_id") Long categId){
        Brand brand = brandRepository.findById(brandId).orElse(null);
        Category category = categoryRepository.findById(categId).orElse(null);
        Pol pol = polRepository.findById(polId).orElse(null);

        if(brand != null && category != null && pol != null) {
            Clothing clothing = new Clothing();
            clothing.setSize(size);
            clothing.setPrice(price);
            clothing.setBrand(brand);
            clothing.setPol(pol);
            clothing.setCategory(category);
            clothingRepository.save(clothing);
        }

        return "redirect:/addClothing";
    }

    @GetMapping(value = "/setting")
    public String setting(Model model){
        List<Users> users = userRepository.findAll();
        model.addAttribute("user" , users);
        return "setting";
    }


    @GetMapping(value = "/details/{id}")
    public String details(@PathVariable(name = "id")Long id , Model model){
        Clothing clothing = clothingRepository.findById(id).orElse(null);
        model.addAttribute("clothing" , clothing);

        List <Brand> brands = brandRepository.findAll();
        model.addAttribute("brand" , brands);

        List <Category> categories = categoryRepository.findAll();
        model.addAttribute("category" , categories);

        List <Pol> pols = polRepository.findAll();
        model.addAttribute("pol" , pols);

        return "Details";
    }

    @PostMapping(value = "/saveClothing")
    public String savePlayer(@RequestParam(name = "id")Long id,
                             @RequestParam(name = "size")int size,
                             @RequestParam(name = "price")int price,
                             @RequestParam(name = "brand_id")Long brandId,
                             @RequestParam(name = "pol_id")Long polId,
                             @RequestParam(name = "cat_id")Long categId){
        Clothing clothing = clothingRepository.findById(id).orElse(null);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        Category category = categoryRepository.findById(categId).orElse(null);
        Pol pol = polRepository.findById(polId).orElse(null);
        if(clothing != null && brand != null && category != null && pol != null) {
            clothing.setSize(size);
            clothing.setPrice(price);
            clothing.setBrand(brand);
            clothing.setCategory(category);
            clothing.setPol(pol);
            clothingRepository.save(clothing);
            return "redirect:/details/" + id;
        }
        return "redirect:/addClothing";
    }

    @PostMapping(value = "/delete")
    public String deletePlayer(@RequestParam(name = "id")Long id){
        Clothing clothing = clothingRepository.findById(id).orElse(null);
        if(clothing != null) {
            clothingRepository.delete(clothing);
        }
        return "redirect:/addClothing";
    }






    @GetMapping(value = "/detailsu/{id}")
    public String detailsu(@PathVariable(name = "id")Long id , Model model){
        Users users = userRepository.findById(id).orElse(null);
        model.addAttribute("user" , users);

        return "detailsu";
    }

    @PostMapping(value = "/saveUser")
    public String savePlayer(@RequestParam(name = "id")Long id,
                             @RequestParam(name = "email")String email,
                             @RequestParam(name = "full_name")String name){
        Users users = userRepository.findById(id).orElse(null);
        if(users != null) {
            users.setEmail(email);
            users.setFullName(name);
            userRepository.save(users);
            return "redirect:/detailsu/" + id;
        }
        return "redirect:/profile";
    }



    @PostMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam(name = "id")Long id){
        Users users = userRepository.findById(id).orElse(null);
        if(users != null) {
            userRepository.delete(users);
        }
        return "redirect:/profile";
    }

    @GetMapping(value = "/players")
    public String players(Model model){
        List<Clothing> clothing = clothingRepository.findAll();
        model.addAttribute("clothing" , clothing);


        return "Players";
    }





}
