import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArmorySharedModule } from '../../shared';
import {
    BookMySuffixService,
    BookMySuffixPopupService,
    BookMySuffixComponent,
    BookMySuffixDetailComponent,
    BookMySuffixDialogComponent,
    BookMySuffixPopupComponent,
    BookMySuffixDeletePopupComponent,
    BookMySuffixDeleteDialogComponent,
    bookRoute,
    bookPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bookRoute,
    ...bookPopupRoute,
];

@NgModule({
    imports: [
        ArmorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BookMySuffixComponent,
        BookMySuffixDetailComponent,
        BookMySuffixDialogComponent,
        BookMySuffixDeleteDialogComponent,
        BookMySuffixPopupComponent,
        BookMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        BookMySuffixComponent,
        BookMySuffixDialogComponent,
        BookMySuffixPopupComponent,
        BookMySuffixDeleteDialogComponent,
        BookMySuffixDeletePopupComponent,
    ],
    providers: [
        BookMySuffixService,
        BookMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArmoryBookMySuffixModule {}
