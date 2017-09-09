import { BaseEntity } from './../../shared';

export class JobMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public jobId?: number,
        public jobTitle?: string,
        public onetCode?: number,
        public jobDescription?: string,
        public minSalary?: number,
        public maxSalary?: number,
        public employeeId?: number,
        public tasks?: BaseEntity[],
    ) {
    }
}
