import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ShoppingCartMySuffix } from './shopping-cart-my-suffix.model';
import { ShoppingCartMySuffixPopupService } from './shopping-cart-my-suffix-popup.service';
import { ShoppingCartMySuffixService } from './shopping-cart-my-suffix.service';
import { EmployeeMySuffix, EmployeeMySuffixService } from '../employee';
import { CartItemMySuffix, CartItemMySuffixService } from '../cart-item';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-shopping-cart-my-suffix-dialog',
    templateUrl: './shopping-cart-my-suffix-dialog.component.html'
})
export class ShoppingCartMySuffixDialogComponent implements OnInit {

    shoppingCart: ShoppingCartMySuffix;
    isSaving: boolean;

    carts: EmployeeMySuffix[];

    cartitems: CartItemMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private shoppingCartService: ShoppingCartMySuffixService,
        private employeeService: EmployeeMySuffixService,
        private cartItemService: CartItemMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.employeeService
            .query({filter: 'shoppingcart-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.shoppingCart.cartId) {
                    this.carts = res.json;
                } else {
                    this.employeeService
                        .find(this.shoppingCart.cartId)
                        .subscribe((subRes: EmployeeMySuffix) => {
                            this.carts = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.cartItemService.query()
            .subscribe((res: ResponseWrapper) => { this.cartitems = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shoppingCart.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shoppingCartService.update(this.shoppingCart));
        } else {
            this.subscribeToSaveResponse(
                this.shoppingCartService.create(this.shoppingCart));
        }
    }

    private subscribeToSaveResponse(result: Observable<ShoppingCartMySuffix>) {
        result.subscribe((res: ShoppingCartMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ShoppingCartMySuffix) {
        this.eventManager.broadcast({ name: 'shoppingCartListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackEmployeeById(index: number, item: EmployeeMySuffix) {
        return item.id;
    }

    trackCartItemById(index: number, item: CartItemMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-shopping-cart-my-suffix-popup',
    template: ''
})
export class ShoppingCartMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shoppingCartPopupService: ShoppingCartMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shoppingCartPopupService
                    .open(ShoppingCartMySuffixDialogComponent as Component, params['id']);
            } else {
                this.shoppingCartPopupService
                    .open(ShoppingCartMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
