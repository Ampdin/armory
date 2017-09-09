import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CartItemMySuffix } from './cart-item-my-suffix.model';
import { CartItemMySuffixPopupService } from './cart-item-my-suffix-popup.service';
import { CartItemMySuffixService } from './cart-item-my-suffix.service';

@Component({
    selector: 'jhi-cart-item-my-suffix-delete-dialog',
    templateUrl: './cart-item-my-suffix-delete-dialog.component.html'
})
export class CartItemMySuffixDeleteDialogComponent {

    cartItem: CartItemMySuffix;

    constructor(
        private cartItemService: CartItemMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cartItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cartItemListModification',
                content: 'Deleted an cartItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cart-item-my-suffix-delete-popup',
    template: ''
})
export class CartItemMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cartItemPopupService: CartItemMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cartItemPopupService
                .open(CartItemMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
