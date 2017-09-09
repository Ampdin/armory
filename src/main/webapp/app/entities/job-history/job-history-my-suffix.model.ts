import { BaseEntity } from './../../shared';

const enum Language {
    'FRENCH',
    'ENGLISH',
    'SPANISH',
    'ITALIAN'
}

export class JobHistoryMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public jobHistoryId?: number,
        public startDate?: any,
        public endDate?: any,
        public language?: Language,
        public jobId?: number,
        public departmentId?: number,
        public employeeId?: number,
    ) {
    }
}
