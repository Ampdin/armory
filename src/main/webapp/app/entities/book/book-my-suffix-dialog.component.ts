import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BookMySuffix } from './book-my-suffix.model';
import { BookMySuffixPopupService } from './book-my-suffix-popup.service';
import { BookMySuffixService } from './book-my-suffix.service';

@Component({
    selector: 'jhi-book-my-suffix-dialog',
    templateUrl: './book-my-suffix-dialog.component.html'
})
export class BookMySuffixDialogComponent implements OnInit {

    book: BookMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bookService: BookMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.book.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bookService.update(this.book));
        } else {
            this.subscribeToSaveResponse(
                this.bookService.create(this.book));
        }
    }

    private subscribeToSaveResponse(result: Observable<BookMySuffix>) {
        result.subscribe((res: BookMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BookMySuffix) {
        this.eventManager.broadcast({ name: 'bookListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-book-my-suffix-popup',
    template: ''
})
export class BookMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bookPopupService: BookMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bookPopupService
                    .open(BookMySuffixDialogComponent as Component, params['id']);
            } else {
                this.bookPopupService
                    .open(BookMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
