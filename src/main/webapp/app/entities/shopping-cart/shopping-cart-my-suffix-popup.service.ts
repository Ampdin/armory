import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ShoppingCartMySuffix } from './shopping-cart-my-suffix.model';
import { ShoppingCartMySuffixService } from './shopping-cart-my-suffix.service';

@Injectable()
export class ShoppingCartMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private shoppingCartService: ShoppingCartMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.shoppingCartService.find(id).subscribe((shoppingCart) => {
                    this.ngbModalRef = this.shoppingCartModalRef(component, shoppingCart);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.shoppingCartModalRef(component, new ShoppingCartMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    shoppingCartModalRef(component: Component, shoppingCart: ShoppingCartMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.shoppingCart = shoppingCart;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
