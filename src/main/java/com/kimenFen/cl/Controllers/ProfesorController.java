package com.kimenFen.cl.Controllers;

import java.util.List;

import com.kimenFen.cl.Service.AlumnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    private AlumnoRepository alumnoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProfesorController.class);

    @GetMapping("/alumnos/nuevo")
    public String mostrarFormularioNuevoAlumno(Model model) {
        model.addAttribute("alumno", new Alumno());
        model.addAttribute("rol", "profesor");
        return "nuevo-alumno";
    }

    @PostMapping("/alumnos/guardar")
    public String guardarAlumno(@ModelAttribute Alumno alumno, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-alumno";
        }
        alumnoRepository.save(alumno);
        logger.info("Alumno guardado correctamente: " + alumno.toString());
        return "redirect:/profesor/menu";
    }

    @GetMapping("/alumnos")
    public String listarAlumnos(Model model) {
        List<Alumno> alumnos = alumnoRepository.findAll();
        model.addAttribute("alumnos", alumnos);
        model.addAttribute("rol", "profesor");
        return "lista-alumnos";
    }

    @GetMapping("/editar-alumno/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return "redirect:/profesor/alumnos";
        }
        model.addAttribute("alumno", alumno);
        model.addAttribute("rol", "profesor");
        return "editar-alumno";
    }

    @PostMapping("/actualizar-alumno")
    public String actualizarAlumno(@ModelAttribute Alumno alumno) {
        alumnoRepository.save(alumno);
        return "redirect:/profesor/menu";
    }

    @GetMapping("/eliminar-alumno/{id}")
    public String eliminarAlumno(@PathVariable("id") Long id) {
        alumnoRepository.deleteById(id);
        return "redirect:/profesor/alumnos";
    }

    @GetMapping("/anotacion-alumno/{id}")
    public String agregarAnotacion(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            model.addAttribute("rol", "profesor");
        }
        return "anotacion-alumno";
    }

    @PostMapping("/agregar-anotacion")
    public String guardarAnotacion(@RequestParam Long id, @RequestParam String anotacion) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            alumno.getAnotaciones().add(anotacion);
            alumnoRepository.save(alumno);
        }
        return "redirect:/profesor/alumnos";
    }

    @GetMapping("/ver-anotaciones/{id}")
    public String verAnotaciones(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            model.addAttribute("anotaciones", alumno.getAnotaciones());
            model.addAttribute("rol", "profesor");
        }
        return "ver-anotaciones";
    }
}

