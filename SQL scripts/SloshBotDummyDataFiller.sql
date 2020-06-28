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

insert into ingredient (id, name, ingredientTypeID, alcoholContent, price, imageUrl, createdBy, createdDate) values
    (1, 'Maker\'s Mark Kentucky Straight Bourbon Whiskey', 1, 45, 25, 'https://products0.imgix.drizly.com/ci-makers-mark-bourbon-whisky-8993a9352bde4e36.jpeg?auto=format%2Ccompress&fm=jpg&q=20','Admin', now()),
    (2, 'Tito\'s Handmade Vodka', 2, 40, 25, 'https://dydza6t6xitx6.cloudfront.net/ci-titos-handmade-vodka-35af185a6e862599.png','Admin', now()),
    (3, 'Coca Cola', 3, 0, 2, 'https://www.newyorkbeverage.com/wp-content/uploads/2017/09/Coke-Coke-2-Liter.jpg','Admin', now()),
    (4, 'Minute Maid Orange Juice', 4, 0, 5, 'https://www.minutemaid.com/content/dam/minutemaidus/Products/orange-juice/premium-original-oj/Minute-Maid_Orange-Juice_Original-Low-Pulp_59oz.png','Admin', now());

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

insert into clearancelevel (id, role, createdBy, createdDate) values
(0, 'GUEST', 'Admin', now()),
(1, 'USER', 'Admin', now()),
(2, 'MODERATOR', 'Admin', now()),
(3, 'ADMIN', 'Admin', now()),
(4, 'SUPERUSER', 'Admin', now());

INSERT INTO user (id, name, username, password, email, clearanceLevel, createdDate) VALUES
    (1, 'Test SuperUser', 'test', '$2a$10$pDlsGdOf8NvvLPPvCUgK/u13q5I2.qN3GX0S.2uUig0Dq0FDM9Jmm', 'test@test.com', 4, now()),
    (2, 'Test Admin', 'testAdmin', '$2a$10$YWHEgPbdKpRfDO/nxYganOcgj5YZnRczbRl0xqPi.mDmGtWtQFUqK', 'test4@test.com', 3, now()),
    (4, 'Test User', 'testUser', '$2a$10$d/5UCce8uf7ip1oeOUf1QOmp22pIwHWUQ0yTljWJJ/YVESjEV.uUm', 'test2@test.com', 1, now()),
    (6, 'Test Moderator', 'testModerator', '$2a$10$41nIzbisbbjKjD2J4fNJa.AAnOoWvdNrNEs25CoulDbz1MJKv94/m', 'test3@test.com', 2, now());

insert into sloshbot (id, name, ownerID, createdBy, createdDate) value
    (1, 'TestBot', 1, 'Admin', now());

insert into optic (id, ingredientID, distanceFromHome, remainingLiquid, pinNumber, sloshbotID, createdBy, createdDate) values
    (1, 1, 50, 25, 17, 1, 'Admin', now()),
    (2, 2, 150, 25, 12, 1, 'Admin', now()),
    (3, 3, 250, 67, 9, 1, 'Admin', now()),
    (4, 4, 350, 67, 14, 1, 'Admin', now());
