import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShoppingCartMySuffix } from './shopping-cart-my-suffix.model';
import { ShoppingCartMySuffixPopupService } from './shopping-cart-my-suffix-popup.service';
import { ShoppingCartMySuffixService } from './shopping-cart-my-suffix.service';

@Component({
    selector: 'jhi-shopping-cart-my-suffix-delete-dialog',
    templateUrl: './shopping-cart-my-suffix-delete-dialog.component.html'
})
export class ShoppingCartMySuffixDeleteDialogComponent {

    shoppingCart: ShoppingCartMySuffix;

    constructor(
        private shoppingCartService: ShoppingCartMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shoppingCartService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shoppingCartListModification',
                content: 'Deleted an shoppingCart'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shopping-cart-my-suffix-delete-popup',
    template: ''
})
export class ShoppingCartMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shoppingCartPopupService: ShoppingCartMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shoppingCartPopupService
                .open(ShoppingCartMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
