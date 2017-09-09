import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ShoppingCartMySuffixComponent } from './shopping-cart-my-suffix.component';
import { ShoppingCartMySuffixDetailComponent } from './shopping-cart-my-suffix-detail.component';
import { ShoppingCartMySuffixPopupComponent } from './shopping-cart-my-suffix-dialog.component';
import { ShoppingCartMySuffixDeletePopupComponent } from './shopping-cart-my-suffix-delete-dialog.component';

export const shoppingCartRoute: Routes = [
    {
        path: 'shopping-cart-my-suffix',
        component: ShoppingCartMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shopping-cart-my-suffix/:id',
        component: ShoppingCartMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shoppingCartPopupRoute: Routes = [
    {
        path: 'shopping-cart-my-suffix-new',
        component: ShoppingCartMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shopping-cart-my-suffix/:id/edit',
        component: ShoppingCartMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shopping-cart-my-suffix/:id/delete',
        component: ShoppingCartMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
