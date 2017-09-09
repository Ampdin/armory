import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('CartItem e2e test', () => {

    let navBarPage: NavBarPage;
    let cartItemDialogPage: CartItemDialogPage;
    let cartItemComponentsPage: CartItemComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CartItems', () => {
        navBarPage.goToEntity('cart-item-my-suffix');
        cartItemComponentsPage = new CartItemComponentsPage();
        expect(cartItemComponentsPage.getTitle()).toMatch(/armoryApp.cartItem.home.title/);

    });

    it('should load create CartItem dialog', () => {
        cartItemComponentsPage.clickOnCreateButton();
        cartItemDialogPage = new CartItemDialogPage();
        expect(cartItemDialogPage.getModalTitle()).toMatch(/armoryApp.cartItem.home.createOrEditLabel/);
        cartItemDialogPage.close();
    });

    it('should create and save CartItems', () => {
        cartItemComponentsPage.clickOnCreateButton();
        cartItemDialogPage.setCartItemIdInput('5');
        expect(cartItemDialogPage.getCartItemIdInput()).toMatch('5');
        cartItemDialogPage.setProductNameInput('productName');
        expect(cartItemDialogPage.getProductNameInput()).toMatch('productName');
        cartItemDialogPage.setProductPriceInput('5');
        expect(cartItemDialogPage.getProductPriceInput()).toMatch('5');
        cartItemDialogPage.departmentMajorSelectLastOption();
        cartItemDialogPage.setProductDescriptionInput('productDescription');
        expect(cartItemDialogPage.getProductDescriptionInput()).toMatch('productDescription');
        cartItemDialogPage.bookSelectLastOption();
        cartItemDialogPage.save();
        expect(cartItemDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CartItemComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-cart-item-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CartItemDialogPage {
    modalTitle = element(by.css('h4#myCartItemLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    cartItemIdInput = element(by.css('input#field_cartItemId'));
    productNameInput = element(by.css('input#field_productName'));
    productPriceInput = element(by.css('input#field_productPrice'));
    departmentMajorSelect = element(by.css('select#field_departmentMajor'));
    productDescriptionInput = element(by.css('input#field_productDescription'));
    bookSelect = element(by.css('select#field_book'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCartItemIdInput = function (cartItemId) {
        this.cartItemIdInput.sendKeys(cartItemId);
    }

    getCartItemIdInput = function () {
        return this.cartItemIdInput.getAttribute('value');
    }

    setProductNameInput = function (productName) {
        this.productNameInput.sendKeys(productName);
    }

    getProductNameInput = function () {
        return this.productNameInput.getAttribute('value');
    }

    setProductPriceInput = function (productPrice) {
        this.productPriceInput.sendKeys(productPrice);
    }

    getProductPriceInput = function () {
        return this.productPriceInput.getAttribute('value');
    }

    setDepartmentMajorSelect = function (departmentMajor) {
        this.departmentMajorSelect.sendKeys(departmentMajor);
    }

    getDepartmentMajorSelect = function () {
        return this.departmentMajorSelect.element(by.css('option:checked')).getText();
    }

    departmentMajorSelectLastOption = function () {
        this.departmentMajorSelect.all(by.tagName('option')).last().click();
    }
    setProductDescriptionInput = function (productDescription) {
        this.productDescriptionInput.sendKeys(productDescription);
    }

    getProductDescriptionInput = function () {
        return this.productDescriptionInput.getAttribute('value');
    }

    bookSelectLastOption = function () {
        this.bookSelect.all(by.tagName('option')).last().click();
    }

    bookSelectOption = function (option) {
        this.bookSelect.sendKeys(option);
    }

    getBookSelect = function () {
        return this.bookSelect;
    }

    getBookSelectedOption = function () {
        return this.bookSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
