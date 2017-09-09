import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RegionMySuffix } from './region-my-suffix.model';
import { RegionMySuffixPopupService } from './region-my-suffix-popup.service';
import { RegionMySuffixService } from './region-my-suffix.service';

@Component({
    selector: 'jhi-region-my-suffix-dialog',
    templateUrl: './region-my-suffix-dialog.component.html'
})
export class RegionMySuffixDialogComponent implements OnInit {

    region: RegionMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private regionService: RegionMySuffixService,
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
        if (this.region.id !== undefined) {
            this.subscribeToSaveResponse(
                this.regionService.update(this.region));
        } else {
            this.subscribeToSaveResponse(
                this.regionService.create(this.region));
        }
    }

    private subscribeToSaveResponse(result: Observable<RegionMySuffix>) {
        result.subscribe((res: RegionMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RegionMySuffix) {
        this.eventManager.broadcast({ name: 'regionListModification', content: 'OK'});
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
    selector: 'jhi-region-my-suffix-popup',
    template: ''
})
export class RegionMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regionPopupService: RegionMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.regionPopupService
                    .open(RegionMySuffixDialogComponent as Component, params['id']);
            } else {
                this.regionPopupService
                    .open(RegionMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
