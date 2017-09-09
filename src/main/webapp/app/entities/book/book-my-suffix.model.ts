import { BaseEntity } from './../../shared';

const enum Language {
    'FRENCH',
    'ENGLISH',
    'SPANISH',
    'ITALIAN'
}

export class BookMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public bookId?: number,
        public bookName?: string,
        public bookPrice?: number,
        public publisher?: string,
        public language?: Language,
        public isbn10?: string,
        public isbn13?: string,
        public productDimensions?: string,
        public shippingWeight?: string,
        public ranking?: string,
        public averageRanking?: string,
        public author?: string,
        public subject?: string,
        public bookDescription?: string,
        public cartItems?: BaseEntity[],
    ) {
    }
}
