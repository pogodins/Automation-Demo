@run
Feature: Purchase disks in Music store

Scenario: Navigate and login to Music Store
	Given I navigate to the Music Store in 'firefox' browser
	When I try to login as admin
	Then I should not see any errors

Scenario Outline: Add albums to the cart
	Given '<genre>' genre exists in the list
	When I add to cart '<album>' album from '<genre>' genre
	Then Album should be added to the cart
	
	Examples:
	|	album	|	genre	|
	|	random	|	Rock	|
	|	3		|	Latin	|
	|	4		|	Latin	|

Scenario: Navigate to main page
	Given Music Store application is opened
	When I click on main banner
	Then I should see home page

Scenario: Check cart
	Given Music Store application is opened
	When I navigate to cart
	Then I should see 'Review your cart:' page header
		And Total sum should be correct

Scenario: Remove album from the cart
	Given Cart page is opened
	When I remove first album from the cart
	Then Album should be removed from the list

Scenario: Proceed to checkout
	Given Cart page is opened
	When I click 'Checkout' button
	Then I should see 'Address And Payment' page header

Scenario: Submit order
	Given Address And Payment page is opened
	When I submit order with shipping details:
		| First Name| Last Name	| Address			|	City	|	State	|Postal Code|	Country	|	Phone	|	Email		|Promo Code	|	
		|	Sergey	|	Pogodin	|46 Dalnytska st.	|	Odessa	|	Odessa	|	65005	|	Ukraine	|1111111111	|test@lohika.com|	FREE	|
	Then I should see 'Checkout Complete' page header
		And Order number should be displayed