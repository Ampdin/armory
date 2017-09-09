import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ArmoryEmployeeMySuffixModule } from './employee/employee-my-suffix.module';
import { ArmoryDepartmentMySuffixModule } from './department/department-my-suffix.module';
import { ArmoryRegionMySuffixModule } from './region/region-my-suffix.module';
import { ArmoryCountryMySuffixModule } from './country/country-my-suffix.module';
import { ArmoryLocationMySuffixModule } from './location/location-my-suffix.module';
import { ArmoryTaskMySuffixModule } from './task/task-my-suffix.module';
import { ArmoryJobMySuffixModule } from './job/job-my-suffix.module';
import { ArmoryJobHistoryMySuffixModule } from './job-history/job-history-my-suffix.module';
import { ArmoryShoppingCartMySuffixModule } from './shopping-cart/shopping-cart-my-suffix.module';
import { ArmoryCartItemMySuffixModule } from './cart-item/cart-item-my-suffix.module';
import { ArmoryBookMySuffixModule } from './book/book-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ArmoryEmployeeMySuffixModule,
        ArmoryDepartmentMySuffixModule,
        ArmoryRegionMySuffixModule,
        ArmoryCountryMySuffixModule,
        ArmoryLocationMySuffixModule,
        ArmoryTaskMySuffixModule,
        ArmoryJobMySuffixModule,
        ArmoryJobHistoryMySuffixModule,
        ArmoryShoppingCartMySuffixModule,
        ArmoryCartItemMySuffixModule,
        ArmoryBookMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArmoryEntityModule {}
