package es.ies.puerto;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.FileOperations;

public class Prueba {
    public static void main(String[] args) {
        FileOperations fileOps = new FileOperations();
        
        Empleado emp1 = new Empleado("1", "Juan Pérez", "Desarrollador", 3000.50, "15/10/1995");
        fileOps.create(emp1);
        
        Empleado emp2 = new Empleado("2", "Ana Gómez", "Diseñadora", 2800.75, "10/01/1990");
        fileOps.create(emp2);
        
        System.out.println("Empleado leído: " + fileOps.read("1"));
        
        emp1.setSalario(3500.00);
        fileOps.update(emp1);
        
        fileOps.delete("2");
        
        System.out.println("Empleados Desarrolladores: " + fileOps.empleadosPorPuesto("Desarrollador"));
    }
}
