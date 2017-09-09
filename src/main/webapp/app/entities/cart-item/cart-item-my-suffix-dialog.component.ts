import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CartItemMySuffix } from './cart-item-my-suffix.model';
import { CartItemMySuffixPopupService } from './cart-item-my-suffix-popup.service';
import { CartItemMySuffixService } from './cart-item-my-suffix.service';
import { BookMySuffix, BookMySuffixService } from '../book';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-cart-item-my-suffix-dialog',
    templateUrl: './cart-item-my-suffix-dialog.component.html'
})
export class CartItemMySuffixDialogComponent implements OnInit {

    cartItem: CartItemMySuffix;
    isSaving: boolean;

    books: BookMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private cartItemService: CartItemMySuffixService,
        private bookService: BookMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.bookService.query()
            .subscribe((res: ResponseWrapper) => { this.books = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cartItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cartItemService.update(this.cartItem));
        } else {
            this.subscribeToSaveResponse(
                this.cartItemService.create(this.cartItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<CartItemMySuffix>) {
        result.subscribe((res: CartItemMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CartItemMySuffix) {
        this.eventManager.broadcast({ name: 'cartItemListModification', content: 'OK'});
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

    trackBookById(index: number, item: BookMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cart-item-my-suffix-popup',
    template: ''
})
export class CartItemMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cartItemPopupService: CartItemMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cartItemPopupService
                    .open(CartItemMySuffixDialogComponent as Component, params['id']);
            } else {
                this.cartItemPopupService
                    .open(CartItemMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
