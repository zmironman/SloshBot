create table IngredientType
(
	id int not null auto_increment primary key,
	name nvarchar(100) not null,
	createdBy nvarchar(100) not null,
	createdDate datetime not null,
	modifiedBy nvarchar(100) null,
	modifiedDate datetime null
);

create table Ingredient
(
	id int not null auto_increment primary key,
	name nvarchar(100) not null,
	ingredientTypeID int not null,
	constraint `Ingredient_fk_ingredient_type`
        foreign key (ingredientTypeID) references IngredientType(id)
        on delete restrict
        on update cascade,
	alcoholContent int not null,
	price int,
	createdBy nvarchar(100) not null,
	createdDate datetime not null,
	modifiedBy nvarchar(100) null,
	modifiedDate datetime null
);

create table DrinkType(
    id int not null auto_increment primary key,
    name nvarchar(100) not null,
    createdBy nvarchar(100) not null,
    createdDate datetime not null,
	modifiedBy nvarchar(100) null,
	modifiedDate datetime null
);

create table DefaultIngredients(
    id int not null auto_increment primary key,
    drinkTypeID int not null,
    constraint `DefaultIngredients_fk_drink_type`
        foreign key (drinkTypeID) references DrinkType(id)
        on delete restrict
        on update cascade,
    IngredientTypeID int not null,
    constraint `DefaultIngredients_fk_ingredient_type`
        foreign key (ingredientTypeID) references IngredientType(id)
        on delete restrict
        on update cascade,
    amount int not null,
    createdBy nvarchar(100) not null,
    createdDate datetime not null,
	modifiedBy nvarchar(100) null,
	modifiedDate datetime null
);

create table Recipe(
    id int not null auto_increment primary key,
    name nvarchar(100) not null,
    drinkTypeID int not null,
    constraint `Recipe_fk_drink_type`
        foreign key (drinkTypeID) references DrinkType(id)
        on delete restrict
        on update cascade,
    featured bool,
    createdBy nvarchar(100) not null,
    createdDate datetime not null,
	modifiedBy nvarchar(100) null,
	modifiedDate datetime null
);

create table RecipeIngredients(
    id int not null auto_increment primary key,
    recipeID int not null,
    constraint `RecipeIngredients_fk_recipe_id`
        foreign key (recipeID) references Recipe(id)
        on delete restrict
        on update cascade,
    IngredientID int not null,
    constraint `RecipeIngredients_fk_ingredient_id`
        foreign key (ingredientID) references Ingredient(id)
        on delete restrict
        on update cascade,
    amount int not null,
    createdBy nvarchar(100) not null,
    createdDate datetime not null,
	modifiedBy nvarchar(100) null,
	modifiedDate datetime null
);

create table ClearanceLevel(
    id int not null primary key,
    role nvarchar(100) not null,
    createdBy nvarchar(100) not null,
    createdDate datetime not null,
    modifiedBy nvarchar(100) null,
    modifiedDate datetime null
);

create table User(
    id int not null auto_increment primary key,
    name nvarchar(100) not null,
    username nvarchar(100) not null unique,
    password nvarchar(100) not null,
    email nvarchar(100) not null unique,
    clearanceLevel int default 0 not null,
    constraint `User_fk_clearance_level`
        foreign key (clearanceLevel) references ClearanceLevel(id)
        on delete restrict
        on update cascade,
    createdDate datetime not null,
	modifiedBy nvarchar(100) null,
	modifiedDate datetime null
);

create table SloshBot(
    id int not null auto_increment primary key,
    name nvarchar(100) not null,
    ownerID int not null,
    constraint `SloshBot_fk_owner_id`
        foreign key (ownerID) references User(id)
        on delete restrict
        on update cascade,
    createdBy nvarchar(100) not null,
    createdDate datetime not null,
	modifiedBy nvarchar(100) null,
	modifiedDate datetime null
);

create table Optic(
    id int not null auto_increment primary key,
    ingredientID int not null,
    constraint `Optic_fk_ingredient_id`
        foreign key (ingredientID) references Ingredient(id)
        on delete restrict
        on update cascade,
    distanceFromHome int not null,
    remainingLiquid int not null,
    pinNumber int not null,
    sloshbotID int not null,
    constraint `Optic_fk_sloshbot_id`
        foreign key (sloshbotID) references SloshBot(id)
        on delete restrict
        on update cascade,
    createdBy nvarchar(100) not null,
    createdDate datetime not null,
	modifiedBy nvarchar(100) null,
	modifiedDate datetime null
);

create table OrderHistory(
    id int not null auto_increment primary key,
    recipeID int not null,
    constraint `OrderHistory_fk_recipe_id`
        foreign key (recipeID) references Recipe(id)
        on delete restrict
        on update cascade,
    userID int not null,
    constraint `OrderHistory_fk_user_id`
        foreign key (userID) references User(id)
        on delete restrict
        on update cascade,
    sloshbotID int not null,
    constraint `OrderHistory_fk_sloshbot_id`
        foreign key (sloshbotID) references SloshBot(id)
        on delete restrict
        on update cascade,
    createdDate datetime not null
);

insert into clearancelevel (id, role, createdBy, createdDate) values
(0, 'GUEST', 'Admin', now()),
(1, 'USER', 'Admin', now()),
(2, 'MODERATOR', 'Admin', now()),
(3, 'ADMIN', 'Admin', now()),
(4, 'SUPERUSER', 'Admin', now());
