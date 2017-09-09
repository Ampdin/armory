import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArmorySharedModule } from '../../shared';
import {
    CartItemMySuffixService,
    CartItemMySuffixPopupService,
    CartItemMySuffixComponent,
    CartItemMySuffixDetailComponent,
    CartItemMySuffixDialogComponent,
    CartItemMySuffixPopupComponent,
    CartItemMySuffixDeletePopupComponent,
    CartItemMySuffixDeleteDialogComponent,
    cartItemRoute,
    cartItemPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cartItemRoute,
    ...cartItemPopupRoute,
];

@NgModule({
    imports: [
        ArmorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CartItemMySuffixComponent,
        CartItemMySuffixDetailComponent,
        CartItemMySuffixDialogComponent,
        CartItemMySuffixDeleteDialogComponent,
        CartItemMySuffixPopupComponent,
        CartItemMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        CartItemMySuffixComponent,
        CartItemMySuffixDialogComponent,
        CartItemMySuffixPopupComponent,
        CartItemMySuffixDeleteDialogComponent,
        CartItemMySuffixDeletePopupComponent,
    ],
    providers: [
        CartItemMySuffixService,
        CartItemMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArmoryCartItemMySuffixModule {}
