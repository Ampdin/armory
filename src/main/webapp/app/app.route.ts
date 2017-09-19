import { NgModule } from '@angular/core';

import { Routes, RouterModule } from '@angular/router';

import { JhiMainComponent, JhiSimpleComponent } from './layouts';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
    },
    {
        path: '',
        component: JhiMainComponent,
        data: {
            title: 'Home'
        },
        children: [
            {
                path: 'dashboard',
                loadChildren: './layouts/main/JhiMainComponent'
            }
            // {
            //     path: 'components',
            //     loadChildren: './views/components/components.module#ComponentsModule'
            // },
            // {
            //     path: 'icons',
            //     loadChildren: './views/icons/icons.module#IconsModule'
            // },
            // {
            //     path: 'widgets',
            //     loadChildren: './views/widgets/widgets.module#WidgetsModule'
            // },
            // {
            //     path: 'charts',
            //     loadChildren: './views/chartjs/chartjs.module#ChartJSModule'
            // }
        ]
    },
    {
        path: 'pages',
        component: JhiSimpleComponent,
        data: {
            title: 'Pages'
        },
        children: [
            {
                path: '',
                loadChildren: './layouts/main/JhiSimpleComponent',
            }
        ]
    }
    // path: '',
    // component: NavbarComponent,
    // outlet: 'navbar'
];

@NgModule({
    imports: [ RouterModule.forRoot(routes) ],
    exports: [ RouterModule ]
})
export class AppRoutingModule {}
