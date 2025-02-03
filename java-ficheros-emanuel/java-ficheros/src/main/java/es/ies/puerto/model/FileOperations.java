package es.ies.puerto.model;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FileOperations implements Operaciones {
    private static final String FILE_NAME = "empleados.txt";
    
    @Override
    public boolean create(Empleado empleado) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(empleado.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Empleado read(String identificador) {
        return getAllEmpleados().stream()
                .filter(emp -> emp.getIdentificador().equals(identificador))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public Empleado read(Empleado empleado) {
        return read(empleado.getIdentificador());
    }
    
    @Override
    public boolean update(Empleado empleado) {
        Set<Empleado> empleados = getAllEmpleados();
        if (empleados.removeIf(e -> e.getIdentificador().equals(empleado.getIdentificador()))) {
            empleados.add(empleado);
            return saveAllEmpleados(empleados);
        }
        return false;
    }
    
    @Override
    public boolean delete(String identificador) {
        Set<Empleado> empleados = getAllEmpleados();
        if (empleados.removeIf(e -> e.getIdentificador().equals(identificador))) {
            return saveAllEmpleados(empleados);
        }
        return false;
    }
    
    @Override
    public Set<Empleado> empleadosPorPuesto(String puesto) {
        return getAllEmpleados().stream()
                .filter(emp -> emp.getPuesto().equalsIgnoreCase(puesto))
                .collect(Collectors.toSet());
    }
    
    @Override
    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd\\MM\\yyyy");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);
        
        return getAllEmpleados().stream()
                .filter(emp -> {
                    LocalDate nacimiento = LocalDate.parse(emp.getFechaNacimiento(), formatter);
                    return (nacimiento.isAfter(inicio) || nacimiento.equals(inicio)) &&
                           (nacimiento.isBefore(fin) || nacimiento.equals(fin));
                })
                .collect(Collectors.toSet());
    }
    
    private Set<Empleado> getAllEmpleados() {
        Set<Empleado> empleados = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                empleados.add(new Empleado(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empleados;
    }
    
    private boolean saveAllEmpleados(Set<Empleado> empleados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Empleado emp : empleados) {
                writer.write(emp.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

