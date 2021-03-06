import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';

import { ArmorySharedModule, UserRouteAccessService } from './shared';
import { ArmoryHomeModule } from './home/home.module';
import { ArmoryAdminModule } from './admin/admin.module';
import { ArmoryAccountModule } from './account/account.module';
import { ArmoryEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// Import components
import {
    AppAside
    // AppBreadcrumbs,
    // AppFooter,
    // AppHeader,
    // AppSidebar
} from './components';

const APP_COMPONENTS = [
    AppAside
    // AppBreadcrumbs,
    // AppFooter,
    // AppHeader,
    // AppSidebar
]

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        ArmorySharedModule,
        ArmoryHomeModule,
        ArmoryAdminModule,
        ArmoryAccountModule,
        ArmoryEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        ...APP_COMPONENTS
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,
        {
            provide: LocationStrategy,
            useClass: HashLocationStrategy
        }],
    bootstrap: [ JhiMainComponent ]
})
export class ArmoryAppModule {}
