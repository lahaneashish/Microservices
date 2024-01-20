package net.javaguides.departmentservice.service;

import net.javaguides.departmentservice.dto.DepartmentDto;


public interface DepartmentService {
    DepartmentDto saveDepartment(DepartmentDto dto);
    DepartmentDto getDepartmentByCode(String code);

}
