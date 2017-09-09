import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('ShoppingCart e2e test', () => {

    let navBarPage: NavBarPage;
    let shoppingCartDialogPage: ShoppingCartDialogPage;
    let shoppingCartComponentsPage: ShoppingCartComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ShoppingCarts', () => {
        navBarPage.goToEntity('shopping-cart-my-suffix');
        shoppingCartComponentsPage = new ShoppingCartComponentsPage();
        expect(shoppingCartComponentsPage.getTitle()).toMatch(/armoryApp.shoppingCart.home.title/);

    });

    it('should load create ShoppingCart dialog', () => {
        shoppingCartComponentsPage.clickOnCreateButton();
        shoppingCartDialogPage = new ShoppingCartDialogPage();
        expect(shoppingCartDialogPage.getModalTitle()).toMatch(/armoryApp.shoppingCart.home.createOrEditLabel/);
        shoppingCartDialogPage.close();
    });

    it('should create and save ShoppingCarts', () => {
        shoppingCartComponentsPage.clickOnCreateButton();
        shoppingCartDialogPage.setShoppingCartIdInput('5');
        expect(shoppingCartDialogPage.getShoppingCartIdInput()).toMatch('5');
        shoppingCartDialogPage.setCartStatusInput('cartStatus');
        expect(shoppingCartDialogPage.getCartStatusInput()).toMatch('cartStatus');
        shoppingCartDialogPage.setSaveForLaterInput('saveForLater');
        expect(shoppingCartDialogPage.getSaveForLaterInput()).toMatch('saveForLater');
        shoppingCartDialogPage.cartSelectLastOption();
        shoppingCartDialogPage.cartItemSelectLastOption();
        shoppingCartDialogPage.save();
        expect(shoppingCartDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ShoppingCartComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-shopping-cart-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ShoppingCartDialogPage {
    modalTitle = element(by.css('h4#myShoppingCartLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    shoppingCartIdInput = element(by.css('input#field_shoppingCartId'));
    cartStatusInput = element(by.css('input#field_cartStatus'));
    saveForLaterInput = element(by.css('input#field_saveForLater'));
    cartSelect = element(by.css('select#field_cart'));
    cartItemSelect = element(by.css('select#field_cartItem'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setShoppingCartIdInput = function (shoppingCartId) {
        this.shoppingCartIdInput.sendKeys(shoppingCartId);
    }

    getShoppingCartIdInput = function () {
        return this.shoppingCartIdInput.getAttribute('value');
    }

    setCartStatusInput = function (cartStatus) {
        this.cartStatusInput.sendKeys(cartStatus);
    }

    getCartStatusInput = function () {
        return this.cartStatusInput.getAttribute('value');
    }

    setSaveForLaterInput = function (saveForLater) {
        this.saveForLaterInput.sendKeys(saveForLater);
    }

    getSaveForLaterInput = function () {
        return this.saveForLaterInput.getAttribute('value');
    }

    cartSelectLastOption = function () {
        this.cartSelect.all(by.tagName('option')).last().click();
    }

    cartSelectOption = function (option) {
        this.cartSelect.sendKeys(option);
    }

    getCartSelect = function () {
        return this.cartSelect;
    }

    getCartSelectedOption = function () {
        return this.cartSelect.element(by.css('option:checked')).getText();
    }

    cartItemSelectLastOption = function () {
        this.cartItemSelect.all(by.tagName('option')).last().click();
    }

    cartItemSelectOption = function (option) {
        this.cartItemSelect.sendKeys(option);
    }

    getCartItemSelect = function () {
        return this.cartItemSelect;
    }

    getCartItemSelectedOption = function () {
        return this.cartItemSelect.element(by.css('option:checked')).getText();
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
