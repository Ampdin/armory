import { BaseEntity } from './../../shared';

export class ShoppingCartMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public shoppingCartId?: number,
        public cartStatus?: string,
        public saveForLater?: string,
        public cartId?: number,
        public cartItemId?: number,
    ) {
    }
}
