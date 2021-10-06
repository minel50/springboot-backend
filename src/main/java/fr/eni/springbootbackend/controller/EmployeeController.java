package fr.eni.springbootbackend.controller;

import fr.eni.springbootbackend.exception.ResourceNotFoundException;
import fr.eni.springbootbackend.model.Employee;
import fr.eni.springbootbackend.dao.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class EmployeeController {


    @Autowired
    private EmployeeDAO dao;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/employees")
    public List<Employee> getAll(Employee e) {
        return dao.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee e) {
        return dao.save(e);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getById(@PathVariable int id) {
        Employee e = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));

        return new ResponseEntity<Employee>(e, HttpStatus.OK);

    }

    //update employee rest api
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee empDetails) {
        Employee e = dao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        e.setFirstname(empDetails.getFirstname());
        e.setLastname(empDetails.getLastname());
        e.setEmail(empDetails.getEmail());
        Employee emp = dao.save(e);
        return new ResponseEntity<Employee>(emp, HttpStatus.OK);
    }
    //delete employee rest api
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping ("/employees/{id}")
    public ResponseEntity <Map<String,Boolean>> deleteEmployee(@PathVariable int id){
        Employee e = dao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        dao.delete(e);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }

}