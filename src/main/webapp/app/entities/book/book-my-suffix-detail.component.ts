import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BookMySuffix } from './book-my-suffix.model';
import { BookMySuffixService } from './book-my-suffix.service';

@Component({
    selector: 'jhi-book-my-suffix-detail',
    templateUrl: './book-my-suffix-detail.component.html'
})
export class BookMySuffixDetailComponent implements OnInit, OnDestroy {

    book: BookMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bookService: BookMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBooks();
    }

    load(id) {
        this.bookService.find(id).subscribe((book) => {
            this.book = book;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBooks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bookListModification',
            (response) => this.load(this.book.id)
        );
    }
}
