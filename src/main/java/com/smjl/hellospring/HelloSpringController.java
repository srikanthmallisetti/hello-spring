package com.smjl.hellospring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloSpringController {

    List<HelloSpringGrade> studentGrades = new ArrayList<>();

    @GetMapping("/show-grades")
    public String showGrades(Model getGradesModel){
        getGradesModel.addAttribute("grades", studentGrades);
        return "grades";
    }

    @GetMapping("/")
    public String getGradesForm(Model getGradesFormModel, @RequestParam(required = false) String id){
        Integer gradeIdxFound = getGradeIdx(id);

        getGradesFormModel.addAttribute("grade",
                gradeIdxFound == Constants.NOT_FOUND ? new HelloSpringGrade(): studentGrades.get(gradeIdxFound)
        );
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitForm(HelloSpringGrade grade){
        Integer gradeIdxFound = getGradeIdx(grade.getId());
        if (gradeIdxFound == Constants.NOT_FOUND) {
            studentGrades.add(grade);
        } else{
            studentGrades.set(gradeIdxFound, grade);
        }
        studentGrades.add(grade);
        return "redirect:/show-grades";
    }

    public Integer getGradeIdx(String id){
        for (int i=0; i < studentGrades.size(); i++){
            if (studentGrades.get(i).getId().equals(id)) return i;
        }
        return Constants.NOT_FOUND;
    }


}
