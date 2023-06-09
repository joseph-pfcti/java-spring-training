package com.pfcti.springreactive.service;

import com.pfcti.springreactive.model.Employee;
import com.pfcti.springreactive.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public void create(Employee employee) {
        employeeRepository.save(employee).subscribe();
    }

    public Mono<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    public Flux<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public Flux<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Mono<Employee> update(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Mono<Void> deleteById(Integer id) {
        return employeeRepository.deleteById(id);
    }
}
