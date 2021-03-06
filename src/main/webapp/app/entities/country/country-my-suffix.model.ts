import { BaseEntity } from './../../shared';

export class CountryMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public countryId?: number,
        public countryName?: string,
        public regionId?: number,
    ) {
    }
}
