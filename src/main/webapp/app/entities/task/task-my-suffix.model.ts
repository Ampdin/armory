import { BaseEntity } from './../../shared';

export class TaskMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public taskID?: string,
        public taskName?: string,
        public scaleID?: string,
        public scaleName?: string,
        public dataValue?: number,
        public n?: number,
        public standardError?: number,
        public lowerClBound?: number,
        public upperClBound?: number,
        public recommendSuppress?: string,
        public notRelevant?: string,
        public date?: any,
        public domainSource?: string,
        public description?: string,
        public jobs?: BaseEntity[],
    ) {
    }
}
