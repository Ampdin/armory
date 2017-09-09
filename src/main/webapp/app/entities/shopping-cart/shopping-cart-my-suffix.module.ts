import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArmorySharedModule } from '../../shared';
import {
    ShoppingCartMySuffixService,
    ShoppingCartMySuffixPopupService,
    ShoppingCartMySuffixComponent,
    ShoppingCartMySuffixDetailComponent,
    ShoppingCartMySuffixDialogComponent,
    ShoppingCartMySuffixPopupComponent,
    ShoppingCartMySuffixDeletePopupComponent,
    ShoppingCartMySuffixDeleteDialogComponent,
    shoppingCartRoute,
    shoppingCartPopupRoute,
} from './';

const ENTITY_STATES = [
    ...shoppingCartRoute,
    ...shoppingCartPopupRoute,
];

@NgModule({
    imports: [
        ArmorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShoppingCartMySuffixComponent,
        ShoppingCartMySuffixDetailComponent,
        ShoppingCartMySuffixDialogComponent,
        ShoppingCartMySuffixDeleteDialogComponent,
        ShoppingCartMySuffixPopupComponent,
        ShoppingCartMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ShoppingCartMySuffixComponent,
        ShoppingCartMySuffixDialogComponent,
        ShoppingCartMySuffixPopupComponent,
        ShoppingCartMySuffixDeleteDialogComponent,
        ShoppingCartMySuffixDeletePopupComponent,
    ],
    providers: [
        ShoppingCartMySuffixService,
        ShoppingCartMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArmoryShoppingCartMySuffixModule {}
