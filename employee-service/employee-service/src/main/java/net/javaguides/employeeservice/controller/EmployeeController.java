package net.javaguides.employeeservice.controller;

import net.javaguides.employeeservice.dto.ApiResponseDto;
import net.javaguides.employeeservice.dto.EmployeeDto;
import net.javaguides.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @PostMapping("")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto employeeDto1=employeeService.saveEmployee(employeeDto);

        return  new ResponseEntity<>(employeeDto1, HttpStatus.CREATED);

    }


    @GetMapping("{id}")
    public ResponseEntity<ApiResponseDto> getEmployeeById(@PathVariable("id") Long employeeId){
        ApiResponseDto apiResponseDto=employeeService.getEmployeeById(employeeId);

        return  new ResponseEntity<>(apiResponseDto,HttpStatus.OK);
    }

}
