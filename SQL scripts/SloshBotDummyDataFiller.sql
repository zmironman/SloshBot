# BEFORE RUNNING THIS SCRIPT
# MAKE SURE YOU HAVE AT LEAST 2 USERS BUILT WITH ID'S 1 AND 2

insert into ingredienttype (id, name, createdBy, createdDate) values
    (1, 'Whiskey', 'Admin', now()),
    (2, 'Vodka', 'Admin', now()),
    (3, 'Cola', 'Admin', now()),
    (4, 'Orange Juice', 'Admin', now());

insert into drinktype (id, name, createdBy, createdDate) values
    (1, 'Whiskey and Cola', 'Admin', now()),
    (2, 'Screwdriver', 'Admin', now()),
    (3, 'Manhattan', 'Admin', now()),
    (4, 'Margarita', 'Admin', now());

insert into defaultingredients (drinkTypeID, IngredientTypeID, amount, createdBy, createdDate) values
    (1, 1, 6, 'Admin', now()),
    (1, 3, 6,'Admin', now()),
    (2, 2, 3, 'Admin', now()),
    (2, 4, 9, 'Admin', now());

insert into ingredient (id, name, ingredientTypeID, alcoholContent, price, createdBy, createdDate) values
    (1, 'Maker\'s Mark Kentucky Straight Bourbon Whiskey', 1, 45, 25, 'Admin', now()),
    (2, 'Tito\'s Handmade Vodka', 2, 40, 25, 'Admin', now()),
    (3, 'Coca Cola', 3, 0, 2, 'Admin', now()),
    (4, 'Minute Maid Orange Juice', 4, 0, 5, 'Admin', now());

insert into recipe (id, name, drinkTypeID, featured, createdBy, createdDate) values
    (1, 'Zach\'s Black Tooth Grin', 1, true, 'Admin', now()),
    (2, 'Old School Whiskey and Coke', 1, false, 'Admin', now()),
    (3, 'Old School Screwdriver', 2, false, 'Admin', now()),
    (4, 'Noah\'s Hair of the Dog', 2, true, 'Admin', now());

insert into recipeingredients (id, recipeID, IngredientID, amount, createdBy, createdDate) values
    (1, 1, 1, 9, 'Admin', now()),
    (2, 1, 3, 3, 'Admin', now()),
    (3, 2, 1, 6, 'Admin', now()),
    (4, 2, 3, 6, 'Admin', now()),
    (5, 3, 2, 3, 'Admin', now()),
    (6, 3, 4, 9, 'Admin', now()),
    (7, 4, 2, 6, 'Admin', now()),
    (8, 4, 4, 6, 'Admin', now());

insert into sloshbot (id, name, ownerID, createdBy, createdDate) value
    (1, 'TestBot', 1, 'Admin', now());

insert into optic (id, ingredientID, distanceFromHome, remainingLiquid, pinNumber, sloshbotID, createdBy, createdDate) values
    (1, 1, 50, 25, 17, 1, 'Admin', now()),
    (2, 2, 150, 25, 12, 1, 'Admin', now()),
    (3, 3, 250, 67, 9, 1, 'Admin', now()),
    (4, 4, 350, 67, 14, 1, 'Admin', now());
