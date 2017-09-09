import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LocationMySuffix } from './location-my-suffix.model';
import { LocationMySuffixPopupService } from './location-my-suffix-popup.service';
import { LocationMySuffixService } from './location-my-suffix.service';
import { CountryMySuffix, CountryMySuffixService } from '../country';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-location-my-suffix-dialog',
    templateUrl: './location-my-suffix-dialog.component.html'
})
export class LocationMySuffixDialogComponent implements OnInit {

    location: LocationMySuffix;
    isSaving: boolean;

    countries: CountryMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private locationService: LocationMySuffixService,
        private countryService: CountryMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.countryService
            .query({filter: 'location-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.location.countryId) {
                    this.countries = res.json;
                } else {
                    this.countryService
                        .find(this.location.countryId)
                        .subscribe((subRes: CountryMySuffix) => {
                            this.countries = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== undefined) {
            this.subscribeToSaveResponse(
                this.locationService.update(this.location));
        } else {
            this.subscribeToSaveResponse(
                this.locationService.create(this.location));
        }
    }

    private subscribeToSaveResponse(result: Observable<LocationMySuffix>) {
        result.subscribe((res: LocationMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: LocationMySuffix) {
        this.eventManager.broadcast({ name: 'locationListModification', content: 'OK'});
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

    trackCountryById(index: number, item: CountryMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-location-my-suffix-popup',
    template: ''
})
export class LocationMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationPopupService: LocationMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.locationPopupService
                    .open(LocationMySuffixDialogComponent as Component, params['id']);
            } else {
                this.locationPopupService
                    .open(LocationMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
