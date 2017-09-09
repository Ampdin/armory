import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ShoppingCartMySuffix } from './shopping-cart-my-suffix.model';
import { ShoppingCartMySuffixService } from './shopping-cart-my-suffix.service';

@Component({
    selector: 'jhi-shopping-cart-my-suffix-detail',
    templateUrl: './shopping-cart-my-suffix-detail.component.html'
})
export class ShoppingCartMySuffixDetailComponent implements OnInit, OnDestroy {

    shoppingCart: ShoppingCartMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shoppingCartService: ShoppingCartMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShoppingCarts();
    }

    load(id) {
        this.shoppingCartService.find(id).subscribe((shoppingCart) => {
            this.shoppingCart = shoppingCart;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShoppingCarts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shoppingCartListModification',
            (response) => this.load(this.shoppingCart.id)
        );
    }
}
