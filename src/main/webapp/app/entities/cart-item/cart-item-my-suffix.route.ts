import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CartItemMySuffixComponent } from './cart-item-my-suffix.component';
import { CartItemMySuffixDetailComponent } from './cart-item-my-suffix-detail.component';
import { CartItemMySuffixPopupComponent } from './cart-item-my-suffix-dialog.component';
import { CartItemMySuffixDeletePopupComponent } from './cart-item-my-suffix-delete-dialog.component';

export const cartItemRoute: Routes = [
    {
        path: 'cart-item-my-suffix',
        component: CartItemMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cart-item-my-suffix/:id',
        component: CartItemMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cartItemPopupRoute: Routes = [
    {
        path: 'cart-item-my-suffix-new',
        component: CartItemMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cart-item-my-suffix/:id/edit',
        component: CartItemMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cart-item-my-suffix/:id/delete',
        component: CartItemMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
