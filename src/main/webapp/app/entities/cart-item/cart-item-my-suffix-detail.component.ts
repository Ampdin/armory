import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CartItemMySuffix } from './cart-item-my-suffix.model';
import { CartItemMySuffixService } from './cart-item-my-suffix.service';

@Component({
    selector: 'jhi-cart-item-my-suffix-detail',
    templateUrl: './cart-item-my-suffix-detail.component.html'
})
export class CartItemMySuffixDetailComponent implements OnInit, OnDestroy {

    cartItem: CartItemMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cartItemService: CartItemMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCartItems();
    }

    load(id) {
        this.cartItemService.find(id).subscribe((cartItem) => {
            this.cartItem = cartItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCartItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cartItemListModification',
            (response) => this.load(this.cartItem.id)
        );
    }
}
