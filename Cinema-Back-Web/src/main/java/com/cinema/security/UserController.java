package com.cinema.security;

import java.util.ArrayList;
import java.util.List;
import com.cinema.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class UserController {
 
    @Autowired
    private UserRepository userRepository;
     
    @Autowired
    private RoleRepository roleRepository;
     
    @GetMapping("formUsernew")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("formUser");
        mav.addObject("user", user);
        
         
        List<Role> roles =  roleRepository.findAll();
         
        mav.addObject("allRoles", roles);
         
        return mav;    
    } 
    @GetMapping("/user")
    public String users(Model model){
        model.addAttribute("user",        userRepository.findAll());
    
        return "user";
    }


		@PostMapping("/saveUser")
		public String saveUser(User user, RolesList roles , Model model , BindingResult bindingResult) {
            PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
            System.out.println(passwordEncoder.encode("1234"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            roles.getRoles().forEach((role) -> userRepository.addUserRoles(user.getId(), role.getId() , user.getUsername(), role.getRole()));
           //     System.out.println(role.getId() +"ach kayn" +user.getId());

             
		//	salles.getSalles().forEach((salle) -> salleRepository.save(salle));
			

    		return "redirect:/salle";
		}
     
    @GetMapping("/formUserEdit")
    public ModelAndView editUser(@RequestParam(name="id") Long id,  RolesList roles ) {
        User user = userRepository.findById(id).get();
        ModelAndView mav = new ModelAndView("formUseredit");
        mav.addObject("user", user);
         
  //      List<Role> roles = (List<Role>) roleRepository.findAll();        
     //   roles.setRoles(roleRepository.findAll());
     //   roleRepository.findAll().forEach((role) -> roles.addRole(role));
         
        mav.addObject("allRoles", roleRepository.findAll());
         
        return mav;
    } 
    @Data  @NoArgsConstructor
	public class RolesList {
		private ArrayList<Role> roles;
	
		// default and parameterized constructor
	
		public void addRole(Role role) {
			this.roles.add(role);
		}
		
		// getter and setter
    } 

}