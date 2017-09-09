import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DepartmentMySuffix } from './department-my-suffix.model';
import { DepartmentMySuffixPopupService } from './department-my-suffix-popup.service';
import { DepartmentMySuffixService } from './department-my-suffix.service';
import { LocationMySuffix, LocationMySuffixService } from '../location';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-department-my-suffix-dialog',
    templateUrl: './department-my-suffix-dialog.component.html'
})
export class DepartmentMySuffixDialogComponent implements OnInit {

    department: DepartmentMySuffix;
    isSaving: boolean;

    locations: LocationMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private departmentService: DepartmentMySuffixService,
        private locationService: LocationMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.locationService
            .query({filter: 'department-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.department.locationId) {
                    this.locations = res.json;
                } else {
                    this.locationService
                        .find(this.department.locationId)
                        .subscribe((subRes: LocationMySuffix) => {
                            this.locations = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.department.id !== undefined) {
            this.subscribeToSaveResponse(
                this.departmentService.update(this.department));
        } else {
            this.subscribeToSaveResponse(
                this.departmentService.create(this.department));
        }
    }

    private subscribeToSaveResponse(result: Observable<DepartmentMySuffix>) {
        result.subscribe((res: DepartmentMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DepartmentMySuffix) {
        this.eventManager.broadcast({ name: 'departmentListModification', content: 'OK'});
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

    trackLocationById(index: number, item: LocationMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-department-my-suffix-popup',
    template: ''
})
export class DepartmentMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private departmentPopupService: DepartmentMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.departmentPopupService
                    .open(DepartmentMySuffixDialogComponent as Component, params['id']);
            } else {
                this.departmentPopupService
                    .open(DepartmentMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
