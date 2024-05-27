package com.kimenFen.cl.Service;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public Alumno obtenerAlumnoPorId(Long id) {
        return alumnoRepository.findById(id).orElse(null);
    }

    public void actualizarAlumno(Alumno alumno) {
        alumnoRepository.save(alumno);
    }

    public void eliminarAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }

    public void agregarAnotacion(Long id, String anotacion) {
        Alumno alumno = obtenerAlumnoPorId(id);
        if (alumno != null) {
            alumno.getAnotaciones().add(anotacion);
            alumnoRepository.save(alumno);
        }
    }
}
