package com.example.patientsecurity.web;

import com.example.patientsecurity.entities.Patient;
import com.example.patientsecurity.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;


    @GetMapping(path="/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword
    ){
        Page<Patient> pagePatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id, String keyword, int page) {

        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }
    @GetMapping("/user//patients")
    @ResponseBody  //renvoie directememt le corps de la reponse http
    public List<Patient> lisPatients(){
        return patientRepository.findAll();
    }

    @GetMapping("/admin/formPatients")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
    @GetMapping("/admin/editPatients")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatients(Model model,Long id , String keyword, int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient", patient);
        model.addAttribute("page", page);
        model.addAttribute("keyword",keyword);
        return "editPatients";
    }

    @PostMapping(path="/admin/save")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(Model model, //BindingResult place les erreurs dans le model
                       @Valid Patient patient,
                       BindingResult bindingResult  ,//gener la liste des erreur,
                       @RequestParam(defaultValue = "0") int page ,
                       @RequestParam(defaultValue = "") String keyword) {
      if(bindingResult.hasErrors()) return "formPatients";
      patientRepository.save(patient);
        return "redirect:/user/index"; //?page="+page+"&keyword="+keyword
    }
}