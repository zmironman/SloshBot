delete from orderhistory where id > 0;
delete from optic where id > 0;
delete from sloshbot where id > 0;
delete from recipeingredients where id > 0;
delete from ingredient where id > 0;
delete from recipe where id > 0;
delete from defaultingredients where id > 0;
delete from drinktype where id > 0;
delete from ingredienttype where id > 0;

ALTER TABLE orderhistory AUTO_INCREMENT = 1;
ALTER TABLE optic AUTO_INCREMENT = 1;
ALTER TABLE sloshbot AUTO_INCREMENT = 1;
ALTER TABLE recipeingredients AUTO_INCREMENT = 1;
ALTER TABLE ingredient AUTO_INCREMENT = 1;
ALTER TABLE recipe AUTO_INCREMENT = 1;
ALTER TABLE defaultingredients AUTO_INCREMENT = 1;
ALTER TABLE drinktype AUTO_INCREMENT = 1;
ALTER TABLE ingredienttype AUTO_INCREMENT = 1;