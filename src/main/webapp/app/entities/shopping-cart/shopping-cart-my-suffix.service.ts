import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ShoppingCartMySuffix } from './shopping-cart-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ShoppingCartMySuffixService {

    private resourceUrl = 'api/shopping-carts';
    private resourceSearchUrl = 'api/_search/shopping-carts';

    constructor(private http: Http) { }

    create(shoppingCart: ShoppingCartMySuffix): Observable<ShoppingCartMySuffix> {
        const copy = this.convert(shoppingCart);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(shoppingCart: ShoppingCartMySuffix): Observable<ShoppingCartMySuffix> {
        const copy = this.convert(shoppingCart);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ShoppingCartMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(shoppingCart: ShoppingCartMySuffix): ShoppingCartMySuffix {
        const copy: ShoppingCartMySuffix = Object.assign({}, shoppingCart);
        return copy;
    }
}
