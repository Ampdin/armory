import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ShoppingCartMySuffix } from './shopping-cart-my-suffix.model';
import { ShoppingCartMySuffixService } from './shopping-cart-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-shopping-cart-my-suffix',
    templateUrl: './shopping-cart-my-suffix.component.html'
})
export class ShoppingCartMySuffixComponent implements OnInit, OnDestroy {
shoppingCarts: ShoppingCartMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private shoppingCartService: ShoppingCartMySuffixService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.shoppingCartService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.shoppingCarts = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.shoppingCartService.query().subscribe(
            (res: ResponseWrapper) => {
                this.shoppingCarts = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInShoppingCarts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ShoppingCartMySuffix) {
        return item.id;
    }
    registerChangeInShoppingCarts() {
        this.eventSubscriber = this.eventManager.subscribe('shoppingCartListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
