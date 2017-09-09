import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BookMySuffixComponent } from './book-my-suffix.component';
import { BookMySuffixDetailComponent } from './book-my-suffix-detail.component';
import { BookMySuffixPopupComponent } from './book-my-suffix-dialog.component';
import { BookMySuffixDeletePopupComponent } from './book-my-suffix-delete-dialog.component';

export const bookRoute: Routes = [
    {
        path: 'book-my-suffix',
        component: BookMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.book.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'book-my-suffix/:id',
        component: BookMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.book.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bookPopupRoute: Routes = [
    {
        path: 'book-my-suffix-new',
        component: BookMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.book.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'book-my-suffix/:id/edit',
        component: BookMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.book.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'book-my-suffix/:id/delete',
        component: BookMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'armoryApp.book.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
