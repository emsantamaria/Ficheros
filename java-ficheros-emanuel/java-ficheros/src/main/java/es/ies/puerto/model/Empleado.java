package es.ies.puerto.model;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Empleado {
    private String identificador;
    private String nombre;
    private String puesto;
    private double salario;
    private String fechaNacimiento;
    
    public Empleado(String identificador, String nombre, String puesto, double salario, String fechaNacimiento) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getIdentificador() { return identificador; }
    public String getNombre() { return nombre; }
    public String getPuesto() { return puesto; }
    public double getSalario() { return salario; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
    public void setSalario(double salario) { this.salario = salario; }
    
    public int getEdad() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd\\MM\\yyyy");
        LocalDate birthDate = LocalDate.parse(fechaNacimiento, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    @Override
    public String toString() {
        return identificador + ", " + nombre + ", " + puesto + ", " + salario + ", " + fechaNacimiento;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Empleado empleado = (Empleado) obj;
        return identificador.equals(empleado.identificador);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }
}