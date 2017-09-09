import { BaseEntity } from './../../shared';

export class EmployeeMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public employeeId?: number,
        public playerType?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phone?: string,
        public badgeNumber?: string,
        public startDate?: any,
        public previousSalary?: number,
        public currentSalary?: number,
        public goalSalary?: number,
        public pathGoal?: string,
        public address?: string,
        public city?: string,
        public zip?: number,
        public state?: string,
        public departmentId?: number,
        public jobs?: BaseEntity[],
        public managerId?: number,
    ) {
    }
}
