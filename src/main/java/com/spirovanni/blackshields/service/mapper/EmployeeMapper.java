package com.spirovanni.blackshields.service.mapper;

import com.spirovanni.blackshields.domain.*;
import com.spirovanni.blackshields.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Employee and its DTO EmployeeDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, })
public interface EmployeeMapper extends EntityMapper <EmployeeDTO, Employee> {

    @Mapping(source = "department.id", target = "departmentId")

    @Mapping(source = "manager.id", target = "managerId")
    EmployeeDTO toDto(Employee employee); 

    @Mapping(source = "departmentId", target = "department")
    @Mapping(target = "jobs", ignore = true)

    @Mapping(source = "managerId", target = "manager")
    Employee toEntity(EmployeeDTO employeeDTO); 
    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
