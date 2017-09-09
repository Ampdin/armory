import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CountryMySuffix } from './country-my-suffix.model';
import { CountryMySuffixPopupService } from './country-my-suffix-popup.service';
import { CountryMySuffixService } from './country-my-suffix.service';
import { RegionMySuffix, RegionMySuffixService } from '../region';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-country-my-suffix-dialog',
    templateUrl: './country-my-suffix-dialog.component.html'
})
export class CountryMySuffixDialogComponent implements OnInit {

    country: CountryMySuffix;
    isSaving: boolean;

    regions: RegionMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private countryService: CountryMySuffixService,
        private regionService: RegionMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.regionService
            .query({filter: 'country-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.country.regionId) {
                    this.regions = res.json;
                } else {
                    this.regionService
                        .find(this.country.regionId)
                        .subscribe((subRes: RegionMySuffix) => {
                            this.regions = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.country.id !== undefined) {
            this.subscribeToSaveResponse(
                this.countryService.update(this.country));
        } else {
            this.subscribeToSaveResponse(
                this.countryService.create(this.country));
        }
    }

    private subscribeToSaveResponse(result: Observable<CountryMySuffix>) {
        result.subscribe((res: CountryMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CountryMySuffix) {
        this.eventManager.broadcast({ name: 'countryListModification', content: 'OK'});
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

    trackRegionById(index: number, item: RegionMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-country-my-suffix-popup',
    template: ''
})
export class CountryMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countryPopupService: CountryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.countryPopupService
                    .open(CountryMySuffixDialogComponent as Component, params['id']);
            } else {
                this.countryPopupService
                    .open(CountryMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
