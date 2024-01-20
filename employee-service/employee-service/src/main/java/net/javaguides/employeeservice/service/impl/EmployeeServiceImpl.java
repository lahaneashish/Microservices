package net.javaguides.employeeservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import net.javaguides.employeeservice.dto.ApiResponseDto;
import net.javaguides.employeeservice.dto.DepartmentDto;
import net.javaguides.employeeservice.dto.EmployeeDto;
import net.javaguides.employeeservice.entity.Employee;
import net.javaguides.employeeservice.repository.EmployeeRepository;
import net.javaguides.employeeservice.service.APIClient;
import net.javaguides.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER= LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    public EmployeeRepository employeeRepository;

//    @Autowired
//    public RestTemplate restTemplate;

    @Autowired
    public WebClient webClient;

//    @Autowired
//    private APIClient apiClient;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {


       Employee employee= modelMapper.map(employeeDto, Employee.class);
       Employee savedEmployee=employeeRepository.save(employee);
       EmployeeDto savedEmployeeDto=modelMapper.map(savedEmployee,EmployeeDto.class);
        return savedEmployeeDto;
    }

//    @CircuitBreaker(name="${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Retry(name="${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Override
    public ApiResponseDto getEmployeeById(Long id) {


        LOGGER.info("inside getemployeebyid method");
        Employee employee=employeeRepository.findById(id).get();
//        ResponseEntity<DepartmentDto> dtoResponseEntity= restTemplate.getForEntity(
//                "http://localhost:8080/api/departments/"+employee.getDepartmentCode(),DepartmentDto.class);
//
//        DepartmentDto departmentDto=dtoResponseEntity.getBody();

        DepartmentDto departmentDto=webClient.get()
                .uri("http://localhost:8080/api/departments/"+employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

//        DepartmentDto departmentDto=apiClient.getByDepartmentCode(employee.getDepartmentCode());

        EmployeeDto employeeDto= modelMapper.map(employee,EmployeeDto.class);

        ApiResponseDto apiResponseDto=new ApiResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        return  apiResponseDto;


    }

    public ApiResponseDto getDefaultDepartment(Long id,Exception e) {

        LOGGER.info("inside getemployeebyid method");

        Employee employee=employeeRepository.findById(id).get();


        DepartmentDto departmentDto=new DepartmentDto();
        departmentDto.setDepartmentName("R&D Department");

        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and development department");


        EmployeeDto employeeDto= modelMapper.map(employee,EmployeeDto.class);

        ApiResponseDto apiResponseDto=new ApiResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        return  apiResponseDto;


    }
}
