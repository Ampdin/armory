import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BookMySuffix } from './book-my-suffix.model';
import { BookMySuffixPopupService } from './book-my-suffix-popup.service';
import { BookMySuffixService } from './book-my-suffix.service';

@Component({
    selector: 'jhi-book-my-suffix-delete-dialog',
    templateUrl: './book-my-suffix-delete-dialog.component.html'
})
export class BookMySuffixDeleteDialogComponent {

    book: BookMySuffix;

    constructor(
        private bookService: BookMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bookService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bookListModification',
                content: 'Deleted an book'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-book-my-suffix-delete-popup',
    template: ''
})
export class BookMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bookPopupService: BookMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bookPopupService
                .open(BookMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
