package net.javaguides.departmentservice.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.departmentservice.dto.DepartmentDto;
import net.javaguides.departmentservice.entity.Department;
import net.javaguides.departmentservice.repository.DepartmentRepository;
import net.javaguides.departmentservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Override

    public DepartmentDto saveDepartment(DepartmentDto dto) {
        Department department=new Department(
                dto.getDepartmentid(),
                dto.getDepartmentName(),
                dto.getDepartmentDescription(),
                dto.getDepartmentCode()
        );
        Department savedDepartment=departmentRepository.save(department);

        DepartmentDto savedDepartmentDto= new DepartmentDto(
                savedDepartment.getId(),
                savedDepartment.getDepartmentName(),
                savedDepartment.getDepartmentDescription(),
                savedDepartment.getDepartmentCode()
        );


        return savedDepartmentDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
      Department department= departmentRepository.findByDepartmentCode(departmentCode);

      DepartmentDto departmentDto=new DepartmentDto(
              department.getId(),
              department.getDepartmentName(),
              department.getDepartmentDescription(),
              department.getDepartmentCode()
      );

        return departmentDto;
    }


}
