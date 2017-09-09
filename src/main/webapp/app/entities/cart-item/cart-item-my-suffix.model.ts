import { BaseEntity } from './../../shared';

const enum DepartmentMajor {
    'APPS',
    'BEAUTY',
    'BOOKS',
    'MUSIC',
    'GROCERY',
    'FINANCIAL',
    'HEALTH',
    'OFFICE',
    'SOFTWARE'
}

export class CartItemMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public cartItemId?: number,
        public productName?: string,
        public productPrice?: number,
        public departmentMajor?: DepartmentMajor,
        public productDescription?: string,
        public shoppingCarts?: BaseEntity[],
        public bookId?: number,
    ) {
    }
}
